package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 用户等级对象 tb_user_level
 * 
 * @author ruoyi
 * @date 2022-07-18
 */
public class TbUserLevel extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 等级称呼 */
    @Excel(name = "等级称呼")
    private String name;

    /** 等级值 */
    @Excel(name = "等级值")
    private Long value;

    /** 重做次数 */
    @Excel(name = "重做次数")
    private Integer redoNumber;

    public void setId(Long id) 
    {
        this.id = id;
    }

    public Long getId() 
    {
        return id;
    }
    public void setName(String name) 
    {
        this.name = name;
    }

    public String getName() 
    {
        return name;
    }
    public void setValue(Long value) 
    {
        this.value = value;
    }

    public Long getValue() 
    {
        return value;
    }
    public void setRedoNumber(Integer redoNumber) 
    {
        this.redoNumber = redoNumber;
    }

    public Integer getRedoNumber() 
    {
        return redoNumber;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("value", getValue())
            .append("redoNumber", getRedoNumber())
            .toString();
    }
}
