package com.ruoyi.web.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.netty.util.AliPayUtil;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/aliPay/")
public class AliPayController {

    /**
     * 发起支付宝支付
     * @return
     */
    @GetMapping("/pay/{orderSn}")
    public String pay(@PathVariable("orderSn") String orderSn) {
        AliPayUtil aliPayUtil = new AliPayUtil();
        return aliPayUtil.requestH5Partnerid(orderSn,"买","0.01");
    }

    /**
     * 支付宝回调接口
     */
    //@RequestMapping("/notify")
    public void aliPayNotify(@RequestBody JSONObject jsonObject, HttpServletRequest request){

    }
}
