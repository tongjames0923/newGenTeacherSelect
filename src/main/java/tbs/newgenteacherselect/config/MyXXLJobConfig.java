package tbs.newgenteacherselect.config;

import org.springframework.stereotype.Component;
import tbs.framework.xxl.interfaces.IXXLJobsConfig;


//@Component
public class MyXXLJobConfig implements IXXLJobsConfig {
    @Override
    public String adminAddress() {
        return "http://127.0.0.1:8888/xxl-job-admin";
    }

    @Override
    public String appName() {
        return "teacherSelectTasks";
    }

    @Override
    public String address() {
        return null;
    }

    @Override
    public String ip() {
        return null;
    }

    @Override
    public int port() {
        return 8088;
    }

    @Override
    public String accessToken() {
        return "default_token";
    }

    @Override
    public String logPath() {
        return null;
    }

    @Override
    public int logRetentionsDays() {
        return 30;
    }
}
