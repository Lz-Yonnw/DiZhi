package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.TbUserCoupon;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户优惠劵Mapper接口
 *
 * @author ruoyi
 * @date 2022-05-31
 */
public interface TbUserCouponMapper
{
    /**
     * 优惠价数量
     * @param userId
     * @return
     */
    @Select("select count(*) from tb_user_coupon where user_id = #{userId}")
    public int selectTbUserCouponCountByUserId(Integer userId);
    /**
     * 查询用户优惠劵
     *
     * @param id 用户优惠劵主键
     * @return 用户优惠劵
     */
    public TbUserCoupon selectTbUserCouponById(Long id);

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
     * 删除用户优惠劵
     *
     * @param id 用户优惠劵主键
     * @return 结果
     */
    public int deleteTbUserCouponById(Long id);

    /**
     * 批量删除用户优惠劵
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbUserCouponByIds(Long[] ids);
}
