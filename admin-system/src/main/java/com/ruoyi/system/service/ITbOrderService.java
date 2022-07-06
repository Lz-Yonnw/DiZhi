package com.ruoyi.system.service;

import com.ruoyi.system.domain.TbOrder;

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
     * 查询订单
     *
     * @param id 订单主键
     * @return 订单
     */
    public TbOrder selectTbOrderById(Long id);

    /**
     * 查询订单列表
     *
     * @param tbOrder 订单
     * @return 订单集合
     */
    public List<TbOrder> selectTbOrderList(TbOrder tbOrder);

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
     * 查询订单详情  列表
     * @param tbOrder
     * @return
     */
    public List<TbOrder> selectTbOrderDerailsList(TbOrder tbOrder);

    /**
     * 添加订单
     * @param orderSn  订单号
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
    public int insertTbOrder(String orderSn,String name,String image,String payAmount,String amount,Integer storeId,Integer mallId,Integer userId,Integer addressId,Integer couponId,
                             String size,String colour,Integer quantity,String remark,Integer payType,Integer modelType,String clothes_json);

    /**
     * 修改订单
     *
     * @param tbOrder 订单
     * @return 结果
     */
    public int updateTbOrder(TbOrder tbOrder);

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
}
