package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.math.BigDecimal;

/**
 * 商品规格（SKU）对象 tb_mall_sku
 *
 * @author liaozan8888@163.com
 * @date 2022-05-17
 */
@Data
public class TbMallSku extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 商品id */
    @Excel(name = "商品id")
    private Long mallId;

    /** 多规格属性 */
    private String specsValueIdJSon;

    /** 规格多属性 JSON属性名*/
    @Excel(name = "规格多属性")
    private String specsData;

    /** 库存 */
    @Excel(name = "库存")
    private Integer amount;

    /** 单品价格 */
    @Excel(name = "单品价格")
    private BigDecimal price;

    /** 状态 */
    @Excel(name = "状态")
    private Integer status;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("mallId", getMallId())
            .append("specsData", getSpecsData())
            .append("amount", getAmount())
            .append("price", getPrice())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("status", getStatus())
            .toString();
    }
}
