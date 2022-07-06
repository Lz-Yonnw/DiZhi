package com.ruoyi.web.controller.system;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.netty.common.protobuf.MessageProtocol;
import com.ruoyi.netty.server.entity.SuccessCallBackObj;
import com.ruoyi.netty.server.handler.AuthServerHandler;
import com.ruoyi.netty.util.PayUtil;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;

/**
 * 支付回调通知接口
 */
@Controller
public class PayNotifyController {

    @Autowired
    private AuthServerHandler authServerHandler;

    @Autowired
    private RedisCache redisCache;


    //支付通知
    @PostMapping(value = "/wxPay/notify")
    public void wxPay(@RequestBody SuccessCallBackObj successCallBackObj, HttpServletRequest request){
        //TODO 支付验证签名

        MessageProtocol.MessageBase.Builder authMsg= MessageProtocol.MessageBase.newBuilder();
        MessageProtocol.MessageBase.WxpayStatusResp.Builder wxpayStatusResp = MessageProtocol.MessageBase.WxpayStatusResp.newBuilder();

        //解密后的body
        String resource = PayUtil.decryptToString(successCallBackObj);
        try {
            if(resource==null){
                return;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(resource);
            //订单号
            String out_trade_no = jsonNode.get("out_trade_no").toString();

            //修改订单状态
//            TbOrderInfo tbOrderInfo = new TbOrderInfo();
//            tbOrderInfo.setOrderSn(out_trade_no);
//            tbOrderInfo.setStatus(9);
//            tbOrderInfo.setPayTime(new Date());
//            tbOrderInfoService.updateTbOrderInfoByOrderSn(tbOrderInfo);

            //当前下单客户端
            ChannelHandlerContext channelHandlerContext = AuthServerHandler.ctxMap.get(out_trade_no);
            //响应微信支付成功通知
            wxpayStatusResp.setOutRefundNo(jsonNode.get("transaction_id").toString());
            wxpayStatusResp.setPayertotal(jsonNode.get("amount").get("payer_total").toString());
            wxpayStatusResp.setTradestate(1);
            wxpayStatusResp.setSuccesstime(jsonNode.get("success_time").toString());
            authMsg.setWxpayStatusResp(wxpayStatusResp);
            authMsg.setClientId(channelHandlerContext.channel().id().toString());//拿到ClientId
            authMsg.setType(MessageProtocol.MessageBase.Type.WXPAY_STATUS_RESP);
            //推送消息至下单客户端
            authServerHandler.send(channelHandlerContext,authMsg,200);
            //删除缓存订单号
            boolean b = redisCache.deleteObject(out_trade_no);
            return;
        }catch (Exception e){
            e.printStackTrace();
        }
        return;
    }

    //退款通知
    @PostMapping(value = "/wxPay/refunds")
    public void refunds(@RequestBody SuccessCallBackObj successCallBackObj, HttpServletRequest request){
        //TODO 支付验证签名
        MessageProtocol.MessageBase.Builder authMsg= MessageProtocol.MessageBase.newBuilder();
        MessageProtocol.MessageBase.WxrefundStatusResp.Builder wxrefundStatusResp = MessageProtocol.MessageBase.WxrefundStatusResp.newBuilder();

        //解密后的body
        String resource = PayUtil.decryptToString(successCallBackObj);
        try {
            if(request!=null){
                return;
            }
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(resource);
            //商户订单号
            String out_trade_no = jsonNode.get("out_trade_no").toString();
            //修改订单状态
//            TbOrderInfo tbOrderInfo = new TbOrderInfo();
//            tbOrderInfo.setOrderSn(out_trade_no);
//            tbOrderInfo.setStatus(4);
//            tbOrderInfo.setUpdateTime(new Date());
//            tbOrderInfoService.updateTbOrderInfoByOrderSn(tbOrderInfo);

            //当前退款客户端
            ChannelHandlerContext channelHandlerContext = AuthServerHandler.ctxMap.get(out_trade_no);
            //响应微信支付成功通知
            wxrefundStatusResp.setOutRefundNo(jsonNode.get("out_refund_no").toString());
            wxrefundStatusResp.setRefundId(jsonNode.get("refund_id").toString());
            wxrefundStatusResp.setRefundStatus(1);
            wxrefundStatusResp.setSuccessTime(jsonNode.get("success_time").toString());
            wxrefundStatusResp.setUserReceivedAccount(jsonNode.get("user_received_account").toString());
            wxrefundStatusResp.setRefund(jsonNode.get("amount").get("refund").toString());
            authMsg.setWxrefundStatusResp(wxrefundStatusResp);
            authMsg.setClientId(channelHandlerContext.channel().id().toString());//拿到ClientId
            authMsg.setType(MessageProtocol.MessageBase.Type.WXREFUND_STATUS_RESP);
            //推送消息至下单客户端
            authServerHandler.send(channelHandlerContext,authMsg,200);
            //删除缓存订单号
            boolean b = redisCache.deleteObject(out_trade_no);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
