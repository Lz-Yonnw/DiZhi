package com.ruoyi.system.domain.dto;

import com.ruoyi.common.annotation.Excel;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

@Data
@ApiModel(value = "衣服及其全部件树形dto")
public class TbModelStyleDto {

    /** $column.columnComment */
    private Long id;


    /** 上级ID */
    @Excel(name = "上级ID")
    @ApiModelProperty("上级ID")
    private Long parentId;

    @ApiModelProperty("名称")
    private String name;
    /**
     * 类型 1为板块大类  2为板块小类 3为板块部位 ，4为板块部位组成
     */
    private Integer status;

    @ApiModelProperty("子集")
    private List<TbModelStyleDto> data ;

}
