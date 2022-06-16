package com.ruoyi.system.domain;

import io.swagger.annotations.ApiModel;
import lombok.Data;

/**
 * 单个商品详细信息
 */
@Data
@ApiModel(value = "单个商品")
public class TbMallImgs {

  private long id;//主键
  private long mallId;//商品id
  private String img;//商品图片

  public TbMallImgs() {
  }

  public TbMallImgs(long mallId, String img) {
    this.mallId = mallId;
    this.img = img;
  }
}
