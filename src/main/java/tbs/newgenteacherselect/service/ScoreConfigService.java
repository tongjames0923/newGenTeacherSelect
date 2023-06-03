package tbs.newgenteacherselect.service;

import tbs.newgenteacherselect.model.ScoreTemplateVO;
import tbs.utils.AOP.authorize.model.BaseRoleModel;
import tbs.utils.error.NetError;

import java.util.List;

public interface ScoreConfigService {


    boolean hasRights(BaseRoleModel model,String template);
    void makeTemplate(ScoreTemplateVO vo, BaseRoleModel roleModel) throws NetError;

    void removeTemplate(String template) throws NetError;
    List<ScoreTemplateVO> listTemplate(int dep)throws Exception;

}
