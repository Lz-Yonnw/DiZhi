package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * 分类对象 tb_classify
 *
 * @author zzz
 * @date 2021-08-17
 */
@Data
@ApiModel(value = "分类对象")
public class TbClassify extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 名称 */
    @Excel(name = "名称")
    @ApiModelProperty("名称")
    private String name;

    /** 类型 */
    @Excel(name = "类型")
    @ApiModelProperty("类型")
    private Integer type;

    @ApiModelProperty("分类的上级id")
    private Long pId;

    @ApiModelProperty("分类的上级id")
    private Long superiorId;


    @ApiModelProperty("部件上级id")
    private Long parentId;

    private List<TbPattern> tbPatternList;//图案部件公共类
}
