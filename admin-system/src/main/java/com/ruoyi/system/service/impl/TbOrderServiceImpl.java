package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.ITbCouponService;
import com.ruoyi.system.service.ITbOrderService;
import com.ruoyi.system.service.TbMallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
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

    @Autowired
    private TbModelStyleMapper tbModelStyleMapper;

    @Autowired
    private TbPlateClassMapper tbPlateClassMapper;

    @Autowired
    private TbPlateCutWayMapper tbPlateCutWayMapper;

    @Autowired
    private TbComponentMapper tbComponentMapper;

    @Autowired
    private TbClassifyMapper tbClassifyMapper;

    @Autowired
    private TbPatternComponentMapper tbPatternComponentMapper;

    @Autowired
    private TbPatternMapper tbPatternMapper;

    @Autowired
    private TbOrderReturnMapper tbOrderReturnMapper;

    /**
     * 根据订单号获取订单详情
     *
     * @param orderSn 订单主键
     * @return 订单
     */
    @Override
    public TbOrder selectTbOrderByOrderSn(String orderSn)
    {
        //获取当前订单
        TbOrder tbOrder = new TbOrder();
        tbOrder.setOrderSn(orderSn);
        TbOrder result = tbOrderMapper.selectTbOrderList(tbOrder).get(0);
        //获取当前订单详情
        getTbOrderAllDesc(result);
        return result;
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
     * 查询当前用户有无购买过此版型模型
     * @param userId
     * @param modelStyleId
     * @return
     */
    @Override
    public Integer selectTborderModelStyleUser(Integer userId, Integer modelStyleId) {
        return tbOrderMapper.selectTborderModelStyleUser(userId,modelStyleId);
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
        //重做订单
        if(status==13){
            for (TbOrder item : tbOrders){
                TbOrderReturn tbOrderReturn = new TbOrderReturn();
                tbOrderReturn.setOrderSn(item.getOrderSn());
                tbOrderReturn.setReturnStatus(modelType);
                item.setReturnList(tbOrderReturnMapper.selectTbOrderReturnList(tbOrderReturn));

            }
            return tbOrders;
        }
        for (TbOrder item : tbOrders){
            TbOrderDerails tbOrderDerails = new TbOrderDerails();
            tbOrderDerails.setOrdersn(item.getOrderSn());
            TbAddressBook tbAddressBook = tbAddressBookMapper.selectTbAddressBookById(item.getTbOrderDerails().getAddressId());
            item.setTbAddressBook(tbAddressBook);
        }
        return tbOrders;
    }

    /**
     * 根据订单编号  获取所有详情消息
     * @param item
     */
    public void getTbOrderAllDesc(TbOrder item){
        TbOrderDerails tbOrderDerails = new TbOrderDerails();
        tbOrderDerails.setOrdersn(item.getOrderSn());
        //订单详情
        TbOrderDerails orderDerail = tbOrderDerailsMapper.selectTbOrderDerailsList(tbOrderDerails).get(0);
        //用户地址
        if(item.getModelType()==1){
            //商品详情
            TbMall tbMall = tbMallService.selectTbMallById(orderDerail.getMallId());
            item.setImgsList(tbMall.getImgsList());
        }else {
            //模型属性JSON解析
            TbModelStyle modelParam = getModelParam(item.getClothesJson());
            item.setTbModelStyle(modelParam);
        }
        if(orderDerail.getCouponId()!=null){
            //优惠卷
            item.setTbCoupon(tbCouponService.selectTbCouponById(orderDerail.getCouponId()));
        }
        if(item.getStatus()==6||item.getStatus()==11){
            //退款  查询
            TbOrderReturn tbOrderReturn = tbOrderReturnMapper.selectTbOrderReturnByOrderSn(item.getOrderSn());
            item.setTbOrderReturn(tbOrderReturn);
        }
        if(item.getStatus()==13){
            //重做
            TbOrderReturn tbOrderReturn = new TbOrderReturn();
            tbOrderReturn.setOrderSn(item.getOrderSn());
            item.setReturnList(tbOrderReturnMapper.selectTborderReturnAddressList(tbOrderReturn));
        }
        TbAddressBook tbAddressBook = tbAddressBookMapper.selectTbAddressBookById(orderDerail.getAddressId());
        item.setTbAddressBook(tbAddressBook);
        item.setTbOrderDerails(orderDerail);
    }

    /**
     * 解析 模型部件参数json
     * @param json
     * @return
     */
    public TbModelStyle getModelParam(String json){
        JSONArray jsonArray = JSONArray.parseArray(json);

        //当前版型小类
        Long modelStyleId = jsonArray.getJSONObject(0).getLong("parentId");
        TbModelStyle tbModelStyle = tbModelStyleMapper.selectTbModelStyleById(modelStyleId);

        ArrayList<TbPlateClass> plateClassList = new ArrayList<TbPlateClass>();
        //版型大类-部位
        for (int i =0;i<jsonArray.size();i++){
            //版型大类-部位
            JSONObject plateClassObject = jsonArray.getJSONObject(i);
            Long plateClassId = plateClassObject.getLong("id");
            TbPlateClass tbPlateClass = tbPlateClassMapper.selectTbPlateClassById(plateClassId);
            plateClassList.add(tbPlateClass);

            String plateCutWayJSON = plateClassObject.getString("plateCutWay");
            //板块小类-裁剪方式
            JSONArray plateCutWay = JSONArray.parseArray(plateCutWayJSON);
            ArrayList<TbPlateCutWay> tbPlateCutWayList = new ArrayList<TbPlateCutWay>();
            for (int j=0;j<plateCutWay.size();j++){
                JSONObject plateCutWayObject = plateCutWay.getJSONObject(j);
                Long id = plateCutWayObject.getLong("id");
                TbPlateCutWay tbPlateCutWay = tbPlateCutWayMapper.selectTbPlateCutWayById(id);
                tbPlateCutWayList.add(tbPlateCutWay);

                String componentJSON = plateCutWayObject.getString("componentList");
                //部位
                JSONArray component = JSONArray.parseArray(componentJSON);
                ArrayList<TbComponent> tbComponentList = new ArrayList<TbComponent>();
                for (int l=0;l<component.size();l++){
                    JSONObject componentObject = component.getJSONObject(l);
                    Long componentId = componentObject.getLong("id");
                    TbComponent tbComponent = tbComponentMapper.selectTbComponentById(componentId);
                    tbComponentList.add(tbComponent);

                    //分类
                    String tbClassify = componentObject.getString("outBottomFabric");
                    if(tbClassify==null){
                        continue;
                    }
                    JSONArray tbClassifyJSON = JSONArray.parseArray(tbClassify);
                    ArrayList<TbClassify> classifyList = new ArrayList<TbClassify>();
                    for (int c=0;c<tbClassifyJSON.size();c++){
                        JSONObject tbClassifyJObject = tbClassifyJSON.getJSONObject(c);
                        Long tbClassifyId = tbClassifyJObject.getLong("id");
                        TbClassify classify = tbClassifyMapper.selectTbClassifyById(tbClassifyId);
                        classifyList.add(classify);

                        String pattern = tbClassifyJObject.getString("pattern");
                        //图库-分类-部位中间类
                        JSONArray tbPatternComponentJSON = JSONArray.parseArray(pattern);
                        ArrayList<TbPattern> tbPatternList = new ArrayList<TbPattern>();
                        for (int d=0;d<tbPatternComponentJSON.size();d++){
                            JSONObject tbPatternComponentObject = tbPatternComponentJSON.getJSONObject(d);
                            Long tbPatternComponentObjectId = tbPatternComponentObject.getLong("id");
                            TbPatternComponent patternComponent = tbPatternComponentMapper.selectTbPatternComponentById(tbPatternComponentObjectId);
                            if(patternComponent!=null){
                                //获取面料
                                TbPattern tbPattern = tbPatternMapper.selectTbPatternById(patternComponent.getPatternId());
                                tbPatternList.add(tbPattern);
                            }
                        }
                        classify.setTbPatternList(tbPatternList);
                    }
                    tbComponent.setTbClassifyList(classifyList);
                }
                tbPlateCutWay.setTbComponentList(tbComponentList);
            }
            tbPlateClass.setTbPlateCutWayList(tbPlateCutWayList);
        }
        tbModelStyle.setTbPlateClassList(plateClassList);
        return tbModelStyle;
    }


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
    public String insertTbOrder(String name, String image, String payAmount, String amount, Integer storeId, Integer mallId, Integer userId, Integer addressId, Integer couponId, String size, String colour, Integer quantity, String remark, Integer payType, Integer modelType, String clothes_json) {
        //订单号生成
        String orderSn = "10";
        for (Integer i = 0; i < 18; i++) {
            String randChar = String.valueOf(Math.round(Math.random() * 9));
            orderSn = orderSn.concat(randChar);
        }

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
        tbOrderDerails.setQuantity(quantity.intValue());
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
        tbOrderDerailsMapper.insertTbOrderDerails(tbOrderDerails);
        return orderSn;
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
     * 根据订单号修改
     * @param tbOrder
     * @return
     */
    @Override
    public int updateTbOrderByOrderSn(TbOrder tbOrder) {
        return tbOrderMapper.updateTbOrderByOrderSn(tbOrder);
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

    /**
     * 申请 1：退货  2：重做 3：取消  订单
     * @param tbOrderReturn
     * @return
     */
    @Transactional
    @Override
    public int applyTbOrderRefund(TbOrderReturn tbOrderReturn,Integer type) {
        TbOrder tbOrder = tbOrderMapper.selectTbOrderDerailsList(tbOrderReturn.getOrderSn(), null, null, null).get(0);
        switch (type){
            case 1:
                tbOrder.setStatus(6);
                break;
            case 2:
                tbOrder.setStatus(13);
                break;
            case 3:
                tbOrder.setStatus(12);
                break;
        }
        //修改订单状态
        tbOrderMapper.updateTbOrder(tbOrder);
        //保存订单操作信息
        return tbOrderReturnMapper.insertTbOrderReturn(tbOrderReturn);
    }
}
