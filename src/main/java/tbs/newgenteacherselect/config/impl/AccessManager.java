package tbs.newgenteacherselect.config.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import tbs.framework.error.AuthorizationFailureException;
import tbs.framework.interfaces.IAccess;
import tbs.framework.interfaces.IPermissionVerification;
import tbs.framework.model.BaseRoleModel;
import tbs.framework.redis.IRedisService;
import tbs.newgenteacherselect.dao.AdminDao;
import tbs.newgenteacherselect.dao.RoleDao;
import tbs.newgenteacherselect.NetErrorEnum;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.pojo.Role;
import tbs.pojo.dto.AdminDetail;


import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component("redisAccess")
public class AccessManager implements IAccess, IPermissionVerification {

    @Resource
     IRedisService redisService;

    @Resource
    RoleDao roleDao;

    long login_timeout = 30;
    TimeUnit login_timeout_unit = TimeUnit.MINUTES;

    @Resource
    AdminDao adminDao;

    public static final String PREFIX = "ACCESS_TOKEN_", SINGLE_LOGIN_PREFIX = "SINGLE_LOGIN_";

    @Override
    public BaseRoleModel readRole(String tokenStr) {
        BaseRoleModel roleModel = redisService.get(PREFIX + tokenStr, BaseRoleModel.class);
        if (roleModel == null) {
            AdminDetail admin = adminDao.find(tokenStr);
            if (admin == null)
                return null;
            roleModel = roleDao.findOne(admin.getRole());
            roleModel.setUserId(admin.getPhone());
        }
        return roleModel;
    }

    @Override
    public List<BaseRoleModel> granded(Method method) {
        return null;
    }

    @Override
    public List<BaseRoleModel> grandedManual(int[] manuals) {
        QueryWrapper<Role> roleQueryWrapper=new QueryWrapper<>();
        List<Integer> arr=new LinkedList<>();
        for(int i:manuals)
        {
            arr.add(i);
        }
        if(CollectionUtils.isEmpty(arr))
            return new LinkedList<>();
        List<Role> roles= roleDao.selectBatchIds(arr);
        List<BaseRoleModel> roleModels=new LinkedList<>();

        for(Role r:roles)
        {
            BaseRoleModel b=new BaseRoleModel();
            b.setRoleCode(r.getRoleid());
            b.setRoleName(r.getRolename());
            roleModels.add(b);
        }
        return roleModels;
    }

    @Override
    public void put(String token, BaseRoleModel detail) throws Exception {

        if (detail != null && redisService.hasKey(SINGLE_LOGIN_PREFIX + detail.getUserId())) {
            throw NetErrorEnum.makeError(NetErrorEnum.Repeated_Login);
        }

        if (detail == null)
            detail = readRole(token);
        if (detail != null) {
            redisService.set(SINGLE_LOGIN_PREFIX + detail.getUserId(), detail, login_timeout, login_timeout_unit);
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

    @Override
    public VerificationConclusion accessCheck(BaseRoleModel user, Map<Integer, BaseRoleModel> needRoleMap) throws AuthorizationFailureException {
        if (user.getRoleCode() == RoleVO.ROLE_ADMIN)
            return VerificationConclusion.Authorized;
        else {
            return needRoleMap.containsKey(user.getRoleCode()) ? VerificationConclusion.EQUAL : VerificationConclusion.UnAuthorized;
        }
    }
}
