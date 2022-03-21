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
import com.ruoyi.system.domain.TbModelType;
import com.ruoyi.system.service.ITbModelTypeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 版型大类列表Controller
 *
 * @author zzz
 * @date 2021-09-27
 */
@RestController
@RequestMapping("/system/modelType")
@Api(tags = "版型大类列表")
public class TbModelTypeController extends BaseController
{
    @Autowired
    private ITbModelTypeService tbModelTypeService;

    /**
     * 查询版型大类列表列表
     */
    @ApiOperation("查询版型大类列表列表")
    @PreAuthorize("@ss.hasPermi('system:modelType:list')")
    @GetMapping("/list")
    public TableDataInfo<List<TbModelType>> list(TbModelType tbModelType)
    {
        startPage();
        List<TbModelType> list = tbModelTypeService.selectTbModelTypeList(tbModelType);
        return getDataTable(list);
    }

    /**
     * 导出版型大类列表列表
     */
    @ApiOperation("导出版型大类列表列表")
    @PreAuthorize("@ss.hasPermi('system:modelType:export')")
    @Log(title = "版型大类列表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TbModelType tbModelType)
    {
        List<TbModelType> list = tbModelTypeService.selectTbModelTypeList(tbModelType);
        ExcelUtil<TbModelType> util = new ExcelUtil<TbModelType>(TbModelType.class);
        return util.exportExcel(list, "modelType");
    }

    /**
     * 获取版型大类列表详细信息
     */
    @ApiOperation("获取版型大类列表详细信息")
    @PreAuthorize("@ss.hasPermi('system:modelType:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbModelTypeService.selectTbModelTypeById(id));
    }

    /**
     * 新增版型大类列表
     */
    @ApiOperation("新增版型大类列表")
    @PreAuthorize("@ss.hasPermi('system:modelType:add')")
    @Log(title = "版型大类列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbModelType tbModelType)
    {
        return toAjax(tbModelTypeService.insertTbModelType(tbModelType));
    }

    /**
     * 修改版型大类列表
     */
    @ApiOperation("修改版型大类列表")
    @PreAuthorize("@ss.hasPermi('system:modelType:edit')")
    @Log(title = "版型大类列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbModelType tbModelType)
    {
        return toAjax(tbModelTypeService.updateTbModelType(tbModelType));
    }

    /**
     * 删除版型大类列表
     */
    @ApiOperation("删除版型大类列表")
    @PreAuthorize("@ss.hasPermi('system:modelType:remove')")
    @Log(title = "版型大类列表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbModelTypeService.deleteTbModelTypeByIds(ids));
    }
}
