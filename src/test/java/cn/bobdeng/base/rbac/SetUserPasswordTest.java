package cn.bobdeng.base.rbac;

import cn.bobdeng.base.IntegrationTest;
import cn.bobdeng.base.PermissionSessionUserGetter;
import cn.bobdeng.base.rbac.repos.UserDAO;
import cn.bobdeng.base.rbac.repos.UserPasswordDAO;
import cn.bobdeng.base.rbac.user.SetPasswordForm;
import cn.bobdeng.base.role.RoleAlreadyExistException;
import cn.bobdeng.base.user.PasswordDO;
import cn.bobdeng.base.user.User;
import cn.bobdeng.base.user.UserDO;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SetUserPasswordTest extends IntegrationTest {
    @Autowired
    UserDAO userDAO;
    @Autowired
    UserPasswordDAO userPasswordDAO;
    BCryptPasswordEncoder bCryptPasswordEncoder;

    @BeforeEach
    public void setup() {
        super.setup();
        super.setSessionUser();
        super.setPermission("rbac.user.set_password");
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Test
    public void should_has_password_when_set() throws Exception {
        User bob = new User(userDAO.save(UserDO.builder()
                .name("bob1")
                .tenantId(PermissionSessionUserGetter.TENANT_ID)
                .build()));
        SetPasswordForm setPasswordForm = new SetPasswordForm();
        setPasswordForm.setPassword("123456");
        mockMvc.perform(put("/rbac/user/" + bob.id() + "/password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(setPasswordForm))
        ).andExpect(status().isOk());

        PasswordDO passwordDO = userPasswordDAO.findById(bob.id()).orElseThrow();
        assertThat(bCryptPasswordEncoder.matches("123456", passwordDO.getPassword()), is(true));
    }
}
