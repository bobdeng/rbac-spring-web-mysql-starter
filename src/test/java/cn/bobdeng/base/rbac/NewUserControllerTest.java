package cn.bobdeng.base.rbac;

import cn.bobdeng.base.IntegrationTest;
import cn.bobdeng.base.PermissionSessionUserGetter;
import cn.bobdeng.base.rbac.permission.SessionUser;
import cn.bobdeng.base.rbac.repository.UserAccountDAO;
import cn.bobdeng.base.rbac.repository.UserDAO;
import cn.bobdeng.base.rbac.repository.UserDO;
import cn.bobdeng.base.rbac.user.NewUserForm;
import cn.bobdeng.base.rbac.user.UserIdVO;
import cn.bobdeng.base.user.TenantId;
import cn.bobdeng.base.user.User;
import cn.bobdeng.base.user.Users;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class NewUserControllerTest extends IntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    UserDAO userDAO;
    @Autowired
    UserAccountDAO userAccountDAO;
    @Autowired
    PermissionSessionUserGetter permissionSessionUserGetter;
    private User admin;

    @BeforeEach
    public void setup() {
        userDAO.deleteAll();
        userAccountDAO.deleteAll();
    }

    public void initAdmin() {
        TenantId tenantId = TenantId.of(PermissionSessionUserGetter.TENANT_ID);
        admin = Users.ofTenant(tenantId).newAdmin();
        permissionSessionUserGetter.setSessionUser(new SessionUser(admin.getId(), tenantId));
    }

    @Test
    public void should_has_1_user_when_new_user() throws Exception {
        initAdmin();
        NewUserForm form = new NewUserForm();
        form.setName("张三");
        String result = mockMvc.perform(post("/rbac/users")
                .content(new Gson().toJson(form))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().is2xxSuccessful()).andReturn()
                .getResponse().getContentAsString(StandardCharsets.UTF_8);
        UserIdVO userId = new Gson().fromJson(result, UserIdVO.class);

        List<UserDO> users = userDAO.findByTenantId(PermissionSessionUserGetter.TENANT_ID)
                .stream().filter(userDO -> userDO.getLevel().equals("user"))
                .collect(Collectors.toList());
        assertThat(users.size(), is(1));
        assertThat(users.get(0).getStatus(), is("active"));
        assertThat(users.get(0).getName(), is("张三"));
        assertThat(users.get(0).getLevel(), is("user"));
        assertThat(userId.getId(), is(users.get(0).getId()));
    }

    @Test
    public void should_has_throw_user_when_has_no_permission() throws Exception {
        permissionSessionUserGetter.init();
        NewUserForm form = new NewUserForm();
        form.setName("张三");
        mockMvc.perform(post("/rbac/users")
                .content(new Gson().toJson(form))
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isForbidden()).andReturn()
                .getResponse().getContentAsString(StandardCharsets.UTF_8);
    }
}
