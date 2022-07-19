package com;

import java.io.IOException;

public class ImagesTest {

    public static void main(String[] args) throws IOException {

        String uid = "10";
        for (Integer i = 0; i < 32; i++) {
            String randChar = String.valueOf(Math.round(Math.random() * 9));
            uid = uid.concat(randChar);
        }
        System.out.println(uid);

        //AuthServerHandler.ctxMap.get("165733324087415");
//        //模型详情
//        String clothesJson = "[{\"id\":\"15\",\"parentId\":\"1\",\"plateCutWay\":[{\"id\":\"16\",\"parentId\":\"15\",\"componentList\":[{\"id\":\"62\",\"parentId\":\"16\",\"outBottomFabric\":[{\"id\":\"18\",\"parentId\":\"62\",\"pattern\":[{\"id\":\"194\",\"typeId\":18}]}]}]}]},{\"id\":\"11\",\"parentId\":\"1\",\"plateCutWay\":[{\"id\":\"8\",\"parentId\":\"11\",\"componentList\":[{\"id\":\"53\",\"parentId\":\"8\",\"outBottomFabric\":[{\"id\":\"6\",\"parentId\":\"53\",\"pattern\":[{\"id\":\"383\",\"typeId\":6}]}]},{\"id\":\"54\",\"parentId\":\"8\",\"outBottomFabric\":[{\"id\":\"7\",\"parentId\":\"54\",\"pattern\":[{\"id\":\"350\",\"typeId\":7}]}]},{\"id\":\"55\",\"parentId\":\"8\",\"outBottomFabric\":[{\"id\":\"8\",\"parentId\":\"55\",\"pattern\":[{\"id\":\"354\",\"typeId\":8}]}]},{\"id\":\"56\",\"parentId\":\"8\",\"outBottomFabric\":[{\"id\":\"9\",\"parentId\":\"56\",\"pattern\":[{\"id\":\"358\",\"typeId\":9}]}]}]}]},{\"id\":\"1\",\"parentId\":\"1\",\"plateCutWay\":[{\"id\":\"3\",\"parentId\":\"1\",\"componentList\":[{\"id\":\"63\",\"parentId\":\"3\",\"outBottomFabric\":[{\"id\":\"20\",\"parentId\":\"63\",\"pattern\":[{\"id\":\"37\",\"typeId\":20}]}]},{\"id\":\"64\",\"parentId\":\"3\",\"outBottomFabric\":[{\"id\":\"21\",\"parentId\":\"64\",\"pattern\":[{\"id\":\"52\",\"typeId\":21}]}]},{\"id\":\"65\",\"parentId\":\"3\",\"outBottomFabric\":[{\"id\":\"22\",\"parentId\":\"65\",\"pattern\":[{\"id\":\"68\",\"typeId\":22}]}]},{\"id\":\"66\",\"parentId\":\"3\",\"outBottomFabric\":[{\"id\":\"23\",\"parentId\":\"66\",\"pattern\":[{\"id\":\"84\",\"typeId\":23}]}]},{\"id\":\"67\",\"parentId\":\"3\",\"outBottomFabric\":[{\"id\":\"24\",\"parentId\":\"67\",\"pattern\":[{\"id\":\"100\",\"typeId\":24}]}]}]}]},{\"id\":\"13\",\"parentId\":\"1\",\"plateCutWay\":[{\"id\":\"12\",\"parentId\":\"13\",\"componentList\":[{\"id\":\"60\",\"parentId\":\"12\",\"outBottomFabric\":[{\"id\":\"16\",\"parentId\":\"60\",\"pattern\":[{\"id\":\"338\",\"typeId\":16}]}]}]}]}]";
//        try {
//            JSONArray jsonArray = JSONArray.parseArray(clothesJson);
//
//            for(int i = 0; i<jsonArray.size();i++){
//                //版型小类
//                JSONObject jsonObject = jsonArray.getJSONObject(i);
//                //版心大类
//                JSONObject plateCutWay = JSONArray.parseArray(jsonObject.getString("plateCutWay")).getJSONObject(0);
//
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//        }




//        //图片路径
//        String imagePath = "E:\\DianZhi\\点指服装物料（后端版）\\所有衣服款式（后端完整版）\\布料\\净色天鹅绒\\TER00019（湖蓝色)29cm 四方连续.png";
//
//        //保存生成图片路径
//        String genePath = "E:\\DianZhi\\点指服装物料（后端版）\\所有衣服款式（后端完整版）\\布料\\净色天鹅绒\\eifjwo.jpg";
//        //将指定文件压缩后 放置到指定路径下
//        //下面是一些常用的方法展示
//        Thumbnails.of(imagePath)
//                .scale(0.5f)  //按比例放大缩小  >0   cale(scaleWidth,scaleHeight)
////                .size(820, 547)  //设置生成图片的长宽 与scale 不能同时使用
//                .rotate(90)
//                .watermark(Positions.CENTER, ImageIO.read(new File("E:\\DianZhi\\点指服装物料（后端版）\\所有衣服款式（后端完整版）\\布料\\净色天鹅绒\\28.png")),0.5f)
//                .outputFormat("jpg")   //保存为文件的格式设置
//                .allowOverwrite(true) //是否允许覆盖已存在的文件
//                .outputQuality(0.5f)    //输出的图片质量  0~1 之间,否则报错
////                .toOutputStream(new FileOutputStream("url"))   //输出到指定的输出流中
//                // //asBufferedImage()返回BufferedImage  ImageIO.write(thumbnail,"jpg",newFile("c:/a380_1280x1024_BufferedImage.jpg"));
////                .asBufferedImages()
//                .toFile(genePath);
    }
}
