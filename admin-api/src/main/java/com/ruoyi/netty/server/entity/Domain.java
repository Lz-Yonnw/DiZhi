package com.ruoyi.netty.server.entity;

import io.netty.channel.ChannelHandlerContext;

import java.io.Serializable;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class Domain implements Serializable {
    //当前下单用户
    public static Map<String, ChannelHandlerContext> ctxMap = new ConcurrentHashMap<String, ChannelHandlerContext>();

    //退单用户
    public static  Map<String,ChannelHandlerContext> refundMap = new ConcurrentHashMap<String,ChannelHandlerContext>();
}
