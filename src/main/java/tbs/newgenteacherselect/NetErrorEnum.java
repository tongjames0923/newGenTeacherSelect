package tbs.newgenteacherselect;

import tbs.utils.error.NetError;

public enum NetErrorEnum {

    LOGIN_FAIL("登录失败",50000),BAD_ROLE("错误的权限信息",50001),NO_PARENT("无效父项",50005),
    NOT_AVALIABLE("尚未完成功能",50002),Repeated_Login("重复登录",50003),HAS_MORE_NODE("存在无去处子项",50004),
    NOT_FOUND("不存在项",50006);

    public static NetError makeError(NetErrorEnum errorEnum)
    {
         return new NetError(errorEnum.errorText, errorEnum.errorCode);
    }
    public static NetError makeError(NetErrorEnum errorEnum,String msg)
    {
        return new NetError(msg, errorEnum.errorCode);
    }

    private String errorText;
    private long errorCode;
    NetErrorEnum(String t,long c)
    {
        this.errorCode=c;
        this.errorText=t;
    }
}
