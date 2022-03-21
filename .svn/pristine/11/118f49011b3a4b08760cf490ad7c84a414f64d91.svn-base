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
import com.ruoyi.system.domain.TbWhiteDesign;
import com.ruoyi.system.service.ITbWhiteDesignService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 白膜Controller
 *
 * @author zzz
 * @date 2021-10-25
 */
@RestController
@RequestMapping("/system/whiteDesign")
@Api(tags = "白膜")
public class TbWhiteDesignController extends BaseController
{
    @Autowired
    private ITbWhiteDesignService tbWhiteDesignService;

    @Autowired
    private ITbUserWardrobeService tbUserWardrobeService;


    /**
     * 查询白膜列表
     */
    @ApiOperation("查询白膜列表")
    @PreAuthorize("@ss.hasPermi('system:whiteDesign:list')")
    @GetMapping("/list")
    public TableDataInfo<List<TbWhiteDesign>> list(TbWhiteDesign tbWhiteDesign)
    {
        startPage();
        List<TbWhiteDesign> list = tbWhiteDesignService.selectTbWhiteDesignList(tbWhiteDesign);
        return getDataTable(list);
    }

    /**
     * 导出白膜列表
     */
    @ApiOperation("导出白膜列表")
    @PreAuthorize("@ss.hasPermi('system:whiteDesign:export')")
    @Log(title = "白膜", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TbWhiteDesign tbWhiteDesign)
    {
        List<TbWhiteDesign> list = tbWhiteDesignService.selectTbWhiteDesignList(tbWhiteDesign);
        ExcelUtil<TbWhiteDesign> util = new ExcelUtil<TbWhiteDesign>(TbWhiteDesign.class);
        return util.exportExcel(list, "whiteDesign");
    }

    /**
     * 获取白膜详细信息
     */
    @ApiOperation("获取白膜详细信息")
    @PreAuthorize("@ss.hasPermi('system:whiteDesign:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbWhiteDesignService.selectTbWhiteDesignById(id));
    }

    /**
     * 新增白膜
     */
    @ApiOperation("新增白膜")
    @PreAuthorize("@ss.hasPermi('system:whiteDesign:add')")
    @Log(title = "白膜", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbWhiteDesign tbWhiteDesign)
    {
        return toAjax(tbWhiteDesignService.insertTbWhiteDesign(tbWhiteDesign));
    }

    /**
     * 修改白膜
     */
    @ApiOperation("修改白膜")
    @PreAuthorize("@ss.hasPermi('system:whiteDesign:edit')")
    @Log(title = "白膜", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbWhiteDesign tbWhiteDesign)
    {
        TbWhiteDesign whiteDesign = tbWhiteDesignService.selectTbWhiteDesignByStyleId(tbWhiteDesign.getStyleId());
        TbUserWardrobe tbUserWardrobe = tbUserWardrobeService.selectTbUserWardrobeById(tbWhiteDesign.getWardrobeId());
        tbWhiteDesign.setName(tbUserWardrobe.getName());
        tbWhiteDesign.setClothesJson(tbUserWardrobe.getClothesJson());
        tbWhiteDesign.setImage(tbUserWardrobe.getImage());
        if (whiteDesign==null) {
            return toAjax( tbWhiteDesignService.insertTbWhiteDesign(tbWhiteDesign));
        }else {
            tbWhiteDesign.setId(whiteDesign.getId());
            return toAjax(tbWhiteDesignService.updateTbWhiteDesign(tbWhiteDesign));
        }

    }

    /**
     * 删除白膜
     */
    @ApiOperation("删除白膜")
    @PreAuthorize("@ss.hasPermi('system:whiteDesign:remove')")
    @Log(title = "白膜", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbWhiteDesignService.deleteTbWhiteDesignByIds(ids));
    }
}
