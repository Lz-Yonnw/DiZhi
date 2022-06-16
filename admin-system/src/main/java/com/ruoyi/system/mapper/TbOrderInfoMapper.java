package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.TbOrderInfo;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 订单信息
 */

public interface TbOrderInfoMapper {

    /**
     * 用户所有订单
     * @param userId
     * @param status
     * @param orderSn
     * @return
     */
    List<TbOrderInfo> selectTbOrderInfoListByUserId(@Param("userId") Long userId, @Param("status") int status, @Param("orderSn") String orderSn);
    /**
     * 查询订单信息列表
     *
     * @param tbOrderInfo 订单信息
     * @return 订单信息集合
     */
    List<TbOrderInfo> selectTbOrderInfoList(TbOrderInfo tbOrderInfo);

    /**
     * 订单状态变跟
     * @param id
     * @param status
     * @return
     */
    int updateTbOrderInfoStatus(@Param("id") Long id,@Param("status") Long status);

    //查询该用户单个订单
    TbOrderInfo selectOrderInfo(@Param("orderInfoId") long orderInfoId);

    /**
     * 查询用户订单列表
     * @param phoneNumber
     * @param status
     * @param orderSn
     * @return
     */
    List<TbOrderInfo> selectOrderInfoPhones(String phoneNumber,int status,String orderSn);




    /**
     * 添加订单
     */
    void insert(TbOrderInfo tbOrderInfo);

    /**
     * 根据订单号查询订单
     * @param s
     * @return
     */
    TbOrderInfo selectOrderSn(String s);


    /**
     * 删除订单
     * @param orderInfoId 订单ID
     * @param delBy 删除人
     */
    int deleteTbOrderInfoById(@Param("orderInfoId") Long orderInfoId,@Param("delBy") String delBy);

    /**
     * 修改订单
     * @param tbOrderInfo
     */
    int updateTbOrderInfo(TbOrderInfo tbOrderInfo);
}
