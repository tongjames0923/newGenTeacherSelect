package tbs.newgenteacherselect.config.impl.JobHandlerModel;

import lombok.Data;

@Data
public class SelectiveStatusParam {

    public static final int GET_STATUS = 0, SET_STATUS = 1;
    private int method=0;

    private Integer dep;
    private Integer status;
    private Integer minutes;
}
