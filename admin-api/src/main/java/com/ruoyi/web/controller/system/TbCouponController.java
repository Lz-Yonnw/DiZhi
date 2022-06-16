package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.TbCoupon;
import com.ruoyi.system.service.ITbCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 优惠劵Controller
 *
 * @author ruoyi
 * @date 2022-05-31
 */
@RestController
@RequestMapping("/system/coupon")
public class TbCouponController extends BaseController
{
    @Autowired
    private ITbCouponService tbCouponService;


    /**
     * 查询优惠劵列表
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbCoupon tbCoupon)
    {
        startPage();
        List<TbCoupon> list = tbCouponService.selectTbCouponList(tbCoupon);
        return getDataTable(list);
    }

    /**
     * 导出优惠劵列表
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:export')")
    @Log(title = "优惠劵", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbCoupon tbCoupon)
    {
        List<TbCoupon> list = tbCouponService.selectTbCouponList(tbCoupon);
        ExcelUtil<TbCoupon> util = new ExcelUtil<TbCoupon>(TbCoupon.class);
        util.exportExcel(list, "优惠劵数据");
    }

    /**
     * 获取优惠劵详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbCouponService.selectTbCouponById(id));
    }

    /**
     * 新增优惠劵
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:add')")
    @Log(title = "优惠劵", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbCoupon tbCoupon)
    {
        return toAjax(tbCouponService.insertTbCoupon(tbCoupon));
    }

    /**
     * 修改优惠劵
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:edit')")
    @Log(title = "优惠劵", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbCoupon tbCoupon)
    {
        return toAjax(tbCouponService.updateTbCoupon(tbCoupon));
    }

    /**
     * 删除优惠劵
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:remove')")
    @Log(title = "优惠劵", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbCouponService.deleteTbCouponByIds(ids));
    }
}
