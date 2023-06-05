package tbs.newgenteacherselect;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tbs.dao.*;
import tbs.newgenteacherselect.model.ScoreTemplateVO;
import tbs.newgenteacherselect.service.ScoreConfigService;
import tbs.newgenteacherselect.service.StudentService;
import tbs.newgenteacherselect.service.TeacherService;
import tbs.pojo.MasterRelation;
import tbs.utils.AOP.authorize.model.BaseRoleModel;
import tbs.utils.Async.ThreadUtil;
import tbs.utils.Async.interfaces.AsyncToDo;
import tbs.utils.Async.interfaces.ILockProxy;
import tbs.utils.Async.interfaces.ILocker;
import tbs.utils.BatchUtil;
import tbs.utils.Results.AsyncTaskResult;
import tbs.utils.error.NetError;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

@SpringBootTest
class NewGenTeacherSelectApplicationTests {


    @Resource
    ScoreConfigService service;

    @Resource
    MasterRelationDao dao;


    @Resource
    ThreadUtil threadUtil;

    int a=0;


    @Resource
    ILockProxy lockProxy;
    @Test
    void contextLoads() throws InterruptedException, NetError {
        for(int i=0;i<10;i++)
        {
            threadUtil.doWithAsync(new AsyncToDo() {
                @Override
                public void doSomething(AsyncTaskResult result) throws Exception {
                    lockProxy.run(new Function<ILocker, Object>() {
                        @Override
                        public Object apply(ILocker iLocker) {
                            System.out.println(a++);
                            return null;
                        }
                    },"TEST_LOCK");
                }
            }).execute();
        }
        while (a<010);
        System.out.println("end");

    }

}
