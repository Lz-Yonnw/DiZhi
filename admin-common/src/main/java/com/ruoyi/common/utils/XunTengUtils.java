package com.ruoyi.common.utils;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.XunTengConstants;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.http.HttpUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.TimeUnit;

import static org.apache.commons.codec.binary.Hex.encodeHex;

public class XunTengUtils {

    @Autowired
    private RedisCache redisCache;

    /**
     * token签发接口
     *
     * 4.3系统认证说明
     * 次数限制以及有效时间说明：
     * 次数限制：获取认证token的上限为20次/24小时
     * 有效时间：8小时/次
     * @return
     * @throws IOException
     */
    public String getToken() throws IOException {
        final String tokenKey = "xun_teng_token";
        String token = redisCache.getCacheObject(tokenKey);
        Map<String, String> params = new HashMap<>();
        params.put("clientId", XunTengConstants.CLIENT_ID);
        params.put("clientSecret", XunTengConstants.CLIENT_SECRET);
        params.put("sign", createSign(params));
        if(StringUtils.isBlank(tokenKey)){
            String result = HttpUtil.executePost(HttpUtil.mapToJson(params), XunTengConstants.GET_TOKEN_URL);
            JSONObject obj= JSONObject.parseObject(result);
            if(obj != null && obj.get("rt_data")!=null){
                token = obj.getString("rt_data");
                redisCache.setCacheObject(tokenKey, token,21600, TimeUnit.SECONDS);
            }
        }
        System.out.println("token=="+token);
        //{"rt_code":"40009","rt_msg":"Token已过期","rt_data":null}
        return token;
    }

    /**
     * 手机注册验证码短信发送接口
     * @param mobile
     * @return
     * @throws IOException
     */
    public String sendRegMsg(String mobile) throws IOException {
        Map<String, String> params = new HashMap<>();
        params.put("mobile", mobile);
        String token = getToken();
        return HttpUtil.executePostAsToken(HttpUtil.mapToJson(params), XunTengConstants.SEND_MSG, token);
    }

    public static void main(String[] args) {
        try {
//            String ss = getToken();
            String ss = new XunTengUtils().sendRegMsg("18819322541");
            System.out.println(ss);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 生成签名
     * @param params
     * @return
     */
    public static String createSign(Map<String, String> params) {
        int i = 0;
        StringBuilder sb = new StringBuilder();
        // 将参数以参数名的字典升序排序
        Map<String, String> sortParams = new TreeMap<>(params);
        // 遍历排序的字典,并拼接"key=value"格式
        for (Map.Entry<String, String> entry : sortParams.entrySet()) {
            String key = entry.getKey();
            String value = (entry.getValue()).trim();
            if (null != value && !value.equals("") ) {
                if (i != 0)
                    sb.append("&");
                sb.append(key).append("=").append(value);
            }
            i++;
        }
        String endUrl="&key=" + XunTengConstants.CLIENT_KEY;
        sb=sb.append(endUrl);
        String md5=md5(sb.toString()).toUpperCase();
        System.out.println(md5);
        return md5;
    }

    /**
     * 对字符串进行MD5加密
     *
     * @param text 明文
     * @return 密文
     */
    public static String md5(String text) {
        MessageDigest msgDigest = null;
        try {
            msgDigest = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new IllegalStateException("System doesn't support MD5 algorithm.");
        }
        try {
            msgDigest.update(text.getBytes("UTF-8")); // 注意改接口是按照指定编码形式签名
        } catch (UnsupportedEncodingException e) {
            throw new IllegalStateException("System doesn't support your  EncodingException.");
        }
        byte[] bytes = msgDigest.digest();
        String md5Str = new String(encodeHex(bytes));
        return md5Str;
    }

}
