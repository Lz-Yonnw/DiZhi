package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 评价类型对象 tb_mall_type
 * 
 * @author ruoyi
 * @date 2022-06-08
 */
public class TbMallType extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 评价类别 */
    private Long typeId;

    /** 评价类型 */
    @Excel(name = "评价类型")
    private String typeName;

    public void setTypeId(Long typeId) 
    {
        this.typeId = typeId;
    }

    public Long getTypeId() 
    {
        return typeId;
    }
    public void setTypeName(String typeName) 
    {
        this.typeName = typeName;
    }

    public String getTypeName() 
    {
        return typeName;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("typeId", getTypeId())
            .append("typeName", getTypeName())
            .toString();
    }
}
