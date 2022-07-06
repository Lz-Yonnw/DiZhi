package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.TbClassify;
import com.ruoyi.system.domain.TbPatternComponent;
import com.ruoyi.system.service.ITbClassifyService;
import com.ruoyi.system.service.ITbPatternComponentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 图案部件中间Controller
 *
 * @author zzz
 * @date 2021-09-15
 */
@RestController
@RequestMapping("/system/componentPattern")
@Api(tags = "图案部件中间")
public class TbPatternComponentController extends BaseController
{
    @Autowired
    private ITbPatternComponentService tbPatternComponentService;

    @Autowired
    private ITbClassifyService iTbClassifyService;

    /**
     * 查询图案部件中间列表
     */
    @ApiOperation("查询图案部件中间列表")
    @PreAuthorize("@ss.hasPermi('system:componentPattern:list')")
    @GetMapping("/list")
    public TableDataInfo<List<TbPatternComponent>> list(TbPatternComponent tbPatternComponent)
    {
        startPage();
        List<TbPatternComponent> list = tbPatternComponentService.selectTbPatternComponentList(tbPatternComponent);
        return getDataTable(list);
    }

    /**
     * 导出图案部件中间列表
     */
    @ApiOperation("导出图案部件中间列表")
    @PreAuthorize("@ss.hasPermi('system:componentPattern:export')")
    @Log(title = "图案部件中间", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TbPatternComponent tbPatternComponent)
    {
        List<TbPatternComponent> list = tbPatternComponentService.selectTbPatternComponentList(tbPatternComponent);
        ExcelUtil<TbPatternComponent> util = new ExcelUtil<TbPatternComponent>(TbPatternComponent.class);
        return util.exportExcel(list, "componentPattern");
    }

    /**
     * 获取图案部件中间详细信息
     */
    @ApiOperation("获取图案部件中间详细信息")
    @PreAuthorize("@ss.hasPermi('system:componentPattern:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbPatternComponentService.selectTbPatternComponentById(id));
    }

    /**
     * 新增图案部件中间
     */
    @ApiOperation("新增图案部件中间")
    @PreAuthorize("@ss.hasPermi('system:componentPattern:add')")
    @Log(title = "图案部件中间", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbPatternComponent tbPatternComponent)
    {
        return toAjax(tbPatternComponentService.insertTbPatternComponent(tbPatternComponent));
    }

    /**
     * 修改图案部件中间
     */
    @ApiOperation("修改图案部件中间")
    @PreAuthorize("@ss.hasPermi('system:componentPattern:edit')")
    @Log(title = "图案部件中间", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbPatternComponent tbPatternComponent)
    {
        return toAjax(tbPatternComponentService.updateTbPatternComponent(tbPatternComponent));
    }

    /**
     * 删除图案部件中间
     */
    @ApiOperation("删除图案部件中间")
    @PreAuthorize("@ss.hasPermi('system:componentPattern:remove')")
    @Log(title = "图案部件中间", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbPatternComponentService.deleteTbPatternComponentByIds(ids));
    }

    /***
     * 模型分类下的所有面料
     * @param id 分类ID
     * @return
     */
    @GetMapping("/getClassifyPictureById")
    public AjaxResult getClassifyPictureById(Long id)
    {
        TbPatternComponent patternComponent = new TbPatternComponent();
        patternComponent.setClassId(id);
        List<TbPatternComponent> tbPatternComponents = tbPatternComponentService.selectTbPatternComponentAllList(patternComponent);
        return AjaxResult.success(tbPatternComponents);
    }

    @GetMapping("/updateComponentImg")
    public AjaxResult updateComponentImg(Integer val,Long id,Long classId)
    {
        TbClassify classify = iTbClassifyService.selectTbClassifyById(classId);
        TbPatternComponent patternComponent = new TbPatternComponent();
        patternComponent.setClassId(classId);
        patternComponent.setComponentId(classify.getParentId());
        patternComponent.setPatternId(id);
        if (val==0) {
            //删除
            return AjaxResult.success(tbPatternComponentService.deleteTbPatternComponent(patternComponent));
        }else if (val==1){
            //新增
            patternComponent.setCreateTime(DateUtils.getNowDate());
            return AjaxResult.success(tbPatternComponentService.insertTbPatternComponent(patternComponent));
        }
        return AjaxResult.error("系统有误");
    }

}
