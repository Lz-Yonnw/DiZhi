package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.netty.common.encode.MessageDecoder;
import com.ruoyi.system.domain.TbClothesStyle;
import com.ruoyi.system.domain.TbCollection;
import com.ruoyi.system.domain.TbMall;
import com.ruoyi.system.service.TbMallService;
import io.netty.buffer.ByteBuf;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.util.List;

/**
 * 商场信息展示
 */
@RestController
@RequestMapping("/system/mall")
@Api(tags = "商品信息")
public class TbMallController extends BaseController {

    @Autowired
    private TbMallService tbMallService;

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




}
