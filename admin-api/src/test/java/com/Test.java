package com;


import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.ruoyi.netty.server.entity.PayConstants;
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

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.PrivateKey;
import java.util.HashMap;
import java.util.Map;

public class Test {


    private CloseableHttpClient httpClient;

    private CertificatesManager certificatesManager;

    private Verifier verifier;

    @org.junit.Test
    public void test() throws Exception {
        try {
            String resource = "{\"mchid\":\"1582797471\",\"appid\":\"wx5573711f9b6d2664\",\"out_trade_no\":\"1656691280717111115\",\"transaction_id\":\"4200001520202207027274194452\",\"trade_type\":\"NATIVE\",\"trade_state\":\"SUCCESS\",\"trade_state_desc\":\"支付成功\",\"bank_type\":\"OTHERS\",\"attach\":\"\",\"success_time\":\"2022-07-02T00:01:58+08:00\",\"payer\":{\"openid\":\"oVX2q5zuwZMEetXMTJ3bi6yfluQY\"},\"amount\":{\"total\":1,\"payer_total\":1,\"currency\":\"CNY\",\"payer_currency\":\"CNY\"}}";

            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(resource);

            String orderSn = jsonNode.get("out_trade_no").toString();


//            MessageProtocol.MessageBase.WxpaySuccessResp.Builder wxpaySuccessRespbuilder = MessageProtocol.MessageBase.WxpaySuccessResp.newBuilder();
//            wxpaySuccessRespbuilder.setOuttradeno(jsonNode.get("transaction_id").toString());
//            wxpaySuccessRespbuilder.setPayertotal(jsonNode.get("amount").get("payer_total").toString());
//            wxpaySuccessRespbuilder.setTradestate(jsonNode.get("trade_state").toString());
//            wxpaySuccessRespbuilder.setSuccesstime(jsonNode.get("success_time").toString());
        }catch (Exception e){
            e.printStackTrace();
        }





//        String nifoewfwff = requestPartnerid("49845194984984", 1, "nifoewfwff");
//        JSONObject jsonObject = JSONObject.parseObject(nifoewfwff);
//        System.out.println(nifoewfwff);
//        Test.zxingCodeCreate(jsonObject.getString("code_url"),300, 300, "E:/borths.jpg", "jpg");
    }

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

        HttpPost httpPost = new HttpPost("https://api.mch.weixin.qq.com/v3/pay/transactions/native");
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

    //二维码颜色
    private static final int BLACK = 0xFF000000;
    //二维码颜色
    private static final int WHITE = 0xFFFFFFFF;

    /**
     * <span style="font-size:18px;font-weight:blod;">ZXing 方式生成二维码</span>
     * @param text    <a href="javascript:void();">二维码内容</a>
     * @param width    二维码宽
     * @param height    二维码高
     * @param outPutPath    二维码生成保存路径
     * @param imageType        二维码生成格式
     */
    public static void zxingCodeCreate(String text, int width, int height, String outPutPath, String imageType){
        Map<EncodeHintType, String> his = new HashMap<EncodeHintType, String>();
        //设置编码字符集
        his.put(EncodeHintType.CHARACTER_SET, "utf-8");
        try {
            //1、生成二维码
            BitMatrix encode = new MultiFormatWriter().encode(text, BarcodeFormat.QR_CODE, width, height, his);

            //2、获取二维码宽高
            int codeWidth = encode.getWidth();
            int codeHeight = encode.getHeight();

            //3、将二维码放入缓冲流
            BufferedImage image = new BufferedImage(codeWidth, codeHeight, BufferedImage.TYPE_INT_RGB);
            for (int i = 0; i < codeWidth; i++) {
                for (int j = 0; j < codeHeight; j++) {
                    //4、循环将二维码内容定入图片
                    image.setRGB(i, j, encode.get(i, j) ? BLACK : WHITE);
                }
            }
            File outPutImage = new File(outPutPath);
            //如果图片不存在创建图片
            if(!outPutImage.exists())
                outPutImage.createNewFile();
            //5、将二维码写入图片
            ImageIO.write(image, imageType, outPutImage);
        } catch (WriterException e) {
            e.printStackTrace();
            System.out.println("二维码生成失败");
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("生成二维码图片失败");
        }
    }

