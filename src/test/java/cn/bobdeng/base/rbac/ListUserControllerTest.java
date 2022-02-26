package cn.bobdeng.base.rbac;

import cn.bobdeng.base.IntegrationTest;
import cn.bobdeng.base.PermissionSessionUserGetter;
import cn.bobdeng.base.rbac.permission.SessionUser;
import cn.bobdeng.base.rbac.repository.UserDAO;
import cn.bobdeng.base.rbac.repository.UserDO;
import cn.bobdeng.base.user.TenantId;
import cn.bobdeng.base.user.UserId;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ListUserControllerTest extends IntegrationTest {
    public static final String TENANT_ID = "10000";
    @Autowired
    ListUserController listUserController;
    @Autowired
    UserDAO userDAO;
    @Autowired
    PermissionSessionUserGetter permissionSessionUserGetter;
    private SessionUser sessionUser = new SessionUser(UserId.of("100"), TenantId.of(TENANT_ID));

    @Test
    public void should_return_empty_when_has_no_user() {
        permissionSessionUserGetter.setSessionUser(sessionUser);
        List list = listUserController.listUser();
        assertThat(list.isEmpty(), is(true));
    }

    @Test
    public void should_return_empty_when_has_has_1_user() {
        permissionSessionUserGetter.setSessionUser(sessionUser);
        userDAO.save(UserDO.builder()
                .id("123")
                .tenantId(TENANT_ID)
                .name("张三")
                .level("user")
                .status("active")
                .build());

        List list = listUserController.listUser();
        assertThat(list.size(), is(1));
    }
}
