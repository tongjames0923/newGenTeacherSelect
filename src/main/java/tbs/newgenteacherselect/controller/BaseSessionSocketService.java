package tbs.newgenteacherselect.controller;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import tbs.utils.socket.ISocketClient;
import tbs.utils.socket.ISocketManager;
import tbs.utils.socket.ISocketWorker;
import tbs.utils.socket.impl.DefaultSocketClient;
import tbs.utils.socket.model.SocketReceiveMessage;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;

@Slf4j
public abstract class BaseSessionSocketService {
    public static ISocketWorker WORKER = null;


    public abstract String serviceName();

    private void init() {
        if (WORKER == null) {
            WORKER = SpringUtil.getBean(serviceName());
        }
    }

    public static void send(String s) {
        WORKER.sendMessage(s);
    }

    @OnOpen
    public void onOpen(Session session, @PathParam("key") String key) {
        init();
        try {
            ISocketClient client = new DefaultSocketClient(key, session);
            WORKER.accept(client);
        } catch (Exception e) {
            log.error("验证失败", e);
        }
    }

    @OnClose
    public void onClose(@PathParam("key") String key) {
        WORKER.onClose(new DefaultSocketClient(key, null));
    }

    @OnMessage
    public void onMessage(String message) {
        try {
            SocketReceiveMessage receiveMessage = JSON.parseObject(message, SocketReceiveMessage.class);
            if (!WORKER.messageEventFiltering(receiveMessage))
                for (ISocketManager.MessageEvent event : WORKER.messageEvents()) {
                    event.consume(receiveMessage);
                }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
