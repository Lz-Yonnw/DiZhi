package com.ruoyi.web.controller.system;

import com.alibaba.fastjson.JSONArray;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.TbMallCoupon;
import com.ruoyi.system.service.ITbMallCouponService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;
import java.util.Map;

/**
 * 商品+优惠劵中间Controller
 *
 * @author ruoyi
 * @date 2022-05-31
 */
@RestController
@RequestMapping("/system/mallCoupon")
public class TbMallCouponController extends BaseController
{
    @Autowired
    private ITbMallCouponService tbMallCouponService;


    /**
     * 查询商品+优惠劵中间列表
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbMallCoupon tbMallCoupon)
    {
        startPage();
        List<TbMallCoupon> list = tbMallCouponService.selectTbMallCouponList(tbMallCoupon);
        return getDataTable(list);
    }

    /**
     * 导出商品+优惠劵中间列表
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:export')")
    @Log(title = "商品+优惠劵中间", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbMallCoupon tbMallCoupon)
    {
        List<TbMallCoupon> list = tbMallCouponService.selectTbMallCouponList(tbMallCoupon);
        ExcelUtil<TbMallCoupon> util = new ExcelUtil<TbMallCoupon>(TbMallCoupon.class);
        util.exportExcel(list, "商品+优惠劵中间数据");
    }

    /**
     * 获取商品+优惠劵中间详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbMallCouponService.selectTbMallCouponById(id));
    }

    /**
     * 新增商品+优惠劵中间
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:add')")
    @Log(title = "商品+优惠劵中间", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbMallCoupon tbMallCoupon)
    {
        return toAjax(tbMallCouponService.insertTbMallCoupon(tbMallCoupon));
    }

    /**
     * 商品关联优惠劵接口
     * @return
     */
    @PostMapping("/addMultiple")
    public AjaxResult addMultiple(@RequestBody Map<String,String> map, HttpServletRequest request){
        JSONArray jsonArray = JSONArray.parseArray(map.get("ids"));
        Long mallId = Long.valueOf(map.get("mallId"));
        for(int i=0;i<jsonArray.size();i++) {
            Long id = jsonArray.getLong(i);
            TbMallCoupon tbMallCoupon = new TbMallCoupon();
            tbMallCoupon.setMallId(mallId);
            tbMallCoupon.setCouponId(id);
            tbMallCoupon.setStatus(1L);
            tbMallCouponService.insertTbMallCoupon(tbMallCoupon);
        }
        return AjaxResult.success(1);
    }

    /**
     * 取消商品优惠劵关联
     * @param id
     * @return
     */
    @ApiOperation("取消商品关联")
    @DeleteMapping("/delMallCoupon/{id}")
    public AjaxResult removeMallCoupon(@PathVariable("id") Long id){
        TbMallCoupon tbMallCoupon = new TbMallCoupon();
        tbMallCoupon.setId(id);
        tbMallCoupon.setStatus(0L);
        return AjaxResult.success(tbMallCouponService.updateTbMallCoupon(tbMallCoupon));
    }

    /**
     * 修改商品+优惠劵中间
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:edit')")
    @Log(title = "商品+优惠劵中间", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbMallCoupon tbMallCoupon)
    {
        return toAjax(tbMallCouponService.updateTbMallCoupon(tbMallCoupon));
    }

    /**
     * 删除商品+优惠劵中间
     */
    @PreAuthorize("@ss.hasPermi('system:coupon:remove')")
    @Log(title = "商品+优惠劵中间", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbMallCouponService.deleteTbMallCouponByIds(ids));
    }

    /**
     * 未关联的商品优惠劵
     * @param id
     * @return
     */
    @GetMapping("/isNoCouponList/{id}")
    public AjaxResult getMallIsNoAssociatedCoupon(@PathVariable("id") Long id){
        return AjaxResult.success(tbMallCouponService.selectTbMallCouponByMallIdNoCoupon(id));
    }
}
