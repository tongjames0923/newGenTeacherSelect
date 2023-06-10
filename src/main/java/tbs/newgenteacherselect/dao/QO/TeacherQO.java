package tbs.newgenteacherselect.dao.QO;

public class TeacherQO {
    String nameOrPhone;String positionOrTitle;Integer department;Integer scoreLevel;

    public String getNameOrPhone() {
        return nameOrPhone;
    }

    public String getPositionOrTitle() {
        return positionOrTitle;
    }

    public void setPositionOrTitle(String positionOrTitle) {
        this.positionOrTitle = positionOrTitle;
    }

    public void setNameOrPhone(String nameOrPhone) {
        this.nameOrPhone = nameOrPhone;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {
        this.department = department;
    }

    public Integer getScoreLevel() {
        return scoreLevel;
    }

    public void setScoreLevel(Integer scoreLevel) {
        this.scoreLevel = scoreLevel;
    }
}
