package com.ruoyi.netty.client.handler;



import io.netty.channel.ChannelHandlerContext;
import com.ruoyi.netty.common.protobuf.MessageProtocol.MessageBase;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.BASE64Encoder;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Objects;

public class LogicClientHandler extends SimpleChannelInboundHandler<Object> {

	private static final Logger log = LoggerFactory.getLogger(LogicClientHandler.class);

	private final static String CLIENTID = "123456789";

	public  static  int i=1;

	// 连接成功后，向server发送消息  
	@Override  
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		//发送验证码
//		MessageBase.Builder authMsg = MessageBase.newBuilder();
//		authMsg.setType(MessageBase.Type.SEND_MSG_REQ);
//		authMsg.setSendMsgReq(MessageBase.SendMsgReq.newBuilder().setPhoneNumber("18819322541"));
		//登录/注册
//		MessageBase.Builder authMsg = MessageBase.newBuilder();
//		authMsg.setType(MessageBase.Type.LOGIN_REQ);
//		authMsg.setLoginReq(MessageBase.LoginReq.newBuilder().setPhoneNumber("18819322541").setCode("5820"));
//		authMsg.setLoginReq(MessageBase.LoginReq.newBuilder().setPhoneNumber("18819322541").setToken("835e61a8be1749b38eb33a27e0b3e4e5"));


		MessageBase.Builder authMsg = MessageBase.newBuilder();
		authMsg.setType(MessageBase.Type.WARDROBE_SAVE_REQ);
//		byte[] bytes =LogicClientHandler.getFileByteArray(new File("C:\\Users\\Administrator\\Pictures\\profile.jpg"));
//		authMsg.setUserWardrobeReq(MessageBase.UserWardrobeReq.newBuilder().setImage(ByteString.copyFrom(bytes)));

		String base64 =LogicClientHandler.ImageToBase64("C:\\Users\\Administrator\\Pictures\\profile.jpg");
		authMsg.setUserWardrobeReq(MessageBase.UserWardrobeReq.newBuilder().setImage(base64));


//		MessageBase.Builder authMsg = MessageBase.newBuilder();
//		authMsg.setClientId(ctx.channel().id().toString());
//		byte[] imageFromNetByUrl = getImageFromNetByUrl("https://app-1255978856.cos.ap-shanghai.myqcloud.com/upload/2021/10/21/588cf9fd4dc449128179a2ed1c1ebf76.png");

//		authMsg.setType(MessageBase.Type.LOGIN_REQ);
//		authMsg.setLoginOrRegisterUpdateUserInfoReq(MessageBase.LoginOrRegisterUpdateUserInfoReq.newBuilder().setPassword("8888").setPhoneNumber("8888"));
//		authMsg.setType(MessageBase.Type.LOGIN_REQ);
//		authMsg.setLoginOrRegisterUpdateUserInfoReq(MessageBase.LoginOrRegisterUpdateUserInfoReq.newBuilder().setPhoneNumber("15172864264").setCode("1111"));
//		authMsg.setType(MessageBase.Type.UPDATE_USER_REQ);
//		authMsg.setUpdateUserReq(MessageBase.UpdateUserReq.newBuilder().setId(1).setNickname("zzz"));

		byte dataBytes[] = authMsg.build().toByteArray();
		ctx.writeAndFlush(dataBytes);
	}


	public static byte[] getImageFromNetByUrl(String strUrl){
		try {
			URL url = new URL(strUrl);
			HttpURLConnection conn = (HttpURLConnection)url.openConnection();
			conn.setRequestMethod("GET");
			conn.setConnectTimeout(5 * 1000);
			InputStream inStream = conn.getInputStream();//通过输入流获取图片数据
			byte[] btImg = readInputStream(inStream);//得到图片的二进制数据
			return btImg;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public static byte[] readInputStream(InputStream inStream) throws Exception{
		ByteArrayOutputStream outStream = new ByteArrayOutputStream();
		byte[] buffer = new byte[1024];
		int len = 0;
		while( (len=inStream.read(buffer)) != -1 ){
			outStream.write(buffer, 0, len);
		}
		inStream.close();
		return outStream.toByteArray();
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		log.debug("连接断开 ");
	}


	@Override
	protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
		byte[] bytes = (byte[]) msg;

        MessageBase msgBase = MessageBase.parseFrom(bytes);
		if(msgBase.getType().equals(MessageBase.Type.LOGIN_RESP)){
			log.info("登录返回==={}",msgBase.getLoginResp().toString());
		}else if(msgBase.getType().equals(MessageBase.Type.UPDATE_USER_RESP)){
			log.info("更新用户信息返回==={}",msgBase.getUpdateUserResp().toString());
		}
	}


	public static byte[] getFileByteArray(File file) {
		long fileSize = file.length();
		if (fileSize > Integer.MAX_VALUE) {
			System.out.println("file too big...");
			return null;
		}
		byte[] buffer = null;
		try (FileInputStream fi = new FileInputStream(file)) {
			buffer = new byte[(int) fileSize];
			int offset = 0;
			int numRead = 0;
			while (offset < buffer.length
					&& (numRead = fi.read(buffer, offset, buffer.length - offset)) >= 0) {
				offset += numRead;
			}
			// 确保所有数据均被读取
			if (offset != buffer.length) {
				throw new IOException("Could not completely read file " + file.getName());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return buffer;
	}


	/**
	 * 本地图片转换Base64的方法
	 *
	 * @param imgPath
	 */

	private static String ImageToBase64(String imgPath) {
		byte[] data = null;
		// 读取图片字节数组
		try {
			InputStream in = new FileInputStream(imgPath);
			data = new byte[in.available()];
			in.read(data);
			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		// 对字节数组Base64编码
		BASE64Encoder encoder = new BASE64Encoder();
		// 返回Base64编码过的字节数组字符串
		return encoder.encode(Objects.requireNonNull(data));
	}

}
