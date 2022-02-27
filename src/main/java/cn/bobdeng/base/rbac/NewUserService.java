package cn.bobdeng.base.rbac;

import cn.bobdeng.base.rbac.permission.Permission;
import cn.bobdeng.base.rbac.permission.PermissionSessionGetter;
import cn.bobdeng.base.rbac.permission.SessionUser;
import cn.bobdeng.base.user.TenantId;
import cn.bobdeng.base.user.User;
import cn.bobdeng.base.user.UserName;
import cn.bobdeng.base.user.Users;
import org.springframework.stereotype.Service;

@Service
public class NewUserService {
    private PermissionSessionGetter permissionSessionGetter;

    public NewUserService(PermissionSessionGetter permissionSessionGetter) {
        this.permissionSessionGetter = permissionSessionGetter;
    }

    @Permission(admin = true)
    public UserIdVO execute(UserName name) {
        TenantId tenantId = permissionSessionGetter.sessionUser().map(SessionUser::getTenantId).orElse(null);
        User user = Users.ofTenant(tenantId).newUser(name);
        return new UserIdVO(user.id());
    }
}
