package tbs.newgenteacherselect.service;

import tbs.newgenteacherselect.model.ScoreTemplateVO;
import tbs.utils.AOP.authorize.model.BaseRoleModel;
import tbs.utils.error.NetError;

public interface ScoreConfigService {
    void makeTemplate(ScoreTemplateVO vo, BaseRoleModel roleModel) throws NetError;

}
