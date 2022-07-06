package com.ruoyi.netty.server.entity;

public interface PayConstants {



    String NOTIFY_URL = "https://api.iclickdesign.com/wxPay/"; //回调地址


    String MCH_ID = "1582797471"; //商户号

    String MCH_SERIAL_NO = "17BA244A4574F53CA4E1A650555A1E4AADF95658"; //商户证书序列号

    String API_3KEY = "mglobalsourcing12345678999999999";   //Api密钥class_id

    String APP_ID = "wx5573711f9b6d2664";     //ApId

    String PACKAGE = "Sign=WXPay";  //签名国定字符串

    //商户私钥
    static final String privateKey = "-----BEGIN PRIVATE KEY-----\n"+
            "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQC2W8WLPz7znGkx\n" +
            "NgO+3/Bh7lnK6mErAF2JCVjxy1M8g3wXi9C0SbAMID5YC6h8MArIH4Uz7cRvsAGv\n" +
            "hFbodknXAJlpDRODjqm6BDw+HZV2VmI/r8Eg9DpRmWXXUO7tQrLCxrVDIVx+Tlfd\n" +
            "zQ0EY+dII2vS6Mltk22UK/KCdFnHZAqAHwSFrc1YAfSFvrhN+KrX/Sf1RDNRcY01\n" +
            "E56rblwDuKtbtjm13F/qlpsw94qDG7CcEffd2NGGUZq2x24HkU7Yz+uvMosek18a\n" +
            "OqK6pSltbZ5Pfq2kLRjm+R7g1HpB81OIonVUp9WwyTnoq07ef8WuiPRP9NH72lK7\n" +
            "tDi5SDcBAgMBAAECggEAV3XcYNSMlLpHmVqWoU02NUHjvvZ6c+1Q7xCUoCMsYLGP\n" +
            "KpvcFRviW257eC7fN/CyuxijAizfMdHwfQWzyPoyn/OhoEitO0OJE7ZETmZR8Net\n" +
            "d24L6oo4DjrMJWCjtxduNQyokvHFMq7QZYia3agsnOSgKh+LvAohXBdX0EmNZZs+\n" +
            "SsQl5fcKjYiRK+n3LkK1+XKN1XvXao2SAj1GD4rTr9dIpg6lQlcJSz4k6FV2scM6\n" +
            "XgKDzfrTKM8DCjzhph851wf7MrZ3vw8zQubFJZHYPDke4eOPC2YvAF5PRVKpj2/M\n" +
            "qYyzO3efB8i47XxeUxIj46kmieTSkyF1IofCJertoQKBgQDZ7uOJLpT4sivEPURv\n" +
            "qXssgIzfjT4OgeFuFqmggbH54rjXKrJbRygv3MSKP71e8QydTSUzC2sKQh4kfXEJ\n" +
            "dPcnM5TuG8pP20xj7do52TU4WazvSI3xWk+/mjdJWaKoM0peuC4rOiIKMxpXpJlX\n" +
            "UBm5VCih5dCWqEPFMA0MrDksOwKBgQDWNh+nkHWsq2orbM85AM016zWVKRGzpaBw\n" +
            "Ti37MRdhdKLYtXvs45/1p7mQ7A+AJmeW6haE9u151LNVLe+LUPt/wFIaWmGyIOIZ\n" +
            "lZZ9PUBD13jKbvm4MTFhliUzY5p4lIuq6nDMFupw/k3cJ+EiPQOrnQ408GbZuPBW\n" +
            "IUcX3xUB8wKBgQC5dTTGuhBmL2FVZ3oEuCqs7Vx0pYLI37hSchC6GYS2jT0VRi1v\n" +
            "xfcp++p5Z9Kkp+c/ObZNYQsfOLLkBKgcDyCAiMHVe2rIWGwCohfSoMhMguvK2WiJ\n" +
            "mnxBJmCkGBZ2oudIAd6wRJFRMrnZGaawiebKMxc85xCCFQFPHhwMy8b/nwKBgGpC\n" +
            "wwUeViFnju0ntsvyJ9n9acEqdEuHd8T+IzUeLxj7AOU7cdQbPnhf0V+gmjfZ1T05\n" +
            "th4TV849wxAYD2Ius/O1dYT5HMl+ImrKl7JctgpxUsworNvAG1zrXFzXpgL2vSn1\n" +
            "ziqPm5CdtgXmCnovhiiVajy8nGiYozA2pcujsAodAoGBAMED3CQOISi3M4a5RuZa\n" +
            "Hn5/su9Is6rWgKMi5BC1ZcmWTbr93hi5p28lCsNxc0Z7VZGmg0AHwfHkW5bLXCFl\n" +
            "6n9KbgPEQARvZFqJt6Mb2L67GyndSsgnt4mjS7TSNYkpmr7JeGk3bGgIxBq0OT57\n" +
            "PRVyWr+h7IQh0t80dWZNvc+h"
            + "-----END PRIVATE KEY-----\n";
}
