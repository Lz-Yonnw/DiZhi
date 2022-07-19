package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TbOrderReturnMapper;
import com.ruoyi.system.domain.TbOrderReturn;
import com.ruoyi.system.service.ITbOrderReturnService;

/**
 * 订单退货重做Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-07-14
 */
@Service
public class TbOrderReturnServiceImpl implements ITbOrderReturnService 
{
    @Autowired
    private TbOrderReturnMapper tbOrderReturnMapper;

    /**
     * 查询订单退货重做
     * 
     * @param orderSn 订单退货重做主键
     * @return 订单退货重做
     */
    @Override
    public TbOrderReturn selectTbOrderReturnByOrderSn(String orderSn)
    {
        return tbOrderReturnMapper.selectTbOrderReturnByOrderSn(orderSn);
    }

    /**
     * 查询订单退货重做列表
     * 
     * @param tbOrderReturn 订单退货重做
     * @return 订单退货重做
     */
    @Override
    public List<TbOrderReturn> selectTbOrderReturnList(TbOrderReturn tbOrderReturn)
    {
        return tbOrderReturnMapper.selectTbOrderReturnList(tbOrderReturn);
    }

    /**
     * 新增订单退货重做
     * 
     * @param tbOrderReturn 订单退货重做
     * @return 结果
     */
    @Override
    public int insertTbOrderReturn(TbOrderReturn tbOrderReturn)
    {
        return tbOrderReturnMapper.insertTbOrderReturn(tbOrderReturn);
    }

    /**
     * 修改订单退货重做
     * 
     * @param tbOrderReturn 订单退货重做
     * @return 结果
     */
    @Override
    public int updateTbOrderReturn(TbOrderReturn tbOrderReturn)
    {
        return tbOrderReturnMapper.updateTbOrderReturn(tbOrderReturn);
    }

    /**
     * 批量删除订单退货重做
     * 
     * @param orderSns 需要删除的订单退货重做主键
     * @return 结果
     */
    @Override
    public int deleteTbOrderReturnByOrderSns(String[] orderSns)
    {
        return tbOrderReturnMapper.deleteTbOrderReturnByOrderSns(orderSns);
    }

    /**
     * 删除订单退货重做信息
     * 
     * @param orderSn 订单退货重做主键
     * @return 结果
     */
    @Override
    public int deleteTbOrderReturnByOrderSn(String orderSn)
    {
        return tbOrderReturnMapper.deleteTbOrderReturnByOrderSn(orderSn);
    }
}
