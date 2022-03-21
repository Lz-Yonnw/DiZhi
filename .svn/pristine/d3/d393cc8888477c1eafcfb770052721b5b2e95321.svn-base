package com.ruoyi.web.controller.system;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.ruoyi.system.domain.TbClassify;
import com.ruoyi.system.service.ITbClassifyService;
import com.ruoyi.system.service.ITbPatternComponentService;
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
import io.swagger.annotations.ApiOperation;
import com.ruoyi.system.domain.TbPattern;
import com.ruoyi.system.service.ITbPatternService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 材料图片Controller
 *
 * @author zzz
 * @date 2021-09-13
 */
@RestController
@RequestMapping("/system/pattern")
@Api(tags = "材料图片")
public class TbPatternController extends BaseController
{
    @Autowired
    private ITbPatternService tbPatternService;

    @Autowired
    private ITbClassifyService tbClassifyService;

    @Autowired
    private ITbPatternComponentService patternComponentService;

    /**
     * 查询材料图片列表
     */
    @ApiOperation("查询材料图片列表")
    @PreAuthorize("@ss.hasPermi('system:pattern:list')")
    @GetMapping("/list")
    public TableDataInfo<List<TbPattern>> list(TbPattern tbPattern)
    {
        startPage();
        List<TbPattern> list = tbPatternService.selectTbPatternList(tbPattern);
        return getDataTable(list);
    }

    /**
     * 导出材料图片列表
     */
    @ApiOperation("导出材料图片列表")
    @PreAuthorize("@ss.hasPermi('system:pattern:export')")
    @Log(title = "材料图片", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TbPattern tbPattern)
    {
        List<TbPattern> list = tbPatternService.selectTbPatternList(tbPattern);
        ExcelUtil<TbPattern> util = new ExcelUtil<TbPattern>(TbPattern.class);
        return util.exportExcel(list, "pattern");
    }

    /**
     * 获取材料图片详细信息
     */
    @ApiOperation("获取材料图片详细信息")
    @PreAuthorize("@ss.hasPermi('system:pattern:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbPatternService.selectTbPatternById(id));
    }

    /**
     * 新增材料图片
     */
    @ApiOperation("新增材料图片")
    @PreAuthorize("@ss.hasPermi('system:pattern:add')")
    @Log(title = "材料图片", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbPattern tbPattern)
    {
        return toAjax(tbPatternService.insertTbPattern(tbPattern));
    }

    /**
     * 修改材料图片
     */
    @ApiOperation("修改材料图片")
    @PreAuthorize("@ss.hasPermi('system:pattern:edit')")
    @Log(title = "材料图片", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbPattern tbPattern)
    {
        return toAjax(tbPatternService.updateTbPattern(tbPattern));
    }

    /**
     * 删除材料图片
     */
    @ApiOperation("删除材料图片")
    @PreAuthorize("@ss.hasPermi('system:pattern:remove')")
    @Log(title = "材料图片", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbPatternService.deleteTbPatternByIds(ids));
    }

    @GetMapping("/watChPicture")
    public AjaxResult watChPicture(Long parentId,Long pId,Integer type)
    {
        TbClassify classify = new TbClassify();
        classify.setParentId(parentId);
        classify.setPId(pId);
        classify.setType(type);
        List<HashMap> result=new ArrayList<>();
        List<TbClassify> classifies = tbClassifyService.selectTbClassifyList(classify);
        for (TbClassify in:classifies) {
            HashMap hashMap = new HashMap();
            Integer typeId = in.getType();
            TbPattern tbPattern = new TbPattern();
//            tbPattern.setTypeId(typeId+"");
            tbPattern.setClassId(in.getId());
            hashMap.put("className",in.getName());
            hashMap.put("classId",in.getId());
            List<TbPattern> tbPatterns = tbPatternService.selectTbPatternByAllClassPicture(tbPattern);
            hashMap.put("patterns",tbPatterns);
            result.add(hashMap);
        }
        return AjaxResult.success(result);
    }

    @GetMapping("/removePatternImg")
    public AjaxResult removePatternImg(Long id){
        patternComponentService.deleteTbPatternComponentByPatternId(id);
        return AjaxResult.success(tbPatternService.deleteTbPatternById(id));
    }

}
