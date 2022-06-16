package com.ruoyi.system.domain.dto;

import com.ruoyi.system.domain.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.List;

/**
 * 模型属性DTO
 */
@Data
@ApiModel(value = "模型属性DTO")
public class TBModelDto {

    private TbModelType tbModelType;//版型大类

    private TbModelStyle tbModelStyle;//版型小类

    private List<TbPlateClass> tbPlateClassList;//版型大类-部位

    private List<TbPlateCutWay> tbPlateCutWayList;//板块小类-裁剪方式

    private List<TbComponent> tbComponentList;//部件表
}
