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
import com.ruoyi.system.domain.TbUserInfo;
import com.ruoyi.system.service.ITbUserInfoService;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.common.core.page.TableDataInfo;

/**
 * 用户信息Controller
 *
 * @author zzz
 * @date 2021-09-13
 */
@RestController
@RequestMapping("/system/info")
@Api(tags = "用户信息")
public class TbUserInfoController extends BaseController
{
    @Autowired
    private ITbUserInfoService tbUserInfoService;

    /**
     * 查询用户信息列表
     */
    @ApiOperation("查询用户信息列表")
    @PreAuthorize("@ss.hasPermi('system:info:list')")
    @GetMapping("/list")
    public TableDataInfo<List<TbUserInfo>> list(TbUserInfo tbUserInfo)
    {
        startPage();
        List<TbUserInfo> list = tbUserInfoService.selectTbUserInfoList(tbUserInfo);
        return getDataTable(list);
    }

    /**
     * 导出用户信息列表
     */
    @ApiOperation("导出用户信息列表")
    @PreAuthorize("@ss.hasPermi('system:info:export')")
    @Log(title = "用户信息", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TbUserInfo tbUserInfo)
    {
        List<TbUserInfo> list = tbUserInfoService.selectTbUserInfoList(tbUserInfo);
        ExcelUtil<TbUserInfo> util = new ExcelUtil<TbUserInfo>(TbUserInfo.class);
        return util.exportExcel(list, "info");
    }

    /**
     * 获取用户信息详细信息
     */
    @ApiOperation("获取用户信息详细信息")
    @PreAuthorize("@ss.hasPermi('system:info:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbUserInfoService.selectTbUserInfoById(id));
    }

    /**
     * 新增用户信息
     */
    @ApiOperation("新增用户信息")
    @PreAuthorize("@ss.hasPermi('system:info:add')")
    @Log(title = "用户信息", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbUserInfo tbUserInfo)
    {
        return toAjax(tbUserInfoService.insertTbUserInfo(tbUserInfo));
    }

    /**
     * 修改用户信息
     */
    @ApiOperation("修改用户信息")
    @PreAuthorize("@ss.hasPermi('system:info:edit')")
    @Log(title = "用户信息", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbUserInfo tbUserInfo)
    {
        return toAjax(tbUserInfoService.updateTbUserInfo(tbUserInfo));
    }

    /**
     * 删除用户信息
     */
    @ApiOperation("删除用户信息")
    @PreAuthorize("@ss.hasPermi('system:info:remove')")
    @Log(title = "用户信息", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbUserInfoService.deleteTbUserInfoByIds(ids));
    }
}
