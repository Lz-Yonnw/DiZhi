package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.system.domain.TbModelStyle;
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
import com.ruoyi.system.domain.TbPlateClass;
import com.ruoyi.system.service.ITbPlateClassService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 板块大类-部位Controller
 *
 * @author zzz
 * @date 2021-09-13
 */
@RestController
@RequestMapping("/system/class")
@Api(tags = "板块大类-部位")
public class TbPlateClassController extends BaseController
{
    @Autowired
    private ITbPlateClassService tbPlateClassService;

    /**
     * 查询板块大类-部位列表
     */
    @ApiOperation("查询板块大类-部位列表")
    @PreAuthorize("@ss.hasPermi('system:class:list')")
    @GetMapping("/list")
    public TableDataInfo<List<TbPlateClass>> list(TbPlateClass tbPlateClass)
    {
        startPage();
        List<TbPlateClass> list = tbPlateClassService.selectTbPlateClassList(tbPlateClass);
        return getDataTable(list);
    }

    @GetMapping(value = "/asList")
    public TableDataInfo<List<TbPlateClass>> asList(TbPlateClass tbPlateClass)
    {
        startPage();
        List<TbPlateClass>  plateClasses= tbPlateClassService.selectTbPlateClassList(tbPlateClass);
        return getDataTable(plateClasses);
    }

    /**
     * 导出板块大类-部位列表
     */
    @ApiOperation("导出板块大类-部位列表")
    @PreAuthorize("@ss.hasPermi('system:class:export')")
    @Log(title = "板块大类-部位", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TbPlateClass tbPlateClass)
    {
        List<TbPlateClass> list = tbPlateClassService.selectTbPlateClassList(tbPlateClass);
        ExcelUtil<TbPlateClass> util = new ExcelUtil<TbPlateClass>(TbPlateClass.class);
        return util.exportExcel(list, "class");
    }

    /**
     * 获取板块大类-部位详细信息
     */
    @ApiOperation("获取板块大类-部位详细信息")
    @PreAuthorize("@ss.hasPermi('system:class:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbPlateClassService.selectTbPlateClassById(id));
    }

    /**
     * 新增板块大类-部位
     */
    @ApiOperation("新增板块大类-部位")
    @PreAuthorize("@ss.hasPermi('system:class:add')")
    @Log(title = "板块大类-部位", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbPlateClass tbPlateClass)
    {
        return toAjax(tbPlateClassService.insertTbPlateClass(tbPlateClass));
    }

    /**
     * 修改板块大类-部位
     */
    @ApiOperation("修改板块大类-部位")
    @PreAuthorize("@ss.hasPermi('system:class:edit')")
    @Log(title = "板块大类-部位", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbPlateClass tbPlateClass)
    {
        return toAjax(tbPlateClassService.updateTbPlateClass(tbPlateClass));
    }

    /**
     * 删除板块大类-部位
     */
    @ApiOperation("删除板块大类-部位")
    @PreAuthorize("@ss.hasPermi('system:class:remove')")
    @Log(title = "板块大类-部位", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbPlateClassService.deleteTbPlateClassByIds(ids));
    }
}
