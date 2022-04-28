package com.ruoyi.netty.util;

import com.alibaba.fastjson.JSONException;
import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;

import javax.xml.ws.http.HTTPException;
import java.io.IOException;

public class SMSUtil {


    public String sendSMS(String phoneNumber,String text) {
        String reStr = ""; //定义返回值
        // 短信应用SDK AppID  1400开头
        int appid = 1400067117 ;
        // 短信应用SDK AppKey
        String appkey = "38b67e0fb924c2a828e5aca804b05654";
        // 短信模板ID，需要在短信应用中申请
        int templateId = 439092 ;
        // 签名，使用的是签名内容，而不是签名ID
        String smsSign = "点指设计";
        try {
            //参数，一定要对应短信模板中的参数顺序和个数，
            String[] params = new String[2];
            params[0] = text;
            params[1] = "5";
            //创建ssender对象
            SmsSingleSender ssender = new SmsSingleSender(appid, appkey);
            //发送
            SmsSingleSenderResult result = ssender.sendWithParam("86", phoneNumber,templateId, params, smsSign, "", "");
            if(result.result!=0){
                return reStr = "error";
            }
//            SpringUtils.getBean(RedisCache.class).setCacheObject(phoneNumber, text, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
            reStr = "success";
        } catch (HTTPException e) {
            // HTTP响应码错误
            e.printStackTrace();
        } catch (JSONException e) {
            // json解析错误
            e.printStackTrace();
        } catch (IOException e) {
            // 网络IO错误
            e.printStackTrace();
        }catch (Exception e) {
            // 网络IO错误
            e.printStackTrace();
        }
        return reStr;
    }


}
