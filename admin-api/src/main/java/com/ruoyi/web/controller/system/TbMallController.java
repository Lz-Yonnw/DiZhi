package com.ruoyi.web.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.common.utils.poi.ExcelUtil;
import com.ruoyi.system.domain.TbCollection;
import com.ruoyi.system.domain.TbMall;
import com.ruoyi.system.service.ITbMallCouponService;
import com.ruoyi.system.service.TbMallService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * 商品信息展示
 */
@RestController
@RequestMapping("/system/mall")
@Api(tags = "商品信息")
public class TbMallController extends BaseController {

    @Autowired
    private TbMallService tbMallService;

    @Autowired
    private ITbMallCouponService iTbMallCouponService;

    /**
     * 查询所有商品
     */
    @ApiOperation("展示所有商品")
    @PreAuthorize("@ss.hasPermi('system:mall:list')")
    @GetMapping("/list")
    public TableDataInfo<List<TbMall>> list(){
        startPage();
        List<TbMall> list = tbMallService.selectTbMallList();
        return getDataTable(list);

    }
    /**
     * 收藏商品
     */
    @ApiOperation("收藏商品")
    @PreAuthorize("@ss.hasPermi('system:mall:status')")
    @PostMapping("/status")
    public AjaxResult status(@RequestBody TbCollection tbCollection){
        tbMallService.updataStatus(tbCollection);
        return AjaxResult.success("收藏成功");
    }

    /**
     * 展示用户全部收藏
     */
    @ApiOperation("我的收藏")
    @PreAuthorize("@ss.hasPermi('system:mall:liststatus')")
    @GetMapping("/status")
    public TableDataInfo<List<TbMall>> listStatus(@PathVariable("id") Long id){
        startPage();
        List<TbMall> tbMallList = tbMallService.selectTbMallListStatus(id);
        return getDataTable(tbMallList);
    }


    /**
     * 查询商品列表
     */
    @ApiOperation("查询商品列表")
    @GetMapping("/malllist")
    public TableDataInfo list(TbMall tbMall)
    {
        startPage();
        List<TbMall> list = tbMallService.selectTbMallList(tbMall);
        return getDataTable(list);
    }

    /**
     * 导出商品列表
     */
    @ApiOperation("导出商品列表")
    @PostMapping("/export")
    public AjaxResult export(HttpServletResponse response, TbMall tbMall)
    {
        List<TbMall> list = tbMallService.selectTbMallList(tbMall);
        ExcelUtil<TbMall> util = new ExcelUtil<TbMall>(TbMall.class);
        return util.exportExcel(list, "商品数据");
    }

    /**
     * 获取商品详细信息
     */
    @ApiOperation("获取商品详细信息")
    @GetMapping(value = "/{id}")
    public AjaxResult getInfo(@PathVariable("id") Long id)
    {
        return AjaxResult.success(tbMallService.selectTbMallById(id));
    }

    /**
     * 新增商品
     */
    @ApiOperation("新增商品")
    @PostMapping
    public AjaxResult add(@RequestBody JSONObject jsonObject)
    {
        //商品
        TbMall tbMall = JSONObject.parseObject(jsonObject.getString("tbMall"), TbMall.class);
        //商品详情
        String tbMallImagJson = jsonObject.getString("tbMallImag");
        //商品sku选项
        String skuIdJson = jsonObject.getString("skuIds");
        return toAjax(tbMallService.insertTbMall(tbMall,tbMallImagJson,skuIdJson));
    }

    /**
     * 修改商品
     */
    @ApiOperation("修改商品")
    @PutMapping
    public AjaxResult edit(@RequestBody JSONObject jsonObject)
    {
        //商品
        TbMall tbMall = JSONObject.parseObject(jsonObject.getString("tbMall"), TbMall.class);
        //商品详情
        String tbMallImagJson = jsonObject.getString("tbMallImag");
        return toAjax(tbMallService.updateTbMall(tbMall,tbMallImagJson,""));
    }

    /**
     * 删除商品
     */
    @ApiOperation("删除商品")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids)
    {
        return toAjax(tbMallService.deleteTbMallByIds(ids));
    }

    /**
     * 单个商品优惠劵
     * @param id
     * @return
     */
    @ApiOperation("单个商品优惠劵")
    @GetMapping("/listCoupon/{id}")
    public AjaxResult listCoupon(@PathVariable Long id){
        return AjaxResult.success(iTbMallCouponService.selectTbMallCouponByMallId(id));
    }
}
