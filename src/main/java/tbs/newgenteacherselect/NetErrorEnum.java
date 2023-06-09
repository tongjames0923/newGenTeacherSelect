package tbs.newgenteacherselect;


import tbs.framework.error.NetError;

public enum NetErrorEnum {

    LOGIN_FAIL("ERROR.登录失败",50000),BAD_ROLE("ERROR.错误的权限信息",50001),NO_PARENT("ERROR.无效父项",50005),
    NOT_AVALIABLE("ERROR.尚未完成功能",50002),Repeated_Login("ERROR.重复登录",50003),HAS_MORE_NODE("ERROR.存在无去处子项",50004),
    NOT_FOUND("ERROR.不存在项",50006),NOT_ALLOW("ERROR.非法参数",50007);

    public static NetError makeError(NetErrorEnum errorEnum)
    {
         return new NetError(errorEnum.errorText, errorEnum.errorCode);
    }
    public static NetError makeError(NetErrorEnum errorEnum,String msg)
    {
        return new NetError(msg, errorEnum.errorCode);
    }

    private final String errorText;
    private final long errorCode;
    NetErrorEnum(String t,long c)
    {
        this.errorCode=c;
        this.errorText=t;
    }
}
