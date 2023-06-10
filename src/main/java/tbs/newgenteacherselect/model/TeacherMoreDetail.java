package tbs.newgenteacherselect.model;

import tbs.pojo.dto.TeacherDetail;

public class TeacherMoreDetail  extends TeacherDetail {

    //名额数量
    Integer cnt;

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }
}
