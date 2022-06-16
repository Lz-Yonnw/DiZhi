package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 版型大类对象 tb_model_type
 *
 * @author zzz
 * @date 2021-08-10
 */
@Data
@ApiModel(value = "版型大类对象")
public class TbModelType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String name;

    /** 男装或女装 */
    @Excel(name = "男装或女装")
    @ApiModelProperty("男装或女装")
    private Integer type;

    @ApiModelProperty("图片地址")
    private String img;


}
