package com.ruoyi.netty.util;

import com.alibaba.fastjson.JSONObject;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradeRefundRequest;
import com.alipay.api.request.AlipayTradeWapPayRequest;
import com.alipay.api.response.AlipayTradeRefundResponse;
import com.ruoyi.netty.server.entity.AliPayConstants;
import org.springframework.beans.factory.annotation.Value;

/**
 * 支付宝支付工具类
 */
public class AliPayUtil {
    @Value("${alipay.notify_url}")
    private String notifyUrl;

    @Value("${alipay.return_url}")
    private String returnUrl;


    /**
     * 支付宝支付
     * @param out_trade_no
     * @param subject
     * @param total_amount
     * @return
     */
    public String requestH5Partnerid(String out_trade_no,String subject,String total_amount){

        AlipayClient client = new DefaultAlipayClient(AliPayConstants.url, AliPayConstants.APPID, AliPayConstants.RSA_PRIVATE_KEY, AliPayConstants.FORMAT, AliPayConstants.CHARSET, AliPayConstants.ALIPAY_PUBLIC_KEY,AliPayConstants.SIGNTYPE);


        // 相应请求API的request
        AlipayTradeWapPayRequest alipayRequest = new AlipayTradeWapPayRequest();
        // 在公共参数中设置回跳和通知地址
        alipayRequest.setReturnUrl(returnUrl);
        alipayRequest.setNotifyUrl(notifyUrl);

        // 封装商品信息
        JSONObject bizContent = new JSONObject();
        bizContent.put("out_trade_no", out_trade_no);
        bizContent.put("total_amount", total_amount);
        bizContent.put("subject", subject);
        // 沙箱默认值为：QUICK_WAP_WAY
        bizContent.put("product_code", "QUICK_WAP_WAY");


        alipayRequest.setBizContent(bizContent.toString());

        //调用SDK生成表单
        String form = "";
        try {
            form = client.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        return form;
    }

    public String alirefund(String out_trade_no,String refund_amount) throws AlipayApiException {
        AlipayClient client = new DefaultAlipayClient("https://alipay.trade.refund", AliPayConstants.APPID, AliPayConstants.RSA_PRIVATE_KEY, AliPayConstants.FORMAT, AliPayConstants.CHARSET, AliPayConstants.ALIPAY_PUBLIC_KEY,AliPayConstants.SIGNTYPE);

        AlipayTradeRefundRequest request = new AlipayTradeRefundRequest();

        JSONObject bizContent = new JSONObject();
        bizContent.put("trade_no", "2021081722001419121412730660");
        bizContent.put("refund_amount", 0.01);
        bizContent.put("out_request_no", "HZ01RF001");
        request.setBizContent(bizContent.toString());
        AlipayTradeRefundResponse response = client.execute(request);
        if(response.isSuccess()){
            System.out.println("调用成功");
        } else {
            System.out.println("调用失败");
        }
        return "";
    }

}
