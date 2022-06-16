package com.ruoyi.system.domain;

import java.math.BigDecimal;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 优惠劵对象 tb_coupon
 * 
 * @author ruoyi
 * @date 2022-05-31
 */
public class TbCoupon extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 优惠劵标题 */
    @Excel(name = "优惠劵标题")
    private String couponTitle;

    /** path图片路径 */
    @Excel(name = "path图片路径")
    private String couponUrl;

    /** 满减价格 */
    @Excel(name = "满减价格")
    private BigDecimal couponLimit;

    /** 优惠价格 */
    @Excel(name = "优惠价格")
    private BigDecimal couponPrice;

    /** 发卷数量 */
    @Excel(name = "发卷数量")
    private Integer couponNumber;

    /** 是否为所有商品通用优惠劵 1：通用  2：指定商品 */
    @Excel(name = "是否为所有商品通用优惠劵 1：通用  2：指定商品")
    private Integer couponAllavailable;

    /** 优惠劵状态 0：不可用  1：可用 */
    @Excel(name = "优惠劵状态 0：不可用  1：可用")
    private Integer createStatus;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setCouponTitle(String couponTitle) 
    {
        this.couponTitle = couponTitle;
    }

    public String getCouponTitle() 
    {
        return couponTitle;
    }
    public void setCouponUrl(String couponUrl) 
    {
        this.couponUrl = couponUrl;
    }

    public String getCouponUrl() 
    {
        return couponUrl;
    }
    public void setCouponLimit(BigDecimal couponLimit) 
    {
        this.couponLimit = couponLimit;
    }

    public BigDecimal getCouponLimit() 
    {
        return couponLimit;
    }
    public void setCouponPrice(BigDecimal couponPrice) 
    {
        this.couponPrice = couponPrice;
    }

    public BigDecimal getCouponPrice() 
    {
        return couponPrice;
    }
    public void setCouponNumber(Integer couponNumber) 
    {
        this.couponNumber = couponNumber;
    }

    public Integer getCouponNumber() 
    {
        return couponNumber;
    }
    public void setCouponAllavailable(Integer couponAllavailable) 
    {
        this.couponAllavailable = couponAllavailable;
    }

    public Integer getCouponAllavailable() 
    {
        return couponAllavailable;
    }
    public void setCreateStatus(Integer createStatus) 
    {
        this.createStatus = createStatus;
    }

    public Integer getCreateStatus() 
    {
        return createStatus;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("couponTitle", getCouponTitle())
            .append("couponUrl", getCouponUrl())
            .append("couponLimit", getCouponLimit())
            .append("couponPrice", getCouponPrice())
            .append("couponNumber", getCouponNumber())
            .append("couponAllavailable", getCouponAllavailable())
            .append("createTime", getCreateTime())
            .append("createStatus", getCreateStatus())
            .toString();
    }
}
