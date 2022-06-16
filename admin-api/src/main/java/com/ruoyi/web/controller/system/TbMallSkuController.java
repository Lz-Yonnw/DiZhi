package com.ruoyi.system.controller;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.TbMallSku;
import com.ruoyi.system.service.ITbMallSkuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商品规格（SKU）Controller
 *
 * @author liaozan8888@163.com
 * @date 2022-05-17
 */
@Api(tags = "商品规格SKU数据接口")
@RestController
@RequestMapping("/system/sku")
public class TbMallSkuController extends BaseController
{
    @Autowired
    private ITbMallSkuService tbMallSkuService;

    /**
     * 获取SKU 所有属性
     * @param mallId    商品ID
     * @return
     */
    @ApiOperation(value = "获取SKU 所有属性")
    @GetMapping("/attribute/{mallId}")
    public TableDataInfo getAttributeList(@PathVariable(value = "mallId")Long mallId){
        List<TbMallSku> tbMallSkus = tbMallSkuService.selectTbMallSpecsValueByMallid(mallId);
        return getDataTable(tbMallSkus);
    }

    /**
     * 查询商品规格（SKU）列表
     */
    @PreAuthorize("@ss.hasPermi('system:sku:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbMallSku tbMallSku)
    {
        startPage();
        List<TbMallSku> list = tbMallSkuService.selectTbMallSkuList(tbMallSku);
        return getDataTable(list);
    }

    /**
     * 导出商品规格（SKU）列表
     */
    @PreAuthorize("@ss.hasPermi('system:sku:export')")
    @Log(title = "商品规格（SKU）", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public AjaxResult export(HttpServletResponse response, TbMallSku tbMallSku)
    {
        List<TbMallSku> list = tbMallSkuService.selectTbMallSkuList(tbMallSku);
        ExcelUtil<TbMallSku> util = new ExcelUtil<TbMallSku>(TbMallSku.class);
        return util.exportExcel(list, "商品规格（SKU）数据");
    }

    /**
     * 获取商品规格（SKU）详细信息
     */
    @PreAuthorize("@ss.hasPermi('system:sku:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbMallSkuService.selectTbMallSkuById(id));
    }

    /**
     * 新增商品规格（SKU）
     */
    @PreAuthorize("@ss.hasPermi('system:sku:add')")
    @Log(title = "商品规格（SKU）", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbMallSku tbMallSku)
    {
        return toAjax(tbMallSkuService.insertTbMallSkuSpecsValue(tbMallSku));
    }

    /**
     * 修改商品规格（SKU）
     */
    @PreAuthorize("@ss.hasPermi('system:sku:edit')")
    @Log(title = "商品规格（SKU）", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbMallSku tbMallSku)
    {
        return toAjax(tbMallSkuService.updateTbMallSku(tbMallSku));
    }

    /**
     * 删除商品规格（SKU）
     */
    @PreAuthorize("@ss.hasPermi('system:sku:remove')")
    @Log(title = "商品规格（SKU）", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbMallSkuService.deleteTbMallSkuByIds(ids));
    }
}
