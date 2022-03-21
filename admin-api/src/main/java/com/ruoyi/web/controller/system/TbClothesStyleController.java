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
import com.ruoyi.system.domain.TbClothesStyle;
import com.ruoyi.system.service.ITbClothesStyleService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 爆款列表Controller
 *
 * @author zzz
 * @date 2021-09-25
 */
@RestController
@RequestMapping("/system/clothesStyle")
@Api(tags = "爆款列表")
public class TbClothesStyleController extends BaseController
{
    @Autowired
    private ITbClothesStyleService tbClothesStyleService;

    @Autowired
    private ITbUserWardrobeService iTbUserWardrobeService;

    /**
     * 查询爆款列表列表
     */
    @ApiOperation("查询爆款列表列表")
    @PreAuthorize("@ss.hasPermi('system:clothesStyle:list')")
    @GetMapping("/list")
    public TableDataInfo<List<TbClothesStyle>> list(TbClothesStyle tbClothesStyle)
    {
        startPage();
        List<TbClothesStyle> list = tbClothesStyleService.selectTbClothesStyleList(tbClothesStyle);
        return getDataTable(list);
    }

    /**
     * 导出爆款列表列表
     */
    @ApiOperation("导出爆款列表列表")
    @PreAuthorize("@ss.hasPermi('system:clothesStyle:export')")
    @Log(title = "爆款列表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TbClothesStyle tbClothesStyle)
    {
        List<TbClothesStyle> list = tbClothesStyleService.selectTbClothesStyleList(tbClothesStyle);
        ExcelUtil<TbClothesStyle> util = new ExcelUtil<TbClothesStyle>(TbClothesStyle.class);
        return util.exportExcel(list, "clothesStyle");
    }

    /**
     * 获取爆款列表详细信息
     */
    @ApiOperation("获取爆款列表详细信息")
    @PreAuthorize("@ss.hasPermi('system:clothesStyle:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbClothesStyleService.selectTbClothesStyleById(id));
    }

    /**
     * 新增爆款列表
     */
    @ApiOperation("新增爆款列表")
    @PreAuthorize("@ss.hasPermi('system:clothesStyle:add')")
    @Log(title = "爆款列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbClothesStyle tbClothesStyle)
    {
        TbUserWardrobe tbUserWardrobe = iTbUserWardrobeService.selectTbUserWardrobeById(tbClothesStyle.getId());
        tbClothesStyle.setClothesJson(tbUserWardrobe.getClothesJson());
        tbClothesStyle.setQrCode(tbUserWardrobe.getQrCode());
        return toAjax(tbClothesStyleService.insertTbClothesStyle(tbClothesStyle));
    }

    /**
     * 修改爆款列表
     */
    @ApiOperation("修改爆款列表")
    @PreAuthorize("@ss.hasPermi('system:clothesStyle:edit')")
    @Log(title = "爆款列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbClothesStyle tbClothesStyle)
    {
        return toAjax(tbClothesStyleService.updateTbClothesStyle(tbClothesStyle));
    }

    /**
     * 删除爆款列表
     */
    @ApiOperation("删除爆款列表")
    @PreAuthorize("@ss.hasPermi('system:clothesStyle:remove')")
    @Log(title = "爆款列表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbClothesStyleService.deleteTbClothesStyleByIds(ids));
    }
}
