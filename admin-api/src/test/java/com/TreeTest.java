package com;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;

public class TreeTest {
    public static void main(String[] args) throws UnsupportedEncodingException {

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
