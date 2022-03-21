package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

import java.math.BigDecimal;

/**
 * 图案部件中间对象 tb_pattern_component
 * 
 * @author zzz
 * @date 2021-09-13
 */
@Data
@ApiModel(value = "图案部件中间对象")
public class TbPatternComponent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 图案ID */
    @Excel(name = "图案ID")
    @ApiModelProperty("图案ID")
    private Long patternId;

    /** $column.columnComment */
    private Long id;

    /** 部件ID */
    @Excel(name = "部件ID")
    @ApiModelProperty("部件ID")
    private Long componentId;

    /** 可填充配饰ID，有该id代表是在可填充配饰下的图案 */
    @Excel(name = "可填充配饰ID，有该id代表是在可填充配饰下的图案")
    @ApiModelProperty("可填充配饰ID，有该id代表是在可填充配饰下的图案")
    private Long accessoryId;

    /** 分类ID */
    @Excel(name = "分类ID")
    @ApiModelProperty("分类ID")
    private Long classId;

    private  String name;

    private  String code;

    private  String suffixCode;

    private  String qrCode;


    private BigDecimal price;

    private  String picture;

    private Long  typeId;

}
