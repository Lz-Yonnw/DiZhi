package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 板块小类-裁剪方式对象 tb_plate_cut_way
 *
 * @author zzz
 * @date 2021-09-13
 */
@Data
@ApiModel(value = "板块小类-裁剪方式对象")
public class TbPlateCutWay extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String name;

    /** 图片 */
    @Excel(name = "图片")
    @ApiModelProperty("图片")
    private String img;

    /** 编号 */
    @Excel(name = "编号")
    @ApiModelProperty("编号")
    private String code;

    /** 上级ID */
    @Excel(name = "上级ID")
    @ApiModelProperty("上级ID")
    private Long parentId;

    private String suffixCode;

    private String qrCode;

    private List<TbComponent> tbComponentList;//部件表

}
