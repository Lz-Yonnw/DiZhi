package com.ruoyi.web.controller.system;


import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.TbAddressBook;
import com.ruoyi.system.domain.TbRegion;
import com.ruoyi.system.service.ITbAddressBookService;
import com.ruoyi.system.service.ITbRegionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 地址详细Controller
 *
 * @author liaozan8888@163.com
 * @date 2022-05-22
 */
@Api(tags = "地址详细")
@RestController
@RequestMapping("/system/book")
public class TbAddressBookController extends BaseController
{
    @Autowired
    private ITbAddressBookService tbAddressBookService;

    @Autowired
    private ITbRegionService tbRegionService;

    /**
     * 查询省市级地址列表
     */
    @PreAuthorize("@ss.hasPermi('system:region:list')")
    @GetMapping("/region/{parentId}")
    public TableDataInfo listParentId(@PathVariable(value = "parentId") Long parentId)
    {
        TbRegion tbRegion = new TbRegion();
        tbRegion.setStatus(1);
        tbRegion.setParentId(Long.valueOf(parentId));	//省市级父id
        List<TbRegion> list = tbRegionService.selectTbRegionList(tbRegion);
        return getDataTable(list);
    }

    /**
     * 查询地址详细列表
     */
    @ApiOperation(value = "查询地址详细列表")
    @PreAuthorize("@ss.hasPermi('system:book:list')")
    @GetMapping("/list")
    public TableDataInfo list(TbAddressBook tbAddressBook)
    {
        startPage();
        List<TbAddressBook> list = tbAddressBookService.selectTbAddressBookList(tbAddressBook);
        return getDataTable(list);
    }

    /**
     * 导出地址详细列表
     */
    @ApiOperation(value = "导出地址详细列表")
    @PreAuthorize("@ss.hasPermi('system:book:export')")
    @Log(title = "地址详细", businessType = BusinessType.EXPORT)
    @PostMapping("/export")
    public void export(HttpServletResponse response, TbAddressBook tbAddressBook)
    {
        List<TbAddressBook> list = tbAddressBookService.selectTbAddressBookList(tbAddressBook);
        ExcelUtil<TbAddressBook> util = new ExcelUtil<TbAddressBook>(TbAddressBook.class);
        util.exportExcel(list, "地址详细数据");
    }

    /**
     * 获取地址详细详细信息
     */
    @ApiOperation(value = "获取地址详细详细信息")
    @PreAuthorize("@ss.hasPermi('system:book:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbAddressBookService.selectTbAddressBookById(id));
    }

    /**
     * 新增地址详细
     */
    @ApiOperation(value = "新增地址详细")
    @PreAuthorize("@ss.hasPermi('system:book:add')")
    @Log(title = "地址详细", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbAddressBook tbAddressBook)
    {
        return toAjax(tbAddressBookService.insertTbAddressBook(tbAddressBook));
    }

    /**
     * 修改地址详细
     */
    @ApiOperation(value = "修改地址详细")
    @PreAuthorize("@ss.hasPermi('system:book:edit')")
    @Log(title = "地址详细", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbAddressBook tbAddressBook)
    {
        return toAjax(tbAddressBookService.updateTbAddressBook(tbAddressBook));
    }

    /**
     * 删除地址详细
     */
    @ApiOperation(value = "删除地址详细")
    @PreAuthorize("@ss.hasPermi('system:book:remove')")
    @Log(title = "地址详细", businessType = BusinessType.DELETE)
	@DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbAddressBookService.deleteTbAddressBookByIds(ids));
    }
}
