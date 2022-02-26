package cn.bobdeng.base;

import cn.bobdeng.base.rbac.permission.PermissionSessionGetter;
import cn.bobdeng.base.rbac.permission.SessionUser;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class PermissionSessionUserGetter implements PermissionSessionGetter {
    @Override
    public Optional<SessionUser> sessionUser() {
        return Optional.empty();
    }
}
