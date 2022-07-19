package com.ruoyi.system.service;

import com.ruoyi.system.domain.TbOrderReturn;

import java.util.List;

/**
 * 订单退货重做Service接口
 *
 * @author ruoyi
 * @date 2022-07-14
 */
public interface ITbOrderReturnService
{
    /**
     * 查询订单退货重做
     *
     * @param orderSn 订单退货重做主键
     * @return 订单退货重做
     */
    public TbOrderReturn selectTbOrderReturnByOrderSn(String orderSn);

    /**
     * 查询订单退货重做列表
     *
     * @param tbOrderReturn 订单退货重做
     * @return 订单退货重做集合
     */
    public List<TbOrderReturn> selectTbOrderReturnList(TbOrderReturn tbOrderReturn);

    /**
     * 新增订单退货重做
     *
     * @param tbOrderReturn 订单退货重做
     * @return 结果
     */
    public int insertTbOrderReturn(TbOrderReturn tbOrderReturn);

    /**
     * 修改订单退货重做
     *
     * @param tbOrderReturn 订单退货重做
     * @return 结果
     */
    public int updateTbOrderReturn(TbOrderReturn tbOrderReturn);

    /**
     * 批量删除订单退货重做
     *
     * @param orderSns 需要删除的订单退货重做主键集合
     * @return 结果
     */
    public int deleteTbOrderReturnByOrderSns(String[] orderSns);

    /**
     * 删除订单退货重做信息
     *
     * @param orderSn 订单退货重做主键
     * @return 结果
     */
    public int deleteTbOrderReturnByOrderSn(String orderSn);
}
