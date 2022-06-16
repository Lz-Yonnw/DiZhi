package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.List;

/**
 * 部件对象 tb_component
 *
 * @author zzz
 * @date 2021-08-16
 */
@Data
@ApiModel(value = "部件对象")
public class TbComponent extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String name;

    /** 编号 */
    @Excel(name = "编号")
    @ApiModelProperty("编号")
    private String code;

    /** 上级ID */
    @Excel(name = "上级ID")
    @ApiModelProperty("上级ID")
    private Long parentId;

    /** 图片 */
    @Excel(name = "图片")
    @ApiModelProperty("图片")
    private String picture;

    /** 模型地址 */
    @Excel(name = "模型地址")
    @ApiModelProperty("模型地址")
    private String modelUrl;

    /** 基础价 */
    @Excel(name = "基础价")
    @ApiModelProperty("基础价")
    private BigDecimal basicPrice;

    /** 面料用量 */
    @Excel(name = "面料用量")
    @ApiModelProperty("面料用量")
    private Integer materialDosage;

    private String suffixCode;

    private String qrCode;

    private List<TbClassify> tbClassifyList;//分类

}
