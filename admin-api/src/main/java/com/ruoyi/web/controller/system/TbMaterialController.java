package com.ruoyi.web.controller.system;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.common.core.domain.ModelTreeSelect;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 素材中心
 * 
 * @author ruoyi
 */
@RestController
@RequestMapping("/system/material")
public class TbMaterialController extends BaseController
{

    private static final Logger logger = LoggerFactory.getLogger(TbMaterialController.class);

    @Autowired
    private ITbModelTypeService iTbModelTypeService;

    @Autowired
    private ITbModelStyleService iTbModelStyleService;

    @Autowired
    private ITbPlateClassService iTbPlateClassService;

    @Autowired
    private ITbPlateCutWayService iTbPlateCutWayService;

    @Autowired
    private ITbComponentService iTbComponentService;

    /**
     * 获取素材中心下拉树列表
     */
//    @PreAuthorize("@ss.hasPermi('system:material:list')")
    @GetMapping("/treeselect")
    public AjaxResult treeselect()
    {
        List<ModelTreeSelect> list = new ArrayList<>();
        Map<String,Object> indexMap = new HashMap<>();
        List<TbModelType> modelTypes = iTbModelTypeService.selectTbModelTypeList(null);
        ModelTreeSelect modelTreeSelect = null;
        for(TbModelType modelType: modelTypes){
            modelTreeSelect = new ModelTreeSelect();
            modelTreeSelect.setId(modelType.getId());
            modelTreeSelect.setLabel(modelType.getName());
            modelTreeSelect.setCode("modelType");
            list.add(modelTreeSelect);
            indexMap.put("modelType_"+modelType.getId(), modelTreeSelect);
        }
        logger.info("类别"+JSONObject.toJSONString(list));
        List<TbModelStyle> modelStyles = iTbModelStyleService.selectTbModelStyleList(null);
        for(TbModelStyle tbModelStyle: modelStyles){
            ModelTreeSelect firstModel = (ModelTreeSelect) indexMap.get("modelType_"+tbModelStyle.getParentId());
            if(firstModel != null){
                modelTreeSelect = new ModelTreeSelect();
                modelTreeSelect.setId(tbModelStyle.getId());
                modelTreeSelect.setLabel(tbModelStyle.getName());
                modelTreeSelect.setCode("modelStyle");
                List<ModelTreeSelect> modelTreeSelects = firstModel.getChildren();
                if(modelTreeSelects == null){
                    modelTreeSelects = new ArrayList<>();
                }
                modelTreeSelects.add(modelTreeSelect);
                firstModel.setChildren(modelTreeSelects);
                indexMap.put("modelStyle_" + tbModelStyle.getId(), modelTreeSelect);
            }
        }
        logger.info("类型"+JSONObject.toJSONString(list));

        List<TbPlateClass> plateClasses = iTbPlateClassService.selectTbPlateClassList(null);
        for(TbPlateClass plateClass: plateClasses){
            ModelTreeSelect firstModel = (ModelTreeSelect) indexMap.get("modelStyle_"+plateClass.getParentId());
            if(firstModel != null){
                modelTreeSelect = new ModelTreeSelect();
                modelTreeSelect.setId(plateClass.getId());
                modelTreeSelect.setLabel(plateClass.getName());
                modelTreeSelect.setCode("plateClass");
                List<ModelTreeSelect> modelTreeSelects = firstModel.getChildren();
                if(modelTreeSelects == null){
                    modelTreeSelects = new ArrayList<>();
                }
                modelTreeSelects.add(modelTreeSelect);
                firstModel.setChildren(modelTreeSelects);
                indexMap.put("plateClass_" + plateClass.getId(), modelTreeSelect);
            }
        }

        logger.info("部位"+JSONObject.toJSONString(list));

        List<TbPlateCutWay> plateCutWays = iTbPlateCutWayService.selectTbPlateCutWayList(null);
        for(TbPlateCutWay plateCutWay: plateCutWays){
            ModelTreeSelect firstModel = (ModelTreeSelect) indexMap.get("plateClass_"+plateCutWay.getParentId());
            if(firstModel != null){
                modelTreeSelect = new ModelTreeSelect();
                modelTreeSelect.setId(plateCutWay.getId());
                modelTreeSelect.setLabel(plateCutWay.getName());
                modelTreeSelect.setCode("plateCutWay");
                List<ModelTreeSelect> modelTreeSelects = firstModel.getChildren();
                if(modelTreeSelects == null){
                    modelTreeSelects = new ArrayList<>();
                }
                modelTreeSelects.add(modelTreeSelect);
                firstModel.setChildren(modelTreeSelects);
                indexMap.put("plateCutWay_" + plateCutWay.getId(), modelTreeSelect);
            }
        }
        logger.info("部位组成"+JSONObject.toJSONString(list));
        return AjaxResult.success(list);
    }

    //TODO 后台管理系统上传文件
    @GetMapping("/getComponents")
    public AjaxResult getComponents(Long parentId)
    {
        TbComponent tbComponent = new TbComponent();
        tbComponent.setParentId(parentId);
        List<TbComponent> components = iTbComponentService.selectTbComponentList(tbComponent);
        return AjaxResult.success(components);
    }

    /**
     * 添加衣服
     */
    @PostMapping("/mall")
    public AjaxResult getMall(@RequestBody TbModelStyle tbModelStyle){
        int i = iTbModelStyleService.insertTbModelStyle(tbModelStyle);
        return toAjax(i);
    }

    /**
     * 添加部位
     */

    /**
     * 各种部位
     */


}
