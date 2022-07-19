package com.ruoyi.system.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ruoyi.common.annotation.Excel;
import com.ruoyi.common.core.domain.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * 用户信息对象 tb_user_info
 *
 * @author zzz
 * @date 2021-09-17
 */
@Data
@ApiModel(value = "用户信息对象")
public class TbUserInfo extends BaseEntity
{
    private static final long serialVersionUID = 1L;

    /** $column.columnComment */
    private Long id;

    /** 用户id（随机生成） */
    @Excel(name = "用户id", readConverterExp = "随=机生成")
    @ApiModelProperty("用户id")
    private String userId;

    /** 用户昵称：微信昵称、QQ昵称 */
    @Excel(name = "用户昵称：微信昵称、QQ昵称")
    @ApiModelProperty("用户昵称：微信昵称、QQ昵称")
    private String userNickname;

    /** 用户名称（账号） */
    @Excel(name = "用户名称", readConverterExp = "账=号")
    @ApiModelProperty("用户名称")
    private String userName;

    /** 性别：0：男，1：女，2：保密 */
    @Excel(name = "性别：0：男，1：女，2：保密")
    @ApiModelProperty("性别：0：男，1：女，2：保密")
    private String gender;

    /** 手机号码 */
    @Excel(name = "手机号码")
    @ApiModelProperty("手机号码")
    private String phoneNumber;

    /** 密码 */
    @Excel(name = "密码")
    @ApiModelProperty("密码")
    private String password;

    /** 邮箱 */
    @Excel(name = "邮箱")
    @ApiModelProperty("邮箱")
    private String eMail;

    /** 微信号 */
    @Excel(name = "微信号")
    @ApiModelProperty("微信号")
    private String wechatNumber;

    /** QQ号码 */
    @Excel(name = "QQ号码")
    @ApiModelProperty("QQ号码")
    private String qqNumber;

    /** 头像 */
    @Excel(name = "头像")
    @ApiModelProperty("头像")
    private String headImg;

    /** 微信openID */
    @Excel(name = "微信openID")
    @ApiModelProperty("微信openID")
    private String openId;

    /** 注册时间 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "注册时间", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("注册时间")
    private Date registerTime;

    /** 注册方式  1：普通，2：小程序，3：支付宝 */
    @Excel(name = "注册方式  1：普通，2：小程序，3：支付宝")
    @ApiModelProperty("注册方式  1：普通，2：小程序，3：支付宝")
    private Integer source;

    /** 账号状态：0：正常，1：已停用 */
    @Excel(name = "账号状态：0：正常，1：已停用")
    @ApiModelProperty("账号状态：0：正常，1：已停用")
    private Integer status;

    /** 简介 */
    @Excel(name = "简介")
    @ApiModelProperty("简介")
    private String intro;

    /** 标签 */
    @Excel(name = "标签")
    @ApiModelProperty("标签")
    private String tags;

    /** 个性签名 */
    @Excel(name = "个性签名")
    @ApiModelProperty("个性签名")
    private String signature;

    /** 省份 */
    @Excel(name = "省份")
    @ApiModelProperty("省份")
    private String province;

    /** 城市 */
    @Excel(name = "城市")
    @ApiModelProperty("城市")
    private String city;

    /** 国家 */
    @Excel(name = "国家")
    @ApiModelProperty("国家")
    private String country;

    /** 区域 */
    @Excel(name = "区域")
    @ApiModelProperty("区域")
    private String area;

    /** 个人主页大图 */
    @Excel(name = "个人主页大图")
    @ApiModelProperty("个人主页大图")
    private String homePic;

    /** 生日 */
    @JsonFormat(pattern = "yyyy-MM-dd")
    @Excel(name = "生日", width = 30, dateFormat = "yyyy-MM-dd")
    @ApiModelProperty("生日")
    private Date birthday;

    /** 余额 */
    @Excel(name = "余额")
    @ApiModelProperty("余额")
    private BigDecimal price;

    /** 身高 */
    @Excel(name = "身高")
    @ApiModelProperty("身高")
    private BigDecimal height;

    /** 是否首次登录 0:首次登录  1:第二次登录 */
    @Excel(name = "是否首次登录 0:首次登录  1:第二次登录")
    @ApiModelProperty("是否首次登录 0:首次登录  1:第二次登录")
    private Integer isFirst;

    /** 体重 */
    @Excel(name = "体重")
    @ApiModelProperty("体重")
    private BigDecimal weight;

    private Long levelId;//等级ID

    private Integer gradeValue;//经验值
}
