package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

/**
 * 商品信息
 */
@Data
@ApiModel(value = "全部商品")
public class TbMall extends BaseEntity {
    private static final long serialVersionUID = 1L;

    /**
     * 商品id
     */
    private Long id;

    /**
     * 店家id
     */
    private Integer tbStoreId;

    /**
     * 名称
     */
    private String name;

    /**
     * 描述
     */
    private String description;

    /**
     * 状态
     */
    private int status;

    /**
     * 图片
     */
    private String img;
    /**
     * 商品价格
     */
    private BigDecimal price;


}
