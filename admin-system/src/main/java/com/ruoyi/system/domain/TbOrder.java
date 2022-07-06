package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单对象 tb_order
 *
 * @author ruoyi
 * @date 2022-07-05
 */
@Data
public class TbOrder extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 订单编号 */
    @Excel(name = "订单编号")
    private String orderSn;

    /** 商品名称 */
    @Excel(name = "商品名称")
    private String name;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 收货人 */
    @Excel(name = "收货人")
    private String consignee;

    /** 图片 */
    @Excel(name = "图片")
    private String image;

    /** 支付金额 */
    @Excel(name = "支付金额")
    private BigDecimal payAmount;

    /** '支付方式 1：微信，2：支付宝，3：其他', */
    @Excel(name = "'支付方式 1：微信，2：支付宝，3：其他',")
    private Integer payType;

    /** 支付账号 */
    @Excel(name = "支付账号")
    private String payAccount;

    /** 支付时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "支付时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date payTime;

    /** 退款时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "退款时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date refundTime;

    /** 收货时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "收货时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date receivedTime;

    /** 状态
 1：未支付   2：支付失败  3：支付成功  4：待收货  5：已收货   6：退货中  7：退货完成  8：待评价  9：已评价 */
    @Excel(name = "状态1：未支付   2：支付失败  3：支付成功  4：待收货  5：已收货   6：退货中  7：退货完成  8：待评价  9：已评价")
    private Integer status;

    /** '订单类型 1：普通订单，2：模型订单，3：试尺码订单' */
    @Excel(name = "'订单类型 1：普通订单，2：模型订单，3：试尺码订单'")
    private Integer modelType;

    /** 删除时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "删除时间", width = 30, dateFormat = "yyyy-MM-dd")
    private Date delTime;

    /** 是否删除  1：否  2：是 */
    private Integer delFlag;

    /** 删除人Id */
    @Excel(name = "删除人Id")
    private Integer delBy;

    /** 是否超时订单  1：否  2：是 */
    @Excel(name = "是否超时订单  1：否  2：是")
    private Long timeOutFlag;

    /** 衣服模型参数JSON字符 */
    @Excel(name = "衣服模型参数JSON字符")
    private String clothesJson;

    private TbOrderDerails tbOrderDerails;  //订单详情表

    private TbAddressBook tbAddressBook; //用户地址

    private TbCoupon tbCoupon;//优惠卷

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("orderSn", getOrderSn())
            .append("name", getName())
            .append("phone", getPhone())
            .append("consignee", getConsignee())
            .append("image", getImage())
            .append("payAmount", getPayAmount())
            .append("createTime", getCreateTime())
            .append("payType", getPayType())
            .append("payAccount", getPayAccount())
            .append("payTime", getPayTime())
            .append("refundTime", getRefundTime())
            .append("receivedTime", getReceivedTime())
            .append("remark", getRemark())
            .append("status", getStatus())
            .append("modelType", getModelType())
            .append("delTime", getDelTime())
            .append("delFlag", getDelFlag())
            .append("delBy", getDelBy())
            .append("timeOutFlag", getTimeOutFlag())
            .append("clothesJson", getClothesJson())
            .toString();
    }
}
