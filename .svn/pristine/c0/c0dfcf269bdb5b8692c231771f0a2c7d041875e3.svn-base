package com.ruoyi.common.utils.http;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;
import java.util.TreeMap;

public class HttpUtil {
    public static String executePost(String data, String url) throws IOException {

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, data);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String executePostAsToken(String data, String url, String token) throws IOException {

        OkHttpClient client = new OkHttpClient();
        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, data);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .addHeader("token",token)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }
    /**
     * 将map对象转化为json字符串
     *
     * @param map [description]
     * @return [description]
     * @author batchen
     */
    public static String mapToJson(Map<String, String> map) {
        StringBuffer json = new StringBuffer();

        JSONObject jsonObject = new JSONObject();
        for (Map.Entry<String, String> entry : map.entrySet()) {
            jsonObject.put(entry.getKey(), entry.getValue());
        }

        return jsonObject.toString();
    }

    public static String arrayToJson(String[] my) {
        try {
            JSONArray jsonObj = (JSONArray) JSONArray.toJSON(my);// 数组转为JsonArray
            String jsonStr = jsonObj.toString();// JsonArray转为String
            return jsonStr;
        } catch (Exception e) {
            return "0";
        }
    }

    /**
     * 获取POST参数
     */
    public static Map<String, String> getPostParameter(HttpServletRequest request) {
        Map<String, String> requestMap = new TreeMap<>();

        StringBuffer data = new StringBuffer();
        String line = null;
        BufferedReader reader = null;
        try {
            reader = request.getReader();
            while (null != (line = reader.readLine())) {
                data.append(line);
            }
        } catch (IOException e) {
        }

        // 兼容旧的写法
        if (data.toString().equals("")) {
            Map<String, String[]> map = request.getParameterMap();

            for (String param : map.keySet()) {
                if (param.contains("school_code")) {
                    data = new StringBuffer(param);
                }
            }
        }

        JSONObject json = JSON.parseObject(data.toString());
        for (String key : json.keySet()) {
            requestMap.put(key, json.getString(key));
        }

        return requestMap;
    }
}