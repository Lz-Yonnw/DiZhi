package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.system.domain.TbPlateClass;
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
import com.ruoyi.system.domain.TbPlateCutWay;
import com.ruoyi.system.service.ITbPlateCutWayService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 板块小类-裁剪方式Controller
 *
 * @author zzz
 * @date 2021-10-20
 */
@RestController
@RequestMapping("/system/way")
@Api(tags = "板块小类-裁剪方式")
public class TbPlateCutWayController extends BaseController
{
    @Autowired
    private ITbPlateCutWayService tbPlateCutWayService;

    /**
     * 查询板块小类-裁剪方式列表
     */
    @ApiOperation("查询板块小类-裁剪方式列表")
    @PreAuthorize("@ss.hasPermi('system:way:list')")
    @GetMapping("/list")
    public TableDataInfo<List<TbPlateCutWay>> list(TbPlateCutWay tbPlateCutWay)
    {
        startPage();
        List<TbPlateCutWay> list = tbPlateCutWayService.selectTbPlateCutWayList(tbPlateCutWay);
        return getDataTable(list);
    }

    @GetMapping(value = "/asList")
    public TableDataInfo<List<TbPlateCutWay>> asList(TbPlateCutWay tbPlateCutWay)
    {
        startPage();
        List<TbPlateCutWay>  plateCutWays= tbPlateCutWayService.selectTbPlateCutWayList(tbPlateCutWay);
        return getDataTable(plateCutWays);
    }


    /**
     * 导出板块小类-裁剪方式列表
     */
    @ApiOperation("导出板块小类-裁剪方式列表")
    @PreAuthorize("@ss.hasPermi('system:way:export')")
    @Log(title = "板块小类-裁剪方式", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TbPlateCutWay tbPlateCutWay)
    {
        List<TbPlateCutWay> list = tbPlateCutWayService.selectTbPlateCutWayList(tbPlateCutWay);
        ExcelUtil<TbPlateCutWay> util = new ExcelUtil<TbPlateCutWay>(TbPlateCutWay.class);
        return util.exportExcel(list, "way");
    }

    /**
     * 获取板块小类-裁剪方式详细信息
     */
    @ApiOperation("获取板块小类-裁剪方式详细信息")
    @PreAuthorize("@ss.hasPermi('system:way:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbPlateCutWayService.selectTbPlateCutWayById(id));
    }

    /**
     * 新增板块小类-裁剪方式
     */
    @ApiOperation("新增板块小类-裁剪方式")
    @PreAuthorize("@ss.hasPermi('system:way:add')")
    @Log(title = "板块小类-裁剪方式", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbPlateCutWay tbPlateCutWay)
    {
        return toAjax(tbPlateCutWayService.insertTbPlateCutWay(tbPlateCutWay));
    }

    /**
     * 修改板块小类-裁剪方式
     */
    @ApiOperation("修改板块小类-裁剪方式")
    @PreAuthorize("@ss.hasPermi('system:way:edit')")
    @Log(title = "板块小类-裁剪方式", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbPlateCutWay tbPlateCutWay)
    {
        return toAjax(tbPlateCutWayService.updateTbPlateCutWay(tbPlateCutWay));
    }

    /**
     * 删除板块小类-裁剪方式
     */
    @ApiOperation("删除板块小类-裁剪方式")
    @PreAuthorize("@ss.hasPermi('system:way:remove')")
    @Log(title = "板块小类-裁剪方式", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbPlateCutWayService.deleteTbPlateCutWayByIds(ids));
    }
}
