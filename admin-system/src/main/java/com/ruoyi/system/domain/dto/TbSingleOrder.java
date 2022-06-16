package com.ruoyi.system.domain.dto;

import com.ruoyi.common.core.domain.BaseEntity;
import com.ruoyi.system.domain.*;

/**
 * 订单模型实体类
 */
public class TbSingleOrder extends BaseEntity {

    private TbOrderInfo tbOrderInfo;

    private TbUserInfo tbUserInfo;

    private TbStore tbStore;

    private TbMall tbMall;

    private TbOrderDerails tbOrderDerails;

    private TbModelStyle tbModelStyle;
}
