package com.ruoyi.system.service;

import com.ruoyi.system.domain.TbUserCoupon;

import java.util.List;

/**
 * 用户优惠劵Service接口
 *
 * @author ruoyi
 * @date 2022-05-31
 */
public interface ITbUserCouponService
{
    /**
     * 查询用户优惠劵
     *
     * @param id 用户优惠劵主键
     * @return 用户优惠劵
     */
    public TbUserCoupon selectTbUserCouponById(Long id);

    /**
     * 优惠价数量
     * @param userId
     * @return
     */
    public int selectTbUserCouponCountByUserId(Integer userId);

    /**
     * 根据用户查询
     * @param userId
     * @return
     */
    public List<TbUserCoupon> selectTbUserCouponListByUserId(Long userId);

    /**
     * 查询用户优惠劵列表
     *
     * @param tbUserCoupon 用户优惠劵
     * @return 用户优惠劵集合
     */
    public List<TbUserCoupon> selectTbUserCouponList(TbUserCoupon tbUserCoupon);

    /**
     * 新增用户优惠劵
     *
     * @param tbUserCoupon 用户优惠劵
     * @return 结果
     */
    public int insertTbUserCoupon(TbUserCoupon tbUserCoupon);

    /**
     * 修改用户优惠劵
     *
     * @param tbUserCoupon 用户优惠劵
     * @return 结果
     */
    public int updateTbUserCoupon(TbUserCoupon tbUserCoupon);

    /**
     * 批量删除用户优惠劵
     *
     * @param ids 需要删除的用户优惠劵主键集合
     * @return 结果
     */
    public int deleteTbUserCouponByIds(Long[] ids);

    /**
     * 删除用户优惠劵信息
     *
     * @param id 用户优惠劵主键
     * @return 结果
     */
    public int deleteTbUserCouponById(Long id);
}
