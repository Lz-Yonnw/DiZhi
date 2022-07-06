package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 订单详情对象 tb_order_derails
 *
 * @author ruoyi
 * @date 2022-07-05
 */
public class TbOrderDerails extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String ordersn;

    /** 商户Id */
    @Excel(name = "商户Id")
    private Long storeId;

    /** 商品Id */
    @Excel(name = "商品Id")
    private Long mallId;

    /** 用户Id */
    @Excel(name = "用户Id")
    private Long userId;

    /** 收货地址Id */
    @Excel(name = "收货地址Id")
    private Long addressId;

    /** 优惠卷Id */
    @Excel(name = "优惠卷Id")
    private Long couponId;

    /** 优惠金额 */
    @Excel(name = "优惠金额")
    private BigDecimal couponAmount;

    /** 尺码 */
    @Excel(name = "尺码")
    private String size;

    /** 颜色 */
    @Excel(name = "颜色")
    private String colour;

    /** 数量 */
    @Excel(name = "数量")
    private Integer quantity;

    /** 单价金额 */
    @Excel(name = "单价金额")
    private BigDecimal amount;

    /** 总金额 */
    @Excel(name = "总金额")
    private BigDecimal totalAmount;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setOrdersn(String ordersn)
    {
        this.ordersn = ordersn;
    }

    public String getOrdersn()
    {
        return ordersn;
    }
    public void setStoreId(Long storeId)
    {
        this.storeId = storeId;
    }

    public Long getStoreId()
    {
        return storeId;
    }
    public void setMallId(Long mallId)
    {
        this.mallId = mallId;
    }

    public Long getMallId()
    {
        return mallId;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }
    public void setAddressId(Long addressId)
    {
        this.addressId = addressId;
    }

    public Long getAddressId()
    {
        return addressId;
    }
    public void setCouponId(Long couponId)
    {
        this.couponId = couponId;
    }

    public Long getCouponId()
    {
        return couponId;
    }
    public void setCouponAmount(BigDecimal couponAmount)
    {
        this.couponAmount = couponAmount;
    }

    public BigDecimal getCouponAmount()
    {
        return couponAmount;
    }
    public void setSize(String size)
    {
        this.size = size;
    }

    public String getSize()
    {
        return size;
    }
    public void setColour(String colour)
    {
        this.colour = colour;
    }

    public String getColour()
    {
        return colour;
    }
    public void setQuantity(Integer quantity)
    {
        this.quantity = quantity;
    }

    public Integer getQuantity()
    {
        return quantity;
    }
    public void setAmount(BigDecimal amount)
    {
        this.amount = amount;
    }

    public BigDecimal getAmount()
    {
        return amount;
    }
    public void setTotalAmount(BigDecimal totalAmount)
    {
        this.totalAmount = totalAmount;
    }

    public BigDecimal getTotalAmount()
    {
        return totalAmount;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("ordersn", getOrdersn())
            .append("storeId", getStoreId())
            .append("mallId", getMallId())
            .append("userId", getUserId())
            .append("addressId", getAddressId())
            .append("couponId", getCouponId())
            .append("couponAmount", getCouponAmount())
            .append("size", getSize())
            .append("colour", getColour())
            .append("quantity", getQuantity())
            .append("amount", getAmount())
            .append("totalAmount", getTotalAmount())
            .toString();
    }
}
