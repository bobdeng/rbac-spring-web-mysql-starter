package cn.bobdeng.base.rbac.user;

import cn.bobdeng.base.rbac.permission.PermissionSessionGetter;
import cn.bobdeng.base.rbac.permission.SessionUser;
import cn.bobdeng.base.user.TenantId;
import org.springframework.stereotype.Service;

@Service
public class CurrentUser {
    private final PermissionSessionGetter permissionSessionGetter;

    public CurrentUser(PermissionSessionGetter permissionSessionGetter) {
        this.permissionSessionGetter = permissionSessionGetter;
    }

    TenantId tenantId() {
        return permissionSessionGetter.sessionUser().map(SessionUser::getTenantId).orElse(null);
    }
}
