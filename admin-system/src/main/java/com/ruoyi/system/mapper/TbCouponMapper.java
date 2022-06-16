package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.TbCoupon;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 优惠劵Mapper接口
 *
 * @author ruoyi
 * @date 2022-05-31
 */
public interface TbCouponMapper
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
     * 删除优惠劵
     *
     * @param id 优惠劵主键
     * @return 结果
     */
    public int deleteTbCouponById(Long id);

    /**
     * 批量删除优惠劵
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbCouponByIds(Long[] ids);

    /**
     * 商品未关联的优惠劵集
     * @param mallId    商品Id
     * @return
     */
    public List<TbCoupon> selectTbCouponnotInMallCoupon(@Param("mallId") Long mallId);
}
