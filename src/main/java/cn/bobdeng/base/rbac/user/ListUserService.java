package cn.bobdeng.base.rbac.user;

import cn.bobdeng.base.PermissionSessionGetter;
import cn.bobdeng.base.SessionUser;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
public class ListUserService {
    private PermissionSessionGetter permissionSessionGetter;


    public ListUserService(PermissionSessionGetter permissionSessionGetter) {
        this.permissionSessionGetter = permissionSessionGetter;
    }

    public List<UserVO> listUser() {
        SessionUser sessionUser = permissionSessionGetter.sessionUser().orElseThrow();
        return Collections.emptyList();
    }

}
