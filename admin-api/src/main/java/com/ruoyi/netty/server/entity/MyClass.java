package com.ruoyi.netty.server.entity;


import com.ruoyi.framework.web.exception.GlobalExceptionHandler;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.AutoUpdateCertificatesVerifier;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.node.ObjectNode;
import org.json.JSONException;
import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.Signature;
import java.security.SignatureException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MyClass   {

    private static final Logger log = LoggerFactory.getLogger(MyClass.class);

    public static void main(String[] args) {
        requestPrepayId();
    }
    public static String requestPrepayId() {

        if (merchantPrivateKey == null) {
            try {
                merchantPrivateKey = PemUtil
                        .loadPrivateKey(new ByteArrayInputStream(privateKey.getBytes("utf-8")));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        if (verifier == null) {
            try {
                verifier = new AutoUpdateCertificatesVerifier(
                        new WechatPay2Credentials(mchid, new PrivateKeySigner(mchSerialNo, merchantPrivateKey)),apiV3Key.getBytes("utf-8"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }

        if (httpClient == null) {
            httpClient = WechatPayHttpClientBuilder.create()
                    .withMerchant(mchid, mchSerialNo, merchantPrivateKey)
                    .withValidator(new WechatPay2Validator(verifier)).build();
        }


        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("mchid",mchid);
        rootNode.put("appid", appid);
        rootNode.put("notify_url", notify_url);
        SimpleDateFormat sdf=new SimpleDateFormat("yyyyMMddHHmmss");
        String newDate=sdf.format(new Date());
        String result="";
        Random random=new Random();
        for(int i=0;i<3;i++){
            result+=random.nextInt(10);
        }
        String orderNum=newDate+result;

        rootNode.put("description", "Image形象店-深圳腾大-QQ公仔");
        rootNode.put("out_trade_no", orderNum);

        rootNode.putObject("amount")
                .put("total", 1);

        String bodyAsString = "";

        try {
            objectMapper.writeValue(bos, rootNode);

            HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/app");
            httpPost.addHeader("Accept", "application/json");
            httpPost.addHeader("Content-type","application/json; charset=utf-8");
            httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));

            CloseableHttpResponse response = httpClient.execute(httpPost);

            bodyAsString = EntityUtils.toString(response.getEntity());
        } catch (IOException e){

        }


        JSONObject jsonObject = null;

        try {
            System.out.println(new JSONObject(bodyAsString));
            System.out.println(new JSONObject(bodyAsString).get("prepay_id"));
            String s = new JSONObject(bodyAsString).get("prepay_id").toString();
            return s;
        } catch (JSONException e) {
        }
        return "";
    }

    public static HashMap createSign(String prepayId) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException {
        String nonceStr = UUID.randomUUID().toString().
                replaceAll("-","").substring(0,32);
        long timeStamp = System.currentTimeMillis() / 1000;;
        HashMap map = new HashMap();
        map.put("partnerId",mchid);
        map.put("prepayId",prepayId);
        map.put("nonceStr",nonceStr);
        map.put("timeStamp",timeStamp);
        String sign = createSign(appid, timeStamp, nonceStr, prepayId);
        map.put("sign",sign);
        System.out.println("---"+ nonceStr + "---" + timeStamp + "---" + sign);
        return map;


    }

    public static String createSign(String appid, long timeStamp, String nonceStr, String prepayId) {
        String signatureStr = Stream.of(appid, String.valueOf(timeStamp), nonceStr, prepayId)
                .collect(Collectors.joining("\n", "", "\n"));

        try {
            Signature sign = Signature.getInstance("SHA256withRSA");
            sign.initSign(merchantPrivateKey);
            sign.update(signatureStr.getBytes(StandardCharsets.UTF_8));
            Base64.Encoder encoder = Base64.getEncoder();

            signatureStr = encoder.encodeToString(sign.sign());
        }catch (NoSuchAlgorithmException e){

        }catch (InvalidKeyException e){

        }catch (SignatureException e){

        }

        return signatureStr;
    }

    static String notify_url = "http://www.iclickdesign.com/";
    static String mchSerialNo = "17BA244A4574F53CA4E1A650555A1E4AADF95658";

    static String appid = "wx5573711f9b6d2664";
    static String mchid = "1582797471";
    static String apiV3Key = "mglobalsourcing12345678999999999";

    static String privateKey = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC2W8WLPz7znGkx\n" +
            "NgO+3/Bh7lnK6mErAF2JCVjxy1M8g3wXi9C0SbAMID5YC6h8MArIH4Uz7cRvsAGv\n" +
            "hFbodknXAJlpDRODjqm6BDw+HZV2VmI/r8Eg9DpRmWXXUO7tQrLCxrVDIVx+Tlfd\n" +
            "zQ0EY+dII2vS6Mltk22UK/KCdFnHZAqAHwSFrc1YAfSFvrhN+KrX/Sf1RDNRcY01\n" +
            "E56rblwDuKtbtjm13F/qlpsw94qDG7CcEffd2NGGUZq2x24HkU7Yz+uvMosek18a\n" +
            "OqK6pSltbZ5Pfq2kLRjm+R7g1HpB81OIonVUp9WwyTnoq07ef8WuiPRP9NH72lK7\n" +
            "tDi5SDcBAgMBAAECggEAV3XcYNSMlLpHmVqWoU02NUHjvvZ6c+1Q7xCUoCMsYLGP\n" +
            "KpvcFRviW257eC7fN/CyuxijAizfMdHwfQWzyPoyn/OhoEitO0OJE7ZETmZR8Net\n" +
            "d24L6oo4DjrMJWCjtxduNQyokvHFMq7QZYia3agsnOSgKh+LvAohXBdX0EmNZZs+\n" +
            "SsQl5fcKjYiRK+n3LkK1+XKN1XvXao2SAj1GD4rTr9dIpg6lQlcJSz4k6FV2scM6\n" +
            "XgKDzfrTKM8DCjzhph851wf7MrZ3vw8zQubFJZHYPDke4eOPC2YvAF5PRVKpj2/M\n" +
            "qYyzO3efB8i47XxeUxIj46kmieTSkyF1IofCJertoQKBgQDZ7uOJLpT4sivEPURv\n" +
            "qXssgIzfjT4OgeFuFqmggbH54rjXKrJbRygv3MSKP71e8QydTSUzC2sKQh4kfXEJ\n" +
            "dPcnM5TuG8pP20xj7do52TU4WazvSI3xWk+/mjdJWaKoM0peuC4rOiIKMxpXpJlX\n" +
            "UBm5VCih5dCWqEPFMA0MrDksOwKBgQDWNh+nkHWsq2orbM85AM016zWVKRGzpaBw\n" +
            "Ti37MRdhdKLYtXvs45/1p7mQ7A+AJmeW6haE9u151LNVLe+LUPt/wFIaWmGyIOIZ\n" +
            "lZZ9PUBD13jKbvm4MTFhliUzY5p4lIuq6nDMFupw/k3cJ+EiPQOrnQ408GbZuPBW\n" +
            "IUcX3xUB8wKBgQC5dTTGuhBmL2FVZ3oEuCqs7Vx0pYLI37hSchC6GYS2jT0VRi1v\n" +
            "xfcp++p5Z9Kkp+c/ObZNYQsfOLLkBKgcDyCAiMHVe2rIWGwCohfSoMhMguvK2WiJ\n" +
            "mnxBJmCkGBZ2oudIAd6wRJFRMrnZGaawiebKMxc85xCCFQFPHhwMy8b/nwKBgGpC\n" +
            "wwUeViFnju0ntsvyJ9n9acEqdEuHd8T+IzUeLxj7AOU7cdQbPnhf0V+gmjfZ1T05\n" +
            "th4TV849wxAYD2Ius/O1dYT5HMl+ImrKl7JctgpxUsworNvAG1zrXFzXpgL2vSn1\n" +
            "ziqPm5CdtgXmCnovhiiVajy8nGiYozA2pcujsAodAoGBAMED3CQOISi3M4a5RuZa\n" +
            "Hn5/su9Is6rWgKMi5BC1ZcmWTbr93hi5p28lCsNxc0Z7VZGmg0AHwfHkW5bLXCFl\n" +
            "6n9KbgPEQARvZFqJt6Mb2L67GyndSsgnt4mjS7TSNYkpmr7JeGk3bGgIxBq0OT57\n" +
            "PRVyWr+h7IQh0t80dWZNvc+h";

    // 加载商户私钥（privateKey：私钥字符串）
    static PrivateKey merchantPrivateKey;

    // 加载平台证书（mchid：商户号,mchSerialNo：商户证书序列号,apiV3Key：V3密钥）
    static AutoUpdateCertificatesVerifier verifier;

    // 初始化httpClient
    static CloseableHttpClient httpClient;
}



