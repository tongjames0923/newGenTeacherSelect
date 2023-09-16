package tbs.newgenteacherselect.config.impl;

import org.springframework.stereotype.Component;
import tbs.framework.xxl.interfaces.IJsonJobHandler;
import tbs.newgenteacherselect.config.impl.JobHandlerModel.SelectiveStatusParam;
import tbs.newgenteacherselect.enums.SelectionEnums;
import tbs.newgenteacherselect.service.IStarterService;

import javax.annotation.Resource;

@Component("selectiveStatus")
public class SelectiveStatusTask implements IJsonJobHandler<SelectiveStatusParam> {


    @Resource
    IStarterService starterService;

    @Override
    public Class<? extends SelectiveStatusParam> classType() {
        return SelectiveStatusParam.class;
    }

    @Override
    public String handle(SelectiveStatusParam params) throws Exception {
        String result = "";
        if (params.getDep() == null) {
            throw new RuntimeException("输入部门名");
        }

        switch (params.getMethod()) {
            case SelectiveStatusParam.GET_STATUS:
                result = starterService.getStatus(params.getDep()).getName();
                break;
            case SelectiveStatusParam.SET_STATUS:
                if (params.getStatus() == null)
                    throw new RuntimeException("参数缺少");
                SelectionEnums enums = SelectionEnums.convertTo(params.getStatus());
                starterService.changeStatus(params.getDep(), enums, params.getMinutes() == null ? 2 * 60 : params.getMinutes());
                result = enums.getName();
                break;
        }
        return result;
    }
}
