package com;


public class Test {
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
