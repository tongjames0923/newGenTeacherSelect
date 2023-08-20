package tbs.newgenteacherselect.config.impl;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import tbs.framework.controller.enums.VerificationConclusion;
import tbs.framework.controller.interfaces.IAccess;
import tbs.framework.controller.interfaces.IPermissionVerification;
import tbs.framework.error.AuthorizationFailureException;
import tbs.framework.model.BaseRoleModel;
import tbs.framework.redis.IRedisService;
import tbs.newgenteacherselect.NetErrorEnum;
import tbs.newgenteacherselect.dao.AdminDao;
import tbs.newgenteacherselect.dao.RoleDao;
import tbs.newgenteacherselect.model.RoleVO;
import tbs.pojo.Role;
import tbs.pojo.dto.AdminDetail;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

@Component("redisAccess")
public class AccessManager implements IAccess, IPermissionVerification {

    @Resource
    IRedisService redisService;

    @Resource
    RedisTemplate<String, Object> redisTemplate;

    void updateLoginTable(String token, BaseRoleModel detail) {
        Object oldToken = redisTemplate.opsForHash().get(ACCESS_TABLE, detail.getUserId());
        if (!StringUtils.isEmpty(oldToken)) {
            redisService.delete(PREFIX + oldToken);
        }
        redisService.set(PREFIX + token, detail, login_timeout, login_timeout_unit);
        redisTemplate.opsForHash().put(ACCESS_TABLE, detail.getUserId(), token);
    }


    @Resource
    RoleDao roleDao;

    long login_timeout = 30;
    TimeUnit login_timeout_unit = TimeUnit.MINUTES;


    private static AccessManager accessManager;

    public static AccessManager getInstance() {
        return accessManager;
    }

    public AccessManager() {
        AccessManager.accessManager = this;
    }

    public int getLoginActive() {
        return (int) TimeUnit.SECONDS.convert(login_timeout, login_timeout_unit);
    }

    @Resource
    AdminDao adminDao;

    public static final String PREFIX = "ACCESS_TOKEN_", ACCESS_TABLE = "ACCESS_TABLE::";

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
        List<Integer> arr = new LinkedList<>();
        for (int i : manuals) {
            arr.add(i);
        }
        if (arr.isEmpty()) {
            arr.add(RoleVO.ROLE_ADMIN);
            arr.add(RoleVO.ROLE_TEACHER);
            arr.add(RoleVO.ROLE_STUDENT);
        }
        if (CollectionUtils.isEmpty(arr))
            return new LinkedList<>();
        List<Role> roles = roleDao.selectBatchIds(arr);
        List<BaseRoleModel> roleModels = new LinkedList<>();

        for (Role r : roles) {
            BaseRoleModel b = new BaseRoleModel();
            b.setRoleCode(r.getRoleid());
            b.setRoleName(r.getRolename());
            roleModels.add(b);
        }
        return roleModels;
    }

    @Override
    public void put(String token, BaseRoleModel detail) throws Exception {

//        if (detail != null && redisService.hasKey(SINGLE_LOGIN_PREFIX + detail.getUserId())) {
//            throw NetErrorEnum.makeError(NetErrorEnum.Repeated_Login);
//        }

        if (detail == null)
            detail = readRole(token);
        if (detail != null) {
            updateLoginTable(token, detail);
            return;
        }
        throw NetErrorEnum.makeError(NetErrorEnum.BAD_ROLE);


    }

    @Override
    public void deleteToken(String token) {
        BaseRoleModel roleModel = this.readRole(token);
        if (roleModel == null)
            return;
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
