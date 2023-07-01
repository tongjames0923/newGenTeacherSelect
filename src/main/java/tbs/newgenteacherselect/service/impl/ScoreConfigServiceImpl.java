package tbs.newgenteacherselect.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import tbs.framework.error.NetError;
import tbs.framework.model.BaseRoleModel;
import tbs.framework.sql.BatchUtil;
import tbs.framework.utils.EncryptionTool;
import tbs.newgenteacherselect.NetErrorEnum;
import tbs.newgenteacherselect.dao.*;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.newgenteacherselect.model.ScoreTemplateVO;
import tbs.newgenteacherselect.model.ScoreTemplateVO2;
import tbs.newgenteacherselect.service.DepartmentService;
import tbs.newgenteacherselect.service.ScoreConfigService;
import tbs.pojo.*;

import javax.annotation.Resource;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

@Slf4j
@Service
public class ScoreConfigServiceImpl implements ScoreConfigService {


    @Resource
    ScoreConfigDao scoreConfigDao;

    @Resource
    DepartmentDao departmentDao;
    @Resource
    StudentDao studentDao;

    @Resource
    DepartmentService departmentService;

    @Resource
    BasicUserDao basicUserDao;

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
    public boolean hasRights(BaseRoleModel model, String template) {
        if(model.getRoleCode()== RoleVO.ROLE_ADMIN)
            return true;
        ScoreConfigTemplate template1= scoreConfigDao.findById(template);
        if(template1==null)
            return false;
        return template1.getCreateUser().equals(model.getUserId());
    }

    @Resource
    ScoreConfigItemDao scoreConfigItemDao;
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
        scoreConfigDao.insert(template);
        for (ScoreTemplateVO.Item i : vo.getItems()) {
            ScoreConfigTemplateItem item = new ScoreConfigTemplateItem();
            item.setTemplateId(template.getId());
            item.setPercent(i.getPercent());
            item.setSortCode(i.getIndex());
            item.setConfigName(i.getName());
            scoreConfigItemDao.insert(item);
        }
    }

    @Override
    @Transactional
    public void removeTemplate(String template) throws NetError {
        int t= scoreConfigDao.deleteTemplate(template);
        if(t==0)
            throw NetErrorEnum.makeError(NetErrorEnum.NOT_FOUND,"不存在的模板ID");
        scoreConfigDao.deleteTemplateItems(template);
    }



    @Override
    public List<ScoreTemplateVO2> listTemplate(int dep) throws Exception {
        List<ScoreTemplateVO2> list = new LinkedList<>();
        for (ScoreConfigTemplate template : scoreConfigDao.listTemplateByDepartment(dep)) {
            ScoreTemplateVO2 vo = new ScoreTemplateVO2();
            vo.setItems(new LinkedList<>());
            vo.setDepartment(departmentService.departmentFullNamesMap(dep));
            vo.setTemplateName(template.getTemplateName());
            vo.setCreateDate(template.getCreateTime());
            vo.setCreatorPhone(template.getCreateUser());
            BasicUser basicUser= basicUserDao.findOneByPhone(template.getCreateUser());
            vo.setCreatorName(basicUser!=null?basicUser.getName():"");
            vo.setTemplateId(template.getId());
            for (ScoreConfigTemplateItem item : scoreConfigDao.listTemplateItems(template.getId())) {
                vo.getItems().add(item);
            }
            list.add(vo);
        }
        return list;
    }


    @Resource
    BatchUtil batchUtil;

    @Resource
    TeacherDao teacherDao;

    @Override
    @Transactional
    public void applyConfig(int department, String template) throws Exception {
        int inserted=0;
        List<Student> students = studentDao.listDepartmentNoMasterStudentOrderBySocre(department);
        List<Teacher> teachers=teacherDao.listTeacherByDepartment(department);
        int total=students.size();
        ScoreConfigTemplate template1= scoreConfigDao.findById(template);
        if(template1==null||template1.getDepartmentId()!=department)
            throw NetErrorEnum.makeError(NetErrorEnum.NOT_FOUND,"不存在模板id");
        List<ScoreConfigTemplateItem> templateItems=scoreConfigDao.listTemplateItems(template);
        List<Integer> limits=new LinkedList<>();
        for(ScoreConfigTemplateItem item:templateItems)
        {
            double percents= ((double)item.getPercent().intValue())/100.00;
            int num= (int) (percents*total+1);
            limits.add(num);
        }
        int tindex=0,ttotal=teachers.size();
        int tempIndex=0,sindex=0;
        for(Integer cnt:limits)
        {
            ScoreConfigTemplateItem item=templateItems.get(tempIndex);
            while (cnt>0)
            {

                int finalTindex = tindex;
                Student student=sindex<total? students.get(sindex):null;
                batchUtil.batchUpdate(25, new BatchUtil.SqlUpdateExecute() {
                    @Override
                    public void execute() {
                        Teacher teacher=teachers.get(finalTindex);
                        MasterRelation relation=new MasterRelation(teacher.getPhone(),item.getId());
                        batchUtil.getMapper(MasterRelationDao.class).insert(relation);
                        if(student!=null)
                        {
                            StudentLevel level=new StudentLevel(student.getPhone(),item.getId());
                            batchUtil.getMapper(StudentLevelDao.class).insert(level);
                        }
                    }
                });
                cnt--;
                tindex=(tindex+1)%ttotal;
                sindex++;
            }
            batchUtil.flush();
            tempIndex++;
        }
    }
}
