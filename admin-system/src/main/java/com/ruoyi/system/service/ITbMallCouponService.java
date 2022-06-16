package com.ruoyi.system.service;

import com.ruoyi.system.domain.TbCoupon;
import com.ruoyi.system.domain.TbMallCoupon;

import java.util.List;

/**
 * 商品+优惠劵中间Service接口
 *
 * @author ruoyi
 * @date 2022-05-31
 */
public interface ITbMallCouponService
{
    /**
     * 查询商品+优惠劵中间
     *
     * @param id 商品+优惠劵中间主键
     * @return 商品+优惠劵中间
     */
    public TbMallCoupon selectTbMallCouponById(Long id);

    /**
     * 查询商品+优惠劵中间列表
     *
     * @param tbMallCoupon 商品+优惠劵中间
     * @return 商品+优惠劵中间集合
     */
    public List<TbMallCoupon> selectTbMallCouponList(TbMallCoupon tbMallCoupon);

    /**
     * 新增商品+优惠劵中间
     *
     * @param tbMallCoupon 商品+优惠劵中间
     * @return 结果
     */
    public int insertTbMallCoupon(TbMallCoupon tbMallCoupon);

    /**
     * 修改商品+优惠劵中间
     *
     * @param tbMallCoupon 商品+优惠劵中间
     * @return 结果
     */
    public int updateTbMallCoupon(TbMallCoupon tbMallCoupon);

    /**
     * 批量删除商品+优惠劵中间
     *
     * @param ids 需要删除的商品+优惠劵中间主键集合
     * @return 结果
     */
    public int deleteTbMallCouponByIds(Long[] ids);

    /**
     * 删除商品+优惠劵中间信息
     *
     * @param id 商品+优惠劵中间主键
     * @return 结果
     */
    public int deleteTbMallCouponById(Long id);

    /**
     * 根据商品Id查询优惠劵
     * @param id
     * @return
     */
    public List<TbMallCoupon> selectTbMallCouponByMallId(Long id);

    /**
     * 根据商品Id查询未关联的的优惠劵
     * @param id
     * @return
     */
    public List<TbCoupon> selectTbMallCouponByMallIdNoCoupon(Long id);
}
