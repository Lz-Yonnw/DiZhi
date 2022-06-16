package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TbCouponMapper;
import com.ruoyi.system.domain.TbCoupon;
import com.ruoyi.system.service.ITbCouponService;

/**
 * 优惠劵Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-05-31
 */
@Service
public class TbCouponServiceImpl implements ITbCouponService 
{
    @Autowired
    private TbCouponMapper tbCouponMapper;

    /**
     * 查询优惠劵
     * 
     * @param id 优惠劵主键
     * @return 优惠劵
     */
    @Override
    public TbCoupon selectTbCouponById(Long id)
    {
        return tbCouponMapper.selectTbCouponById(id);
    }

    /**
     * 查询优惠劵列表
     * 
     * @param tbCoupon 优惠劵
     * @return 优惠劵
     */
    @Override
    public List<TbCoupon> selectTbCouponList(TbCoupon tbCoupon)
    {
        return tbCouponMapper.selectTbCouponList(tbCoupon);
    }

    /**
     * 新增优惠劵
     * 
     * @param tbCoupon 优惠劵
     * @return 结果
     */
    @Override
    public int insertTbCoupon(TbCoupon tbCoupon)
    {
        tbCoupon.setCreateTime(DateUtils.getNowDate());
        return tbCouponMapper.insertTbCoupon(tbCoupon);
    }

    /**
     * 修改优惠劵
     * 
     * @param tbCoupon 优惠劵
     * @return 结果
     */
    @Override
    public int updateTbCoupon(TbCoupon tbCoupon)
    {
        return tbCouponMapper.updateTbCoupon(tbCoupon);
    }

    /**
     * 批量删除优惠劵
     * 
     * @param ids 需要删除的优惠劵主键
     * @return 结果
     */
    @Override
    public int deleteTbCouponByIds(Long[] ids)
    {
        return tbCouponMapper.deleteTbCouponByIds(ids);
    }

    /**
     * 删除优惠劵信息
     * 
     * @param id 优惠劵主键
     * @return 结果
     */
    @Override
    public int deleteTbCouponById(Long id)
    {
        return tbCouponMapper.deleteTbCouponById(id);
    }
}
