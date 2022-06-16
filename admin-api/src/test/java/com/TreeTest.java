package com;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.system.mapper.TbMallSpecsValueMapper;
import com.sun.org.apache.xpath.internal.operations.String;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import java.io.UnsupportedEncodingException;

public class TreeTest {

    @Autowired
    private TbMallSpecsValueMapper tbMallSpecsValueMapper;


    @Autowired
    public RedisTemplate redisTemplate;
//    public static java.lang.String downloadImage(java.lang.String fileUrl ) {
//        java.lang.String path = null;
//        if (fileUrl != null) {
//            java.lang.String fileName = fileUrl.substring(fileUrl.lastIndexOf("."));//获取后缀名
//            try {
//                java.lang.String dataStr = new SimpleDateFormat("yyyyMMdd").format(new Date());//根据日期生成指定文件夹,我没有用这个,直接放到固定文件夹内了.
//                java.lang.String uuidName = "test123";//设置文件名称,这里可以通过规则动态生成.
//                path = "zyoa/uploadPath"+dataStr+"/"+uuidName+fileName;//用于返回的路径
//                HttpUtil.downloadFile(java.lang.String.valueOf(fileUrl), "E:\\ossModel\\" + File.separator + uuidName + fileName);//文件保存的关键步骤
//            } catch (Exception e) {
//                e.printStackTrace();
//            } finally {
//
//            }
//        }
//        return path;
//    }
    @Test
    public void ong(){
//        downloadImage("https://app-1255978856.cos.ap-shanghai.myqcloud.com/model/2022/06/06/3a1341b42b454474a345114e9b9b0d3a.fbx");

//        java.lang.String str  = "{\"id\":\"10001\",\"name\":\"肉类\",\"menus\":[{\"name\":\"牛肉\",\"price\":\"30.00\"},{\"name\":\"羊肉\",\"price\":\"20.00\"}]}";
    }




        @Test
    public void redisSetGet(){
        //java.lang.String JSON = java.lang.String.valueOf("[{\"id\":\"1\",\"parentId\":\"1\",\"plateCutWay\":[{\"id\":\"3\",\"parentId\":\"1\",\"componentList\":[{\"id\":\"63\",\"parentId\":\"3\",\"outBottomFabric\":[{\"id\":\"20\",\"parentId\":\"63\",\"pattern\":[{\"id\":\"42\",\"typeId\":20}]}]},{\"id\":\"64\",\"parentId\":\"3\",\"outBottomFabric\":[{\"id\":\"21\",\"parentId\":\"64\",\"pattern\":[{\"id\":\"48\",\"typeId\":21}]}]},{\"id\":\"65\",\"parentId\":\"3\",\"outBottomFabric\":[{\"id\":\"22\",\"parentId\":\"65\",\"pattern\":[{\"id\":\"68\",\"typeId\":22}]}]},{\"id\":\"66\",\"parentId\":\"3\",\"outBottomFabric\":[{\"id\":\"23\",\"parentId\":\"66\",\"pattern\":[{\"id\":\"81\",\"typeId\":23}]}]},{\"id\":\"67\",\"parentId\":\"3\",\"outBottomFabric\":[{\"id\":\"24\",\"parentId\":\"67\",\"pattern\":[{\"id\":\"97\",\"typeId\":24}]}]}]}]},{\"id\":\"15\",\"parentId\":\"1\",\"plateCutWay\":[{\"id\":\"16\",\"parentId\":\"15\",\"componentList\":[{\"id\":\"62\",\"parentId\":\"16\",\"outBottomFabric\":[{\"id\":\"18\",\"parentId\":\"62\",\"pattern\":[{\"id\":\"194\",\"typeId\":18}]}]}]}]},{\"id\":\"12\",\"parentId\":\"1\",\"plateCutWay\":[{\"id\":\"11\",\"parentId\":\"12\",\"componentList\":[{\"id\":\"59\",\"parentId\":\"11\",\"outBottomFabric\":[{\"id\":\"14\",\"parentId\":\"59\",\"pattern\":[{\"id\":\"10\",\"typeId\":14}]}]}]}]},{\"id\":\"11\",\"parentId\":\"1\",\"plateCutWay\":[{\"id\":\"8\",\"parentId\":\"11\",\"componentList\":[{\"id\":\"53\",\"parentId\":\"8\",\"outBottomFabric\":[{\"id\":\"6\",\"parentId\":\"53\",\"pattern\":[{\"id\":\"346\",\"typeId\":6}]}]},{\"id\":\"54\",\"parentId\":\"8\",\"outBottomFabric\":[{\"id\":\"7\",\"parentId\":\"54\",\"pattern\":[{\"id\":\"352\",\"typeId\":7}]}]},{\"id\":\"55\",\"parentId\":\"8\",\"outBottomFabric\":[{\"id\":\"8\",\"parentId\":\"55\",\"pattern\":[{\"id\":\"354\",\"typeId\":8}]}]},{\"id\":\"56\",\"parentId\":\"8\",\"outBottomFabric\":[{\"id\":\"9\",\"parentId\":\"56\",\"pattern\":[{\"id\":\"357\",\"typeId\":9}]}]}]}]},{\"id\":\"13\",\"parentId\":\"1\",\"plateCutWay\":[{\"id\":\"12\",\"parentId\":\"13\",\"componentList\":[{\"id\":\"60\",\"parentId\":\"12\",\"outBottomFabric\":[{\"id\":\"16\",\"parentId\":\"60\",\"pattern\":[{\"id\":\"338\",\"typeId\":16}]}]}]}]}]");

        //java.lang.String s = JSON.replaceAll("plateCutWay", "fwefwfwfw");

//        JSONArray jsonArray = JSONArray.parseArray(JSON);
//
//        for(int i =0;i<jsonArray.size();i++){
//            JSONObject jsonObject = jsonArray.getJSONObject(i);
//            replaceValue(jsonObject);
//        }

        //System.out.println(s);
//        redisTemplate.opsForValue().set("key","fefef");
    }

