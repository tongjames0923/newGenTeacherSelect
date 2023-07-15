package tbs.newgenteacherselect.config.impl;

import org.springframework.stereotype.Component;
import tbs.framework.interfaces.IJsonJobHandler;
import tbs.newgenteacherselect.service.MasterRelationService;

import javax.annotation.Resource;
import java.util.Map;

@Component("updateMasterSelect")
public class UpdateMasterSelectTask implements IJsonJobHandler {
    @Resource
    MasterRelationService masterRelationService;


    @Override
    public String handle(Map params) throws Exception {
        masterRelationService.calLeftForMaster();
        return "success";
    }
}
