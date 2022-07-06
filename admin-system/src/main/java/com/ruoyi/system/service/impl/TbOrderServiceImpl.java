package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.TbAddressBookMapper;
import com.ruoyi.system.mapper.TbOrderDerailsMapper;
import com.ruoyi.system.mapper.TbOrderMapper;
import com.ruoyi.system.service.ITbCouponService;
import com.ruoyi.system.service.ITbOrderService;
import com.ruoyi.system.service.TbMallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;

/**
 * 订单Service业务层处理
 *
 * @author ruoyi
 * @date 2022-07-05
 */
@Service
public class TbOrderServiceImpl implements ITbOrderService
{
    @Autowired
    private TbOrderMapper tbOrderMapper;

    @Autowired
    private TbOrderDerailsMapper tbOrderDerailsMapper;

    @Autowired
    private TbAddressBookMapper tbAddressBookMapper;

    @Autowired
    private TbMallService tbMallService;

    @Autowired
    private ITbCouponService tbCouponService;


    /**
     * 查询订单
     *
     * @param id 订单主键
     * @return 订单
     */
    @Override
    public TbOrder selectTbOrderById(Long id)
    {
        return tbOrderMapper.selectTbOrderById(id);
    }

    /**
     * 查询订单列表
     *
     * @param tbOrder 订单
     * @return 订单
     */
    @Override
    public List<TbOrder> selectTbOrderList(TbOrder tbOrder)
    {
        return tbOrderMapper.selectTbOrderList(tbOrder);
    }

    /**
     * 查询订单列表  详情
     * @param orderSn
     * @param status
     * @param modelType
     * @param userId
     * @return
     */
    @Override
    public List<TbOrder> selectTbOrderDerailsList(String orderSn, Integer status, Integer modelType, Integer userId) {
        //订单列表
        List<TbOrder> tbOrders = tbOrderMapper.selectTbOrderDerailsList(orderSn, status, modelType, userId);
        for (TbOrder item : tbOrders){

            TbOrderDerails tbOrderDerails = new TbOrderDerails();
            tbOrderDerails.setOrdersn(item.getOrderSn());
            //订单详情
            TbOrderDerails orderDerail = tbOrderDerailsMapper.selectTbOrderDerailsList(tbOrderDerails).get(0);
            //用户地址
            TbAddressBook tbAddressBook = tbAddressBookMapper.selectTbAddressBookById(orderDerail.getAddressId());
            item.setTbAddressBook(tbAddressBook);
            item.setTbOrderDerails(orderDerail);
        }
        return tbOrders;
    }

    /**
     * 查询订单详情  列表
     * @param tbOrder
     * @return
     */
    @Override
    public List<TbOrder> selectTbOrderDerailsList(TbOrder tbOrder) {

        return tbOrderMapper.selectTbOrderList(tbOrder);
    }


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
     * @param couponId  优惠卷Id
     * @param size      尺码
     * @param colour    颜色
     * @param quantity  数量
     * @param remark    备注
     * @param payType   支付方式
     * @param modelType 订单类型
     * @param clothes_json
     * @return
     */
    @Transactional
    @Override
    public int insertTbOrder(String orderSn, String name, String image, String payAmount, String amount, Integer storeId, Integer mallId, Integer userId, Integer addressId, Integer couponId, String size, String colour, Integer quantity, String remark, Integer payType, Integer modelType, String clothes_json) {
        TbOrder tbOrder = new TbOrder();
        tbOrder.setOrderSn(orderSn);
        tbOrder.setName(name);
        tbOrder.setPayAmount(new BigDecimal(payAmount));
        tbOrder.setRemark(remark);
        tbOrder.setPayType(payType);
        tbOrder.setModelType(modelType);
        tbOrder.setCreateTime(DateUtils.getNowDate());
        tbOrder.setStatus(1);
        tbOrder.setTimeOutFlag((long)1);

        TbOrderDerails tbOrderDerails = new TbOrderDerails();
        tbOrderDerails.setOrdersn(orderSn);
        tbOrderDerails.setAmount(new BigDecimal(amount));
        tbOrderDerails.setStoreId((long)storeId);
        tbOrderDerails.setMallId((long)mallId);
        tbOrderDerails.setUserId((long)userId);
        tbOrderDerails.setAddressId((long)addressId);
        if(couponId!=0){
            //优惠卷查询
            TbCoupon tbCoupon = tbCouponService.selectTbCouponById((long) couponId);
            tbOrderDerails.setCouponId((long)couponId);
            tbOrderDerails.setCouponAmount(tbCoupon.getCouponPrice());
        }
        tbOrderDerails.setSize(size);
        tbOrderDerails.setQuantity(quantity);
        //计算总金额  数量乘以单价
        BigDecimal count = new BigDecimal(quantity.toString());
        tbOrderDerails.setTotalAmount(count.multiply(new BigDecimal(amount)));
        //查看收货地址
        if(addressId!=0){
            //下单收货地址
            TbAddressBook tbAddressBook = tbAddressBookMapper.selectTbAddressBookById((long) addressId);
            tbOrder.setPhone(tbAddressBook.getPhone());
            tbOrder.setConsignee(tbAddressBook.getConsignee());
        }
        //判断添加的订单类型  订单类型 1：普通订单，2：模型订单，3：试尺码订单
        switch (modelType){
            case 1:
                tbOrderDerails.setColour(colour);
                //商品
                TbMall tbMall = tbMallService.selectTbMallById((long)mallId);
                tbOrder.setName(tbMall.getName());
                tbOrder.setImage(tbMall.getImg());
                break;
            case 2:
            case 3:
                tbOrder.setImage(image);
                tbOrder.setClothesJson(clothes_json);
                break;
        }
        //添加订单
        tbOrderMapper.insertTbOrder(tbOrder);
        //添加订单详情
        return tbOrderDerailsMapper.insertTbOrderDerails(tbOrderDerails);
    }


    /**
     * 修改订单
     *
     * @param tbOrder 订单
     * @return 结果
     */
    @Override
    public int updateTbOrder(TbOrder tbOrder)
    {
        return tbOrderMapper.updateTbOrder(tbOrder);
    }

    /**
     * 批量删除订单
     *
     * @param ids 需要删除的订单主键
     * @return 结果
     */
    @Override
    public int deleteTbOrderByIds(String[] ids,Integer userId)
    {
        return tbOrderMapper.deleteTbOrderByIds(ids,userId);
    }

    /**
     * 删除订单信息
     *
     * @param id 订单主键
     * @return 结果
     */
    @Override
    public int deleteTbOrderById(String id,Integer userId)
    {
        return tbOrderMapper.deleteTbOrderById(id,userId);
    }
}
