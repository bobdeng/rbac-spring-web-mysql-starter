package cn.bobdeng.base;

import java.util.Optional;

public interface PermissionSessionGetter {
    Optional<SessionUser> sessionUser();
}
