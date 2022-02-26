package cn.bobdeng.base;

import cn.bobdeng.base.rbac.permission.PermissionSessionGetter;
import cn.bobdeng.base.rbac.permission.SessionUser;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
public class PermissionSessionUserGetter implements PermissionSessionGetter {
    @Setter
    private SessionUser sessionUser;
    @Override
    public Optional<SessionUser> sessionUser() {
        return Optional.ofNullable(sessionUser);
    }
}
