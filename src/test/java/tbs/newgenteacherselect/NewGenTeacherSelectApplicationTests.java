package tbs.newgenteacherselect;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import tbs.dao.*;
import tbs.newgenteacherselect.model.ScoreTemplateVO;
import tbs.newgenteacherselect.service.ScoreConfigService;
import tbs.newgenteacherselect.service.StudentService;
import tbs.newgenteacherselect.service.TeacherService;
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

    @Test
    void contextLoads() throws InterruptedException, NetError {

        ScoreTemplateVO templateVO = new ScoreTemplateVO();
        templateVO.setTemplateName("标准模板");
        templateVO.setDepartment(10);
        String[] text = {"good", "ok", "bad"};
        int[] pec = {20, 30, 50};
        List<ScoreTemplateVO.Item> ls=new LinkedList<>();
        for (int i = 0; i < text.length; i++) {
            ScoreTemplateVO.Item item = new ScoreTemplateVO.Item();
            item.setPercent(pec[i]);
            item.setName(text[i]);
            item.setIndex(i);
            ls.add(item);
        }
        templateVO.setItems(ls);
        BaseRoleModel roleModel=new BaseRoleModel();
        roleModel.setUserId("15606810923");
        service.makeTemplate(templateVO,roleModel);
    }

}
