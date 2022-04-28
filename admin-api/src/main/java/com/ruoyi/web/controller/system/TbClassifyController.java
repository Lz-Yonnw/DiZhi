package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.TbClassify;
import com.ruoyi.system.service.ITbClassifyService;
import com.ruoyi.system.service.ITbPatternComponentService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 分类Controller
 *
 * @author zzz
 * @date 2021-08-17
 */
@RestController
@RequestMapping("/system/classify")
@Api(tags = "分类")
public class TbClassifyController extends BaseController
{
    @Autowired
    private ITbClassifyService tbClassifyService;

    @Autowired
    private ITbPatternComponentService iTbPatternComponentService;

    /**
     * 查询分类列表
     */
    @ApiOperation("查询分类列表")
    @PreAuthorize("@ss.hasPermi('system:classify:list')")
    @GetMapping("/list")
    public TableDataInfo<List<TbClassify>> list(TbClassify tbClassify)
    {
        startPage();
        List<TbClassify> list = tbClassifyService.selectTbClassifyList(tbClassify);
        
      return getDataTable(list);
    }

    /**
     * 导出分类列表
     */
    @ApiOperation("导出分类列表")
    @PreAuthorize("@ss.hasPermi('system:classify:export')")
    @Log(title = "分类", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TbClassify tbClassify)
    {
        List<TbClassify> list = tbClassifyService.selectTbClassifyList(tbClassify);
        ExcelUtil<TbClassify> util = new ExcelUtil<TbClassify>(TbClassify.class);
        return util.exportExcel(list, "classify");
    }

    /**
     * 获取分类详细信息
     */
    @ApiOperation("获取分类详细信息")
    @PreAuthorize("@ss.hasPermi('system:classify:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbClassifyService.selectTbClassifyById(id));
    }

    /**
     * 新增分类
     */
    @ApiOperation("新增分类")
    @PreAuthorize("@ss.hasPermi('system:classify:add')")
    @Log(title = "分类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbClassify tbClassify)
    {
        tbClassify.setPId(tbClassify.getSuperiorId());
        return toAjax(tbClassifyService.insertTbClassify(tbClassify));
    }

    /**
     * 修改分类
     */
    @ApiOperation("修改分类")
    @PreAuthorize("@ss.hasPermi('system:classify:edit')")
    @Log(title = "分类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbClassify tbClassify)
    {
        return toAjax(tbClassifyService.updateTbClassify(tbClassify));
    }

    /**
     * 删除分类
     */
    @ApiOperation("删除分类")
    @PreAuthorize("@ss.hasPermi('system:classify:remove')")
    @Log(title = "分类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbClassifyService.deleteTbClassifyByIds(ids));
    }



    @GetMapping("/getClassifyByComponentId")
    public AjaxResult getClassifyByComponentId(Long parentId,Long pId)
    {
        Map<String, List<TbClassify>> result = new HashMap<>();
        TbClassify classify = new TbClassify();
        classify.setParentId(parentId);
        // 图案
        classify.setType(1);
        if (pId==null) {
            classify.setPId(0L);
            List<TbClassify> outPattern = tbClassifyService.selectTbClassifyList(classify);
            result.put("outPattern",outPattern);
            // 不可填充
            classify.setType(2);
            List<TbClassify> outUnfilled = tbClassifyService.selectTbClassifyList(classify);
            result.put("outUnfilled",outUnfilled);

            //表层面料
            classify.setType(3);
            List<TbClassify> outSurfaceFabric = tbClassifyService.selectTbClassifyList(classify);
            result.put("outSurfaceFabric",outSurfaceFabric);

            //底层面料
            classify.setType(4);
            List<TbClassify> outBottomFabric = tbClassifyService.selectTbClassifyList(classify);
            result.put("outBottomFabric",outBottomFabric);

            //可填充配饰
            classify.setType(5);
            List<TbClassify> outFillAble = tbClassifyService.selectTbClassifyList(classify);
            result.put("outFillAble",outFillAble);
        }else{
            classify.setPId(pId);
            //内部  表层面料
            classify.setType(8);
            List<TbClassify> innerSurfaceFabric = tbClassifyService.selectTbClassifyListByPId(classify);
            result.put("innerSurfaceFabric",innerSurfaceFabric);

            //内部 底层面料
            classify.setType(9);
            List<TbClassify> innerBottomFabric = tbClassifyService.selectTbClassifyListByPId(classify);
            result.put("innerBottomFabric",innerBottomFabric);

            //内部 图案
            classify.setType(6);
            List<TbClassify> innerPattern = tbClassifyService.selectTbClassifyListByPId(classify);
            result.put("innerPattern",innerPattern);

            //内部 不可填充
            classify.setType(7);
            List<TbClassify> innerUnfilled = tbClassifyService.selectTbClassifyListByPId(classify);
            result.put("innerUnfilled",innerUnfilled);
        }
        return AjaxResult.success(result);
    }

    @GetMapping("/removeClassById")
    public AjaxResult removeClassById(Long id)
    {
        tbClassifyService.deleteTbClassifyById(id);
        return  AjaxResult.success(iTbPatternComponentService.deleteTbPatternComponentByClassId(id));
    }

}
