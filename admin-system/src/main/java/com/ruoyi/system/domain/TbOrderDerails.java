package com.ruoyi.system.domain;


import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;

@Data
@ApiModel(value = "订单详细信息")
public class TbOrderDerails extends BaseEntity {

  private static final long serialVersionUID = 1L;

  private long id;//订单详细信息主键
  private String name;//名称
  private long orderInfoId;//订单id
  private long mallId;//衣服id
  private long storeId;//店铺id
  private String size;//衣服尺码
  private String colour;//衣服颜色
  private int quantity;//衣服数量
  private BigDecimal amount;//价格
  private String image;//图片




}
