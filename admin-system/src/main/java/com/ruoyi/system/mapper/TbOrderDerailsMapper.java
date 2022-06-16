package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.TbOrderDerails;
import org.apache.ibatis.annotations.Param;

//订单详细信息
public interface TbOrderDerailsMapper {


    /**
     * 根据订单id查询详情
     * @param orderId
     * @return
     */
    TbOrderDerails selectOrderDerailsByOrderInfoId(@Param("orderId") Long orderId);

    //查询用户的所有订单
    TbOrderDerails selectOrderDerailsList(Long orderDerailsId);

    /**
     * 添加订单详细信息表
     * @param tbOrderDerails
     */
    void insert(TbOrderDerails tbOrderDerails);

    /**
     * 订单Id查询订单详情
     * @param orderInfoId 订单ID
     * @return
     */
    TbOrderDerails selectOrderInfoById(@Param("orderInfoId")Long orderInfoId);

    /**
     * 修改订单详情
     * @param tbOrderDerails
     * @return
     */
    int updateTbOrderDerails(TbOrderDerails tbOrderDerails);
}
