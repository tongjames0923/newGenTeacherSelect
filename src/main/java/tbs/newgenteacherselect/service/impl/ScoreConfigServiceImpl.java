package tbs.newgenteacherselect.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tbs.dao.DepartmentDao;
import tbs.dao.ScoreConfigDao;
import tbs.newgenteacherselect.NetErrorEnum;
import tbs.newgenteacherselect.model.ScoreTemplateVO;
import tbs.newgenteacherselect.service.ScoreConfigService;
import tbs.pojo.Department;
import tbs.pojo.ScoreConfigTemplate;
import tbs.pojo.ScoreConfigTemplateItem;
import tbs.utils.AOP.authorize.model.BaseRoleModel;
import tbs.utils.EncryptionTool;
import tbs.utils.error.NetError;

import javax.annotation.Resource;
import java.util.Date;

@Slf4j
@Service
public class ScoreConfigServiceImpl implements ScoreConfigService {


    @Resource
    ScoreConfigDao scoreConfigDao;

    @Resource
    DepartmentDao departmentDao;

    void checkInputVO(ScoreTemplateVO v) throws NetError {
        if (v == null)
            throw NetErrorEnum.makeError(NetErrorEnum.NOT_ALLOW, "空对象");
        if (CollectionUtils.isEmpty(v.getItems())) {
            throw NetErrorEnum.makeError(NetErrorEnum.NOT_ALLOW, "空模板单项");
        }
        int total = 0;
        for (ScoreTemplateVO.Item i : v.getItems()) {
            total += i.getPercent();
        }
        if (total != 100)
            throw NetErrorEnum.makeError(NetErrorEnum.NOT_ALLOW, "分数配置子项百分比之和必须为100");
        Department department = departmentDao.getById(v.getDepartment());
        if (department == null) {
            throw NetErrorEnum.makeError(NetErrorEnum.NOT_ALLOW, "不存在部门Id");
        }
        ScoreConfigTemplate template = scoreConfigDao.findOneByDepartmentAndName(v.getDepartment(), v.getTemplateName());
        if (template != null)
            throw NetErrorEnum.makeError(NetErrorEnum.NOT_ALLOW, "已存在相同模板名的模板");

    }

    @Override
    @Transactional
    public void makeTemplate(ScoreTemplateVO vo, BaseRoleModel invoker) throws NetError {
        checkInputVO(vo);
        ScoreConfigTemplate template = new ScoreConfigTemplate();
        template.setTemplateName(vo.getTemplateName());
        template.setCreateTime(new Date());
        template.setCreateUser(invoker.getUserId());
        template.setDepartmentId(vo.getDepartment());
        template.setId(EncryptionTool.encrypt(String.format("%s-%d-%d", vo.getTemplateName(), vo.getDepartment(), System.currentTimeMillis())));
        scoreConfigDao.insertTemplate(template);
        for (ScoreTemplateVO.Item i : vo.getItems()) {
            ScoreConfigTemplateItem item = new ScoreConfigTemplateItem();
            item.setTemplateId(template.getId());
            item.setPercent(i.getPercent());
            item.setSortCode(i.getIndex());
            item.setConfigName(i.getName());
            scoreConfigDao.insertTemplateItem(item);
        }
    }
}
