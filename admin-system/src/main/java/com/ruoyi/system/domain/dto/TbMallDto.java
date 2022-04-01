package com.ruoyi.system.domain.dto;

import com.ruoyi.system.domain.TbMall;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "商品详情dto")
public class TbMallDto extends TbMall {
    /**
     * 1为收藏 0位取消收藏
     */
    private int status;
}
