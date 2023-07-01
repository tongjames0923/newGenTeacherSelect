package tbs.newgenteacherselect.model;

import tbs.pojo.dto.TeacherDetail;

public class TeacherMoreDetail  extends TeacherDetail {

    public static final String CNT_SQL=" (SELECT COUNT(1) FROM masterrelation mr1 WHERE mr1.masterPhone=bu.phone AND mr1.studentPhone IS NULL and mr1.scoreConfigItemId= #{scoreLevel}) AS `cnt` ";
    //名额数量
    Integer cnt;

    public Integer getCnt() {
        return cnt;
    }

    public void setCnt(Integer cnt) {
        this.cnt = cnt;
    }
}