    /**
    private static java.lang.String getParsingSpecsValueJson(java.lang.String specsData){
        java.lang.String[] split = specsData.split(",");
        for(java.lang.String str : split){
            java.lang.String[] split1 = str.split("-");
            System.out.println(split1[0]+"---"+split1[1]);
        }
        return "";
    }

    public static void main(String[] args) {
        java.lang.String specsData = "1-3,2-7,4-5";
        getParsingSpecsValueJson(specsData);

        //String JSON = "[{\"id\":\"1\",\"parentId\":\"1\",\"plateCutWay\":[{\"id\":\"3\",\"parentId\":\"1\",\"componentList\":[{\"id\":\"63\",\"parentId\":\"3\",\"outBottomFabric\":[{\"id\":\"20\",\"parentId\":\"63\",\"pattern\":[{\"id\":\"34\",\"typeId\":20}]}]},{\"id\":\"64\",\"parentId\":\"3\",\"outBottomFabric\":[{\"id\":\"21\",\"parentId\":\"64\",\"pattern\":[{\"id\":\"49\",\"typeId\":21}]}]},{\"id\":\"65\",\"parentId\":\"3\",\"outBottomFabric\":[{\"id\":\"22\",\"parentId\":\"65\",\"pattern\":[{\"id\":\"64\",\"typeId\":22}]}]},{\"id\":\"66\",\"parentId\":\"3\",\"outBottomFabric\":[{\"id\":\"23\",\"parentId\":\"66\",\"pattern\":[{\"id\":\"82\",\"typeId\":23}]}]},{\"id\":\"67\",\"parentId\":\"3\",\"outBottomFabric\":[{\"id\":\"24\",\"parentId\":\"67\",\"pattern\":[{\"id\":\"99\",\"typeId\":24}]}]}]}]},{\"id\":\"15\",\"parentId\":\"1\",\"plateCutWay\":[{\"id\":\"16\",\"parentId\":\"15\",\"componentList\":[{\"id\":\"62\",\"parentId\":\"16\",\"outBottomFabric\":[{\"id\":\"18\",\"parentId\":\"62\",\"pattern\":[{\"id\":\"194\",\"typeId\":18}]}]}]}]},{\"id\":\"11\",\"parentId\":\"1\",\"plateCutWay\":[{\"id\":\"8\",\"parentId\":\"11\",\"componentList\":[{\"id\":\"53\",\"parentId\":\"8\",\"outBottomFabric\":[{\"id\":\"6\",\"parentId\":\"53\",\"pattern\":[{\"id\":\"345\",\"typeId\":6}]}]},{\"id\":\"54\",\"parentId\":\"8\",\"outBottomFabric\":[{\"id\":\"7\",\"parentId\":\"54\",\"pattern\":[{\"id\":\"349\",\"typeId\":7}]}]},{\"id\":\"55\",\"parentId\":\"8\",\"outBottomFabric\":[{\"id\":\"8\",\"parentId\":\"55\",\"pattern\":[{\"id\":\"354\",\"typeId\":8}]}]},{\"id\":\"56\",\"parentId\":\"8\",\"outBottomFabric\":[{\"id\":\"9\",\"parentId\":\"56\",\"pattern\":[{\"id\":\"358\",\"typeId\":9}]}]}]}]},{\"id\":\"12\",\"parentId\":\"1\",\"plateCutWay\":[{\"id\":\"11\",\"parentId\":\"12\",\"componentList\":[{\"id\":\"59\",\"parentId\":\"11\",\"outBottomFabric\":[{\"id\":\"14\",\"parentId\":\"59\",\"pattern\":[{\"id\":\"375\",\"typeId\":14}]}]}]}]},{\"id\":\"13\",\"parentId\":\"1\",\"plateCutWay\":[{\"id\":\"12\",\"parentId\":\"13\",\"componentList\":[{\"id\":\"60\",\"parentId\":\"12\",\"outBottomFabric\":[{\"id\":\"16\",\"parentId\":\"60\",\"pattern\":[{\"id\":\"339\",\"typeId\":16}]}]}]}]},{\"id\":\"14\",\"parentId\":\"1\",\"plateCutWay\":[{\"id\":\"15\",\"parentId\":\"14\",\"componentList\":[{\"id\":\"110\",\"parentId\":\"15\",\"outBottomFabric\":[{\"id\":\"44\",\"parentId\":\"110\",\"pattern\":[{\"id\":\"202\",\"typeId\":44}]}]}]}]}]";

//        String str = "232fefebff";
//
//        System.out.println(str.replace("feb","ddd"));

//        JSONArray jsonArray = JSONArray.parseArray(JSON);
//
//        StringBuffer stringBufferJson = new StringBuffer();
//
//        JSONArray repl = repl(jsonArray);
//
//        System.out.println(repl.toString());
//        for(int i =0;i<jsonArray.size();i++){
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            JSONObject jsonObject1 = new JSONObject();
//            replaceValue(jsonObject,jsonObject1);
//        }
//        System.out.println(JSON);
    }
    public static JSONArray repl(JSONArray jsonArray){
        JSONArray jsonArrayResult = new JSONArray();

        for(int i =0;i<jsonArray.size();i++){
            JSONObject jsonObject = jsonArray.getJSONObject(i);
            Set<java.lang.String> strings = jsonObject.keySet();
            for (java.lang.String key : strings) {
                Object object = jsonObject.get(key);
                if(object instanceof JSONArray){
                    jsonArrayResult.add(repl((JSONArray) object));
                }else {

                    jsonArrayResult.add(key);
                }
            }
        }
        return jsonArrayResult;
    }


    //模型参数解析
    private static void replaceValue(JSONObject obj,JSONObject jsonObject1) {
        Set<java.lang.String> strings = obj.keySet();
        for (java.lang.String key : strings) {
            Object jsonObject = obj.get(key);
            if (jsonObject instanceof JSONArray) {
                JSONArray jsonArray = (JSONArray) jsonObject;
                for (int i = 0; i < jsonArray.size(); i++) {
                    JSONObject jsonObjectc = jsonArray.getJSONObject(i);

                    System.out.println(key+":"+jsonObject.toString());
//                    //逻辑处理
//                    if (key.equals("pattern")) {
//                        Integer typeId = jsonObjectc.getInteger("typeId");
//                        typeId = 32132;
//                        jsonObjectc.put("typeId", typeId);
//                        System.out.println(jsonObjectc.toString());
//
//
//                    }
                    replaceValue(jsonObjectc,jsonObject1);
                }

            }else {
                System.out.println(key+":"+jsonObject.toString());
            }
        }
    }

    @Autowired
    private TbMallSpecsValueServiceImpl tbMallSpecsValueService;

    @org.junit.Test
    public void geege(){
        List<TbMallSpecsValue> tbMallSpecsValues =  tbMallSpecsValueService.selectTbMallSpecsChildsBySpecsId(0L);
        System.out.println(tbMallSpecsValues);
//        String json= new String("{\"id\":\"10001\",\"name\":\"肉类\",\"menus\":[{\"name\":\"牛肉\",\"price\":\"30.00\"},{\"name\":\"羊肉\",\"price\":\"20.00\"}]}");
//
//        JSONObject jsonObject = JSONObject.parseObject(json);
//
//        dome dome = JSONObject.parseObject(json, dome.class);
//        dome.setName("fwfwf");
//        dome.setId(12);
//
//        System.out.println(jsonObject);
//        jsonObject.put("id",21323);
//        System.out.println(jsonObject);
//        System.out.println(json);

        //        fefef(json);
//        System.out.println(json);
//        Integer i = new Integer(1232);
//        Integer c = new Integer(1232);
//
//        ArrayList arrayList = new ArrayList();
//        arrayList.add(1);
//        System.out.println(arrayList);
//        fefef(arrayList);
//        System.out.println(arrayList);

//        if(i==c){
//            System.out.println("引用");
//        }else {
//            System.out.println("不是");
//        }
//
//        System.out.println(i);
//        fefef(i);
//        System.out.println(i);
    }

    public void fefef(ArrayList arrayList){
        arrayList.add(2);
    }



*/
}
