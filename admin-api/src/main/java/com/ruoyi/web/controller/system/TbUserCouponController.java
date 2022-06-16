package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.TbUserCoupon;
import com.ruoyi.system.service.ITbUserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 用户优惠劵Controller
 *
 * @author ruoyi
 * @date 2022-05-31
 */
@RestController
@RequestMapping("/system/userCoupon")
public class TbUserCouponController extends BaseController
{
    @Autowired
    private ITbUserCouponService tbUserCouponService;

    /**
     * 查询用户优惠劵列表
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbUserCoupon tbUserCoupon)
    {
        startPage();
        List<TbUserCoupon> list = tbUserCouponService.selectTbUserCouponList(tbUserCoupon);
        return getDataTable(list);
    }

    /**
     * 导出用户优惠劵列表
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:export')")
    @Log(title = "用户优惠劵", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbUserCoupon tbUserCoupon)
    {
        List<TbUserCoupon> list = tbUserCouponService.selectTbUserCouponList(tbUserCoupon);
        ExcelUtil<TbUserCoupon> util = new ExcelUtil<TbUserCoupon>(TbUserCoupon.class);
        util.exportExcel(list, "用户优惠劵数据");
    }

    /**
     * 获取用户优惠劵详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbUserCouponService.selectTbUserCouponById(id));
    }

    /**
     * 新增用户优惠劵
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:add')")
    @Log(title = "用户优惠劵", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbUserCoupon tbUserCoupon)
    {
        return toAjax(tbUserCouponService.insertTbUserCoupon(tbUserCoupon));
    }

    /**
     * 修改用户优惠劵
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:edit')")
    @Log(title = "用户优惠劵", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbUserCoupon tbUserCoupon)
    {
        return toAjax(tbUserCouponService.updateTbUserCoupon(tbUserCoupon));
    }

    /**
     * 删除用户优惠劵
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:remove')")
    @Log(title = "用户优惠劵", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbUserCouponService.deleteTbUserCouponByIds(ids));
    }
}
