package com.ruoyi.netty.util;


import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ruoyi.netty.server.entity.PayConstants;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.*;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import com.wechat.pay.contrib.apache.httpclient.util.RsaCryptoUtil;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.JsonNode;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.HashMap;


@Component
public class PayUtil {
    private CloseableHttpClient httpClient;

    private CertificatesManager certificatesManager;

    private Verifier verifier;

    /**
     * App下单
     * @param total
     * @param description
     * @return 预支付ID
     * @throws Exception
     */
    public String requestPartnerid(String orderSn,int total,String description) throws Exception {
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

        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/app");
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

    /**
     * App调起微信支付
     * @param prepayid
     * @return
     */
    public HashMap createSign(String prepayid){
        HashMap<String,String> map = new HashMap<>();
        StringBuffer stringBuffer = new StringBuffer();

        //APP支付调起微信客户端
        String timestamp = System.currentTimeMillis()+"";

        String nonce = RandomUtil.randomString(32);
        try {
            //应用id
            stringBuffer.append(PayConstants.APP_ID).append("\n");
            //时间戳
            stringBuffer.append(timestamp).append("\n");
            //随机字符串
            stringBuffer.append(nonce).append("\n");
            //预支付交易会话ID
            stringBuffer.append(prepayid).append("\n");
            //获取签名
            String chiphertext = RsaCryptoUtil.encryptOAEP(stringBuffer.toString(),verifier.getValidCertificate());


            map.put("appid",PayConstants.APP_ID);
            map.put("partnerid",PayConstants.MCH_ID);
            map.put("noncestr",RandomUtil.randomString(32));
            map.put("timestamp",System.currentTimeMillis()+"");
            map.put("sign",chiphertext);
            JSONObject jsonObject = JSONObject.parseObject(prepayid);
            map.put("prepayid",jsonObject.getString("prepay_id"));
            map.put("package",PayConstants.PACKAGE);
            return map;
        }catch (Exception e){
            e.printStackTrace();
        }
        return map;
    }

    /**
     * 关闭订单
     * @param orderSn 商户订单号
     */
    public void CloseTheTrder(String orderSn){
        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/"+orderSn+"/close");
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type","application/json; charset=utf-8");
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("mchid", PayConstants.MCH_ID);

        try {
            objectMapper.writeValue(bos,rootNode);
            httpPost.setEntity(new StringEntity(bos.toString("utf-8"),"utf-8"));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            EntityUtils.toString(response.getEntity());
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 验证签名
     * @param serial
     * @param message
     * @param signatrue
     * @return
     */
    public static boolean signVerify(String serial,String message,String signatrue){
        CertificatesManager certificatesManager = null;

        Verifier verifier = null;
        try {
            PrivateKey merchantPrivateKey = PemUtil.loadPrivateKey(new ByteArrayInputStream(PayConstants.privateKey.getBytes("utf-8")));
            // 获取证书管理器实例
            certificatesManager = CertificatesManager.getInstance();
            // 向证书管理器增加需要自动更新平台证书的商户信息
            certificatesManager.putMerchant(PayConstants.MCH_ID, new WechatPay2Credentials(PayConstants.MCH_ID,
                            new PrivateKeySigner(PayConstants.MCH_SERIAL_NO, merchantPrivateKey)),
                    PayConstants.API_3KEY.getBytes(StandardCharsets.UTF_8));

            return verifier.verify(serial,message.getBytes("utf-8"),signatrue);
        }catch (Exception e){

        }
        return false;
    }

    /**
     * 回调通知解密
     * @param body
     * @return
     */
    public static String decryptToString(String body){
        try {
            AesUtil aesUtil = new AesUtil(PayConstants.API_3KEY.getBytes("utf-8"));
            org.codehaus.jackson.map.ObjectMapper objectMapper = new org.codehaus.jackson.map.ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(body);
            JsonNode resource = jsonNode.get("resource");
            String ciphertext = resource.get("ciphertext").toString();
            String associated_data = resource.get("associated_data").toString();
            String nonce = resource.get("nonce").toString();
            return aesUtil.decryptToString(associated_data.getBytes("utf-8"), nonce.getBytes("utf-8"), ciphertext);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }
}
