package com.ruoyi.web.controller.monitor;

import com.ruoyi.common.core.controller.BaseController;
import com.ruoyi.common.core.domain.AjaxResult;
import com.ruoyi.framework.web.domain.Server;
import com.ruoyi.netty.server.handler.AuthServerHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 服务器监控
 *
 * @author liaozan8888@163.com
 */
@RestController
@RequestMapping("/monitor/server")
public class ServerController extends BaseController {

    @Autowired
    private AuthServerHandler authServerHandler;

    @PreAuthorize("@ss.hasPermi('monitor:server:list')")
    @GetMapping()
    public AjaxResult getInfo() throws Exception {
        Server server = new Server();
        server.copyTo();
        return AjaxResult.success(server);
    }

    @PostMapping(value = "/voltmeterEquipmentControl")
    public Object voltmeterController(String userId, String msg) throws Exception {
//        ChannelHandlerContext myctx = AuthServerHandler.ctxMap.get(userId);
//        MessageProtocol.MessageBase.Builder authMsg= MessageProtocol.MessageBase.newBuilder();
//        //响应微信支付成功通知
//        MessageProtocol.MessageBase.WxpaySuccessResp.Builder builder = MessageProtocol.MessageBase.WxpaySuccessResp.newBuilder();
//        authMsg.setWxpaySuccessResp(builder);
//        //推送消息给客户端
//        authServerHandler.send(myctx,authMsg,200);
        return null;
    }
}
