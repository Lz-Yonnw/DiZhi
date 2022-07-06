package com.ruoyi.system.controller;

import java.util.List;
import javax.servlet.http.HttpServletResponse;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.system.domain.TbOrderDerails;
import com.ruoyi.system.service.ITbOrderDerailsService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 订单详情Controller
 *
 * @author ruoyi
 * @date 2022-07-05
 */
@RestController
@RequestMapping("/system/derails")
public class TbOrderDerailsController extends BaseController
{
    @Autowired
    private ITbOrderDerailsService tbOrderDerailsService;

    /**
     * 查询订单详情列表
     */
    @PreAuthorize("@ss.hasPermi('system:derails:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbOrderDerails tbOrderDerails)
    {
        startPage();
        List<TbOrderDerails> list = tbOrderDerailsService.selectTbOrderDerailsList(tbOrderDerails);
        return getDataTable(list);
    }

    /**
     * 导出订单详情列表
     */
    @PreAuthorize("@ss.hasPermi('system:derails:export')")
    @Log(title = "订单详情", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbOrderDerails tbOrderDerails)
    {
        List<TbOrderDerails> list = tbOrderDerailsService.selectTbOrderDerailsList(tbOrderDerails);
        ExcelUtil<TbOrderDerails> util = new ExcelUtil<TbOrderDerails>(TbOrderDerails.class);
        util.exportExcel(list, "订单详情数据");
    }

    /**
     * 获取订单详情详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:derails:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbOrderDerailsService.selectTbOrderDerailsById(id));
    }

    /**
     * 新增订单详情
     */
    @PreAuthorize("@ss.hasPermi('system:derails:add')")
    @Log(title = "订单详情", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbOrderDerails tbOrderDerails)
    {
        return toAjax(tbOrderDerailsService.insertTbOrderDerails(tbOrderDerails));
    }

    /**
     * 修改订单详情
     */
    @PreAuthorize("@ss.hasPermi('system:derails:edit')")
    @Log(title = "订单详情", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbOrderDerails tbOrderDerails)
    {
        return toAjax(tbOrderDerailsService.updateTbOrderDerails(tbOrderDerails));
    }

    /**
     * 删除订单详情
     */
    @PreAuthorize("@ss.hasPermi('system:derails:remove')")
    @Log(title = "订单详情", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbOrderDerailsService.deleteTbOrderDerailsByIds(ids));
    }
}
