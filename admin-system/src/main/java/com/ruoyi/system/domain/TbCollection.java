package com.ruoyi.system.domain;

import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "收藏商品")
public class TbCollection extends BaseEntity {
    private static final long serialVersionUID=1L;
    /**
     * 主键
     */
    private Long id;
    /**
     * 商品id
     */
    private Long mallId;
    /**
     * 用户id
     */
    private Long userId;
    /**
     * 状态 1为收藏，0为取消收藏
     */
    private int status;

    private String mallName; //商品name

    private String userName; //用户name

}
