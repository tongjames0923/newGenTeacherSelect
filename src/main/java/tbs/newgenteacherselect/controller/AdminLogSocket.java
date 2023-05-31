package tbs.newgenteacherselect.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import tbs.newgenteacherselect.config.SocketServiceConfig;

import javax.websocket.server.ServerEndpoint;

@Component
@ServerEndpoint("/adminlog/{key}")
public class AdminLogSocket extends BaseSessionSocketService {
    @Override
    public String serviceName() {
        return SocketServiceConfig.ADMIN_LOG_SERVICE;
    }
}
