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
import tbs.utils.Async.interfaces.ILockProxy;
import tbs.utils.Async.interfaces.ILocker;
import tbs.utils.BatchUtil;
import tbs.utils.error.NetError;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class NewGenTeacherSelectApplicationTests {


    @Resource
    ScoreConfigService service;

    @Resource
    MasterRelationDao dao;

    @Test
    void contextLoads() throws InterruptedException, NetError {

        MasterRelation relation=new MasterRelation("10013",11);
        dao.insert(relation);
    }

}
