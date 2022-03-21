package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import lombok.Data;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * banner列表对象 tb_banner
 * 
 * @author zzz
 * @date 2021-09-22
 */
@Data
@ApiModel(value = "banner列表对象")
public class TbBanner extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** banner名称 */
    @Excel(name = "banner名称")
    @ApiModelProperty("banner名称")
    private String name;

    /** 图片地址或视频地址 */
    @Excel(name = "图片地址或视频地址")
    @ApiModelProperty("图片地址或视频地址")
    private String bannerUrl;

    /** 类型: 1:开机页   2.首页 */
    @Excel(name = "类型: 1:开机页   2.首页")
    @ApiModelProperty("类型: 1:开机页   2.首页")
    private Integer bannerType;

    /** 跳转地址 */
    @Excel(name = "跳转地址")
    @ApiModelProperty("跳转地址")
    private String jumpLink;


}
