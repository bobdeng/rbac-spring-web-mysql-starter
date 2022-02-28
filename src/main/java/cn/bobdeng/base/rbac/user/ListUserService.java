package cn.bobdeng.base.rbac.user;

import cn.bobdeng.base.rbac.permission.PermissionSessionGetter;
import cn.bobdeng.base.rbac.permission.SessionUser;
import cn.bobdeng.base.user.Users;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class ListUserService {
    public ListUserService(PermissionSessionGetter permissionSessionGetter) {
        this.permissionSessionGetter = permissionSessionGetter;
    }

    private PermissionSessionGetter permissionSessionGetter;

    public List<UserVO> listUser() {
        SessionUser sessionUser = permissionSessionGetter.sessionUser().orElseThrow();
        return Users.ofTenant(sessionUser.getTenantId()).list().stream()
                .map(UserVO::new).collect(Collectors.toList());
    }

}
