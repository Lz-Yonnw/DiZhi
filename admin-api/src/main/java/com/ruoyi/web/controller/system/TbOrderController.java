package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.netty.util.PayUtil;
import com.ruoyi.system.domain.TbOrder;
import com.ruoyi.system.service.ITbOrderService;
import com.wechat.pay.contrib.apache.httpclient.exception.HttpCodeException;
import com.wechat.pay.contrib.apache.httpclient.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.math.BigDecimal;
import java.security.GeneralSecurityException;
import java.util.List;

/**
 * 订单Controller
 *
 * @author ruoyi
 * @date 2022-07-05
 */
@RestController
@RequestMapping("/system/order")
public class TbOrderController extends BaseController
{
    @Autowired
    private ITbOrderService tbOrderService;

    /**
     * 查询订单列表
     */
    @PreAuthorize("@ss.hasPermi('system:order:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbOrder tbOrder)
    {
            startPage();
            List<TbOrder> list = tbOrderService.selectTbOrderDerailsList(tbOrder.getOrderSn(),tbOrder.getStatus(),tbOrder.getModelType(),null);
            return getDataTable(list);
    }

    /**
     * 导出订单列表
     */
    @PreAuthorize("@ss.hasPermi('system:order:export')")
    @Log(title = "订单", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbOrder tbOrder)
    {
        List<TbOrder> list = tbOrderService.selectTbOrderList(tbOrder);
        ExcelUtil<TbOrder> util = new ExcelUtil<TbOrder>(TbOrder.class);
        util.exportExcel(list, "订单数据");
    }

    /**
     * 获取订单详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:order:query')")
    @GetMapping(value = "/{orderSn}")
    public AjaxResult getInfo(@PathVariable("orderSn") String orderSn)
    {
        return AjaxResult.success(tbOrderService.selectTbOrderByOrderSn(orderSn));
    }

    /**
     * 新增订单
     */
    @PreAuthorize("@ss.hasPermi('system:order:add')")
    @Log(title = "订单", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbOrder tbOrder)
    {
        //return toAjax(tbOrderService.insertTbOrder(tbOrder));
        return null;
    }

    /**
     * 修改订单
     */
    @PreAuthorize("@ss.hasPermi('system:order:edit')")
    @Log(title = "订单", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbOrder tbOrder)
    {
        return toAjax(tbOrderService.updateTbOrder(tbOrder));
    }
    /**
     * 退款接口
     */
    @PostMapping("/refund/{orderSn}")
    public AjaxResult refund(@PathVariable("orderSn") String orderSn){
        TbOrder tbOrder = tbOrderService.selectTbOrderDerailsList(orderSn, null, null, null).get(0);
        switch (tbOrder.getPayType()){
            case 1:
                //微信退款
                try {
                    if(tbOrder.getStatus()==6 || tbOrder.getStatus()==11){
                        PayUtil payUtil = new PayUtil();
                        String refunds = payUtil.refunds(tbOrder.getOrderSn(), tbOrder.getRemark(), tbOrder.getPayAmount().multiply(new BigDecimal(100)).intValue(), tbOrder.getPayAmount().multiply(new BigDecimal(100)).intValue());
                        return AjaxResult.success(refunds);
                    }else {
                        return AjaxResult.error();
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (HttpCodeException e) {
                    e.printStackTrace();
                } catch (GeneralSecurityException e) {
                    e.printStackTrace();
                } catch (NotFoundException e) {
                    e.printStackTrace();
                }
                break;
            case 2:
                break;
            case 3:
                break;
        }
        return null;
    }



    /**
     * 删除订单
     */
//    @PreAuthorize("@ss.hasPermi('system:order:remove')")
//    @Log(title = "订单", businessType = BusinessType.DELETE)
//	@DeleteMapping("/{ids}")
//    public AjaxResult remove(@PathVariable Long[] ids)
//    {
//        return toAjax(tbOrderService.deleteTbOrderByIds(ids));
//    }
}
