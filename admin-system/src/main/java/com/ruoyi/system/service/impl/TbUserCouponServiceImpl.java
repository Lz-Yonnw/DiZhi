package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.TbCoupon;
import com.ruoyi.system.domain.TbUserCoupon;
import com.ruoyi.system.mapper.TbCouponMapper;
import com.ruoyi.system.mapper.TbUserCouponMapper;
import com.ruoyi.system.service.ITbUserCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户优惠劵Service业务层处理
 *
 * @author ruoyi
 * @date 2022-05-31
 */
@Service
public class TbUserCouponServiceImpl implements ITbUserCouponService
{
    @Autowired
    private TbUserCouponMapper tbUserCouponMapper;

    @Autowired
    private TbCouponMapper tbCouponMapper;

    /**
     * 查询用户优惠劵
     *
     * @param id 用户优惠劵主键
     * @return 用户优惠劵
     */
    @Override
    public TbUserCoupon selectTbUserCouponById(Long id)
    {
        return tbUserCouponMapper.selectTbUserCouponById(id);
    }

    /**
     * 优惠价数量
     * @param userId
     * @return
     */
    @Override
    public int selectTbUserCouponCountByUserId(Integer userId) {
        return tbUserCouponMapper.selectTbUserCouponCountByUserId(userId);
    }

    /**
     * 根据用户查询
     * @param userId
     * @return
     */
    @Override
    public List<TbUserCoupon> selectTbUserCouponListByUserId(Long userId) {
        TbUserCoupon tbUserCoupon = new TbUserCoupon();
        tbUserCoupon.setUserId(userId);
        tbUserCoupon.setStatus(1);
        //优惠价列表
        List<TbUserCoupon> tbUserCoupons = tbUserCouponMapper.selectTbUserCouponList(tbUserCoupon);
        tbUserCoupons.forEach(tbUserCoupon1 -> {
            //获取当个优惠价并赋值
            TbCoupon tbCoupon = tbCouponMapper.selectTbCouponById(tbUserCoupon1.getCouponId());
            tbUserCoupon1.setTbCoupon(tbCoupon);
        });
        return tbUserCoupons;
    }

    /**
     * 查询用户优惠劵列表
     *
     * @param tbUserCoupon 用户优惠劵
     * @return 用户优惠劵
     */
    @Override
    public List<TbUserCoupon> selectTbUserCouponList(TbUserCoupon tbUserCoupon)
    {
        return tbUserCouponMapper.selectTbUserCouponList(tbUserCoupon);
    }

    /**
     * 新增用户优惠劵
     *
     * @param tbUserCoupon 用户优惠劵
     * @return 结果
     */
    @Override
    public int insertTbUserCoupon(TbUserCoupon tbUserCoupon)
    {
        tbUserCoupon.setCreateTime(DateUtils.getNowDate());
        return tbUserCouponMapper.insertTbUserCoupon(tbUserCoupon);
    }

    /**
     * 修改用户优惠劵
     *
     * @param tbUserCoupon 用户优惠劵
     * @return 结果
     */
    @Override
    public int updateTbUserCoupon(TbUserCoupon tbUserCoupon)
    {
        return tbUserCouponMapper.updateTbUserCoupon(tbUserCoupon);
    }

    /**
     * 批量删除用户优惠劵
     *
     * @param ids 需要删除的用户优惠劵主键
     * @return 结果
     */
    @Override
    public int deleteTbUserCouponByIds(Long[] ids)
    {
        return tbUserCouponMapper.deleteTbUserCouponByIds(ids);
    }

    /**
     * 删除用户优惠劵信息
     *
     * @param id 用户优惠劵主键
     * @return 结果
     */
    @Override
    public int deleteTbUserCouponById(Long id)
    {
        return tbUserCouponMapper.deleteTbUserCouponById(id);
    }
}
