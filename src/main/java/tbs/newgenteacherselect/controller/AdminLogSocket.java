package tbs.newgenteacherselect.controller;

import cn.hutool.extra.spring.SpringUtil;
import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tbs.newgenteacherselect.NetErrorEnum;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.utils.AOP.authorize.interfaces.IAccess;
import tbs.utils.AOP.authorize.model.BaseRoleModel;
import tbs.utils.Async.ThreadUtil;
import tbs.utils.Async.interfaces.AsyncToDo;
import tbs.utils.Results.AsyncTaskResult;
import tbs.utils.error.NetError;
import tbs.utils.socket.ISocketManager;
import tbs.utils.socket.model.SocketReceiveMessage;

import javax.annotation.Resource;
import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.util.LinkedHashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

@Slf4j
@Component
@ServerEndpoint("/adminlog/{key}")
public class AdminLogSocket {
    static IAccess access = null;

    public static final String SERVICE_NAME = "test";
    static ISocketManager socketManager = null;

    static boolean isInit = false;
    private static Set<String> keys = new LinkedHashSet<>();


    private static void init() {
        if (!isInit) {
            access = SpringUtil.getBean(IAccess.class);
            socketManager = SpringUtil.getBean(ISocketManager.class);
            threadUtil = SpringUtil.getBean(ThreadUtil.class);
            socketManager.ApplyonMessageEvent(SERVICE_NAME, new ISocketManager.MessageEvent() {
                @Override
                public void consume(SocketReceiveMessage receiveMessage) {
                    socketManager.sendMessage(String.format("you said %s ,thanks for your test", receiveMessage.getData().toString()), receiveMessage.getKey(), receiveMessage.getService());
                }
            });
            keys.clear();
            isInit = true;
        }
    }

    private static ThreadUtil threadUtil = null;

    public static void send(Object data) {
        init();
        threadUtil.doWithAsync(new AsyncToDo() {
            @Override
            public void doSomething(AsyncTaskResult result) throws Exception {
                for (String k : keys) {
                    socketManager.sendMessage(data, k, SERVICE_NAME);
                }
            }
        }).execute();

    }

    @OnOpen
    public void onOpen(Session session, @PathParam("key") String key) {
        init();
        try {
            BaseRoleModel baseRoleModel = access.readRole(key);
            if (baseRoleModel == null) {
                throw NetErrorEnum.makeError(NetErrorEnum.NOT_FOUND, "未登录的用户");
            }
            if (baseRoleModel.getRoleCode() != RoleVO.ROLE_ADMIN) {
                throw NetErrorEnum.makeError(NetErrorEnum.BAD_ROLE);
            }
            if (socketManager.getSockets(key) != null)
                throw NetErrorEnum.makeError(NetErrorEnum.Repeated_Login);
            socketManager.putSocket(key, session);
            keys.add(key);
        } catch (Exception e) {
            try {
                session.close();
            } catch (Exception ex) {
                log.error(ex.getMessage(), ex);
            }
            log.error(e.getMessage(), e);
        }
    }

    @OnClose
    public void onClose(@PathParam("key") String key) {
        keys.remove(key);
        socketManager.remove(key);
    }

    @OnMessage
    public void onMessage(String message) {
        try {
            SocketReceiveMessage receiveMessage = JSON.parseObject(message, SocketReceiveMessage.class);
            if (SERVICE_NAME.equals(receiveMessage.getService()))
                for (ISocketManager.MessageEvent event : socketManager.getEvents(receiveMessage.getService())) {
                    event.consume(receiveMessage);
                }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
}
