package com.ruoyi.wxPay;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;



//DOTO MD5加密工具
public class MD5Util{

    public static void main(String[] args) {
        System.out.println(md5Encode("444"));
    }

    //MD5加密 编码：UTF-8
    public static String md5Encode(String paramStr){
        String encoderStr = null;

        try {
            encoderStr = new String(paramStr);
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            encoderStr = byteArrayToHexString(md5.digest(encoderStr.getBytes("UTF-8")));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encoderStr;
    }

    private static String byteArrayToHexString(byte b[]){
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < b.length; i++) {
            stringBuffer.append(byteToHexString(b[i]));
        }
        return stringBuffer.toString();
    }
    private static String byteToHexString(byte b) {
        int n = b;
        if (n < 0) {
            n += 256;
        }
        int d1 = n/16;
        int d2 = n%16;
        return HEX_DIGITS[d1] + HEX_DIGITS[d2];

    }
    private static final String[] HEX_DIGITS ={ "0", "1", "2", "3", "4", "5",

            "6", "7", "8", "9", "a", "b", "c", "d", "e", "f"};

}