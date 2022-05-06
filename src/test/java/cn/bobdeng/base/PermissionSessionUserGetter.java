package cn.bobdeng.base;

import cn.bobdeng.base.user.User;
import cn.bobdeng.base.user.UserId;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class PermissionSessionUserGetter implements PermissionSessionGetter {
    public static final String TENANT_ID = "10000";
    public static final Integer USER_ID = 100;

    @Setter
    private SessionUser sessionUser;

    @Override
    public Optional<SessionUser> sessionUser() {
        return Optional.ofNullable(sessionUser);
    }

    public void init() {
        this.sessionUser = new SessionUser(new UserId(USER_ID), new TenantId(TENANT_ID));
    }

    public void init(User user) {
        this.sessionUser = new SessionUser(user.getId(), new TenantId(TENANT_ID));
    }
}
