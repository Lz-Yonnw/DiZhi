package com.ruoyi.netty.server.entity;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;

public class PayClass {
    private static CloseableHttpClient httpClient;

    private static CertificatesManager certificatesManager;

    private static Verifier verifier;
    public static void main(String[] args) throws Exception {
        String result = requestPartnerid(System.currentTimeMillis() + "", 100, "真的很好");
        System.out.println(result);
    }

    public static String requestPartnerid(String orderSn, int total, String description) throws Exception {
        PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(new ByteArrayInputStream(PayConstants.privateKey.getBytes("utf-8")));
        // 获取证书管理器实例
        certificatesManager = CertificatesManager.getInstance();
        // 向证书管理器增加需要自动更新平台证书的商户信息
        certificatesManager.putMerchant(PayConstants.MCH_ID, new WechatPay2Credentials(PayConstants.MCH_ID,
                        new PrivateKeySigner(PayConstants.MCH_SERIAL_NO, merchantPrivateKey)),
                PayConstants.API_3KEY.getBytes(StandardCharsets.UTF_8));
        // 从证书管理器中获取verifier
        verifier = certificatesManager.getVerifier(PayConstants.MCH_ID);
        httpClient = WechatPayHttpClientBuilder.create()
                .withMerchant(PayConstants.MCH_ID, PayConstants.MCH_SERIAL_NO, merchantPrivateKey)
                .withValidator(new WechatPay2Validator(certificatesManager.getVerifier(PayConstants.MCH_ID)))
                .build();

        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/h5");
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type","application/json; charset=utf-8");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("mchid", PayConstants.MCH_ID)
                .put("appid", PayConstants.APP_ID)
                .put("notify_url", PayConstants.NOTIFY_URL)
                .put("description", description)
                .put("out_trade_no", orderSn);
        rootNode.putObject("amount")
                .put("total", total)
                .put("currency","CNY");
        rootNode.putObject("scene_info")
                .put("payer_client_ip","14.23.150.211")
                .putObject("h5_info")
                .put("type","Android");
        try {
            objectMapper.writeValue(bos, rootNode);
            httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            String bodyAsString = EntityUtils.toString(response.getEntity());
            return bodyAsString;
        }catch (Exception e){

        }
        return "";
    }
}
