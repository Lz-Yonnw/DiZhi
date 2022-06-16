package com.ruoyi.web.controller.system;


import com.alibaba.fastjson.JSONObject;
import com.ruoyi.netty.common.protobuf.MessageProtocol;
import com.ruoyi.netty.server.handler.AuthServerHandler;
import io.netty.channel.ChannelHandlerContext;
import org.codehaus.jackson.JsonNode;
import org.codehaus.jackson.map.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;

@Controller
public class PayNotifyController {

    @Autowired
    private AuthServerHandler authServerHandler;

    @PostMapping(value = "/wxPay")
    public void wxPay(@RequestBody JSONObject jsonObject, HttpServletRequest request){
        System.out.println(jsonObject);

        StringBuilder builder = new StringBuilder();
        try {
            BufferedReader reader = request.getReader();
            String str;
            while ((str = reader.readLine())!=null){
                builder.append(str);
            }
            reader.close();

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(builder.toString());
            //商户订单号
            String out_trade_no = jsonNode.get("out_trade_no").toString();


            ChannelHandlerContext channelHandlerContext = authServerHandler.ctxMap.get(out_trade_no);
            MessageProtocol.MessageBase.Builder authMsg= MessageProtocol.MessageBase.newBuilder();
            //响应微信支付成功通知
            MessageProtocol.MessageBase.WxpaySuccessResp.Builder wxpaySuccessRespbuilder = MessageProtocol.MessageBase.WxpaySuccessResp.newBuilder();
            authMsg.setWxpaySuccessResp(wxpaySuccessRespbuilder);
            //推送消息给客户端
            authServerHandler.send(channelHandlerContext,authMsg,200);
        }catch (Exception e){
            e.printStackTrace();
        }
    }


}
