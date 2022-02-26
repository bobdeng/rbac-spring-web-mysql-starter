package cn.bobdeng.base.rbac;

import cn.bobdeng.base.rbac.permission.PermissionSessionGetter;
import cn.bobdeng.base.rbac.permission.SessionUser;
import cn.bobdeng.base.user.Users;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ListUserController {
    private PermissionSessionGetter permissionSessionGetter;

    public ListUserController(PermissionSessionGetter permissionSessionGetter) {
        this.permissionSessionGetter = permissionSessionGetter;
    }

    @GetMapping("/rbac/users")
    public List<UserVO> listUser() {
        SessionUser sessionUser = permissionSessionGetter.sessionUser().orElseThrow();
        return Users.ofTenant(sessionUser.getTenantId()).list().stream()
                .map(UserVO::new).collect(Collectors.toList());
    }
}
