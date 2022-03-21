package com.ruoyi.web.controller.common;

import net.coobird.thumbnailator.Thumbnails;
import javax.imageio.ImageIO;
import javax.imageio.stream.ImageOutputStream;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.LinkedList;

public class ImgUtils {
    /*public static void main(String[] args) {
        String prefix = "thumb_";
        File file = new File("D:\\zz\\uploadPath\\uploadPath\\upload\\2021\\01\\18\\c5c9b75f-f22d-4f10-b59a-7597a23b633e.png");
        boolean isImage = new ImgUtils().isImage(file);
        if(isImage){
            System.out.println(file.length()/1000);
            String parentUrl = file.getParent();
            String name = file.getName();
            String url = parentUrl + File.separator +prefix + name;
            System.out.println(url);
            try {
                Thumbnails.of(file)
                        .scale(0.3f)
//                    .outputQuality(0.5f)
                        .toFile(url);
            } catch (IOException e) {
                try {
                    BufferedImage image = ImageIO.read(file);
                    ImageOutputStream output = ImageIO.createImageOutputStream(file);
                    if (!ImageIO.write(image, "jpg", output)) {

                    }
                    Thumbnails.of(image).scale(0.4f).toFile(url);
                } catch (IOException e1) {
                }
            }
        }
    }*/

    public static float getThumbAccuracy(Long size) {
        float accuracy;
        if (size < 20) {
            accuracy = 1f;
        } else if (size < 100) {
            accuracy = 0.4f;
        } else if (size < 200) {
            accuracy = 0.4f;
        }else if (size < 900) {
            accuracy = 0.3f;
        } else if (size < 2047) {
            accuracy = 0.1f;
        } else {
            accuracy = 0.25f;
        }
        return accuracy;
    }

    public static void generateThumb(File file) {
        String prefix = "thumb_";
        String parentUrl = file.getParent();
        String name = file.getName();
        String url = parentUrl + File.separator + prefix + name;
        try {
            Thumbnails.of(file).scale(getThumbAccuracy(file.length()/1000)).toFile(url);
        } catch (IOException e) {
            //解决 Unsupported Image Type 异常问题
            try {
                BufferedImage image = ImageIO.read(file);
                ImageOutputStream output = ImageIO.createImageOutputStream(file);
                if (!ImageIO.write(image, "jpg", output)) {
                }
                Thumbnails.of(image).scale(getThumbAccuracy(file.length()/1000)).toFile(url);
            } catch (IOException e1) {
                e1.printStackTrace();
            }
        }
    }

    public static boolean isImage(File file) {
//        String mimetype= new MimetypesFileTypeMap().getContentType(file);
//        String type = mimetype.split("/")[0];
//        return type.equals("image");
        // 截取文件扩展名
        String expandName = file.getName().substring(file.getName().lastIndexOf("."),file.getName().length());
        // 判断文件扩展名
        if (expandName.equalsIgnoreCase(".jpg")
                || expandName.equalsIgnoreCase(".jpeg")
                || expandName.equalsIgnoreCase(".png")
                || expandName.equalsIgnoreCase(".bmp")) {
            return true;
        }
        return false;
    }



    public static void main(String[] args) throws IOException {
        String path ="D:\\dijiangProject\\第三版资料\\布料\\净色卫衣布 - 副本";
        int fileNum = 0, folderNum = 0;
        File file = new File(path);
        if (file.exists()) {
            LinkedList<File> list = new LinkedList<File>();
            File[] files = file.listFiles();
            for (File file2 : files) {
                if (file2.isDirectory()) {
                    System.out.println("文件夹:" + file2.getAbsolutePath());
                    list.add(file2);
                } else {
                    System.out.println("文件:" + file2.getAbsolutePath());
                    boolean isImage = new ImgUtils().isImage(file2);
                    if(isImage) {
                        generateThumb(file2);
                    }
                    fileNum++;
                }
            }
            File temp_file;
            while (!list.isEmpty()) {
                temp_file = list.removeFirst();
                files = temp_file.listFiles();
                for (File file2 : files) {
                    if (file2.isDirectory()) {
                        System.out.println("文件夹:" + file2.getAbsolutePath());
                        list.add(file2);
                    } else {
                        System.out.println("文件:" + file2.getAbsolutePath());
                        boolean isImage = new ImgUtils().isImage(file2);
                        if(isImage) {
                            generateThumb(file2);
                        }
                        fileNum++;
                    }
                }
            }
        } else {
            System.out.println("文件不存在!");
        }
        System.out.println("文件夹共有:" + folderNum + ",文件共有:" + fileNum);
    }
}