    private static void replaceValue(JSONObject obj) {
//        Set<java.lang.String> strings = obj.keySet();
//        for(java.lang.String key : strings){
//            Object jsonObject = obj.get(key);
//            if(jsonObject instanceof JSONArray){
//                JSONArray jsonArray = (JSONArray)jsonObject;
//                for(int i =0;i<jsonArray.size();i++){
//                    JSONObject jsonObjectc = jsonArray.getJSONObject(i);
//                    //逻辑处理
//                    if(key.equals("pattern")){
//                        Integer typeId = jsonObjectc.getInteger("typeId");
//                        typeId = 32132;
//                        jsonObjectc.put("typeId",typeId);
//                        System.out.println(jsonObjectc.toString());
//                    }
//                    replaceValue(jsonObjectc);
//                }
//            }
//            if(JSONObject.parseObject(string) instanceof JSONObject){
//                replaceValue(JSONObject.parseObject(string));
//            }
//
//            if(key=="pattern"){
//                System.out.println(key);
//            }else {
//                replaceValue(JSONObject.parseObject(key));
//            }
//        }
    }
    public static void main(String[] args) throws UnsupportedEncodingException {

//
//         String json = "[{\"id\":\"1\",\"parentId\":\"1\",\"plateCutWay\":[{\"id\":\"3\",\"parentId\":\"1\",\"componentList\":[{\"id\":\"63\",\"parentId\":\"3\",\"outBottomFabric\":[{\"id\":\"20\",\"parentId\":\"63\",\"pattern\":[{\"id\":\"34\",\"typeId\":20}]}]},{\"id\":\"64\",\"parentId\":\"3\",\"outBottomFabric\":[{\"id\":\"21\",\"parentId\":\"64\",\"pattern\":[{\"id\":\"49\",\"typeId\":21}]}]},{\"id\":\"65\",\"parentId\":\"3\",\"outBottomFabric\":[{\"id\":\"22\",\"parentId\":\"65\",\"pattern\":[{\"id\":\"64\",\"typeId\":22}]}]},{\"id\":\"66\",\"parentId\":\"3\",\"outBottomFabric\":[{\"id\":\"23\",\"parentId\":\"66\",\"pattern\":[{\"id\":\"82\",\"typeId\":23}]}]},{\"id\":\"67\",\"parentId\":\"3\",\"outBottomFabric\":[{\"id\":\"24\",\"parentId\":\"67\",\"pattern\":[{\"id\":\"99\",\"typeId\":24}]}]}]}]},{\"id\":\"15\",\"parentId\":\"1\",\"plateCutWay\":[{\"id\":\"16\",\"parentId\":\"15\",\"componentList\":[{\"id\":\"62\",\"parentId\":\"16\",\"outBottomFabric\":[{\"id\":\"18\",\"parentId\":\"62\",\"pattern\":[{\"id\":\"194\",\"typeId\":18}]}]}]}]},{\"id\":\"11\",\"parentId\":\"1\",\"plateCutWay\":[{\"id\":\"8\",\"parentId\":\"11\",\"componentList\":[{\"id\":\"53\",\"parentId\":\"8\",\"outBottomFabric\":[{\"id\":\"6\",\"parentId\":\"53\",\"pattern\":[{\"id\":\"345\",\"typeId\":6}]}]},{\"id\":\"54\",\"parentId\":\"8\",\"outBottomFabric\":[{\"id\":\"7\",\"parentId\":\"54\",\"pattern\":[{\"id\":\"349\",\"typeId\":7}]}]},{\"id\":\"55\",\"parentId\":\"8\",\"outBottomFabric\":[{\"id\":\"8\",\"parentId\":\"55\",\"pattern\":[{\"id\":\"354\",\"typeId\":8}]}]},{\"id\":\"56\",\"parentId\":\"8\",\"outBottomFabric\":[{\"id\":\"9\",\"parentId\":\"56\",\"pattern\":[{\"id\":\"358\",\"typeId\":9}]}]}]}]},{\"id\":\"12\",\"parentId\":\"1\",\"plateCutWay\":[{\"id\":\"11\",\"parentId\":\"12\",\"componentList\":[{\"id\":\"59\",\"parentId\":\"11\",\"outBottomFabric\":[{\"id\":\"14\",\"parentId\":\"59\",\"pattern\":[{\"id\":\"375\",\"typeId\":14}]}]}]}]},{\"id\":\"13\",\"parentId\":\"1\",\"plateCutWay\":[{\"id\":\"12\",\"parentId\":\"13\",\"componentList\":[{\"id\":\"60\",\"parentId\":\"12\",\"outBottomFabric\":[{\"id\":\"16\",\"parentId\":\"60\",\"pattern\":[{\"id\":\"339\",\"typeId\":16}]}]}]}]},{\"id\":\"14\",\"parentId\":\"1\",\"plateCutWay\":[{\"id\":\"15\",\"parentId\":\"14\",\"componentList\":[{\"id\":\"110\",\"parentId\":\"15\",\"outBottomFabric\":[{\"id\":\"44\",\"parentId\":\"110\",\"pattern\":[{\"id\":\"202\",\"typeId\":44}]}]}]}]}]";
//
//
//        JSONArray jsonArray = JSONArray.parseArray(json);
//
//        for (int i =0;i<jsonArray.size();i++){
//            String id = jsonArray.getJSONArray(1).
//            String plateCutWay = jsonArray.getJSONObject(i).getString("plateCutWay");
//
//            JSONArray jsonArray23 = JSONArray.parseArray(plateCutWay);
//            jsonArray.getJSONObject(i).getString("parentId");
//
//        }
//
//        int w = 0;
//        String s1 = base64Encoder.encodeBuffer(bytes1);
//        System.out.println(s1);


//        List<Map<String,Object>> firstList = new ArrayList<>();
//        Map<String,Object> map = new HashMap<>();
//        map.put("id",100);
//        map.put("label",100);
//        map.put("parentId",0);
//        map.put("children",null);
//        firstList.add(map);
//
//        map = new HashMap<>();
//        map.put("id",101);
//        map.put("label",101);
//        map.put("parentId",0);
//        map.put("children",null);
//        firstList.add(map);
//
////        System.out.println(firstList);
//        Map<String,Object> indexMap = new HashMap<>();
//        for(Map<String,Object> dataMap: firstList){
//            indexMap.put("first_"+dataMap.get("id").toString(),dataMap);
//        }
//
//
//        List<Map<String,Object>> secondList = new ArrayList<>();
//        map = new HashMap<>();
//        map.put("id",1100);
//        map.put("label",1100);
//        map.put("parentId",100);
//        map.put("children",null);
//        secondList.add(map);
//
//        map = new HashMap<>();
//        map.put("id",1101);
//        map.put("label",1101);
//        map.put("parentId",101);
//        map.put("children",null);
//        secondList.add(map);
//
//
//        System.out.println(firstList);
//        for(Map<String,Object> dataMap: secondList){
//            Map<String,Object> iMap = (Map<String, Object>) indexMap.get("first_"+dataMap.get("parentId").toString());
//            if(iMap!=null){
//                List<Map<String,Object>> childrens = (List) iMap.get("children");
//                if(childrens ==null){
//                    childrens = new ArrayList<>();
//                }
//                System.out.println("============");
//                indexMap.put("second_"+dataMap.get("id").toString(),dataMap);
//                childrens.add(dataMap);
//                iMap.put("children",childrens);
//            }
//        }
//        System.out.println(firstList);
//
//        Map<String,Object> dataMap = (Map<String, Object>) indexMap.get("second_1101");
//        dataMap.put("children","88888888");
//        System.out.println(indexMap);
//        System.out.println(firstList);
    }


}
