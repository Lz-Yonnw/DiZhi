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
import com.ruoyi.system.domain.TbDesignRecommend;
import com.ruoyi.system.service.ITbDesignRecommendService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 设计推荐Controller
 *
 * @author zzz
 * @date 2021-10-09
 */
@RestController
@RequestMapping("/system/recommend")
@Api(tags = "设计推荐")
public class TbDesignRecommendController extends BaseController
{
    @Autowired
    private ITbDesignRecommendService tbDesignRecommendService;

    @Autowired
    private ITbUserWardrobeService iTbUserWardrobeService;

    /**
     * 查询设计推荐列表
     */
    @ApiOperation("查询设计推荐列表")
    @PreAuthorize("@ss.hasPermi('system:recommend:list')")
    @GetMapping("/list")
    public TableDataInfo<List<TbDesignRecommend>> list(TbDesignRecommend tbDesignRecommend)
    {
        startPage();
        List<TbDesignRecommend> list = tbDesignRecommendService.selectTbDesignRecommendList(tbDesignRecommend);
        return getDataTable(list);
    }

    /**
     * 导出设计推荐列表
     */
    @ApiOperation("导出设计推荐列表")
    @PreAuthorize("@ss.hasPermi('system:recommend:export')")
    @Log(title = "设计推荐", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TbDesignRecommend tbDesignRecommend)
    {
        List<TbDesignRecommend> list = tbDesignRecommendService.selectTbDesignRecommendList(tbDesignRecommend);
        ExcelUtil<TbDesignRecommend> util = new ExcelUtil<TbDesignRecommend>(TbDesignRecommend.class);
        return util.exportExcel(list, "recommend");
    }

    /**
     * 获取设计推荐详细信息
     */
    @ApiOperation("获取设计推荐详细信息")
    @PreAuthorize("@ss.hasPermi('system:recommend:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbDesignRecommendService.selectTbDesignRecommendById(id));
    }

    /**
     * 新增设计推荐
     */
    @ApiOperation("新增设计推荐")
    @PreAuthorize("@ss.hasPermi('system:recommend:add')")
    @Log(title = "设计推荐", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbDesignRecommend tbDesignRecommend)
    {
        TbUserWardrobe tbUserWardrobe = iTbUserWardrobeService.selectTbUserWardrobeById(tbDesignRecommend.getId());
        tbDesignRecommend.setClothesJson(tbUserWardrobe.getClothesJson());
        return toAjax(tbDesignRecommendService.insertTbDesignRecommend(tbDesignRecommend));
    }

    /**
     * 修改设计推荐
     */
    @ApiOperation("修改设计推荐")
    @PreAuthorize("@ss.hasPermi('system:recommend:edit')")
    @Log(title = "设计推荐", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbDesignRecommend tbDesignRecommend)
    {
        return toAjax(tbDesignRecommendService.updateTbDesignRecommend(tbDesignRecommend));
    }

    /**
     * 删除设计推荐
     */
    @ApiOperation("删除设计推荐")
    @PreAuthorize("@ss.hasPermi('system:recommend:remove')")
    @Log(title = "设计推荐", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbDesignRecommendService.deleteTbDesignRecommendByIds(ids));
    }
}
