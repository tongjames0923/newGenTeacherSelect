package tbs.newgenteacherselect.service;

import tbs.framework.error.NetError;
import tbs.framework.model.BaseRoleModel;
import tbs.newgenteacherselect.model.ScoreTemplateVO;
import tbs.newgenteacherselect.model.ScoreTemplateVO2;


import java.util.List;

public interface ScoreConfigService {


    boolean hasRights(BaseRoleModel model, String template);
    void makeTemplate(ScoreTemplateVO vo, BaseRoleModel roleModel) throws NetError;

    void removeTemplate(String template) throws NetError;
    List<ScoreTemplateVO2> listTemplate(int dep)throws Exception;


    void applyConfig(int department,String template) throws Exception;

}
