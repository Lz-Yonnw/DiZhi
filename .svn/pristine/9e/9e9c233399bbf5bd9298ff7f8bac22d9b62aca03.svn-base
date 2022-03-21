package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 爆款列表对象 tb_clothes_style
 * 
 * @author zzz
 * @date 2021-09-25
 */
@Data
@ApiModel(value = "爆款列表对象")
public class TbClothesStyle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 爆款名称 */
    @Excel(name = "爆款名称")
    @ApiModelProperty("爆款名称")
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

    /** 爆款类型 */
    @Excel(name = "爆款类型")
    @ApiModelProperty("爆款类型")
    private Integer clothesType;

    private Long boardCount;


}
