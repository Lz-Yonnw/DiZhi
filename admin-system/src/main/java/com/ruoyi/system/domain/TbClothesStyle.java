package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

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
    private String name;

    /** 衣服字符串 */
    @Excel(name = "衣服字符串")
    private String clothesJson;

    /** 版型小类Id */
    @Excel(name = "版型小类Id")
    private Long modelstyleId;

    /** 价格 */
    @Excel(name = "价格")
    private BigDecimal price;

    /** 衣服二维码 */
    @Excel(name = "衣服二维码")
    private String qrCode;

    /** 衣服图片 */
    @Excel(name = "衣服图片")
    private String image;

    /** 板材数量 */
    @Excel(name = "板材数量")
    private Long boardCount;

    /** 1：日度爆款  2：周度爆款  3：年度爆款 */
    @Excel(name = "1：日度爆款  2：周度爆款  3：年度爆款")
    private Integer clothesType;


}
