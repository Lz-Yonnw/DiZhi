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
import com.ruoyi.system.domain.TbUserWardrobe;
import com.ruoyi.system.service.ITbUserWardrobeService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 衣柜列表Controller
 *
 * @author zzz
 * @date 2021-09-25
 */
@RestController
@RequestMapping("/system/userWardrobe")
@Api(tags = "衣柜列表")
public class TbUserWardrobeController extends BaseController
{
    @Autowired
    private ITbUserWardrobeService tbUserWardrobeService;

    /**
     * 查询衣柜列表列表
     */
    @ApiOperation("查询衣柜列表列表")
    @PreAuthorize("@ss.hasPermi('system:userWardrobe:list')")
    @GetMapping("/list")
    public TableDataInfo<List<TbUserWardrobe>> list(TbUserWardrobe tbUserWardrobe)
    {
        startPage();
        List<TbUserWardrobe> list = tbUserWardrobeService.selectTbUserWardrobeList(tbUserWardrobe);
        return getDataTable(list);
    }

    /**
     * 导出衣柜列表列表
     */
    @ApiOperation("导出衣柜列表列表")
    @PreAuthorize("@ss.hasPermi('system:userWardrobe:export')")
    @Log(title = "衣柜列表", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TbUserWardrobe tbUserWardrobe)
    {
        List<TbUserWardrobe> list = tbUserWardrobeService.selectTbUserWardrobeList(tbUserWardrobe);
        ExcelUtil<TbUserWardrobe> util = new ExcelUtil<TbUserWardrobe>(TbUserWardrobe.class);
        return util.exportExcel(list, "userWardrobe");
    }

    /**
     * 获取衣柜列表详细信息
     */
    @ApiOperation("获取衣柜列表详细信息")
    @PreAuthorize("@ss.hasPermi('system:userWardrobe:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbUserWardrobeService.selectTbUserWardrobeById(id));
    }

    /**
     * 新增衣柜列表
     */
    @ApiOperation("新增衣柜列表")
    @PreAuthorize("@ss.hasPermi('system:userWardrobe:add')")
    @Log(title = "衣柜列表", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbUserWardrobe tbUserWardrobe)
    {
        return toAjax(tbUserWardrobeService.insertTbUserWardrobe(tbUserWardrobe));
    }

    /**
     * 修改衣柜列表
     */
    @ApiOperation("修改衣柜列表")
    @PreAuthorize("@ss.hasPermi('system:userWardrobe:edit')")
    @Log(title = "衣柜列表", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbUserWardrobe tbUserWardrobe)
    {
        return toAjax(tbUserWardrobeService.updateTbUserWardrobe(tbUserWardrobe));
    }

    /**
     * 删除衣柜列表
     */
    @ApiOperation("删除衣柜列表")
    @PreAuthorize("@ss.hasPermi('system:userWardrobe:remove')")
    @Log(title = "衣柜列表", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbUserWardrobeService.deleteTbUserWardrobeByIds(ids));
    }
}
