package com.ruoyi.system.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户优惠劵对象 tb_user_coupon
 *
 * @author ruoyi
 * @date 2022-05-31
 */
@Data
public class TbUserCoupon extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 优惠劵id */
    @Excel(name = "优惠劵id")
    private Long couponId;

    /** 是否不可用 0：不可用 1：可用 */
    @Excel(name = "是否不可用 0：不可用 1：可用")
    private Integer status;

    private TbCoupon tbCoupon; //优惠卷

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("couponId", getCouponId())
            .append("createTime", getCreateTime())
            .append("status", getStatus())
            .toString();
    }
}
