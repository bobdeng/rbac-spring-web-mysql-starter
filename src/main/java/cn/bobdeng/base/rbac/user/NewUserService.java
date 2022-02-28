package cn.bobdeng.base.rbac.user;

import cn.bobdeng.base.rbac.permission.Permission;
import cn.bobdeng.base.rbac.permission.PermissionSessionGetter;
import cn.bobdeng.base.user.TenantId;
import cn.bobdeng.base.user.User;
import cn.bobdeng.base.user.UserName;
import cn.bobdeng.base.user.Users;
import org.springframework.stereotype.Service;

@Service
public class NewUserService {
    private CurrentUser currentUser;

    public NewUserService(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }

    @Permission(allow = "user.create")
    public UserIdVO execute(UserName name) {
        User user = Users.ofTenant(currentUser.tenantId()).newUser(name);
        return new UserIdVO(user.id());
    }
}
