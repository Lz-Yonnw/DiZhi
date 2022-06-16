package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.dto.TbOrderDetailsDto;
import com.ruoyi.system.domain.dto.TbOrderInfoDto;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.ITbOrderInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

/*
获取订单详细信息
 */
@Service
public class TbOrderInfoServiceImpl implements ITbOrderInfoService {

    @Autowired
    private TbOrderDerailsMapper tbOrderDerailsMapper;
    @Autowired
    private TbOrderInfoMapper tbOrderInfoMapper;
    @Autowired
    private TbMallMapper tbMallMapper;
    @Autowired
    private TbAddressBookMapper tbAddressBookMapper;
    @Autowired
    private TbUserInfoMapper tbUserInfoMapper;
    private int mallId;
    private int addressId;
    private String remark;
    private String colour;
    private String size;
    private int pay;
    private String amount;
    private int quantity;
    private int userId;
    private int storeId;

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

    //商户Dao
    @Autowired
    private TbStoreMapper tbStoreMapper;

    @Autowired
    private TbMallImgsMapper tbMallImgsMapper;

    //根据订单详细信息id获取信息
    @Override
    public TbOrderDerails selectOrderDerails(int orderDerailsId) {

        TbOrderDerails tbOrderDerails = tbOrderDerailsMapper.selectOrderDerailsByOrderInfoId((long)orderDerailsId);
        return tbOrderDerails;
    }

    @Override
    public TbOrderInfo selectOrderInfo(long orderInfoId) {
        TbOrderInfo tbOrderInfo = tbOrderInfoMapper.selectOrderInfo(orderInfoId);
        return tbOrderInfo;
    }
    /**
     * 订单列表
     * @param tbOrderInfo
     * @return
     */
    @Override
    public List<TbOrderInfo> selectTbOrderInfoList(TbOrderInfo tbOrderInfo) {
        return tbOrderInfoMapper.selectTbOrderInfoList(tbOrderInfo);
    }

    /**
     * 修改订单
     * @param tbOrderInfo
     * @param json
     * @return
     */
    @Override
    @Transactional
    public int updateTbOrderInfoById(TbOrderInfo tbOrderInfo,String json) {
        JSONObject imagesJSON = JSONObject.parseObject(json);
        if(imagesJSON.getString("mallImagesList")!=null){
            //商品
            JSONArray jsonArray = JSONArray.parseArray(imagesJSON.getString("mallImagesList"));
            if(jsonArray.size()>=1){
                Long id = jsonArray.getJSONObject(0).getLong("id");
                String fileUrl = jsonArray.getJSONObject(0).getString("fileUrl");
                TbMall tbMall = new TbMall();
                tbMall.setId(id);
                tbMall.setImg(fileUrl);
                tbMallMapper.updateMallById(tbMall);
            }
        }
        if(imagesJSON.getString("modelTypeImageList")!=null){
            //布料
            JSONArray jsonArray = JSONArray.parseArray(imagesJSON.getString("modelTypeImageList"));

            if(jsonArray.size()>=1){
                //模型参数属性

                StringBuffer stringBuffer = new StringBuffer(tbOrderInfo.getClothesJson());

                for(int i =0;i<jsonArray.size();i++){
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    String clothesJson = tbOrderInfo.getClothesJson();
                    //TODO json解析递归需要完善
                    TbPatternComponent tbPatternComponent = new TbPatternComponent();
                    tbPatternComponent.setClassId(jsonObject.getLong("typeId"));
                    tbPatternComponent.setPatternId(jsonObject.getLong("oldId"));
                    TbPatternComponent tbPatternComponent1 = tbPatternComponentMapper.selectTbPatternComponentList(tbPatternComponent).get(0);
                    if(tbPatternComponent1!=null){
                        //获取面料
                        if(clothesJson.contains("pattern\":[{\"id\":\""+tbPatternComponent1.getId()+"\",\"typeId\":"+jsonObject.getLong("typeId")+"}]")){
                            TbPatternComponent tbPatternco = new TbPatternComponent();
                            tbPatternco.setClassId(jsonObject.getLong("typeId"));
                            tbPatternco.setPatternId(jsonObject.getLong("newId"));
                            TbPatternComponent tbPatternCom = tbPatternComponentMapper.selectTbPatternComponentList(tbPatternco).get(0);
                            String replace = clothesJson.replace("pattern\":[{\"id\":\""+tbPatternComponent1.getId()+"\",\"typeId\":"+jsonObject.getLong("typeId")+"}]"
                                    , "pattern\":[{\"id\":\""+tbPatternCom.getId()+"\",\"typeId\":"+jsonObject.getLong("typeId")+"}]");
                            tbOrderInfo.setClothesJson(replace);
                        }
                    }
//                    clothesJson.replace
//                    TbPattern tbPattern = new TbPattern();
//                    tbPattern.setId(jsonArray.getJSONObject(i).getLong("id"));
//                    tbPattern.setName(jsonArray.getJSONObject(i).getString("name"));
//                    tbPattern.setPicture(jsonArray.getJSONObject(i).getString("fileUrl"));
//                    tbPatternMapper.updateTbPattern(tbPattern);
                }
            }
        }
        if(imagesJSON.getString("mallDescImageList")!=null){
            //商品详情图
            JSONArray jsonArray = JSONArray.parseArray(imagesJSON.getString("mallDescImageList"));
            if(jsonArray.size()>=1){
                for(int i =0;i<jsonArray.size();i++){
                    TbOrderDerails tbOrderDerails = new TbOrderDerails();
                    tbOrderDerails.setId(jsonArray.getJSONObject(i).getLong("id"));
                    tbOrderDerails.setImage(jsonArray.getJSONObject(i).getString("fileUrl"));
                    tbOrderDerailsMapper.updateTbOrderDerails(tbOrderDerails);
                }
            }
        }
        return tbOrderInfoMapper.updateTbOrderInfo(tbOrderInfo);
    }

