package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.TbCoupon;

/**
 * 优惠劵Service接口
 * 
 * @author ruoyi
 * @date 2022-05-31
 */
public interface ITbCouponService 
{
    /**
     * 查询优惠劵
     * 
     * @param id 优惠劵主键
     * @return 优惠劵
     */
    public TbCoupon selectTbCouponById(Long id);

    /**
     * 查询优惠劵列表
     * 
     * @param tbCoupon 优惠劵
     * @return 优惠劵集合
     */
    public List<TbCoupon> selectTbCouponList(TbCoupon tbCoupon);

    /**
     * 新增优惠劵
     * 
     * @param tbCoupon 优惠劵
     * @return 结果
     */
    public int insertTbCoupon(TbCoupon tbCoupon);

    /**
     * 修改优惠劵
     * 
     * @param tbCoupon 优惠劵
     * @return 结果
     */
    public int updateTbCoupon(TbCoupon tbCoupon);

    /**
     * 批量删除优惠劵
     * 
     * @param ids 需要删除的优惠劵主键集合
     * @return 结果
     */
    public int deleteTbCouponByIds(Long[] ids);

    /**
     * 删除优惠劵信息
     * 
     * @param id 优惠劵主键
     * @return 结果
     */
    public int deleteTbCouponById(Long id);
}
