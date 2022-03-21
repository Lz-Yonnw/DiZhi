package com.ruoyi.web.controller.system;

import java.util.List;
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
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import com.ruoyi.system.domain.TbBanner;
import com.ruoyi.system.service.ITbBannerService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * banner列表Controller
 *
 * @author zzz
 * @date 2021-09-22
 */
@RestController
@RequestMapping("/system/banner")
@Api(tags = "banner列表")
public class TbBannerController extends BaseController
{
    @Autowired
    private ITbBannerService tbBannerService;

    /**
     * 查询banner列表列表
     */
    @ApiOperation("查询banner列表列表")
    @PreAuthorize("@ss.hasPermi('system:banner:list')")
    @GetMapping("/list")
    public TableDataInfo<List<TbBanner>> list(TbBanner tbBanner)
    {
        startPage();
        List<TbBanner> list = tbBannerService.selectTbBannerList(tbBanner);
        return getDataTable(list);
    }

    /**
     * 导出banner列表列表
     */
    @ApiOperation("导出banner列表列表")
    @PreAuthorize("@ss.hasPermi('system:banner:export')")
    @Log(title = "banner列表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TbBanner tbBanner)
    {
        List<TbBanner> list = tbBannerService.selectTbBannerList(tbBanner);
        ExcelUtil<TbBanner> util = new ExcelUtil<TbBanner>(TbBanner.class);
        return util.exportExcel(list, "banner");
    }

    /**
     * 获取banner列表详细信息
     */
    @ApiOperation("获取banner列表详细信息")
    @PreAuthorize("@ss.hasPermi('system:banner:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbBannerService.selectTbBannerById(id));
    }

    /**
     * 新增banner列表
     */
    @ApiOperation("新增banner列表")
    @PreAuthorize("@ss.hasPermi('system:banner:add')")
    @Log(title = "banner列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbBanner tbBanner)
    {
        return toAjax(tbBannerService.insertTbBanner(tbBanner));
    }

    /**
     * 修改banner列表
     */
    @ApiOperation("修改banner列表")
    @PreAuthorize("@ss.hasPermi('system:banner:edit')")
    @Log(title = "banner列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbBanner tbBanner)
    {
        return toAjax(tbBannerService.updateTbBanner(tbBanner));
    }

    /**
     * 删除banner列表
     */
    @ApiOperation("删除banner列表")
    @PreAuthorize("@ss.hasPermi('system:banner:remove')")
    @Log(title = "banner列表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbBannerService.deleteTbBannerByIds(ids));
    }
}
