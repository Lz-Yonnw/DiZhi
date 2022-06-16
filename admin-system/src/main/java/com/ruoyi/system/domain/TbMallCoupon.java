package com.ruoyi.system.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商品+优惠劵中间对象 tb_mall_coupon
 *
 * @author ruoyi
 * @date 2022-05-31
 */
@Data
public class TbMallCoupon extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 商品ID */
    @Excel(name = "商品ID")
    private Long mallId;

    /** 优惠劵ID */
    @Excel(name = "优惠劵ID")
    private Long couponId;

    /** 状态 */
    @Excel(name = "状态")
    private Long status;

    private TbCoupon tbCoupon;


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("mallId", getMallId())
            .append("couponId", getCouponId())
            .append("status", getStatus())
            .toString();
    }
}
