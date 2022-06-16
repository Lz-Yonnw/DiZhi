package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.TbMallCoupon;

/**
 * 商品+优惠劵中间Mapper接口
 * 
 * @author ruoyi
 * @date 2022-05-31
 */
public interface TbMallCouponMapper 
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
     * 删除商品+优惠劵中间
     * 
     * @param id 商品+优惠劵中间主键
     * @return 结果
     */
    public int deleteTbMallCouponById(Long id);

    /**
     * 批量删除商品+优惠劵中间
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbMallCouponByIds(Long[] ids);
}
