package com.ruoyi.netty.common.encode;

import com.ruoyi.netty.common.util.BitConverter;
import com.ruoyi.netty.common.util.ReflectionUtil;
import com.ruoyi.netty.server.handler.AuthServerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;

/**
 * Created by haoxy on 2018/10/17.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * 服务端解码器
 */
public class MessageDecoder extends ByteToMessageDecoder {

    private static final Logger log = LoggerFactory.getLogger(MessageDecoder.class);

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
        byte[] bytes = new byte[byteBuf.readableBytes()];
        byteBuf.readBytes(bytes);
        System.out.println("=====bytes:" + Arrays.toString(bytes));//字节数组打印

        int packageLen = BitConverter.toInt(bytes, 0);
//        short packageLen = BitConverter.toShort(bytes,0);
        short protocol =BitConverter.toShort(bytes,4);

        log.info("===接收到===包体长度："+packageLen+"，协议编号："+protocol/*+"，数据长度："+dataLen+"，协议类型："+type*/);

        byte[] dataBytes = ReflectionUtil.copyOfRange(bytes, 6, bytes.length);
        //粘包处理
        if(bytes.length - 4 < packageLen){
            log.info("=============包体不完整，重置等待下一次。包长：{},当前长度：{}", packageLen, bytes.length);
            byteBuf.resetReaderIndex();//恢复读索引
            return;
        }
        channelHandlerContext.fireChannelRead(dataBytes);
    }
}
