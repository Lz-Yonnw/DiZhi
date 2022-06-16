package com.ruoyi.config;


import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

/**
 * WebSocketServer
 */

//@ServerEndpoint("/chat/{userId}")
//@Component
public class WebSocketServer {

    private static final Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    //用来记录连接数量,静态变量
    private static int onlineCount = 0;
    //concurrent包的线程安全Set,用来存放每个客户端对应的MyWebSocket对象
    private static ConcurrentHashMap<String, WebSocketServer> websocketMap = new ConcurrentHashMap<>();
    //与某个客户端的连接会话，需要通过它来给客户端发送数据
    private static Map<String, Session> map = new HashMap<String, Session>();
    private Session session;

    //接收userId
    private String userId = "";

    //客服userId
    private String adminId = "";

    //获取当前在线的客服
    private String[] userList ={"admin","zhangsan"};

    /**
     * 建立连接
     * @param session
     * @param userId
     */
    @OnOpen
    public void onOpen(Session session,@PathParam("userId") String userId) throws IOException {

        this.session = session;
        this.userId = userId;

        websocketMap.put(userId, this);
        if (websocketMap.containsKey(userId)) {
            websocketMap.remove(userId);
            websocketMap.put(userId, this);
            //加入set中
        } else {
            map.put(session.getId(), session);
            addOnlineCount();//在线数量加1
        }

        if(StringUtils.isEmpty(adminId)){
            Random random = new Random();
            int r = random.nextInt(userList.length);    // random.nextInt(100)只能生成[0,100)
            //推送的客服
            adminId = userList[r];
        }

        //获取当前连接的所有用户
        JSONObject jsonObject = new JSONObject();
        HashMap<String, User> hashMap = new HashMap<>();
        for (String key : websocketMap.keySet()){
            User user = new User(key, "/upload/2021/11/05/e00adef7fbca40fbb52734fa6386004c.png");
            hashMap.put(key,user);
        }
        jsonObject.put("userList",JSON.toJSONString(hashMap));
        //当前客服
        jsonObject.put("adminId",adminId);
        jsonObject.put("status",200);
        //用户建立连接   推送给在线客服
        WebSocketServer admin = websocketMap.get(adminId);
        admin.sendMessage(jsonObject.toJSONString());
    }

    /**
     * 关闭连接
     */
    @OnClose
    public void onClose() throws IOException {
        if (websocketMap.containsKey(userId)) {
            websocketMap.remove(userId);
            //从set中删除
            subOnlineCount();
            //获取当前连接的所有用户
            JSONObject jsonObject = new JSONObject();
            HashMap<String, User> hashMap = new HashMap<>();
            for (String key : websocketMap.keySet()){
                User user = new User(key, "/upload/2021/11/05/e00adef7fbca40fbb52734fa6386004c.png");
                hashMap.put(key,user);
            }
            jsonObject.put("userList",JSON.toJSONString(hashMap));
            jsonObject.put("status",200);

            WebSocketServer webSocketServer = websocketMap.get(adminId);
            webSocketServer.sendMessage(jsonObject.toJSONString());

        }
        log.info("用户退出:" + userId + ",当前在线人数为:" + getOnlineCount());
    }

    /**
     * 当前用户集合
     * @return
     */
    public String getUserList(){
        //获取当前连接的所有用户  除获取自己
        JSONObject jsonObject = new JSONObject();
        HashMap<String, User> hashMap = new HashMap<>();
        for (String key : websocketMap.keySet()){
            if(!key.equals(userId)){
                //获取用户消息
                User user = new User(key, "/upload/2021/11/05/e00adef7fbca40fbb52734fa6386004c.png");
                hashMap.put(key,user);
            }

        }
        jsonObject.put("userList",JSON.toJSONString(hashMap));
        jsonObject.put("status",200);
        return jsonObject.toJSONString();
    }

    /**
     * 收到客户端消息后调用方法
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.info("用户消息:" + userId + ",报文:" + message);
        //可以群发消息
        //消息保存到数据库、redis
        if (StringUtils.isNotBlank(message)) {
            try {
                //解析发送的报文
                JSONObject jsonObject = JSON.parseObject(message);
                //追加发送人(防止串改)
                jsonObject.put("from", this.userId);
                String toform = jsonObject.getString("tofrom");
                //广播消息
                //sendAllMessage(message);

                //传送给对应的touserId用户的Websocket
                if (StringUtils.isNotBlank(toform) && websocketMap.containsKey(toform)) {
                    websocketMap.get(toform).sendMessage(jsonObject.toJSONString());
                    websocketMap.get(userId).sendMessage(jsonObject.toJSONString());
                } else {
                    log.error("请求的userId:" + toform + "不在该服务器上");
                    websocketMap.get(this.userId).sendMessage(toform+"当前用户占不在线！");
                    //否则不在这个服务器上，发送到mysql或者redis

                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 广播消息
     */
    public void sendAllMessage(String message){
        for (String key : websocketMap.keySet()) {
            log.info("【websocket消息】广播消息:" + message);
            try {
                WebSocketServer webSocketServer = websocketMap.get(key);
                webSocketServer.session.getAsyncRemote().sendText(message);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("用户错误:" + this.userId + ",原因:" + error.getMessage());
    }

    /**
     * 发送自定义消息
     */
    public static void sendInfo(String message, @PathParam("userId") String userId) throws IOException {
        log.info("发送消息到:" + userId + "，报文:" + message);
        if (StringUtils.isNotBlank(userId) && websocketMap.containsKey(userId)) {
            websocketMap.get(userId).sendMessage(message);
        } else {
            log.error("用户" + userId + ",不在线！");
        }
    }

    /**
     * 实现服务器主动推送
     */
    public void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }

    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        WebSocketServer.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        WebSocketServer.onlineCount--;
    }
}
