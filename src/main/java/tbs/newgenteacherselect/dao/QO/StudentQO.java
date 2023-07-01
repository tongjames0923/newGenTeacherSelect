package tbs.newgenteacherselect.dao.QO;

import org.springframework.util.StringUtils;


public class StudentQO {
    private String nameOrPhone;
    private Integer department;
    Integer level;
    String Clas;
    Integer grade;
    String masterPhoneOrName;

    public String getNameOrPhone() {
        return nameOrPhone;
    }

    public void setNameOrPhone(String nameOrPhone) {
        if(StringUtils.isEmpty(nameOrPhone))
            nameOrPhone=null;
        this.nameOrPhone = nameOrPhone;
    }

    public Integer getDepartment() {
        return department;
    }

    public void setDepartment(Integer department) {

        this.department = department;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getClas() {
        return Clas;
    }

    public void setClas(String clas) {
        if(StringUtils.isEmpty(clas))
            clas=null;
        Clas = clas;
    }

    public Integer getGrade() {
        return grade;
    }

    public String getMasterPhoneOrName() {
        return masterPhoneOrName;
    }

    public void setMasterPhoneOrName(String masterPhoneOrName) {
        if(StringUtils.isEmpty(masterPhoneOrName))
            masterPhoneOrName=null;
        this.masterPhoneOrName = masterPhoneOrName;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

}
