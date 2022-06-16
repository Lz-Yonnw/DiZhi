package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.util.Date;

/**
 * 店铺对象 tb_store
 *
 * @author liaozan8888@163.com
 * @date 2022-05-06
 */
public class TbStore extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 店家id */
    private Long id;

    /** 店铺名称 */
    @Excel(name = "店铺名称")
    private String name;

    /** 新建日期 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "新建日期", width = 30, dateFormat = "yyyy-MM-dd")
    private Date newTime;

    /** 店铺描述 */
    @Excel(name = "店铺描述")
    private String description;

    /** 所属用户 */
    @Excel(name = "所属用户")
    private Long userId;

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
    public void setNewTime(Date newTime)
    {
        this.newTime = newTime;
    }

    public Date getNewTime()
    {
        return newTime;
    }
    public void setDescription(String description)
    {
        this.description = description;
    }

    public String getDescription()
    {
        return description;
    }
    public void setUserId(Long userId)
    {
        this.userId = userId;
    }

    public Long getUserId()
    {
        return userId;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("name", getName())
            .append("newTime", getNewTime())
            .append("updateTime", getUpdateTime())
            .append("description", getDescription())
            .append("userId", getUserId())
            .toString();
    }
}
