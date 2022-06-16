package com.ruoyi.system.domain;

import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import lombok.Data;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 * 地址详细对象 tb_address_book
 *
 * @author ruoyi
 * @date 2022-05-22
 */
@Data
public class TbAddressBook extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** 地址表id */
    private Long id;

    /** 用户id */
    @Excel(name = "用户id")
    private Long userId;

    /** 收货人 */
    @Excel(name = "收货人")
    private String consignee;

    /** 手机号 */
    @Excel(name = "手机号")
    private String phone;

    /** 性别 */
    @Excel(name = "性别")
    private String sex;

    /** 省级区划编号 */
    @Excel(name = "省级区划编号")
    private String provinceCode;

    /** 省级名称 */
    @Excel(name = "省级名称")
    private String provinceName;

    /** 市级区划编号 */
    @Excel(name = "市级区划编号")
    private String cityCode;

    /** 市级名称 */
    @Excel(name = "市级名称")
    private String cityName;

    /** 区级区划编号 */
    @Excel(name = "区级区划编号")
    private String districtCode;

    /** 区级名称 */
    @Excel(name = "区级名称")
    private String districtName;

    /** 详细地址 */
    @Excel(name = "详细地址")
    private String detail;

    /** 标签 */
    @Excel(name = "标签")
    private String label;

    /** 是否默认 0 否 1是 */
    @Excel(name = "是否默认 0 否 1是")
    private Long isDefault;

    /** 创建人 */
    @Excel(name = "创建人")
    private Long createUser;

    /** 修改人 */
    @Excel(name = "修改人")
    private Long updateUser;

    /** 是否删除 */
    @Excel(name = "是否删除")
    private Long isDeleted;

    @Override
    public String toString() {
        return new ToStringBuilder(this,ToStringStyle.MULTI_LINE_STYLE)
            .append("id", getId())
            .append("userId", getUserId())
            .append("consignee", getConsignee())
            .append("phone", getPhone())
            .append("sex", getSex())
            .append("provinceCode", getProvinceCode())
            .append("provinceName", getProvinceName())
            .append("cityCode", getCityCode())
            .append("cityName", getCityName())
            .append("districtCode", getDistrictCode())
            .append("districtName", getDistrictName())
            .append("detail", getDetail())
            .append("label", getLabel())
            .append("isDefault", getIsDefault())
            .append("createTime", getCreateTime())
            .append("updateTime", getUpdateTime())
            .append("createUser", getCreateUser())
            .append("updateUser", getUpdateUser())
            .append("isDeleted", getIsDeleted())
            .toString();
    }
}
