//package tbs.newgenteacherselect.config;
//
//import cn.hutool.extra.spring.SpringUtil;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import tbs.framework.async.AsyncTaskResult;
//import tbs.framework.async.ThreadUtil;
//import tbs.framework.interfaces.async.AsyncToDo;
//import tbs.framework.model.BaseRoleModel;
//import tbs.newgenteacherselect.model.RoleVO;
//
//
//import java.util.Arrays;
//import java.util.Enumeration;
//import java.util.List;
//
//@Configuration
//public class SocketServiceConfig {
//
//    public static final String ADMIN_LOG_SERVICE = "adminSocketService";
//
//    @Bean(ADMIN_LOG_SERVICE)
//    public ISocketWorker admin() {
//        return new BasicSocketWorker(ADMIN_LOG_SERVICE) {
//            @Override
//            public void customAccept(ISocketClient client, BaseRoleModel roleModel) throws Exception {
//                if (roleModel.getRoleCode() != RoleVO.ROLE_ADMIN)
//                    throw new Exception("此服务仅供管理员用户使用");
//            }
//
//            @Override
//            public void sendMessage(Object data) {
//                ThreadUtil threadUtil = SpringUtil.getBean(ThreadUtil.class);
//                threadUtil.doWithAsync(new AsyncToDo() {
//                    @Override
//                    public void doSomething(AsyncTaskResult result) throws Exception {
//                        Enumeration<String> em = getSocketManager().all();
//                        while (em.hasMoreElements()) {
//                            getSocketManager().sendMessage(data, em.nextElement(), serviceName());
//                        }
//                    }
//                }).execute();
//            }
//
//            @Override
//            public List<ISocketManager.MessageEvent> messageEvents() {
//                return Arrays.asList(new ISocketManager.MessageEvent() {
//                    @Override
//                    public void consume(SocketReceiveMessage receiveMessage) {
//                        getSocketManager().sendMessage(String.format("you said %s ,thanks for your test", receiveMessage.getData().toString()), receiveMessage.getKey(), receiveMessage.getService());
//                    }
//                });
//            }
//        };
//    }
//}
