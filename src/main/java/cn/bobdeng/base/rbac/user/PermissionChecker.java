package cn.bobdeng.base.rbac.user;

import cn.bobdeng.base.PermissionSessionGetter;
import cn.bobdeng.base.SessionUser;
import cn.bobdeng.base.user.User;
import cn.bobdeng.base.user.UserRepository;
import cn.bobdeng.base.user.UserRoleRepository;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Aspect
public class PermissionChecker {
    private final PermissionSessionGetter permissionSessionGetter;
    private final UserRepository userRepository;
    private final UserRoleRepository userRoleRepository;

    public PermissionChecker(PermissionSessionGetter permissionSessionGetter, UserRepository userRepository, UserRoleRepository userRoleRepository) {
        this.permissionSessionGetter = permissionSessionGetter;
        this.userRepository = userRepository;
        this.userRoleRepository = userRoleRepository;
    }

    @Before("@annotation(permission)")
    public void before(Permission permission) {
        User user = getCurrentUser();
        if (!user.hasAnyPermission(Arrays.asList(permission.value()), userRoleRepository)) {
            throw new PermissionDeniedException();
        }
    }


    private User getCurrentUser() {
        SessionUser sessionUser = permissionSessionGetter.sessionUser().orElse(null);
        if (sessionUser == null) {
            throw new PermissionDeniedException();
        }
        return userRepository.findById(sessionUser.getUserId()).orElseThrow(PermissionDeniedException::new);
    }
}
