package com.ruoyi.netty.server.handler;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.constant.Constants;
import com.ruoyi.common.core.domain.entity.SysDictData;
import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.common.utils.StringUtils;
import com.ruoyi.netty.common.protobuf.MessageProtocol.MessageBase;
import com.ruoyi.netty.server.ChannelRepository;
import com.ruoyi.netty.server.entity.Domain;
import com.ruoyi.netty.server.serviceImpl.AuthServiceImpl;
import com.ruoyi.netty.util.PayUtil;
import com.ruoyi.netty.util.SMSUtil;
import com.ruoyi.netty.util.keyUtil;
import com.ruoyi.system.domain.*;
import com.ruoyi.system.domain.dto.TbUserDescDto;
import com.ruoyi.system.service.*;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.AttributeKey;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import sun.misc.BASE64Decoder;

import javax.imageio.stream.FileImageOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.net.InetSocketAddress;
import java.util.Date;
import java.util.List;


/**
 * 连接认证Handler
 * 1. 连接成功后客户端发送CommandType.AUTH指令，Sever端验证通过后返回CommandType.AUTH_BACK指令
 * 2. 处理心跳指令
 * 3. 触发下一个Handler
 * @author Ke Shanqiang
 *
 */
@Component
@Qualifier("authServerHandler")
@ChannelHandler.Sharable
public class AuthServerHandler extends ChannelInboundHandlerAdapter {
	private static final Logger log = LoggerFactory.getLogger(AuthServerHandler.class);
	private final AttributeKey<String> clientInfo = AttributeKey.valueOf("clientInfo");
	
	@Autowired
	@Qualifier("channelRepository")
	ChannelRepository channelRepository;

	@Autowired
	ITbModelTypeService iTbModelTypeService;

	@Autowired
	ITbModelStyleService iTbModelStyleService;

	@Autowired
	ITbPlateClassService iTbPlateClassService;

	@Autowired
	ITbPlateCutWayService iTbPlateCutWayService;

//	@Autowired
//	private RedisCache redisCache;

	@Autowired
	ITbWhiteDesignService tbWhiteDesignService;

	@Autowired
	ITbComponentService iTbComponentService;

	@Autowired
	ITbClassifyService iTbClassifyService;

	@Autowired
	ITbPatternService iTbPatternService;

	@Autowired
	ITbPatternComponentService iTbPatternComponentService;

	@Autowired
	ITbUserInfoService iTbUserInfoService;

	@Autowired
	ITbUserWardrobeService iTbUserWardrobeService;

	@Autowired
	TbMallService tbMallService;

	@Autowired
	AuthServiceImpl authService;

	@Autowired
	RedisCache redisCache;

	@Value("${domain_name}")
	private String domainName;

