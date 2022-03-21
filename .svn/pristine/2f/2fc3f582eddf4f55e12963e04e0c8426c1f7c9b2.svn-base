package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 白膜对象 tb_white_design
 * 
 * @author zzz
 * @date 2021-10-25
 */
@Data
@ApiModel(value = "白膜对象")
public class TbWhiteDesign extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String name;

    /** 衣服字符串 */
    @Excel(name = "衣服字符串")
    @ApiModelProperty("衣服字符串")
    private String clothesJson;

    /** 衣服二维码 */
    @Excel(name = "衣服二维码")
    @ApiModelProperty("衣服二维码")
    private String qrCode;

    /** 衣服图片 */
    @Excel(name = "衣服图片")
    @ApiModelProperty("衣服图片")
    private String image;

    /** 模块小类id */
    @Excel(name = "模块小类id")
    @ApiModelProperty("模块小类id")
    private Long styleId;

    /** 板材数量 */
    @Excel(name = "板材数量")
    @ApiModelProperty("板材数量")
    private Long boardCount;

    private Long wardrobeId;
}
