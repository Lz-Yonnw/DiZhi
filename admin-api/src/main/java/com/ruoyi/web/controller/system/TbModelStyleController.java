package com.ruoyi.web.controller.system;

import java.util.List;

import com.ruoyi.system.domain.TbUserWardrobe;
import com.ruoyi.system.service.ITbUserWardrobeService;
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
import com.ruoyi.system.domain.TbModelStyle;
import com.ruoyi.system.service.ITbModelStyleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 版型小类Controller
 *
 * @author zzz
 * @date 2021-09-27
 */
@RestController
@RequestMapping("/system/modelStyle")
@Api(tags = "版型小类")
public class TbModelStyleController extends BaseController
{
    @Autowired
    private ITbModelStyleService tbModelStyleService;

    @Autowired
    private ITbUserWardrobeService iTbUserWardrobeService;

    /**
     * 查询版型小类列表
     */
    @ApiOperation("查询版型小类列表")
    @PreAuthorize("@ss.hasPermi('system:modelStyle:list')")
    @GetMapping("/getList")
    public AjaxResult getList(TbModelStyle tbModelStyle)
    {
        List<TbModelStyle> list = tbModelStyleService.selectTbModelStyleList(tbModelStyle);
        return AjaxResult.success(list);
    }

    /**
     * 导出版型小类列表
     */
    @ApiOperation("导出版型小类列表")
    @PreAuthorize("@ss.hasPermi('system:modelStyle:export')")
    @Log(title = "版型小类", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TbModelStyle tbModelStyle)
    {
        List<TbModelStyle> list = tbModelStyleService.selectTbModelStyleList(tbModelStyle);
        ExcelUtil<TbModelStyle> util = new ExcelUtil<TbModelStyle>(TbModelStyle.class);
        return util.exportExcel(list, "modelStyle");
    }

    /**
     * 获取版型小类详细信息
     */
    @ApiOperation("获取版型小类详细信息")
    @PreAuthorize("@ss.hasPermi('system:modelStyle:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbModelStyleService.selectTbModelStyleById(id));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/asList")
    public TableDataInfo<List<TbModelStyle>> dictType(TbModelStyle tbModelStyle)
    {
        startPage();
        List<TbModelStyle> modelStyles = tbModelStyleService.selectTbModelStyleList(tbModelStyle);
        return getDataTable(modelStyles);
    }

    /**
     * 新增版型小类
     */
    @ApiOperation("新增版型小类")
    @PreAuthorize("@ss.hasPermi('system:modelStyle:add')")
    @Log(title = "版型小类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbModelStyle tbModelStyle)
    {
        return toAjax(tbModelStyleService.insertTbModelStyle(tbModelStyle));
    }

    /**
     * 修改版型小类
     */
    @ApiOperation("修改版型小类")
    @PreAuthorize("@ss.hasPermi('system:modelStyle:edit')")
    @Log(title = "版型小类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbModelStyle tbModelStyle)
    {
        TbUserWardrobe tbUserWardrobe = iTbUserWardrobeService.selectTbUserWardrobeById(tbModelStyle.getWardrobeId());
        tbModelStyle.setClothesJson(tbUserWardrobe.getClothesJson());
        return toAjax(tbModelStyleService.updateTbModelStyle(tbModelStyle));
    }

    /**
     * 删除版型小类
     */
    @ApiOperation("删除版型小类")
    @PreAuthorize("@ss.hasPermi('system:modelStyle:remove')")
    @Log(title = "版型小类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbModelStyleService.deleteTbModelStyleByIds(ids));
    }
}
