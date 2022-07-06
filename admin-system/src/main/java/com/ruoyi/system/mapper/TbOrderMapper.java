package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.TbOrder;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单Mapper接口
 *
 * @author ruoyi
 * @date 2022-07-05
 */
public interface TbOrderMapper
{
    /**
     * 查询订单
     *
     * @param id 订单主键
     * @return 订单
     */
    public TbOrder selectTbOrderById(Long id);

    /**
     * 查询订单详情
     * @param orderSn
     * @param status
     * @param modelType
     * @param userId
     * @return
     */
    public List<TbOrder> selectTbOrderDerailsList(@Param("orderSn") String orderSn,
                                                  @Param("status")Integer status,
                                                  @Param("modelType")Integer modelType,
                                                  @Param("userId")Integer userId);

    /**
     * 查询订单列表
     *
     * @param tbOrder 订单
     * @return 订单集合
     */
    public List<TbOrder> selectTbOrderList(TbOrder tbOrder);

    /**
     * 新增订单
     *
     * @param tbOrder 订单
     * @return 结果
     */
    public int insertTbOrder(TbOrder tbOrder);

    /**
     * 修改订单
     *
     * @param tbOrder 订单
     * @return 结果
     */
    public int updateTbOrder(TbOrder tbOrder);

    /**
     * 删除订单
     *
     * @param id 订单主键
     * @return 结果
     */
    public int deleteTbOrderById(@Param("id") String id,@Param("userId") Integer userId);

    /**
     * 批量删除订单
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbOrderByIds(@Param("ids") String[] ids,@Param("userId") Integer userId);
}
