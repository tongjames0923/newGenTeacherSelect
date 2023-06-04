package tbs.newgenteacherselect.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tbs.newgenteacherselect.service.MasterRelationService;
import tbs.utils.AOP.authorize.annotations.AccessRequire;
import tbs.utils.error.NetError;

import javax.annotation.Resource;

@RequestMapping("coreService")
@RestController
public class MasterRelationController {


    @Resource
    MasterRelationService relationService;
    @RequestMapping("select")
    @AccessRequire
    public Object select(String student,String master) throws NetError {
        return relationService.selectMaster(student,master);
    }

    @RequestMapping("listMasterRelationStatus")
    public Object listRelationStatus(int configItem,String master)
    {
        return relationService.listStatus(configItem,master);
    }

}
