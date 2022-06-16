package com.ruoyi.system.service;

import com.ruoyi.system.domain.TbOrderDerails;
import com.ruoyi.system.domain.TbOrderInfo;
import com.ruoyi.system.domain.dto.TbOrderDetailsDto;
import com.ruoyi.system.domain.dto.TbOrderInfoDto;

import java.util.List;

/**
 * 订单信息
 */
public interface ITbOrderInfoService {

    //根据订单详细信息表id获取数据
    TbOrderDerails selectOrderDerails(int orderDerailsId);


    //根据订单表id获取订单表
    TbOrderInfo selectOrderInfo(long orderInfoId);

    //封装数据返回
    TbOrderInfoDto orderAddDerails(TbOrderDerails tbOrderDerails, TbOrderInfo tbOrderInfo);



    /**
     * 根据用户Id查询订单
     * @param userId
     * @param status
     * @param orderSn
     * @return
     */
    List<TbOrderInfo> selectOrderInfoList(Long userId,int status,String orderSn);

    /**
     * 根据订单id查询详情
     * @param orderId
     * @return
     */
    TbOrderDerails selectOrderDerailsList(Long orderId);

    /**
     * 订单列表
     * @param tbOrderInfo
     * @return
     */
    List<TbOrderInfo> selectTbOrderInfoList(TbOrderInfo tbOrderInfo);

    /**
     * 修改订单
     * @param tbOrderInfo
     * @return
     */
    int updateTbOrderInfoById(TbOrderInfo tbOrderInfo,String json);

    /**
     * 订单状态变跟
     * @param id
     * @param status
     * @return
     */
    int updateTborderInfoStatusById(Long id,Long status);

    /**
     * 删除订单
     * @param orderInfoId 订单ID
     * @param delName 删除人
     * @return
     */
    int deleteTbOrderInfoById(Long orderInfoId,String delName);

    /**
     * 订单详情
     * @param id
     * @return
     */
    TbOrderDetailsDto selectTbOrderInfoById(Long id);

    /**
     * 下单
     * @param mallId 商品id
     * @param addressId 地址id
     * @param remark 备注
     * @param colour 颜色
     * @param size  尺码
     * @param pay  支付方式
     * @param amount 金额
     * @param quantity  数量
     * @param userId  用户id
     * @param storeId  商户id
     * @return 状态码
     */
    String insertOrderInfo(int mallId, int addressId, String remark, String colour, String size, int pay, String amount, int quantity, int userId, int storeId,Long modelType,String clothesJson);

}
