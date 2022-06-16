package com.ruoyi.system.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.TbMallSpecsValue;
import com.ruoyi.system.service.ITbMallSpecsValueService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商品属性Controller
 *
 * @author liaozan8888@163.com
 * @date 2022-05-17
 */
@Api(value = "规格属性数据接口")
@RestController
@RequestMapping("/system/value")
public class TbMallSpecsValueController extends BaseController
{
    @Autowired
    private ITbMallSpecsValueService tbMallSpecsValueService;


    /**
     * 规格属性结构查询
     * @param specsName
     * @return
     */
    @ApiOperation(value = "规格属性子节点查询")
    @GetMapping("/list/specsName")
    public TableDataInfo getSpecsNameList(String specsName){
        tbMallSpecsValueService.selectTbMallSpecsChildsBySpecsName(specsName);
        return null;
    }

    /**
     * 根据父属性 查询子属性
     * @param specsId
     * @return
     */
    @ApiOperation(value = "数据规格联级查询")
    @GetMapping("/list/childs/{specsId}")
    public AjaxResult getSpecsNameList(@PathVariable("specsId") Long specsId){
        List<TbMallSpecsValue> tbMallSpecsValues = tbMallSpecsValueService.selectTbMallSpecsChildsBySpecsId(specsId);
         return AjaxResult.success(tbMallSpecsValues);
    }

    /**
     * 查询商品属性列表
     */
    @PreAuthorize("@ss.hasPermi('system:value:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbMallSpecsValue tbMallSpecsValue)
    {
        startPage();
        List<TbMallSpecsValue> list = tbMallSpecsValueService.selectTbMallSpecsValueList(tbMallSpecsValue);
        return getDataTable(list);
    }

    /**
     * 导出商品属性列表
     */
    @PreAuthorize("@ss.hasPermi('system:value:export')")
    @Log(title = "商品属性", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public AjaxResult export(HttpServletResponse response, TbMallSpecsValue tbMallSpecsValue)
    {
        List<TbMallSpecsValue> list = tbMallSpecsValueService.selectTbMallSpecsValueList(tbMallSpecsValue);
        ExcelUtil<TbMallSpecsValue> util = new ExcelUtil<TbMallSpecsValue>(TbMallSpecsValue.class);
        return util.exportExcel(list, "商品属性数据");
    }

    /**
     * 获取商品属性详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:value:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbMallSpecsValueService.selectTbMallSpecsValueById(id));
    }

    /**
     * 新增商品属性
     */
    @PreAuthorize("@ss.hasPermi('system:value:add')")
    @Log(title = "商品属性", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbMallSpecsValue tbMallSpecsValue)
    {
        return toAjax(tbMallSpecsValueService.insertTbMallSpecsValue(tbMallSpecsValue));
    }

    /**
     * 修改商品属性
     */
    @PreAuthorize("@ss.hasPermi('system:value:edit')")
    @Log(title = "商品属性", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbMallSpecsValue tbMallSpecsValue)
    {
        return toAjax(tbMallSpecsValueService.updateTbMallSpecsValue(tbMallSpecsValue));
    }

    /**
     * 删除商品属性
     */
    @PreAuthorize("@ss.hasPermi('system:value:remove')")
    @Log(title = "商品属性", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbMallSpecsValueService.deleteTbMallSpecsValueByIds(ids));
    }
}
