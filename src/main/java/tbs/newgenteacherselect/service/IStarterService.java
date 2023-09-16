package tbs.newgenteacherselect.service;

import tbs.newgenteacherselect.enums.SelectionEnums;

public interface IStarterService {
    SelectionEnums getStatus(int dep);

    void changeStatus(int dep,SelectionEnums enums,long mins);
}
