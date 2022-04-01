package com.ruoyi.wxPay;

public class WxpayConfig {
    //微信开发平台应用id
    public static final String APPID = "wx5cb4da2827891470";
    //微信支付商户号
    public static final String MCH_ID = "1458672702";
    //应用对应的凭证
    public static final String APP_SECRET = "68c39252926ac35b091b9f824c1e065e";
    //应用对应的秘钥
    public static final String APP_KEY = "7PEsu5akPFRDfORYWNWj6QanQhzuXTJ0";
    //商户号对应的秘钥
    public static final String PARTNER_KEY = "123456";
    //商户id
    public static final String PARTNER_ID = "14698sdfs402dsfdew402";
    //常量固定值
    public static final String GRANT_TYPE = "client_credential";
    //微信服务器回调通知URL
    public static final String NOTIFY_URL = "/wxPayNotify";
    //获取预支付ID的接口URL统一下单接口地址
    public static final String REQUEST_URL = "https://api.mch.weixin.qq.com/pay/unifiedorder";
    //关闭订单接口，避免出现（订单重复提交）
    public static final String CLOSE_ORDER_URL = "https://api.mch.weixin.qq.com/pay/closeorder";
    //查看订单接口
    public static final String ORDER_QUERY_URL = "https://api.mch.weixin.qq.com/pay/orderquery";

}