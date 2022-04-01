package com.ruoyi.netty.server.serviceImpl;

import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.common.utils.uuid.IdUtils;
import com.ruoyi.netty.util.SMSUtil;
import com.ruoyi.netty.util.keyUtil;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.service.*;
import com.ruoyi.netty.common.protobuf.MessageProtocol.MessageBase;
import io.netty.channel.ChannelHandlerContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class AuthServiceImpl {

    private static final Logger log = LoggerFactory.getLogger(AuthServiceImpl.class);

    @Autowired
    private RedisCache redisCache;

    @Autowired
    ITbUserInfoService iTbUserInfoService;

    public void login(ChannelHandlerContext ctx, MessageBase msgBase) {
        MessageBase.Builder authMsg = MessageBase.newBuilder();
        authMsg.setClientId(ctx.channel().id().toString());
        authMsg.setType(MessageBase.Type.LOGIN_RESP);
        String phoneNumber = msgBase.getLoginReq().getPhoneNumber();
        if (StringUtils.isNotBlank(phoneNumber)) {
            String code = msgBase.getLoginReq().getCode();
            String token = msgBase.getLoginReq().getToken();
            if (StringUtils.isNotBlank(code)) {//验证码登录，校验验证码
                String cacheCode = redisCache.getCacheObject(Constants.MSG_CACHE_PREFIX + phoneNumber);
                if (StringUtils.isNotBlank(cacheCode) && cacheCode.equals(code)) {
                    TbUserInfo tbUserInfo = iTbUserInfoService.selectTbUserInfoByPhoneNumber(phoneNumber);
                    if (tbUserInfo == null) {
                        tbUserInfo = new TbUserInfo();
                        tbUserInfo.setPhoneNumber(phoneNumber);
                        String number = "";
                        for (int n = 0; n < 4; n++) {
                            number += (int) (10 * (Math.random()));
                        }
                        tbUserInfo.setUserNickname("点指_" + number);
                        tbUserInfo.setHeadImg("/upload/2021/11/05/e00adef7fbca40fbb52734fa6386004c.png");

                        int count = iTbUserInfoService.insertTbUserInfo(tbUserInfo);
                    }
                }
                TbUserInfo tbUserInfo = iTbUserInfoService.selectTbUserInfoByPhoneNumber(phoneNumber);
                if (tbUserInfo != null) {
                    String userToken = IdUtils.fastSimpleUUID();
                    redisCache.setCacheObject(Constants.USER_TOKEN_KEY + userToken, phoneNumber, 7, TimeUnit.DAYS);
                    authMsg.setLoginResp(MessageBase.LoginResp.newBuilder()
                            .setId(tbUserInfo.getId())
                            .setPhoneNumber(tbUserInfo.getPhoneNumber())
                            .setHeadImg(tbUserInfo.getHeadImg())
                            .setUserNickname(tbUserInfo.getUserNickname())
                            .setToken(userToken));
                    this.send(ctx, authMsg, 200);
                    return;
                }
            }else if(StringUtils.isNotBlank(token)){//token登录

                String tokenPhoneNumber = redisCache.getCacheObject(Constants.USER_TOKEN_KEY + token);
                if(StringUtils.isBlank(tokenPhoneNumber)){
                    this.send(ctx,authMsg,302);
                    return;
                }

                TbUserInfo tbUserInfo = iTbUserInfoService.selectTbUserInfoByPhoneNumber(tokenPhoneNumber);
                if (tbUserInfo != null) {
                    authMsg.setLoginResp(MessageBase.LoginResp.newBuilder()
                            .setId(tbUserInfo.getId())
                            .setPhoneNumber(tbUserInfo.getPhoneNumber())
                            .setHeadImg(tbUserInfo.getHeadImg())
                            .setUserNickname(tbUserInfo.getUserNickname())
                            .setToken(token));
                    this.send(ctx, authMsg, 200);
                    return;
                }
            }
        }
        this.send(ctx, authMsg, 500);
    }


    public void sendMsg(ChannelHandlerContext ctx, MessageBase msgBase) {

        MessageBase.Builder authMsg= MessageBase.newBuilder();
        authMsg.setType(MessageBase.Type.SEND_MSG_RESP);
        String phoneNumber = msgBase.getSendMsgReq().getPhoneNumber();
        if(StringUtils.isNotBlank(phoneNumber)){
            //随机生成四位验证码的工具类
            String code = keyUtil.keyUtils();
            log.info("手机号：{}，发送验证码：{}", phoneNumber, code);
            redisCache.setCacheObject(Constants.MSG_CACHE_PREFIX + phoneNumber, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
            String result = new SMSUtil().sendSMS(phoneNumber,code);//发送短信验证码
            this.send(ctx,authMsg,200);
            return;
        }
        this.send(ctx,authMsg,500);

    }


    public void send(ChannelHandlerContext ctx,MessageBase.Builder builder,int code) {
        builder.setClientId(ctx.channel().id().toString());
        builder.setCode(code);
        ctx.writeAndFlush(builder.build().toByteArray());
    }
}
