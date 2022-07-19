package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 订单退货重做对象 tb_order_return
 *
 * @author ruoyi
 * @date 2022-07-14
 */
@Data
public class TbOrderReturn extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    private long id;

    /** 订单号 */
    @Excel(name = "订单号")
    private String orderSn;

    /** 退回原因 */
    @Excel(name = "退回原因")
    private String returnWhy;

    /** 退回详情 */
    @Excel(name = "退回详情")
    private String returnWhydesc;

    /** 退回图 */
    @Excel(name = "退回图")
    private String returnImags;

    /** 退回物流号 */
    @Excel(name = "退回物流号")
    private String returnLogisticssn;

    /** 我的收货地址 */
    @Excel(name = "我的收货地址")
    private Long newAddressid;

    /** 衣服模型参数JSON字符 */
    @Excel(name = "衣服模型参数JSON字符")
    private String newClothesjson;

    /** 退回类型：1：退回  2：重做 */
    @Excel(name = "退回类型：1：退回  2：重做")
    private Integer returnStatus;

    private TbAddressBook tbAddressBook;//地址


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("orderSn", getOrderSn())
            .append("returnWhy", getReturnWhy())
            .append("returnWhydesc", getReturnWhydesc())
            .append("returnImags", getReturnImags())
            .append("returnLogisticssn", getReturnLogisticssn())
            .append("newAddressid", getNewAddressid())
            .append("newClothesjson", getNewClothesjson())
            .append("returnStatus", getReturnStatus())
            .toString();
    }
}
