package tbs.newgenteacherselect.config.impl;

import org.springframework.stereotype.Component;
import tbs.dao.RoleDao;
import tbs.newgenteacherselect.NetErrorEnum;
import tbs.utils.AOP.authorize.interfaces.IAccess;
import tbs.utils.AOP.authorize.model.BaseRoleModel;
import tbs.utils.redis.IRedisService;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.TimeUnit;

@Component
public class AccessManager implements IAccess {

    @Resource
    IRedisService redisService;

    @Resource
    RoleDao roleDao;

    long login_timeout = 30;
    TimeUnit login_timeout_unit = TimeUnit.MINUTES;

    public static final String PREFIX = "ACCESS_TOKEN_", SINGLE_LOGIN_PREFIX = "SINGLE_LOGIN_";

    @Override
    public BaseRoleModel readRole(String tokenStr) {
        return redisService.get(PREFIX + tokenStr, BaseRoleModel.class);
    }

    @Override
    public List<BaseRoleModel> granded(Method method) {
        return null;
    }

    @Override
    public List<BaseRoleModel> grandedManual(int[] manuals) {
        List<Integer> l = new LinkedList<>();
        for (int i : manuals) {
            l.add(i);
        }
        return roleDao.roleInList(l);
    }

    @Override
    public void put(String token, BaseRoleModel detail) throws Exception {

        if (detail != null&&redisService.hasKey(SINGLE_LOGIN_PREFIX + detail.getUserId())) {
            throw NetErrorEnum.makeError(NetErrorEnum.Repeated_Login);
        }

        if (detail == null)
            detail = readRole(token);
        if (detail != null) {
            redisService.set(SINGLE_LOGIN_PREFIX + detail.getUserId(), detail,login_timeout, login_timeout_unit);
            redisService.set(PREFIX + token, detail, login_timeout, login_timeout_unit);
            return;
        }
        throw NetErrorEnum.makeError(NetErrorEnum.BAD_ROLE);


    }

    @Override
    public void deleteToken(String token) {
        BaseRoleModel roleModel = this.readRole(token);
        if (roleModel == null)
            return;
        redisService.delete(SINGLE_LOGIN_PREFIX + roleModel.getUserId());
        redisService.delete(PREFIX + token);
    }
}
