package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.TbCoupon;
import com.ruoyi.system.domain.TbMallCoupon;
import com.ruoyi.system.mapper.TbCouponMapper;
import com.ruoyi.system.mapper.TbMallCouponMapper;
import com.ruoyi.system.service.ITbMallCouponService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品+优惠劵中间Service业务层处理
 *
 * @author ruoyi
 * @date 2022-05-31
 */
@Service
public class TbMallCouponServiceImpl implements ITbMallCouponService
{
    @Autowired
    private TbMallCouponMapper tbMallCouponMapper;

    @Autowired
    private TbCouponMapper tbCouponMapper;

    /**
     * 查询商品+优惠劵中间
     *
     * @param id 商品+优惠劵中间主键
     * @return 商品+优惠劵中间
     */
    @Override
    public TbMallCoupon selectTbMallCouponById(Long id)
    {
        return tbMallCouponMapper.selectTbMallCouponById(id);
    }

    /**
     * 查询商品+优惠劵中间列表
     *
     * @param tbMallCoupon 商品+优惠劵中间
     * @return 商品+优惠劵中间
     */
    @Override
    public List<TbMallCoupon> selectTbMallCouponList(TbMallCoupon tbMallCoupon)
    {
        return tbMallCouponMapper.selectTbMallCouponList(tbMallCoupon);
    }

    /**
     * 新增商品+优惠劵中间
     *
     * @param tbMallCoupon 商品+优惠劵中间
     * @return 结果
     */
    @Override
    public int insertTbMallCoupon(TbMallCoupon tbMallCoupon)
    {
        return tbMallCouponMapper.insertTbMallCoupon(tbMallCoupon);
    }

    /**
     * 修改商品+优惠劵中间
     *
     * @param tbMallCoupon 商品+优惠劵中间
     * @return 结果
     */
    @Override
    public int updateTbMallCoupon(TbMallCoupon tbMallCoupon)
    {
        return tbMallCouponMapper.updateTbMallCoupon(tbMallCoupon);
    }

    /**
     * 批量删除商品+优惠劵中间
     *
     * @param ids 需要删除的商品+优惠劵中间主键
     * @return 结果
     */
    @Override
    public int deleteTbMallCouponByIds(Long[] ids)
    {
        return tbMallCouponMapper.deleteTbMallCouponByIds(ids);
    }

    /**
     * 删除商品+优惠劵中间信息
     *
     * @param id 商品+优惠劵中间主键
     * @return 结果
     */
    @Override
    public int deleteTbMallCouponById(Long id)
    {
        return tbMallCouponMapper.deleteTbMallCouponById(id);
    }

    /**
     * 根据商品Id查询优惠劵
     * @param id
     * @return
     */
    @Override
    public List<TbMallCoupon> selectTbMallCouponByMallId(Long id) {
        TbMallCoupon tbMallCoupon = new TbMallCoupon();
        tbMallCoupon.setMallId(id);
        tbMallCoupon.setStatus(1L);
        List<TbMallCoupon> tbMallCoupons = tbMallCouponMapper.selectTbMallCouponList(tbMallCoupon);
        tbMallCoupons.forEach((item->{
            item.setTbCoupon(tbCouponMapper.selectTbCouponById(item.getCouponId()));
        }));
        return tbMallCoupons;
    }


    /**
     * 根据商品Id查询未关联的的优惠劵
     * @param id
     * @return
     */
    @Override
    public List<TbCoupon> selectTbMallCouponByMallIdNoCoupon(Long id) {
        return tbCouponMapper.selectTbCouponnotInMallCoupon(id);
    }
}
