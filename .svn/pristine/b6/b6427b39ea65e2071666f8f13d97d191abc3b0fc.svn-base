package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 衣柜列表对象 tb_user_wardrobe
 * 
 * @author zzz
 * @date 2021-09-25
 */
@Data
@ApiModel(value = "衣柜列表对象")
public class TbUserWardrobe extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    @ApiModelProperty("用户id")
    private Long userId;

    /** 衣服名称 */
    @Excel(name = "衣服名称")
    @ApiModelProperty("衣服名称")
    private String name;

    /** 衣服字符串 */
    @Excel(name = "衣服字符串")
    @ApiModelProperty("衣服字符串")
    private String clothesJson;

    /** 二维码 */
    @Excel(name = "二维码")
    @ApiModelProperty("二维码")
    private String qrCode;

    /** 衣服图片 */
    @Excel(name = "衣服图片")
    @ApiModelProperty("衣服图片")
    private String image;

    /** 爆款分类 */
    @Excel(name = "爆款分类")
    @ApiModelProperty("爆款分类")
    private Integer clothesType;

    private Long styleId;

    private String userName;

    private String phoneNumber;

    private String headImg;


}
