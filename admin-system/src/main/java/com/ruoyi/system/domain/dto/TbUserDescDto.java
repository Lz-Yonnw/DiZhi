package com.ruoyi.system.domain.dto;

import com.ruoyi.system.domain.TbUserInfo;
import com.ruoyi.system.domain.TbUserLevel;
import lombok.Data;

/**
 * 用户信息
 */
@Data
public class TbUserDescDto{
    private TbUserInfo tbUserInfo;
    private TbUserLevel tbUserLevel;//用户等级
    private Integer couponNumber; //优惠卷数量
    private Integer collectionNumber; //商品收藏
    private String agreement;//用户协议
}
