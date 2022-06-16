package com.ruoyi.system.domain;

import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;

/**
 * 评论对象 tb_comment
 *
 * @author ruoyi
 * @date 2022-06-08
 */
@Data
public class TbComment extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 留言id */
    private Integer id;

    /** 发表留言用户id */
    @Excel(name = "发表留言用户id")
    private Integer userId;

    /** 留言内容 */
    @Excel(name = "留言内容")
    private String data;

    /** 留言所属商铺 */
    @Excel(name = "留言所属商铺")
    private Integer storeId;

    /** 评价类型 */
    @Excel(name = "评价类型")
    private Integer typeId;

    /** 被评价的商品 */
    @Excel(name = "被评价的商品")
    private Integer mallId;

    private String userNickName;//用户名称

    private String headImg;//用户头像


    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("data", getData())
            .append("storeId", getStoreId())
            .append("typeId", getTypeId())
            .append("mallId", getMallId())
            .toString();
    }
}
