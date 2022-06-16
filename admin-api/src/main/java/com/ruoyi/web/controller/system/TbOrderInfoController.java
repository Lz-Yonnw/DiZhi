package com.ruoyi.web.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.TbOrderInfo;
import com.ruoyi.system.service.ITbOrderInfoService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 订单详情
 * @author liaozan8888@163.com
 * @date 2022-5-5
 */
@RestController
@Api(tags = "订单详情")
@RequestMapping("/system/order/info/")
public class TbOrderInfoController extends BaseController {

    @Autowired
    private ITbOrderInfoService tbOrderInfoService;

    /**
     * 查询订单信息
     列表
     */
    @GetMapping("/list")
    public TableDataInfo<List<TbOrderInfo>> list(TbOrderInfo tbOrderInfo)
    {
        startPage();
        List<TbOrderInfo> tbOrderInfos = tbOrderInfoService.selectTbOrderInfoList(tbOrderInfo);
        return getDataTable(tbOrderInfos);
    }
    /**
     * 获取订单信息详细信息
     */
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbOrderInfoService.selectTbOrderInfoById(id));
    }

    /**
     * 删除订单
     * @param params    请求参数
     * @return
     */
    @PostMapping(value = "/del")
    public AjaxResult deleteById(@RequestBody Map<String, Object> params)
    {
        return toAjax(tbOrderInfoService.deleteTbOrderInfoById(Long.valueOf(params.get("orderInfoId").toString()), (String) params.get("delName")));
    }

    /**
     * 修改订单
     * @param jsonObject
     * @return
     */
    @PostMapping(value = "/update")
    public AjaxResult updateTbOrderInfo(@RequestBody JSONObject jsonObject)
    {
        //订单对象
        String orderInfo = jsonObject.getString("orderInfo");
        TbOrderInfo tbOrderInfo = JSONObject.parseObject(orderInfo, TbOrderInfo.class);
        //更改的图片
        String list = jsonObject.getString("list");
        return toAjax(tbOrderInfoService.updateTbOrderInfoById(tbOrderInfo,list));
    }

    /**
     * 订单状态变跟
     * @param id
     * @param status
     * @return
     */
    @PutMapping(value = "/updateStatus")
    public AjaxResult updateTbOrderInfoStatus(Long id,Long status){
        return toAjax(tbOrderInfoService.updateTborderInfoStatusById(id,status));
    }


    /**
     * 添加订单
     * @param mallId
     * @param addressId
     * @param remark
     * @param colour
     * @param size
     * @param pay
     * @param amount
     * @param quantity
     * @param userId
     * @param storeId
     * @param modelType
     * @param clothesJson
     * @return
     */
    @PostMapping(value = "/insert")
    public AjaxResult insertTbOrderInfo(int mallId, int addressId, String remark, String colour, String size, int pay, String amount, int quantity, int userId, int storeId,Long modelType,String clothesJson){
        String s = tbOrderInfoService.insertOrderInfo(mallId, addressId, remark, colour, size, pay, amount, quantity, userId, storeId, modelType, clothesJson);
        return toAjax(Integer.valueOf(s));
    }

}
