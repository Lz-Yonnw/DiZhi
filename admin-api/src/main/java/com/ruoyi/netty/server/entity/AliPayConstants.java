package com.ruoyi.netty.server.entity;

import org.springframework.stereotype.Repository;

@Repository
public interface AliPayConstants {
    //appid
    String APPID = "2017032106317484";
    //应用私钥
    String RSA_PRIVATE_KEY = "MIIEvgIBADANBgkqhkiG9w0BAQEFAASCBKgwggSkAgEAAoIBAQCcOIMdQtr6s+AYzRNmnZkhqE2bZfuKLAS0TmaRcppSmrOL/qnH9pDTHgHJoLjeE45+62okuQVMAn1+IAGdbLdvoTmIgAJBTdAFamKhxFS833h4eF+Nvd/gvw52Acqof+ZstIkQOVZFN045yj5pZbj2sRPIYcPjb0WLPRXIuXLnkYJVgUlfCmMdJfJhWAYSVbbK/KBVxO1BkQkDv71FnS+B3k5a5S3auBqoEH8jEZmiSLcvhCctDK/0KKbUQXSm4tZkjKhkS9BkLQA7Kg43gjNuYJvCUJFKGzSwSGn6Pp0mKXtd6x4YpR0CNAGwcyHDdcXKMyX5/POK78O3U0iD1sWLAgMBAAECggEBAIZxeB0pmS0iulx9uRGZ1dyKbGetZ8HsygumYU0RNNCcRKLZgqINh2u5Kk6snAy7BeoijZGXDYDxr7BouRsTIfbW6J2ZlNdAsSLIAKQ5Q7fcFmCPL6TbYJ2Zr0dNmWxTzimFYWPuBFnDE/ZWIEoyefEMSfsscBn+e/otkt7RAptYV6KWwat0IzWa8f/7AjoK6fukXn/DdnztxAjd9wojTIs8WVHwj9/X0O/sIiW0/ynR2QQXrE9G9qyMb5HNUWtOhkQCWjCS0U1t9rrIUFCUJt4xmWVYxbQLlIrMtCw3mu0Ec3UntDE1tFNtCqzMFXWXVzKHBhPwALlPkH2TNfjLn7kCgYEA8Gs81r9RV1PXDFoKLsTSJ6PVDIAuCzD24Qnd192lhv6pU2I+Vmx74cBXWsFHg8QnGxSu0jJZBez6ErXFPDY1NIm77rdn7fwnKNPjspZ7N9ZWu0RfbEn0JDAnbvBHuGV3axngAuV852mR0IGmyeknum3ehGiDs4BMyM5TiXURSM8CgYEAplhaTAYgXf31g+gGuo2RDHvameb7k/iJHynFyqBAy6C14Pm92aX3MNTkEQGLm5ExH+SQEz8t6nSu3JrLF6cT2c7nArGCwagoGbteCX0RWipYHuzWFU5J91SXH90UekaXmXTmT+f/WPaD2n1zKBmlgFojJR7Bpce49DbwMrD9boUCgYAa0Arn9ExRDfOk2AHct2ysqRPgogcAjoRqEpygL4L/bDVcLE/BebXTNo1HfNjJ9D1cQtFAdgcBpy04mvcVQQJsCpRgCipYG1AOuJrjVu8FCi2AEx8FR7Yntwsez0NgaEiKNEeMayWj9V0iwOLmD8+zdf9kCvftM/jcSE5+vQShdwKBgBarzAK4gwjMZ/3GTcR3LuGor65yEK+mG+onY1GubaZTDPbQktNrDzmUilDXCRP2wd8643f8EGH37S0b27oph0cUL9/qGYwTQwPeyX45kXfcdY4hcTwOI36WBNLO6s+Pd3HuwMT7NY4GawoINv2sISZRIqEYhAP4KHkAU9RU0vWRAoGBAM5jQN5BO/SAVkfJ6zY0Br+i5/6sOujDTJuHxRkr+2AeZCImxQ8yjxJi82A/HHQs8EPjSCayCky9oGSGNPqTCm5ogSIZ6+d2zbN8CC9GvNA4mtD8gNeW980VsIlCxK8ZA2gEpDZSf1NPx2pzlZLJT4Cc+SVmn6LmLPnP4vfhQCjS";

    //请求网关地址
    String url = "https://openapi.alipay.com/gateway.do";

    String CHARSET = "UTF-8";

    String FORMAT = "json";

    String ALIPAY_PUBLIC_KEY = "MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAnDiDHULa+rPgGM0TZp2ZIahNm2X7iiwEtE5mkXKaUpqzi/6px/aQ0x4ByaC43hOOfutqJLkFTAJ9fiABnWy3b6E5iIACQU3QBWpiocRUvN94eHhfjb3f4L8OdgHKqH/mbLSJEDlWRTdOOco+aWW49rETyGHD429Fiz0VyLly55GCVYFJXwpjHSXyYVgGElW2yvygVcTtQZEJA7+9RZ0vgd5OWuUt2rgaqBB/IxGZoki3L4QnLQyv9Cim1EF0puLWZIyoZEvQZC0AOyoON4IzbmCbwlCRShs0sEhp+j6dJil7XeseGKUdAjQBsHMhw3XFyjMl+fzziu/Dt1NIg9bFiwIDAQAB";

    String SIGNTYPE = "RSA2";

}