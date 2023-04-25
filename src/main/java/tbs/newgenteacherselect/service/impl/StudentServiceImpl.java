package tbs.newgenteacherselect.service.impl;

import org.springframework.stereotype.Service;
import tbs.Utils.EncryptionTool;
import tbs.newgenteacherselect.model.StudentVO;
import tbs.newgenteacherselect.service.StudentService;
import tbs.pojo.BasicUser;
import tbs.pojo.StudentDetail;
import tbs.repo.StudentDao;

import javax.annotation.Resource;
import java.util.*;

@Service
public class StudentServiceImpl implements StudentService {

    @Resource
    StudentDao studentDao;

    @Override
    public List<StudentDetail> batchRead(List<StudentVO> students) {
        List<StudentDetail> studentDetails= new LinkedList<StudentDetail>();
        for(StudentVO v:students)
        {
            StudentDetail detail=new StudentDetail();
            BasicUser basicUser=new BasicUser();
            basicUser.setUid(EncryptionTool.encrypt(v.getPhone()+"TIME_!@#$%12390",50));
            basicUser.setPhone(v.getPhone());
            basicUser.setName(v.getName());
            basicUser.setPassword(EncryptionTool.encrypt(v.getPassword(),50));
            detail.setBasic(basicUser);
            detail.setStudentNo(v.getNumber());
            detail.setGrade(v.getGrade());
            studentDetails.add(detail);
        }

        Iterator<StudentDetail> rs= studentDao.batchSave(studentDetails,studentDetails.size()).iterator();
        studentDetails.clear();
        while (rs.hasNext())
        {
            studentDetails.add(rs.next());
        }
        return studentDetails;
    }
}
