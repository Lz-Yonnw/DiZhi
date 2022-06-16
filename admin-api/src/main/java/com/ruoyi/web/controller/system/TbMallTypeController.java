package com.ruoyi.system.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.TbMallType;
import com.ruoyi.system.service.ITbMallTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 评价类型Controller
 *
 * @author ruoyi
 * @date 2022-06-08
 */
@RestController
@RequestMapping("/system/type")
public class TbMallTypeController extends BaseController
{
    @Autowired
    private ITbMallTypeService tbMallTypeService;

    /**
     * 查询评价类型列表
     */
    @PreAuthorize("@ss.hasPermi('system:type:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbMallType tbMallType)
    {
        startPage();
        List<TbMallType> list = tbMallTypeService.selectTbMallTypeList(tbMallType);
        return getDataTable(list);
    }

    /**
     * 导出评价类型列表
     */
    @PreAuthorize("@ss.hasPermi('system:type:export')")
    @Log(title = "评价类型", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbMallType tbMallType)
    {
        List<TbMallType> list = tbMallTypeService.selectTbMallTypeList(tbMallType);
        ExcelUtil<TbMallType> util = new ExcelUtil<TbMallType>(TbMallType.class);
        util.exportExcel(list, "评价类型数据");
    }

    /**
     * 获取评价类型详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:type:query')")
    @GetMapping(value = "/{typeId}")
    public AjaxResult getInfo(@PathVariable("typeId") Long typeId)
    {
        return AjaxResult.success(tbMallTypeService.selectTbMallTypeByTypeId(typeId));
    }

    /**
     * 新增评价类型
     */
    @PreAuthorize("@ss.hasPermi('system:type:add')")
    @Log(title = "评价类型", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbMallType tbMallType)
    {
        return toAjax(tbMallTypeService.insertTbMallType(tbMallType));
    }

    /**
     * 修改评价类型
     */
    @PreAuthorize("@ss.hasPermi('system:type:edit')")
    @Log(title = "评价类型", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbMallType tbMallType)
    {
        return toAjax(tbMallTypeService.updateTbMallType(tbMallType));
    }

    /**
     * 删除评价类型
     */
    @PreAuthorize("@ss.hasPermi('system:type:remove')")
    @Log(title = "评价类型", businessType = BusinessType.DELETE)
	@DeleteMapping("/{typeIds}")
    public AjaxResult remove(@PathVariable Long[] typeIds)
    {
        return toAjax(tbMallTypeService.deleteTbMallTypeByTypeIds(typeIds));
    }
}
