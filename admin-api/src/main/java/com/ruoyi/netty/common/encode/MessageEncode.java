package com.ruoyi.netty.common.encode;

import com.ruoyi.netty.common.util.BitConverter;
import com.ruoyi.netty.common.util.ReflectionUtil;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * Created by haoxy on 2018/10/17.
 * E-mail:hxyHelloWorld@163.com
 * github:https://github.com/haoxiaoyong1014
 * 客户端编码器
 */
public class MessageEncode extends MessageToByteEncoder<byte[]> {

    private static final Logger log = LoggerFactory.getLogger(MessageEncode.class);

    @Override
    protected void encode(ChannelHandlerContext channelHandlerContext, byte[] bytes, ByteBuf byteBuf) throws Exception {

//        final BASE64Encoder encoder = new BASE64Encoder();
//        final String encodedText = encoder.encode(bytes);

//        ByteBuf byteBuf2 = Unpooled.buffer(bytes.length);
//        byteBuf2.writeBytes(bytes);
//        // 写消息头
//        byteBuf.writeBytes(encodedText.getBytes(("UTF-8")));
//        byteBuf.discardReadBytes();
//        byteBuf.writeInt(1);
//        byteBuf.writeBytes(bytes);

//        byteBuf.writeShort(packet.getCmd());

//        byteBuf.writeShort(104);//协议编号
//        byteBuf.writeByte(9) ;//协议类型


        byte totalBytes[] = this.append(bytes);

        System.out.println("MessageEncode===="+ Arrays.toString(totalBytes));
        System.out.println("MessageEncode长度："+ totalBytes.length);
        byteBuf.writeBytes(totalBytes) ;//内容
    }

    public byte[] append(byte[] bytes) {
//        byte[] packageLen= BitConverter.to2Byte(bytes.length + 2);
        byte[] packageLen= BitConverter.toLH(bytes.length + 2);

        byte[] protocol = new byte[]{105, 0};//协议标号

//        byte[] dataLen= BitConverter.to2Byte(bytes.length);

//        byte[] type= new byte[]{0, 0};

        //{包体长度2位，协议编号2位，数据长度2位，未知2位，数据bytes}
        byte totalBytes[] = ReflectionUtil.addBytes(packageLen,
                ReflectionUtil.addBytes(protocol,bytes
            )
        );

        return totalBytes;
    }
}
