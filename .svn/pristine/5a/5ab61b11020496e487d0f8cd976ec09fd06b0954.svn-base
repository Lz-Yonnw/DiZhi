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
import com.ruoyi.system.domain.TbComponent;
import com.ruoyi.system.service.ITbComponentService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 部件Controller
 *
 * @author zzz
 * @date 2021-08-16
 */
@RestController
@RequestMapping("/system/component")
@Api(tags = "部件")
public class TbComponentController extends BaseController
{
    @Autowired
    private ITbComponentService tbComponentService;

    /**
     * 查询部件列表
     */
    @ApiOperation("查询部件列表")
    @PreAuthorize("@ss.hasPermi('system:component:list')")
    @GetMapping("/list")
    public TableDataInfo<List<TbComponent>> list(TbComponent tbComponent)
    {
        startPage();
        List<TbComponent> list = tbComponentService.selectTbComponentList(tbComponent);
        return getDataTable(list);
    }

    /**
     * 导出部件列表
     */
    @ApiOperation("导出部件列表")
    @PreAuthorize("@ss.hasPermi('system:component:export')")
    @Log(title = "部件", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TbComponent tbComponent)
    {
        List<TbComponent> list = tbComponentService.selectTbComponentList(tbComponent);
        ExcelUtil<TbComponent> util = new ExcelUtil<TbComponent>(TbComponent.class);
        return util.exportExcel(list, "component");
    }

    /**
     * 获取部件详细信息
     */
    @ApiOperation("获取部件详细信息")
    @PreAuthorize("@ss.hasPermi('system:component:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbComponentService.selectTbComponentById(id));
    }

    /**
     * 新增部件
     */
    @ApiOperation("新增部件")
    @PreAuthorize("@ss.hasPermi('system:component:add')")
    @Log(title = "部件", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbComponent tbComponent)
    {
        return toAjax(tbComponentService.insertTbComponent(tbComponent));
    }

    /**
     * 修改部件
     */
    @ApiOperation("修改部件")
    @PreAuthorize("@ss.hasPermi('system:component:edit')")
    @Log(title = "部件", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbComponent tbComponent)
    {
        return toAjax(tbComponentService.updateTbComponent(tbComponent));
    }

    /**
     * 删除部件
     */
    @ApiOperation("删除部件")
    @PreAuthorize("@ss.hasPermi('system:component:remove')")
    @Log(title = "部件", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbComponentService.deleteTbComponentByIds(ids));
    }
}
