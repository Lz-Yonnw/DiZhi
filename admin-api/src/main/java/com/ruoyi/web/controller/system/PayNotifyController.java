package com.ruoyi.web.controller.system;


import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.netty.common.protobuf.MessageProtocol;
import com.ruoyi.netty.server.entity.Domain;
import com.ruoyi.netty.server.entity.SuccessCallBackObj;
import com.ruoyi.netty.server.handler.AuthServerHandler;
import com.ruoyi.netty.util.PayUtil;
import com.ruoyi.system.domain.TbOrder;
import com.ruoyi.system.service.ITbOrderService;
import io.netty.channel.ChannelHandlerContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Date;

/**
 * 支付回调通知接口
 */
@Controller
public class PayNotifyController {

    @Autowired
    private AuthServerHandler authServerHandler;

    @Autowired
    private RedisCache redisCache;

    @Autowired
    private ITbOrderService tbOrderService;

    //支付通知
    @PostMapping(value = "/wxPay/notify")
    public void wxPay(@RequestBody SuccessCallBackObj successCallBackObj, HttpServletRequest request){
        //TODO 支付验证签名

        MessageProtocol.MessageBase.Builder authMsg= MessageProtocol.MessageBase.newBuilder();
        MessageProtocol.MessageBase.WxpayStatusResp.Builder wxpayStatusResp = MessageProtocol.MessageBase.WxpayStatusResp.newBuilder();

        //解密后的body
        String resource = PayUtil.decryptToString(successCallBackObj);
        try {
            JSONObject jsonObject = JSONObject.parseObject(resource);
            String out_trade_no = jsonObject.getString("out_trade_no");
            System.out.println(out_trade_no);

            if(Domain.ctxMap.containsKey(out_trade_no)){
                //获取订单客户端
                ChannelHandlerContext channelHandlerContext = Domain.ctxMap.get(out_trade_no);
                //响应微信支付成功通知
                wxpayStatusResp.setOutRefundNo(jsonObject.get("transaction_id").toString());
                wxpayStatusResp.setPayertotal(jsonObject.getJSONObject("amount").get("payer_total").toString());
                wxpayStatusResp.setTradestate(1);
                wxpayStatusResp.setSuccesstime(jsonObject.get("success_time").toString());
                authMsg.setWxpayStatusResp(wxpayStatusResp);
                authMsg.setClientId(channelHandlerContext.channel().id().toString());//拿到ClientId
                authMsg.setType(MessageProtocol.MessageBase.Type.WXPAY_STATUS_RESP);
                //推送消息至下单客户端
                authServerHandler.send(channelHandlerContext,authMsg,200);

                //修改订单状态
                TbOrder tbOrder = new TbOrder();
                tbOrder.setOrderSn(out_trade_no);
                tbOrder.setStatus(3);
                tbOrder.setPayType(1);
                tbOrder.setPayTime(new Date());
                tbOrder.setPayAccount(jsonObject.getJSONObject("payer").get("openid").toString());
                int count = tbOrderService.updateTbOrderByOrderSn(tbOrder);
                if(count>0){
                    //支付成功  删除改用户
                    Domain.ctxMap.remove(out_trade_no);
                }
                return;
            }
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
            JSONObject jsonObject = JSONObject.parseObject(resource);
            //商户订单号
            String out_trade_no = jsonObject.getString("out_trade_no");
            if(Domain.ctxMap.containsKey(out_trade_no)){
                //当前退款客户端
                ChannelHandlerContext channelHandlerContext = Domain.refundMap.get(out_trade_no);
                //响应微信支付成功通知
                wxrefundStatusResp.setOutRefundNo(jsonObject.get("out_refund_no").toString());
                wxrefundStatusResp.setRefundId(jsonObject.get("refund_id").toString());
                wxrefundStatusResp.setRefundStatus(1);
                wxrefundStatusResp.setSuccessTime(jsonObject.get("success_time").toString());
                wxrefundStatusResp.setUserReceivedAccount(jsonObject.get("user_received_account").toString());
                wxrefundStatusResp.setRefund(jsonObject.getJSONObject("amount").get("refund").toString());
                authMsg.setWxrefundStatusResp(wxrefundStatusResp);
                authMsg.setClientId(channelHandlerContext.channel().id().toString());//拿到ClientId
                authMsg.setType(MessageProtocol.MessageBase.Type.WXREFUND_STATUS_RESP);
                //推送消息至下单客户端
                authServerHandler.send(channelHandlerContext,authMsg,200);
                //修改订单状态
                TbOrder tbOrder = new TbOrder();
                tbOrder.setOrderSn(out_trade_no);
                tbOrder.setStatus(10);
                tbOrder.setRefundTime(new Date());
                int count = tbOrderService.updateTbOrderByOrderSn(tbOrder);
                if(count>0){
                    //退单 成功  删除改用户
                    Domain.refundMap.remove(out_trade_no);
                }
                return;
            }
            return;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
