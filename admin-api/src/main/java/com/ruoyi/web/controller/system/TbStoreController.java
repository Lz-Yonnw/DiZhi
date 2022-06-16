package com.ruoyi.web.controller.system;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.page.TableDataInfo;
import com.ruoyi.system.domain.TbStore;
import com.ruoyi.system.service.ITbStoreService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 店铺API接口
 */

@Api("店铺")
@RestController
@RequestMapping(value = "/system/store")
public class TbStoreController extends BaseController {

    @Autowired
    private ITbStoreService iTbStoreService;

    /**
     * 店铺列表
     * @param tbStore
     * @return
     */
    @GetMapping("/list")
    public TableDataInfo<List<TbStore>> getTbStore(TbStore tbStore){
        if(tbStore!=null){
            startPage();
        }
        return getDataTable(iTbStoreService.selectTbStoreList(tbStore));
    }

}
