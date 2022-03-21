package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonInclude;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 板块大类-部位对象 tb_plate_class
 * 
 * @author zzz
 * @date 2021-09-13
 */
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "板块大类-部位对象")
public class TbPlateClass extends BaseEntity
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

    /** 图片 */
    @Excel(name = "图片")
    @ApiModelProperty("图片")
    private String img;

    /** 上级ID */
    @Excel(name = "上级ID")
    @ApiModelProperty("上级ID")
    private Long parentId;


}
