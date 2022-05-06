package cn.bobdeng.base.rbac;

import cn.bobdeng.base.IntegrationTest;
import cn.bobdeng.base.PermissionSessionUserGetter;
import cn.bobdeng.base.TenantId;
import cn.bobdeng.base.rbac.repos.UserDAO;
import cn.bobdeng.base.rbac.user.SetPasswordForm;
import cn.bobdeng.base.user.*;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SetUserPasswordTest extends IntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PermissionSessionUserGetter permissionSessionUserGetter;
    @Autowired
    UserPermissionSetter userPermissionSetter;
    @Autowired
    UserDAO userDAO;
    @Autowired
    PasswordRepository passwordRepository;

    @Test
    public void should_has_password_when_set() throws Exception {
        Users users = new Users(new TenantId(PermissionSessionUserGetter.TENANT_ID));
        UserDO userDO = userDAO.save(UserDO.builder()
                .name("bob")
                .tenantId(PermissionSessionUserGetter.TENANT_ID)
                .build());
        permissionSessionUserGetter.init(new User(userDO));
        userPermissionSetter.setPermission("operator.setPassword");
        User user = new User(userDAO.save(UserDO.builder()
                .name("bob1")
                .tenantId(PermissionSessionUserGetter.TENANT_ID)
                .build()));
        SetPasswordForm setPasswordForm = new SetPasswordForm();
        setPasswordForm.setPassword("123456");
        mockMvc.perform(put("/rbac/user/" + user.id() + "/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(setPasswordForm))
        ).andExpect(status().isOk());

        boolean verifyPasswordResult = user.verifyPassword(new Password("123456"), passwordRepository);
        assertThat(verifyPasswordResult, is(true));
    }
}
