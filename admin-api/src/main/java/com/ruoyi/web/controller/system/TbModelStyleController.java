package com.ruoyi.web.controller.system;

import com.ruoyi.common.annotation.Log;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.enums.BusinessType;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.dto.TbModelStyleDto;
import com.ruoyi.system.service.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

/**
 * 版型小类Controller
 *
 * @author zzz
 * @date 2021-09-27
 */
@RestController
@RequestMapping("/system/modelStyle")
@Api(tags = "版型小类")
public class TbModelStyleController extends BaseController
{
    @Autowired
    private ITbModelStyleService tbModelStyleService;

    @Autowired
    private ITbUserWardrobeService iTbUserWardrobeService;

    @Autowired
    private ITbModelTypeService tbModelTypeService;

    /**
     * 查询版型小类列表
     */
    @ApiOperation("查询版型小类列表")
    @PreAuthorize("@ss.hasPermi('system:modelStyle:list')")
    @GetMapping("/list")
    public TableDataInfo<List<TbModelStyle>> getList(TbModelStyle tbModelStyle)
    {
        startPage();
        List<TbModelStyle> list = tbModelStyleService.selectTbModelStyleList(tbModelStyle);
        return getDataTable(list);
    }

    /**
     * 导出版型小类列表
     */
    @ApiOperation("导出版型小类列表")
    @PreAuthorize("@ss.hasPermi('system:modelStyle:export')")
    @Log(title = "版型小类", businessType = BusinessType.EXPORT)
    @GetMapping("/export")
    public AjaxResult export(TbModelStyle tbModelStyle)
    {
        List<TbModelStyle> list = tbModelStyleService.selectTbModelStyleList(tbModelStyle);
        ExcelUtil<TbModelStyle> util = new ExcelUtil<TbModelStyle>(TbModelStyle.class);
        return util.exportExcel(list, "modelStyle");
    }

    /**
     * 获取版型小类详细信息
     */
    @ApiOperation("获取版型小类详细信息")
    @PreAuthorize("@ss.hasPermi('system:modelStyle:query')")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbModelStyleService.selectTbModelStyleById(id));
    }

    /**
     * 根据字典类型查询字典数据信息
     */
    @GetMapping(value = "/asList")
    public TableDataInfo<List<TbModelStyle>> dictType(TbModelStyle tbModelStyle)
    {
        startPage();
        List<TbModelStyle> modelStyles = tbModelStyleService.selectTbModelStyleList(tbModelStyle);
        return getDataTable(modelStyles);
    }

    /**
     * 新增版型小类
     */
    @ApiOperation("新增版型小类")
    @PreAuthorize("@ss.hasPermi('system:modelStyle:add')")
    @Log(title = "版型小类", businessType = BusinessType.INSERT)
    @PostMapping
    public AjaxResult add(@RequestBody TbModelStyle tbModelStyle)
    {
        return toAjax(tbModelStyleService.insertTbModelStyle(tbModelStyle));
    }

    /**
     * 修改版型小类
     */
    @ApiOperation("修改版型小类")
    @PreAuthorize("@ss.hasPermi('system:modelStyle:edit')")
    @Log(title = "版型小类", businessType = BusinessType.UPDATE)
    @PutMapping
    public AjaxResult edit(@RequestBody TbModelStyle tbModelStyle)
    {
//        TbUserWardrobe tbUserWardrobe = iTbUserWardrobeService.selectTbUserWardrobeById(tbModelStyle.getId());
//        tbModelStyle.setClothesJson(tbUserWardrobe.getClothesJson());
        return toAjax(tbModelStyleService.updateTbModelStyle(tbModelStyle));
    }

    /**
     * 删除版型小类
     */
    @ApiOperation("删除版型小类")
    @PreAuthorize("@ss.hasPermi('system:modelStyle:remove')")
    @Log(title = "版型小类", businessType = BusinessType.DELETE)
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbModelStyleService.deleteTbModelStyleByIds(ids));
    }
    /**
     * 展示所有的衣服及部位
     */
    @ApiOperation("展示所有的衣服及部位")
    @PreAuthorize("@ss.hasPermi('system:modelStyle:fourList')")
    @Log(title = "版型小类", businessType = BusinessType.OTHER)
    @GetMapping("/fourList")
    public AjaxResult getFourList()
    {

        List<TbModelStyleDto> dto = getModelTypeList();

        return AjaxResult.success(dto);
    }

    /**
     * 查询板块大类
     */

    private List<TbModelStyleDto> getModelTypeList() {
        List<TbModelStyleDto> dto = new ArrayList<>();
        List<TbModelType> list =  tbModelTypeService.selectTbModelTypeList(new TbModelType());
        TbModelStyleDto tbModelStyleDto=null;
        if (list.size()>0){
            for (TbModelType tbModelType : list) {
                tbModelStyleDto = new TbModelStyleDto();
                tbModelStyleDto.setId(tbModelType.getId());
                tbModelStyleDto.setName(tbModelType.getName());
                tbModelStyleDto.setParentId(0L);
                tbModelStyleDto.setStatus(1);
                tbModelStyleDto.setData(getModelTypeList(tbModelType.getId()));
                dto.add(tbModelStyleDto);
            }

        }
        return dto;
    }
    private List<TbModelStyleDto> getModelTypeList(long parentId) {
        List<TbModelStyleDto> dto = new ArrayList<>();
        List<TbModelStyle> list = tbModelStyleService.selectTbModelStyleByList(parentId);
        TbModelStyleDto tbModelStyleDto=null;
        if (list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                tbModelStyleDto = new TbModelStyleDto();
                tbModelStyleDto.setId(list.get(i).getId());
                tbModelStyleDto.setName(list.get(i).getName());
                tbModelStyleDto.setParentId(parentId);
                tbModelStyleDto.setStatus(2);
              tbModelStyleDto.setData(getPlateClassList(list.get(i).getId()));

                dto.add(tbModelStyleDto);
            }
        }
        return dto;
    }
    @Autowired
    private ITbPlateClassService tbPlateClassService;
    @Autowired
    private ITbPlateCutWayService tbPlateCutWayService;
    private List<TbModelStyleDto> getPlateClassList(long parentId) {
        List<TbModelStyleDto> dto = new ArrayList<>();
        List<TbPlateClass> list = tbPlateClassService.selectTbPlateClassByList(parentId);
        TbModelStyleDto tbModelStyleDto=null;
        if (list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                tbModelStyleDto = new TbModelStyleDto();
                tbModelStyleDto.setId(list.get(i).getId());
                tbModelStyleDto.setParentId(parentId);
                tbModelStyleDto.setName(list.get(i).getName());
                tbModelStyleDto.setStatus(3);
                tbModelStyleDto.setData(getPlateCutWayList(list.get(i).getId()));

                dto.add(tbModelStyleDto);
            }
        }
        return dto;
    }

    private List<TbModelStyleDto> getPlateCutWayList(long parentId) {
        List<TbModelStyleDto> dto = new ArrayList<>();
        List<TbPlateCutWay> list = tbPlateCutWayService.selectTbPlateCutWayList(parentId);
        TbModelStyleDto tbModelStyleDto=null;
        if (list.size()>0){
            for (int i = 0; i < list.size(); i++) {
                tbModelStyleDto = new TbModelStyleDto();
                tbModelStyleDto.setId(list.get(i).getId());
                tbModelStyleDto.setName(list.get(i).getName());
                tbModelStyleDto.setParentId(parentId);
                tbModelStyleDto.setStatus(4);
                dto.add(tbModelStyleDto);
            }
        }
        return dto;
    }
}