	/**
	 * 通道建立调用
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		InetSocketAddress insocket = (InetSocketAddress) ctx.channel().remoteAddress();
		String clientIP = insocket.getAddress().getHostAddress();
		System.out.println("与客户端建立连接，通道开启！"+clientIP);
		//将链接存入连接池(自定义)
	}

	/**
	 * 通道断开调用
	 * @param ctx
	 * @throws Exception
	 */
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		log.info("与客户端断开连接，通道关闭！");
		//移除channelGroup 通道组
	}




	@SuppressWarnings("deprecation")
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws UnsupportedEncodingException {
		try {
			byte[] dataBytes = (byte[]) msg;
			if (dataBytes==null){
				throw new RuntimeException("无效参数");
			}

			MessageBase msgBase = MessageBase.parseFrom(dataBytes);
			if(msgBase.getType().equals(MessageBase.Type.LOGIN_REQ)){
				log.info("===登录/注册===");
				authService.login(ctx, msgBase);
			}else if(msgBase.getType().equals(MessageBase.Type.REGISTER_REQ)){
				log.info("===注册===");
				MessageBase.Builder authMsg= MessageBase.newBuilder();
				authMsg.setClientId(ctx.channel().id().toString());
				authMsg.setType(MessageBase.Type.REGISTER_RESP);
				String phoneNumber = msgBase.getLoginOrRegisterUpdateUserInfoReq().getPhoneNumber();
				String s="";
				if(phoneNumber!=null&&!phoneNumber.equals("")){
					//随机生成四位验证码的工具类
					String code = keyUtil.keyUtils();
					s = new SMSUtil().sendSMS(phoneNumber,code);
				}
				if(!s.equals("success")){
					this.send(ctx,authMsg,500);
					return;
				}
				TbUserInfo userInfo = new TbUserInfo();
				userInfo.setPhoneNumber(phoneNumber);
				TbUserInfo tbUserInfo = iTbUserInfoService.selectTbUserInfoByOtherParam(userInfo);
				if (tbUserInfo==null) {
					userInfo.setIsFirst(0);
					iTbUserInfoService.insertTbUserInfo(userInfo);
					this.send(ctx,authMsg,200);
				}else{
					this.send(ctx,authMsg,501);
				}
			}else if(msgBase.getType().equals(MessageBase.Type.PERFECT_REQ)){
				log.info("===完善个人信息===");
				MessageBase.Builder authMsg= MessageBase.newBuilder();
				authMsg.setClientId(ctx.channel().id().toString());
				authMsg.setType(MessageBase.Type.PERFECT_RESP);
				TbUserInfo userInfo = new TbUserInfo();
				userInfo.setId(msgBase.getLoginOrRegisterUpdateUserInfoReq().getId());
				userInfo.setGender(msgBase.getLoginOrRegisterUpdateUserInfoReq().getGender()+"");
				userInfo.setUserNickname(msgBase.getLoginOrRegisterUpdateUserInfoReq().getUserNickname());
				userInfo.setIsFirst(msgBase.getLoginOrRegisterUpdateUserInfoReq().getIsFirst());
				userInfo.setBirthday(DateUtils.parseDate(msgBase.getLoginOrRegisterUpdateUserInfoReq().getBirthday()));
				userInfo.setWeight(new BigDecimal(msgBase.getLoginOrRegisterUpdateUserInfoReq().getWeight()));
				userInfo.setHeight(new BigDecimal(msgBase.getLoginOrRegisterUpdateUserInfoReq().getHeight()));
				int i = iTbUserInfoService.updateTbUserInfo(userInfo);
				if (i>0) {
					this.send(ctx,authMsg,200);
				}else{
					this.send(ctx,authMsg,500);
				}

			}else if(msgBase.getType().equals(MessageBase.Type.UPDATE_USER_REQ)){
				log.info("更新用户信息操作==={}", msgBase.getUpdateUserReq().toString());


			}else if(msgBase.getType().equals(MessageBase.Type.MODEL_TYPE_REQ)){
				//TODO =====
				log.info("根据版型类型获取版型大类列表==={}");
				MessageBase.Builder authMsg= MessageBase.newBuilder();
				authMsg.setClientId(ctx.channel().id().toString());
				authMsg.setType(MessageBase.Type.MODEL_TYPE_RESP);
				TbModelType tbModelType = new TbModelType();
				tbModelType.setType(msgBase.getModelTypeReq().getType());
				List<TbModelType> tbModelTypes = iTbModelTypeService.selectTbModelTypeList(tbModelType);
				MessageBase.ModelTypeListResp.Builder builder = MessageBase.ModelTypeListResp.newBuilder();
				for (TbModelType modelType:tbModelTypes) {
					MessageBase.ModelStyleResp.Builder modelStyleResp = MessageBase.ModelStyleResp.newBuilder();
					MessageBase.ModelTypeResp.Builder type=MessageBase.ModelTypeResp.newBuilder();
					type.setId(modelType.getId()).setName(modelType.getName()).setType(modelType.getType()).setImg(modelType.getImg()==null?"":modelType.getImg());

					TbModelStyle modelStyle = new TbModelStyle();
					modelStyle.setParentId(modelType.getId());
					List<TbModelStyle> modelStyles = iTbModelStyleService.selectTbModelStyleList(modelStyle);
					for (TbModelStyle style:modelStyles) {
						modelStyleResp.setImg(style.getImg()).setCode(style.getCode()).setId(style.getId()).setModelId(modelType.getId()).setName(style.getName());
						type.addModelStyleResp(modelStyleResp);
					}
					builder.addModelTypeResp(type);
				}
				authMsg.setModelTypeListResp(builder);
				this.send(ctx,authMsg,200);
			}else if(msgBase.getType().equals(MessageBase.Type.PLATE_CLASS_REQ)){
				log.info("获取设计页列表==={}",msgBase.getModelTypeReq().toString());
				MessageBase.Builder authMsg= MessageBase.newBuilder();
				authMsg.setClientId(ctx.channel().id().toString());
				authMsg.setType(MessageBase.Type.PLATE_CLASS_RESP);
				TbPlateClass plateClass = new TbPlateClass();
				plateClass.setParentId(msgBase.getModelTypeReq().getStyleId());
				List<TbPlateClass> tbPlateClasses = iTbPlateClassService.selectTbPlateClassList(plateClass);
				MessageBase.PlateClassListResp.Builder result = MessageBase.PlateClassListResp.newBuilder();
				for (TbPlateClass tbPlateClass:tbPlateClasses) {
					TbPlateClass tbPlateClass1 = tbPlateClass;
					MessageBase.PlateClassResp.Builder mPlateClass=MessageBase.PlateClassResp.newBuilder();
					mPlateClass.setId(tbPlateClass.getId()).setCode(tbPlateClass.getCode()==null?"":tbPlateClass.getCode()).
							setImg(tbPlateClass.getImg()==null?"":tbPlateClass.getImg())
							.setName(tbPlateClass.getName()==null?"":tbPlateClass.getName()).setParentId(msgBase.getModelTypeReq().getStyleId());
					TbPlateCutWay plateCutWay = new TbPlateCutWay();

					plateCutWay.setParentId(tbPlateClass.getId());
					List<TbPlateCutWay> tbPlateCutWays = iTbPlateCutWayService.selectTbPlateCutWayList(plateCutWay);
					for (TbPlateCutWay tbPlateCutWay:tbPlateCutWays) {
						MessageBase.PlateCutWay.Builder mPlateCutWay=MessageBase.PlateCutWay.newBuilder();
						mPlateCutWay.setCode(tbPlateCutWay.getCode()==null?"":tbPlateCutWay.getCode()).
								setId(tbPlateCutWay.getId()).setImg(tbPlateCutWay.getImg()==null?"":tbPlateCutWay.getImg())
								.setName(tbPlateCutWay.getName()==null?"":tbPlateCutWay.getName()).setParentId(tbPlateClass.getId())
								.setQrCode(tbPlateCutWay.getQrCode()==null?"":tbPlateCutWay.getQrCode()).setSuffixCode(tbPlateCutWay.getSuffixCode()==null?"":tbPlateCutWay.getSuffixCode());
						TbComponent tbComponent = new TbComponent();
						tbComponent.setParentId(tbPlateCutWay.getId());
						List<TbComponent> tbComponents = iTbComponentService.selectTbComponentList(tbComponent);

						for (TbComponent component:tbComponents) {
							MessageBase.Component.Builder mComponent=MessageBase.Component.newBuilder();
							mComponent.setId(component.getId()).setName(component.getName()==null?"":component.getName()).setCode(component.getCode()==null?"":component.getCode())
									.setParentId(component.getParentId()).setPicture(component.getPicture()==null?"":component.getPicture()).setModelUrl(component.getModelUrl()==null?"":component.getModelUrl())
									.setBasicPrice(new Double(component.getBasicPrice().toString())).setMaterialDosage(new Double(component.getMaterialDosage().toString()))
									.setQrCode(component.getQrCode()==null?"":component.getQrCode()).setSuffixCode(component.getSuffixCode()==null?"":component.getSuffixCode());
							mComponent.setModelUrl(component.getModelUrl());
							TbClassify classify = new TbClassify();
							classify.setParentId(component.getId());
							List<TbClassify> classifies = iTbClassifyService.selectTbClassifyList(classify);
							for (TbClassify tbClassify:classifies) {
								if (tbClassify.getPId()==0) {
									MessageBase.Classify.Builder classifyName=MessageBase.Classify.newBuilder();
									classifyName.setId(tbClassify.getId()).setType(tbPlateCutWay.getId().intValue()).setName(tbClassify.getName()==null?"":tbClassify.getName()).setPId(0)
											.setParentId(tbClassify.getParentId());
									TbPatternComponent tbPatternComponent = new TbPatternComponent();
									tbPatternComponent.setClassId(tbClassify.getId());
									List<TbPatternComponent> tbPatternComponents = iTbPatternComponentService.selectTbPatternComponentAllList(tbPatternComponent);
									for (TbPatternComponent patternComponent:tbPatternComponents) {
										MessageBase.Pattern.Builder pattern=MessageBase.Pattern.newBuilder();
										pattern.setId(patternComponent.getId()).setCode(patternComponent.getCode()==null?"":patternComponent.getCode())
												.setName(patternComponent.getName()==null?"":patternComponent.getName()).setPicture(patternComponent.getPicture()==null?"":patternComponent.getPicture())
												.setPrice(new Double(patternComponent.getPrice().toString()==null?0.00+"":patternComponent.getPrice().toString())).setTypeId(patternComponent.getTypeId().intValue());
										classifyName.addPattern(pattern);
									}
									if (tbClassify.getType()==1) {
										//图案
										mComponent.addOutPattern(classifyName);
									}else if(tbClassify.getType()==2){
										//不可填充配饰
										mComponent.addOutUnfilled(classifyName);
									}else if(tbClassify.getType()==3){
										//表层面料
										mComponent.addOutSurfaceFabric(classifyName);
									}else if(tbClassify.getType()==4){
										//底层面料
										mComponent.addOutBottomFabric(classifyName);
									}else if(tbClassify.getType()==5){
										TbClassify classifyOne = new TbClassify();
										classifyOne.setParentId(component.getId());
										classifyOne.setPId(tbClassify.getId());
										List<TbClassify> classifiesOne = iTbClassifyService.selectTbClassifyList(classifyOne);
										for (TbClassify tbClassifyOne:classifiesOne) {
											MessageBase.Classify.Builder classifyInner=MessageBase.Classify.newBuilder();
											classifyInner.setId(tbClassifyOne.getId()).setType(tbClassifyOne.getType()).setName(tbClassifyOne.getName()==null?"":tbClassifyOne.getName()).setPId(tbClassify.getId())
													.setParentId(tbClassifyOne.getParentId());
											TbPatternComponent tbPatternComponentInner = new TbPatternComponent();
											tbPatternComponentInner.setClassId(tbClassifyOne.getId());
											List<TbPatternComponent> tbPatternComponentsInner = iTbPatternComponentService.selectTbPatternComponentAllList(tbPatternComponentInner);
											for (TbPatternComponent patternComponentInner:tbPatternComponentsInner) {
												MessageBase.Pattern.Builder patternInner=MessageBase.Pattern.newBuilder();
												patternInner.setId(patternComponentInner.getId()).setCode(patternComponentInner.getCode()==null?"":patternComponentInner.getCode())
														.setName(patternComponentInner.getName()==null?"":patternComponentInner.getName()).setPicture(patternComponentInner.getPicture()==null?"":patternComponentInner.getPicture())
														.setPrice(new Double(patternComponentInner.getPrice().toString()==null?0.00+"":patternComponentInner.getPrice().toString())).setTypeId(patternComponentInner.getTypeId().intValue())
														.setSuffixCode(patternComponentInner.getSuffixCode()==null?"":patternComponentInner.getSuffixCode()).setQrCode(patternComponentInner.getQrCode()==null?"":patternComponentInner.getQrCode());
												classifyInner.addPattern(patternInner);
											}
											if (tbClassifyOne.getType()==1) {
												//图案
												classifyName.addInnerPattern(classifyInner);
											}else if(tbClassifyOne.getType()==2){
												//不可填充配饰
												classifyName.addInnerUnfilled(classifyInner);
											}else if(tbClassifyOne.getType()==3){
												//表层面料
												classifyName.addInnerSurfaceFabric(classifyInner);
											}else if(tbClassifyOne.getType()==4){
												//底层面料
												classifyName.addInnerBottomFabric(classifyInner);
											}
										}
										//可填充配
										mComponent.addOutFillAble(classifyName);
									}
								}
							}
							mPlateCutWay.addComponentList(mComponent);
						}
						if (tbComponents.size()>0) {
							mPlateClass.addPlateCutWay(mPlateCutWay);
						}
					}
					if (mPlateClass!=null && tbPlateClass1!=null) {
						result.addPlateClassResp(mPlateClass);
						log.info(result.toString());
					}

				}
				for(int i=0;i<result.getPlateClassRespCount();i++){
					int plateCutWayCount = result.getPlateClassResp(i).getPlateCutWayCount();
					if (plateCutWayCount==0) {
						result.removePlateClassResp(i);
					}
				}
				TbWhiteDesign tbWhiteDesign = tbWhiteDesignService.selectTbWhiteDesignByStyleId(msgBase.getModelTypeReq().getStyleId());
				if (tbWhiteDesign!=null) {
					MessageBase.UserWardrobeReq.Builder wardrobe=MessageBase.UserWardrobeReq.newBuilder();
					wardrobe.setId(tbWhiteDesign.getId());
					wardrobe.setClothesJson(tbWhiteDesign.getClothesJson()==null?"":tbWhiteDesign.getClothesJson());
					result.setUserWardrobeReq(wardrobe);
				}

				authMsg.setPlateClassListResp(result);
				this.send(ctx,authMsg,200);
			}else if(msgBase.getType().equals(MessageBase.Type.WARDROBE_SAVE_REQ)){
				log.info("选择衣服保存到衣柜==={}");
				MessageBase.Builder authMsg= MessageBase.newBuilder();
				authMsg.setClientId(ctx.channel().id().toString());
				authMsg.setType(MessageBase.Type.WARDROBE_SAVE_RESP);
//				byte[] bytes = msgBase.getUserWardrobeReq().getImage().toByteArray();;
//				this.byte2image(bytes,"C:\\Users\\Administrator\\Pictures\\test.jpg");
//				String base64 = msgBase.getUserWardrobeReq().getQrCode();;
//				this.GenerateImage(base64,"C:\\Users\\Administrator\\Pictures\\test.jpg");

				TbUserWardrobe tbUserWardrobe = new TbUserWardrobe();

				tbUserWardrobe.setClothesJson(msgBase.getUserWardrobeReq().getClothesJson());
				tbUserWardrobe.setName(msgBase.getUserWardrobeReq().getName());
				tbUserWardrobe.setUserId(msgBase.getUserWardrobeReq().getUserId());
				tbUserWardrobe.setStyleId(msgBase.getUserWardrobeReq().getStyleId());
				tbUserWardrobe.setQrCode(msgBase.getUserWardrobeReq().getQrCode());
				tbUserWardrobe.setImage(msgBase.getUserWardrobeReq().getImage());
				tbUserWardrobe.setCreateTime(DateUtils.getNowDate());
				tbUserWardrobe.setClothesJson(msgBase.getUserWardrobeReq().getClothesJson());
				iTbUserWardrobeService.insertTbUserWardrobe(tbUserWardrobe);
				this.send(ctx,authMsg,200);
			}else if(msgBase.getType().equals(MessageBase.Type.GET_USER_WARDROBE_REQ)){
				log.info("查看衣柜==={}");
				MessageBase.Builder authMsg= MessageBase.newBuilder();
				authMsg.setClientId(ctx.channel().id().toString());
				authMsg.setType(MessageBase.Type.GET_USER_WARDROBE_RESP);
				TbUserWardrobe tbUserWardrobe = new TbUserWardrobe();
				tbUserWardrobe.setUserId(msgBase.getUserWardrobeReq().getUserId());
				tbUserWardrobe.setStyleId(msgBase.getUserWardrobeReq().getStyleId());
				List<TbUserWardrobe> tbUserWardrobes = iTbUserWardrobeService.selectTbUserWardrobeList(tbUserWardrobe);
				MessageBase.UserWardrobeListResp.Builder list=MessageBase.UserWardrobeListResp.newBuilder();
				for (TbUserWardrobe userWardrobe:tbUserWardrobes) {
					MessageBase.UserWardrobeReq.Builder wardrobe=MessageBase.UserWardrobeReq.newBuilder();
					wardrobe.setId(userWardrobe.getId());
					wardrobe.setClothesJson(userWardrobe.getClothesJson()==null?"":userWardrobe.getClothesJson());
					wardrobe.setName(userWardrobe.getName()==null?"":userWardrobe.getName());
					wardrobe.setImage(userWardrobe.getImage());
					list.addUserWardrobeReq(wardrobe);
				}
				authMsg.setUserWardrobeListResp(list);
				this.send(ctx,authMsg,200);
			}else if(msgBase.getType().equals(MessageBase.Type.WHITE_DESIGN_REQ)){
				log.info("查看白膜==={}");
				MessageBase.Builder authMsg= MessageBase.newBuilder();
				authMsg.setClientId(ctx.channel().id().toString());
				authMsg.setType(MessageBase.Type.WHITE_DESIGN_RESP);
				TbWhiteDesign tbWhiteDesign = tbWhiteDesignService.selectTbWhiteDesignByStyleId(msgBase.getUserWardrobeReq().getStyleId());
				MessageBase.UserWardrobeReq.Builder wardrobe=MessageBase.UserWardrobeReq.newBuilder();
				wardrobe.setId(tbWhiteDesign.getId());
				wardrobe.setClothesJson(tbWhiteDesign.getClothesJson());
				authMsg.setUserWardrobeReq(wardrobe);
				this.send(ctx,authMsg,200);
			}else if(msgBase.getType().equals(MessageBase.Type.SEND_MSG_REQ)){
				log.info("===发送验证码===");
				authService.sendMsg(ctx, msgBase);
				/*MessageBase.Builder authMsg= MessageBase.newBuilder();
				authMsg.setType(MessageBase.Type.SEND_MSG_RESP);
				String phoneNumber = msgBase.getSendMsgReq().getPhoneNumber();
				if(StringUtils.isNotBlank(phoneNumber)){
					//随机生成四位验证码的工具类
					String code = keyUtil.keyUtils();
					log.info("手机号：{}，发送验证码：{}", phoneNumber, code);
					redisCache.setCacheObject(Constants.MSG_CACHE_PREFIX + phoneNumber, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
	//				String result = new SMSUtil().sendSMS(phoneNumber,code);//发送短信验证码
					this.send(ctx,authMsg,200);
					return;
				}
				this.send(ctx,authMsg,500);*/

			}else if(msgBase.getType().equals(MessageBase.Type.ORDER_REQ)){
				log.info("===訂單請求===");
				MessageBase.Builder authMsg= MessageBase.newBuilder();
				authMsg.setClientId(ctx.channel().id().toString());
				authMsg.setType(MessageBase.Type.ORDER_RESP);

				MessageBase.OrderResp.Builder wardrobe=MessageBase.OrderResp.newBuilder();
				HashMap map = MyClass.createSign(MyClass.requestPrepayId());
				wardrobe.setPartnerId(map.get("partnerId").toString());
				wardrobe.setPrepayId(map.get("prepayId").toString());
				wardrobe.setNonceStr(map.get("nonceStr").toString());
				wardrobe.setTimeStamp(map.get("timeStamp").toString());
				wardrobe.setSign(map.get("sign").toString());
				authMsg.setOrderResp(wardrobe);
				this.send(ctx,authMsg,200);
				/*MessageBase.Builder authMsg= MessageBase.newBuilder();
				authMsg.setType(MessageBase.Type.SEND_MSG_RESP);
				String phoneNumber = msgBase.getSendMsgReq().getPhoneNumber();
				if(StringUtils.isNotBlank(phoneNumber)){
					//随机生成四位验证码的工具类
					String code = keyUtil.keyUtils();
					log.info("手机号：{}，发送验证码：{}", phoneNumber, code);
					redisCache.setCacheObject(Constants.MSG_CACHE_PREFIX + phoneNumber, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
	//				String result = new SMSUtil().sendSMS(phoneNumber,code);//发送短信验证码
					this.send(ctx,authMsg,200);
					return;
				}
				this.send(ctx,authMsg,500);*/

			}else if(msgBase.getType().equals(MessageBase.Type.MALL_REQ)){
				log.info("===商城信息===");
				MessageBase.Builder authMsg= MessageBase.newBuilder();//new msg的内容
				authMsg.setClientId(ctx.channel().id().toString());//拿到ClientId
				authMsg.setType(MessageBase.Type.MALL_LIST_RESP);

				//获取登录用户id
				int id = msgBase.getMallImgReq().getId();
				List<TbMall> tbMallList = tbMallService.selectTbMallList();//查询所有商品
				MessageBase.MallListResp.Builder builders = MessageBase.MallListResp.newBuilder();
				for (TbMall tbMall : tbMallList) {
					TbCollection tbCollection = tbMallService.selectTbCollection(id,tbMall.getId());
					MessageBase.MallResp.Builder builder = MessageBase.MallResp.newBuilder();
					builder.setId(tbMall.getId().intValue());
					builder.setTbStoreId(tbMall.getTbStoreId());
					builder.setName(tbMall.getName());
					builder.setImg(tbMall.getImg());
					builder.setPrice(tbMall.getPrice().toString());
					if (tbCollection==null){
						builder.setStatus(0);
					}else {
						builder.setStatus(tbCollection.getStatus());
					}
					builders.addMallResp(builder);
				}
				authMsg.setMallListResp(builders);
				this.send(ctx,authMsg,200);


			}else if (msgBase.getType().equals(MessageBase.Type.COLLECTION_REQ)){
				log.info("===收藏商品===");
				MessageBase.Builder authMsg= MessageBase.newBuilder();//new msg的内容
				authMsg.setClientId(ctx.channel().id().toString());//拿到ClientId


				TbCollection tbCollection = new TbCollection();
				Long mallId = (long) msgBase.getCollectionReq().getMallId();
				Long userId = (long) msgBase.getCollectionReq().getUserId();

				tbCollection.setMallId(mallId);
				tbCollection.setUserId(userId);
				tbCollection.setStatus(msgBase.getCollectionReq().getStatus());

				tbMallService.updataStatus(tbCollection);

				this.send(ctx,authMsg,200);

			}else if (msgBase.getType().equals(MessageBase.Type.COLLECTION_LIST_REQ)){
				log.info("===我的收藏===");
				MessageBase.Builder authMsg= MessageBase.newBuilder();//new msg的内容
				authMsg.setClientId(ctx.channel().id().toString());//拿到ClientId
				authMsg.setType(MessageBase.Type.COLLECTION_LIST_RESP);

				//获取用户id
				int userId = msgBase.getCollectionListReq().getUserId();
				//查询该用户所有的藏品
				List<TbMall> tbMallList = tbMallService.selectTbMallListStatus((long) userId);

				MessageBase.CollectionListResp.Builder builders = MessageBase.CollectionListResp.newBuilder();
				for (TbMall tbMall : tbMallList) {
					MessageBase.MallResp.Builder builder = MessageBase.MallResp.newBuilder();
					builder.setId(tbMall.getId().intValue());
					builder.setTbStoreId(tbMall.getTbStoreId());
					builder.setName(tbMall.getName());
					builder.setImg(tbMall.getImg());
					builder.setPrice(tbMall.getPrice().toString());
					builder.setMallStatus(tbMall.getStatus());
					builders.addMallResp(builder);
				}
				authMsg.setCollectionListResp(builders);
				this.send(ctx,authMsg,200);


				MessageBase.CommentResp.Builder builder = MessageBase.CommentResp.newBuilder();

				int count = iTbCommentService.insertTbComment(tbComment);
				if(count>0){
					builder.setStatus(count);
					authMsg.setCommentResp(builder);
					this.send(ctx,authMsg,200);
					return;
				}
				this.send(ctx,authMsg,304);
			}
			/*else if (msgBase.getType().equals(MessageBase.Type.WX_REQ)){
				log.info("微信支付");
				MessageBase.Builder authMsg = MessageBase.newBuilder();
				authMsg.setClientId(ctx.channel().id().toString());
				authMsg.setType(MessageBase.Type.ORDER_RESP);






			}*/else{
				log.info("===============没有该协议类型=============");
				MessageBase.Builder authMsg= MessageBase.newBuilder();
				authMsg.setClientId(ctx.channel().id().toString());
				this.send(ctx,authMsg,200);
			}
			ReferenceCountUtil.release(msg);
		}catch (Exception e){
			e.printStackTrace();

		}
	}


	public void send(ChannelHandlerContext ctx,MessageBase.Builder builder,int code) {
		builder.setClientId(ctx.channel().id().toString());
		builder.setCode(code);
		ctx.writeAndFlush(builder.build().toByteArray());
	}


	//byte数组到图片
	public void byte2image(byte[] data,String path){
		if(data.length<3||path.equals("")) return;
		try{
			FileImageOutputStream imageOutput = new FileImageOutputStream(new File(path));
			imageOutput.write(data, 0, data.length);
			imageOutput.close();
			System.out.println("Make Picture success,Please find image in " + path);
		} catch(Exception ex) {
			System.out.println("Exception: " + ex);
			ex.printStackTrace();
		}
	}
	//byte数组到16进制字符串
	public String byte2string(byte[] data){
		if(data==null||data.length<=1) return "0x";
		if(data.length>200000) return "0x";
		StringBuffer sb = new StringBuffer();
		int buf[] = new int[data.length];
		//byte数组转化成十进制
		for(int k=0;k<data.length;k++){
			buf[k] = data[k]<0?(data[k]+256):(data[k]);
		}
		//十进制转化成十六进制
		for(int k=0;k<buf.length;k++){
			if(buf[k]<16) sb.append("0"+Integer.toHexString(buf[k]));
			else sb.append(Integer.toHexString(buf[k]));
		}
		return "0x"+sb.toString().toUpperCase();
	}


	public static boolean GenerateImage(String imgStr, String imgFilePath) {
		//对字节数组字符串进行Base64解码并生成图片
		//图像数据为空
		if (imgStr == null)
			return false;
		BASE64Decoder decoder = new BASE64Decoder();
		try {
			//Base64解码
			byte[] b = decoder.decodeBuffer(imgStr);
			for (int i = 0; i < b.length; ++i) {
				if (b[i] < 0) {
					//调整异常数据
					b[i] += 256;
				}
			}
			//生成jpeg图片
//			String imagePath = ResourceUtils.getURL("classpath:").getPath();
			//System.currentTimeMillis()
			//新生成的图片
			OutputStream out = new FileOutputStream(imgFilePath);
			out.write(b);
			out.flush();
			out.close();
			return true;
		} catch (Exception e) {
			return false;
		}
	}

}
