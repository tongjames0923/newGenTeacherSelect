package tbs.newgenteacherselect.service;

import tbs.pojo.Student;
import tbs.pojo.dto.StudentUserDetail;

import java.util.List;

public interface MasterRelationService {
    /**
     * 计算定时刷新
     */
    void calLeftForMaster();

    /**
     * 选择导师
     * @param student 学生手机号
     * @param master 导师手机号
     * @return 关系id
     */
    int selectMaster(String student,String master);

    /**
     * 退选导师
     * @param student 学生手机号
     * @param master 导师手机号
     * @return 关系id
     */
    int returnbackMaster(String student,String master);

    /**
     * 获取导师某配置项下的所有学生列表
     * @param master 导师手机号
     * @param config 配置项id
     * @return
     */
    List<StudentUserDetail> listStudentByMasterAndConfig(String master,int config);
}
