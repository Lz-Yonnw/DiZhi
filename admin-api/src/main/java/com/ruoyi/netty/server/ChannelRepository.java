package com.ruoyi.netty.server;

import io.netty.channel.Channel;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Channel Manager
 * @author Ke Shanqiang
 *
 */

@Component
public class ChannelRepository {
	private final static Map<String, Channel> channelCache = new ConcurrentHashMap<String, Channel>();

	public void put(String key, Channel value) {
		channelCache.put(key, value);
	}

	public Channel get(String key) {
		return channelCache.get(key);
	}

	public void remove(String key) { 
		channelCache.remove(key);
	}

	public int size() {
		return channelCache.size();
	}
}
