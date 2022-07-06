package com.ruoyi.netty.util;


import cn.hutool.core.util.RandomUtil;
import com.alibaba.fastjson.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.ruoyi.netty.server.entity.PayConstants;
import com.ruoyi.netty.server.entity.SuccessCallBackObj;
import com.wechat.pay.contrib.apache.httpclient.WechatPayHttpClientBuilder;
import com.wechat.pay.contrib.apache.httpclient.auth.PrivateKeySigner;
import com.wechat.pay.contrib.apache.httpclient.auth.Verifier;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Credentials;
import com.wechat.pay.contrib.apache.httpclient.auth.WechatPay2Validator;
import com.wechat.pay.contrib.apache.httpclient.cert.CertificatesManager;
import com.wechat.pay.contrib.apache.httpclient.exception.HttpCodeException;
import com.wechat.pay.contrib.apache.httpclient.exception.NotFoundException;
import com.wechat.pay.contrib.apache.httpclient.util.PemUtil;
import com.wechat.pay.contrib.apache.httpclient.util.RsaCryptoUtil;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.junit.Before;
import org.junit.Test;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;


@Component
public class PayUtil {
    private CloseableHttpClient httpClient;

    private CertificatesManager certificatesManager;

    private Verifier verifier;


    @Before
    public void init() throws HttpCodeException, GeneralSecurityException, IOException, NotFoundException {
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
    }



    /**
     * App下单
     * @param total
     * @param description
     * @return 预支付ID
     * @throws Exception
     */
    public String requestPartnerid(String orderSn,int total,String description) throws Exception {


        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/app");
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type","application/json; charset=utf-8");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("mchid", PayConstants.MCH_ID)
                .put("appid", PayConstants.APP_ID)
                .put("notify_url", PayConstants.NOTIFY_URL+"notify")
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
     * h5下单
     * @param orderSn
     * @param total
     * @param description
     * @return
     * @throws Exception
     */
    public String requestH5fPartnerid(String orderSn,int total,String description) throws Exception {






        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/h5");
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type","application/json; charset=utf-8");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("mchid", PayConstants.MCH_ID)
                .put("appid", PayConstants.APP_ID)
                .put("notify_url", PayConstants.NOTIFY_URL+"notify")
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


    /**
     * Native下单
     * @param orderSn
     * @param total
     * @param description
     * @return
     * @throws Exception
     */
    public String requestH5Partnerid(String orderSn,int total,String description) throws Exception {





        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/native");
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type","application/json; charset=utf-8");

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("mchid", PayConstants.MCH_ID)
                .put("appid", PayConstants.APP_ID)
                .put("notify_url", PayConstants.NOTIFY_URL+"notify")
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
    public void CloseTheTrder(String orderSn) throws HttpCodeException, GeneralSecurityException, IOException, NotFoundException, URISyntaxException {
        //TODO 关闭订单失败
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


        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/out-trade-no/"+orderSn+"/close?mchid="+PayConstants.MCH_ID);
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type","application/json; charset=utf-8");


        try {
            CloseableHttpResponse response = httpClient.execute(httpPost);
            String s = EntityUtils.toString(response.getEntity());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 查单
     * @param transaction_id  微信商户订单号
     * @return
     */
    public String orderCheckList(String transaction_id) throws HttpCodeException, GeneralSecurityException, IOException, NotFoundException, URISyntaxException {
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

        URIBuilder uriBuilder = new URIBuilder("https://api.mch.weixin.qq.com/v3/pay/transactions/id/"+transaction_id);
        List<NameValuePair> list = new LinkedList<>();
        BasicNameValuePair param1 = new BasicNameValuePair("mchid", PayConstants.MCH_ID);
        list.add(param1);
        uriBuilder.setParameters(list);


        HttpGet httpGet = new HttpGet(uriBuilder.build());
        httpGet.addHeader("Accept", "application/json");
        httpGet.addHeader("Content-type","application/json; charset=utf-8");
        try {
            CloseableHttpResponse response = httpClient.execute(httpGet);
            String bodyAsString = EntityUtils.toString(response.getEntity());

            return bodyAsString;
        }catch (Exception e){
            e.printStackTrace();
        }

        return null;
    }


    /**
     * 退单
     * @param out_trade_no  微信支付订单号
     * @param reason    退单原因
     * @param total     原订单金额
     * @param amount    退还金额
     * @return
     * @throws HttpCodeException
     * @throws GeneralSecurityException
     * @throws IOException
     * @throws NotFoundException
     */
    public String refunds(String out_trade_no,String reason,int total,int amount) throws HttpCodeException, GeneralSecurityException, IOException, NotFoundException {
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

        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/refund/domestic/refunds");
        httpPost.addHeader("Accept", "application/json");
        httpPost.addHeader("Content-type","application/json; charset=utf-8");

        long creationTime = System.currentTimeMillis();
        String out_refund_no = String.valueOf(creationTime);

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectMapper objectMapper = new ObjectMapper();

        ObjectNode rootNode = objectMapper.createObjectNode();
        rootNode.put("out_trade_no",out_trade_no)
                .put("out_refund_no",out_refund_no)
                .put("notify_url",PayConstants.NOTIFY_URL+"refunds")
                .put("reason",reason);
        rootNode.putObject("amount")
                .put("refund",amount)
                .put("total",total)
                .put("currency","CNY");
        try {
            objectMapper.writeValue(bos, rootNode);
            httpPost.setEntity(new StringEntity(bos.toString("UTF-8"), "UTF-8"));
            CloseableHttpResponse response = httpClient.execute(httpPost);
            String bodyAsString = EntityUtils.toString(response.getEntity());
            return bodyAsString;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
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


    @Test
    public void test() throws Exception {

//        orderCheckList("4200001521202207011314708946");

//        String feoinogejge = requestH5fPartnerid("456435151616633", 1, "feoinogejge");
//        System.out.println(feoinogejge);


//        PayUtil payUtil = new PayUtil();
//
//
//        CloseTheTrder("4200001521202207011314708946");

        refunds("15156165198415","库存不够",10,10);
    }




    /**
     * 回调通知解密
     * @param successCallBackObj
     * @return
     */
    public static String decryptToString(SuccessCallBackObj successCallBackObj){
        String associated_data = successCallBackObj.getResource().getAssociated_data();
        String nonce = successCallBackObj.getResource().getNonce();
        String ciphertext = successCallBackObj.getResource().getCiphertext();

        //解密Resource
        AesUtil aesUtil = new AesUtil(PayConstants.API_3KEY.getBytes());
        try {
            return aesUtil.decryptToString(associated_data.getBytes(), nonce.getBytes(), ciphertext);
        }catch (Exception e){

        }
        return null;
    }
}
