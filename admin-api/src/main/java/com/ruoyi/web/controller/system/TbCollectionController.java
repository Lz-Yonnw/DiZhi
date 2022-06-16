package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.TbCollection;
import com.ruoyi.system.service.ITbCollectionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 收藏Controller
 *
 * @author liaozan8888@163.com
 * @date 2022-05-23
 */
@Api(tags = "用户商品收藏")
@RestController
@RequestMapping("/system/collection")
public class TbCollectionController extends BaseController
{
    @Autowired
    private ITbCollectionService tbCollectionService;

    /**
     * 查询收藏列表
     */
    @ApiOperation("查询收藏列表")
    @PreAuthorize("@ss.hasPermi('system:collection:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbCollection tbCollection)
    {
        startPage();
        List<TbCollection> list = tbCollectionService.selectTbCollectionList(tbCollection);
        return getDataTable(list);
    }

    /**
     * 导出收藏列表
     */
    @ApiOperation("导出收藏列表")
    @PreAuthorize("@ss.hasPermi('system:collection:export')")
    @Log(title = "收藏", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbCollection tbCollection)
    {
        List<TbCollection> list = tbCollectionService.selectTbCollectionList(tbCollection);
        ExcelUtil<TbCollection> util = new ExcelUtil<TbCollection>(TbCollection.class);
        util.exportExcel( list, "收藏数据");
    }

    /**
     * 删除收藏
     */
    @ApiOperation("删除收藏")
    @PreAuthorize("@ss.hasPermi('system:collection:remove')")
    @Log(title = "收藏", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbCollectionService.deleteTbCollectionByIds(ids));
    }
}
