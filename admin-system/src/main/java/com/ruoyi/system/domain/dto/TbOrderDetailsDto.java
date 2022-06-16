package com.ruoyi.system.domain.dto;

import com.ruoyi.system.domain.*;
import io.swagger.annotations.ApiModel;
import lombok.Data;

@Data
@ApiModel(value = "订单详情")
public class TbOrderDetailsDto  {
    //订单
    private TbOrderInfo tbOrderInfo;
    //商户
    private TbStore tbStore;
    //商品
    private TbMall tbMall;
    //用户
    private TbUserInfo tbUserInfo;
    //地址
    private TbAddressBook tbAddressBook;
    //订单详情
    private TbOrderDerails tbOrderDerails;
    //版型小类
    private TbModelStyle tbModelStyle;
}
