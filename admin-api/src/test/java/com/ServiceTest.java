package com;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.RuoYiApplication;
import com.ruoyi.common.core.domain.ModelTreeSelect;
import com.ruoyi.system.domain.TbModelStyle;
import com.ruoyi.system.domain.TbModelType;
import com.ruoyi.system.domain.TbPlateClass;
import com.ruoyi.system.domain.TbPlateCutWay;
import com.ruoyi.system.service.ITbModelStyleService;
import com.ruoyi.system.service.ITbModelTypeService;
import com.ruoyi.system.service.ITbPlateClassService;
import com.ruoyi.system.service.ITbPlateCutWayService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(value = SpringJUnit4ClassRunner.class)
@SpringBootTest(classes={RuoYiApplication.class})
public class ServiceTest {

    @Autowired
    private ITbModelTypeService iTbModelTypeService;

    @Autowired
    private ITbModelStyleService iTbModelStyleService;

    @Autowired
    private ITbPlateClassService iTbPlateClassService;

    @Autowired
    private ITbPlateCutWayService iTbPlateCutWayService;


    @Test
    public void testSaveUser() {
        System.out.println("===========微信支付test===========");


    }



   /* @Test
    public void testSaveUser2(){

        List<ModelTreeSelect> list = new ArrayList<>();
        Map<String,Object> indexMap = new HashMap<>();
        List<TbModelType> modelTypes = iTbModelTypeService.selectTbModelTypeList(null);
        ModelTreeSelect modelTreeSelect = null;
        for(TbModelType modelType: modelTypes){
            modelTreeSelect = new ModelTreeSelect();
            modelTreeSelect.setId(modelType.getId());
            modelTreeSelect.setLabel(modelType.getName());
            modelTreeSelect.setCode("modelType");
            list.add(modelTreeSelect);
            indexMap.put("modelType_"+modelType.getId(), modelTreeSelect);
        }
        System.out.println(JSONObject.toJSONString(list));
        List<TbModelStyle> modelStyles = iTbModelStyleService.selectTbModelStyleList(null);
        for(TbModelStyle tbModelStyle: modelStyles){
            ModelTreeSelect firstModel = (ModelTreeSelect) indexMap.get("modelType_"+tbModelStyle.getParentId());
            if(firstModel != null){
                modelTreeSelect = new ModelTreeSelect();
                modelTreeSelect.setId(tbModelStyle.getId());
                modelTreeSelect.setLabel(tbModelStyle.getName());
                modelTreeSelect.setCode("modelStyle");
                List<ModelTreeSelect> modelTreeSelects = firstModel.getChildren();
                if(modelTreeSelects == null){
                    modelTreeSelects = new ArrayList<>();
                }
                modelTreeSelects.add(modelTreeSelect);
                firstModel.setChildren(modelTreeSelects);
                indexMap.put("modelStyle_" + tbModelStyle.getId(), modelTreeSelect);
            }
        }
        System.out.println(JSONObject.toJSONString(list));

        List<TbPlateClass> plateClasses = iTbPlateClassService.selectTbPlateClassList(null);
        for(TbPlateClass plateClass: plateClasses){
            ModelTreeSelect firstModel = (ModelTreeSelect) indexMap.get("modelStyle_"+plateClass.getParentId());
            if(firstModel != null){
                modelTreeSelect = new ModelTreeSelect();
                modelTreeSelect.setId(plateClass.getId());
                modelTreeSelect.setLabel(plateClass.getName());
                modelTreeSelect.setCode("plateClass");
                List<ModelTreeSelect> modelTreeSelects = firstModel.getChildren();
                if(modelTreeSelects == null){
                    modelTreeSelects = new ArrayList<>();
                }
                modelTreeSelects.add(modelTreeSelect);
                firstModel.setChildren(modelTreeSelects);
                indexMap.put("plateClass_" + plateClass.getId(), modelTreeSelect);
            }
        }
        System.out.println(JSONObject.toJSONString(list));



        List<TbPlateCutWay> plateCutWays = iTbPlateCutWayService.selectTbPlateCutWayList(null);

        for(TbPlateCutWay plateCutWay: plateCutWays){
            ModelTreeSelect firstModel = (ModelTreeSelect) indexMap.get("plateClass_"+plateCutWay.getParentId());
            if(firstModel != null){
                modelTreeSelect = new ModelTreeSelect();
                modelTreeSelect.setId(plateCutWay.getId());
                modelTreeSelect.setLabel(plateCutWay.getName());
                modelTreeSelect.setCode("plateCutWay");
                List<ModelTreeSelect> modelTreeSelects = firstModel.getChildren();
                if(modelTreeSelects == null){
                    modelTreeSelects = new ArrayList<>();
                }
                modelTreeSelects.add(modelTreeSelect);
                firstModel.setChildren(modelTreeSelects);
                indexMap.put("plateCutWay_" + plateCutWay.getId(), modelTreeSelect);
            }
        }
        System.out.println(JSONObject.toJSONString(list));

    }*/

}

class WxpayConfig {
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


