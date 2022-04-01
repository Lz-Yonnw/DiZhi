package com.ruoyi.system.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import lombok.Data;

import java.util.Date;
@Data
@ApiModel(value = "订单详情")
public class TbOrderInfo extends BaseEntity {

  private static final long serialVersionUID = 1L;

  private long id;
  private String payAmount;
  private String orderSn;
  private String modelType;
  private long clothesId;
  private long userId;
  private long status;
  private long payType;

  private String payAccount;
  private String paySn;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date payTime;
  private String remark;
  @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
  private Date delTime;
  private long delFlag;
  private String delBy;
  private long timeOutFlag;



}
