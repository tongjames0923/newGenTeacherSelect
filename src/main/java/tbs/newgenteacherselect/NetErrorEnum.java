package tbs.newgenteacherselect;

import tbs.utils.error.NetError;

public enum NetErrorEnum {

    LOGIN_FAIL("登录失败",50000),BAD_ROLE("错误的权限信息",50001),
    NOT_AVALIABLE("尚未完成功能",50002),Repeated_Login("重复登录",50003),;

    public static NetError makeError(NetErrorEnum errorEnum)
    {
         return new NetError(errorEnum.errorText, errorEnum.errorCode);
    }

    private String errorText;
    private long errorCode;
    NetErrorEnum(String t,long c)
    {
        this.errorCode=c;
        this.errorText=t;
    }
}
