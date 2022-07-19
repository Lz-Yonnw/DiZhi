package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 版型小类对象 tb_model_style
 *
 * @author zzz
 * @date 2021-08-10
 */
@Data
@ApiModel(value = "版型小类对象")
public class TbModelStyle extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
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

    @ApiModelProperty("图片地址")
    private String img;

    @ApiModelProperty("衣服json")
    private String clothesJson;

    @ApiModelProperty("尺码字符串")
    private String size;

    @ApiModelProperty("尺码表图")
    private String sizedescUrl;

    private Long wardrobeId;

    private List<TbPlateClass> tbPlateClassList;//版型大类-部位
}
