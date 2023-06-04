package tbs.newgenteacherselect.service.impl;

import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import tbs.newgenteacherselect.service.MasterRelationService;
import tbs.pojo.dto.StudentUserDetail;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Service
public class MasterRelationServiceImpl implements MasterRelationService {


    @Override
    @Scheduled(fixedRate = 30, timeUnit = TimeUnit.MINUTES)
    @Async
    public void calLeftForMaster() {

    }

    @Override
    public int selectMaster(String student, String master) {
        return 0;
    }

    @Override
    public int returnbackMaster(String student, String master) {
        return 0;
    }

    @Override
    public List<StudentUserDetail> listStudentByMasterAndConfig(String master, int config) {
        return null;
    }
}
