package cn.bobdeng.base.rbac.user;

import cn.bobdeng.base.PermissionSessionGetter;
import cn.bobdeng.base.SessionUser;
import cn.bobdeng.base.TenantId;
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
