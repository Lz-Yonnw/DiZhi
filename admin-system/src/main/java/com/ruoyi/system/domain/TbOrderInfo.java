package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
@ApiModel(value = "订单信息")
public class TbOrderInfo extends BaseEntity {

  private static final long serialVersionUID = 1L;

  private long id;//订单信息表主键
  private BigDecimal payAmount;//支付金额
  private BigDecimal amount;//商品价格
  private String orderSn;//订单号
  private String modelType;//订单类型
  private long storeId;//商户Id
  private long mallId;//商品id
  private long userId;//用户id
  private long status;//状态
  private long payType;//支付方式
  private String payAccount;//支付账号
  private String address;//地址
  private String phone;//手机号
  private String consignee;//收货人
  private String remark;//备注
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date payTime;//支付时间
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date delTime;//删除时间
  private long delFlag;//是否删除
  private String delBy;//删除人

  private long timeOutFlag;//是否超时




}