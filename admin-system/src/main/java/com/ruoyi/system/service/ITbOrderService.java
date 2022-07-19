package com.ruoyi.system.service;

import com.ruoyi.system.domain.TbOrder;
import com.ruoyi.system.domain.TbOrderReturn;

import java.util.List;

/**
 * 订单Service接口
 *
 * @author ruoyi
 * @date 2022-07-05
 */
public interface ITbOrderService
{
    /**
     * 根据订单号获取订单详情
     *
     * @param orderSn 订单主键
     * @return 订单
     */
    public TbOrder selectTbOrderByOrderSn(String orderSn);

    /**
     * 查询订单列表
     *
     * @param tbOrder 订单
     * @return 订单集合
     */
    public List<TbOrder> selectTbOrderList(TbOrder tbOrder);

    /**
     * 查询当前用户有无购买过此版型模型
     * @param userId
     * @param modelStyleId
     * @return
     */
    public Integer selectTborderModelStyleUser(Integer userId,Integer modelStyleId);

    /**
     * 查询订单列表  详情
     * @param orderSn
     * @param status
     * @param modelType
     * @param userId
     * @return
     */
    public List<TbOrder> selectTbOrderDerailsList(String orderSn,Integer status,Integer modelType,Integer userId);

    /**
     * 添加订单
     * @param name      订单名称
     * @param image     订单图
     * @param payAmount 订单金额
     * @param amount    产品单价
     * @param storeId   店铺Id
     * @param mallId    商品Id
     * @param userId    用户Id
     * @param addressId 地址Id
     * @param couponId  优惠价Id
     * @param size      尺码
     * @param colour    颜色
     * @param quantity  数量
     * @param remark    备注
     * @param payType   支付方式
     * @param modelType 订单类型
     * @param clothes_json
     * @return
     */
    public String insertTbOrder(String name,String image,String payAmount,String amount,Integer storeId,Integer mallId,Integer userId,Integer addressId,Integer couponId,
                             String size,String colour,Integer quantity,String remark,Integer payType,Integer modelType,String clothes_json);

    /**
     * 修改订单
     *
     * @param tbOrder 订单
     * @return 结果
     */
    public int updateTbOrder(TbOrder tbOrder);

    /**
     * 根据订单号修改
     * @param tbOrder
     * @return
     */
    public int updateTbOrderByOrderSn(TbOrder tbOrder);

    /**
     * 批量删除订单
     *
     * @param ids 需要删除的订单主键集合
     * @return 结果
     */
    public int deleteTbOrderByIds(String[] ids,Integer userId);

    /**
     * 删除订单信息
     *
     * @param id 订单主键
     * @return 结果
     */
    public int deleteTbOrderById(String id,Integer userId);

    /**
     * 申请 取消 重做 退款 订单
     * @param tbOrderReturn
     * @return
     */
    public int applyTbOrderRefund(TbOrderReturn tbOrderReturn, Integer type);

}
