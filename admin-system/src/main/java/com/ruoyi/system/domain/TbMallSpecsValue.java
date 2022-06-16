package com.ruoyi.system.domain;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 商品属性对象 tb_mall_specs_value
 *
 * @author liaozan8888@163.com
 * @date 2022-05-17
 */
public class TbMallSpecsValue extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 主键 */
    private Long id;

    /** 父id */
    @Excel(name = "父id")
    private Long specsId;

    /** 规格名称 */
    @Excel(name = "规格名称")
    private String specsName;

    /** 排序 */
    @Excel(name = "排序")
    private Integer sort;

    /** 规格状态 0 不可用 1可用 */
    @Excel(name = "规格状态 0 不可用 1可用")
    private Integer status;

    public void setId(Long id)
    {
        this.id = id;
    }

    public Long getId()
    {
        return id;
    }
    public void setSpecsId(Long specsId)
    {
        this.specsId = specsId;
    }

    public Long getSpecsId()
    {
        return specsId;
    }
    public void setSpecsName(String specsName)
    {
        this.specsName = specsName;
    }

    public String getSpecsName()
    {
        return specsName;
    }
    public void setSort(Integer sort)
    {
        this.sort = sort;
    }

    public Integer getSort()
    {
        return sort;
    }
    public void setStatus(Integer status)
    {
        this.status = status;
    }

    public Integer getStatus()
    {
        return status;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("specsId", getSpecsId())
            .append("specsName", getSpecsName())
            .append("createTime", getCreateTime())
            .append("sort", getSort())
            .append("status", getStatus())
            .toString();
    }
}