    /**
     * 订单状态变跟
     * @param id
     * @param status
     * @return
     */
    @Override
    public int updateTborderInfoStatusById(Long id, Long status) {
        return tbOrderInfoMapper.updateTbOrderInfoStatus(id, status);
    }

    private static void replaceValue(JSONObject obj) {
        Set<String> strings = obj.keySet();
        for(String key : strings){
            if(key=="pattern" && key.contains("20")){

            }else {
                replaceValue(JSONObject.parseObject(key));
            }
        }
    }
    /**
     * 删除订单
     * @param orderInfoId 订单ID
     * @param delName 删除人
     * @return
     */
    @Override
    public int deleteTbOrderInfoById(Long orderInfoId, String delName) {
        return tbOrderInfoMapper.deleteTbOrderInfoById(orderInfoId,delName);
    }


    /**
     * 订单详情
     * @param id
     * @return
     */
    @Override
    public TbOrderDetailsDto selectTbOrderInfoById(Long id) {
        //订单详情Dto
        TbOrderDetailsDto tbOrderDetailsDto = new TbOrderDetailsDto();
        //single订单
        TbOrderInfo tbOrderInfo = tbOrderInfoMapper.selectOrderInfo(id);

        if(tbOrderInfo!=null){
            //订单详情
            TbOrderDerails tbOrderDerails = tbOrderDerailsMapper.selectOrderInfoById(id);
            tbOrderDetailsDto.setTbOrderDerails(tbOrderDerails);
            if(StringUtils.isNotEmpty(String.valueOf(tbOrderInfo.getStoreId()))){
                //商户信息
                TbStore tbStore = tbStoreMapper.selectTbStoreById(tbOrderInfo.getStoreId());
                tbOrderDetailsDto.setTbStore(tbStore);
            }
            if(StringUtils.isNotEmpty(String.valueOf(tbOrderInfo.getUserId()))){
                //用户信息
                TbUserInfo tbUserInfo = tbUserInfoMapper.selectTbUserInfoById(tbOrderInfo.getUserId());
                tbOrderDetailsDto.setTbUserInfo(tbUserInfo);
            }
            if(StringUtils.isNotEmpty(String.valueOf(tbOrderInfo.getMallId()))){
                if(tbOrderInfo.getMallId()!=0){
                    //商品
                    TbMall select = tbMallMapper.select((int) tbOrderInfo.getMallId());
                    //商品详情图
                    List<TbMallImgs> tbMallImgs = tbMallImgsMapper.selectMallId(select.getId());
                    select.setImgsList(tbMallImgs);
                    tbOrderDetailsDto.setTbMall(select);
                }
            }
            if(StringUtils.isNotEmpty(tbOrderInfo.getClothesJson())){
                //模型属性JSON解析
                TbModelStyle modelParam = getModelParam(tbOrderInfo.getClothesJson());
                tbOrderDetailsDto.setTbModelStyle(modelParam);
            }
            if(StringUtils.isNotEmpty(tbOrderInfo.getAddress())){
                //收货地址
                TbAddressBook tbAddressBook = tbAddressBookMapper.selectTbAddressBookById(Long.valueOf(tbOrderInfo.getAddress()));
                tbOrderInfo.setTbAddressBook(tbAddressBook);
                tbOrderDetailsDto.setTbOrderInfo(tbOrderInfo);
            }
        }
        return tbOrderDetailsDto;
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

    @Override
    public String insertOrderInfo(int mallId, int addressId, String remark, String colour, String size, int pay, String amount, int quantity, int userId, int storeId,Long modelType,String clothesJson) {
        if (String.valueOf(mallId)==null || String.valueOf(userId)==null||String.valueOf(addressId)==null){
            return "404";
        }
        TbUserInfo tbUserInfo = tbUserInfoMapper.selectTbUserInfoById((long) userId);
        TbMall tbMall = tbMallMapper.select(mallId);
        TbAddressBook tbAddressBook = tbAddressBookMapper.selectTbAddressBookById(Long.valueOf(addressId));
        long creationTime = System.currentTimeMillis();
        String orderSn = String.valueOf(creationTime)+userId+pay+storeId+mallId+quantity+ addressId;
        TbOrderInfo tbOrderInfo = new TbOrderInfo();
        tbOrderInfo.setPayAmount(new BigDecimal(amount));
        tbOrderInfo.setAmount(new BigDecimal(amount));
        tbOrderInfo.setOrderSn(orderSn);
        tbOrderInfo.setStoreId(storeId);
        tbOrderInfo.setMallId(mallId);
        tbOrderInfo.setUserId(userId);
        tbOrderInfo.setStatus(0L);
        tbOrderInfo.setPayType(pay);
        tbOrderInfo.setModelType(modelType.toString());
        tbOrderInfo.setPayAccount(tbUserInfo.getWechatNumber());
        tbOrderInfo.setAddress(String.valueOf(addressId));
        tbOrderInfo.setPhone(tbAddressBook.getPhone());
        tbOrderInfo.setConsignee(tbAddressBook.getConsignee());
        tbOrderInfo.setRemark(remark);
        tbOrderInfo.setDelFlag(0L);
        tbOrderInfo.setTimeOutFlag(0L);
        tbOrderInfo.setPayTime(new Date());
        tbOrderInfo.setClothesJson(clothesJson.toString());
        tbOrderInfoMapper.insert(tbOrderInfo);
        TbOrderInfo tbOrderInfoId = tbOrderInfoMapper.selectOrderSn(orderSn);

        TbOrderDerails tbOrderDerails = new TbOrderDerails();
        if(tbMall!=null){
            tbOrderDerails.setName(tbMall.getName());

            tbOrderDerails.setImage(tbMall.getImg());
        }
        tbOrderDerails.setAmount(new BigDecimal(amount));
        tbOrderDerails.setOrderInfoId(tbOrderInfoId.getId());
        tbOrderDerails.setMallId(mallId);
        tbOrderDerails.setStoreId(storeId);
        tbOrderDerails.setSize(size);
        tbOrderDerails.setColour(colour);
        tbOrderDerails.setQuantity(quantity);
        tbOrderDerailsMapper.insert(tbOrderDerails);
       return orderSn;
    }

    @Override
    public TbOrderInfoDto orderAddDerails(TbOrderDerails tbOrderDerails, TbOrderInfo tbOrderInfo) {
        TbOrderInfoDto tbOrderInfoDto = new TbOrderInfoDto();
        tbOrderInfoDto.setId(tbOrderInfo.getId());
        tbOrderInfoDto.setPayAmount(tbOrderInfo.getPayAmount().toString());
        tbOrderInfoDto.setAmount(tbOrderInfo.getAmount().toString());
        tbOrderInfoDto.setOrderSn(tbOrderInfo.getOrderSn());
        tbOrderInfoDto.setModelType(tbOrderInfo.getModelType());
        tbOrderInfoDto.setStoreId(tbOrderInfo.getStoreId());
        tbOrderInfoDto.setMallId(tbOrderInfo.getMallId());
        tbOrderInfoDto.setUserId(tbOrderInfo.getUserId());
        tbOrderInfoDto.setStatus(tbOrderInfo.getStatus());
        tbOrderInfoDto.setPayType(tbOrderInfo.getPayType());
        tbOrderInfoDto.setPayAccount(tbOrderInfo.getPayAccount());
        tbOrderInfoDto.setAddress(tbOrderInfo.getAddress());
        tbOrderInfoDto.setPhone(tbOrderInfo.getPhone());
        tbOrderInfoDto.setConsignee(tbOrderInfo.getConsignee());
        tbOrderInfoDto.setRemark(tbOrderInfo.getRemark());
        tbOrderInfoDto.setPayTime(new Date());
        tbOrderInfoDto.setDelTime(null);
        tbOrderInfoDto.setDelFlag(0L);
        tbOrderInfoDto.setDelBy(null);
        tbOrderInfoDto.setTimeOutFlag(0L);
        tbOrderInfoDto.setTbOrderDerails(tbOrderDerails);
        return tbOrderInfoDto;
    }

    /**
     * 用户所有订单
     * @param userId
     * @param status
     * @param orderSn
     * @return
     */
    @Override
    public List<TbOrderInfo> selectOrderInfoList(Long userId, int status, String orderSn) {
        return tbOrderInfoMapper.selectTbOrderInfoListByUserId(userId, status, orderSn);
    }

    /**
     * 根据订单id查询详情
     * @param orderId
     * @return
     */
    @Override
    public TbOrderDerails selectOrderDerailsList(Long orderId) {
        return tbOrderDerailsMapper.selectOrderDerailsByOrderInfoId(orderId);
    }

}
