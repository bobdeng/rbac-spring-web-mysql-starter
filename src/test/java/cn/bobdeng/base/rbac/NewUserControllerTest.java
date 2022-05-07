package cn.bobdeng.base.rbac;

import cn.bobdeng.base.IntegrationTest;
import cn.bobdeng.base.PermissionSessionUserGetter;
import cn.bobdeng.base.rbac.repos.RoleDAO;
import cn.bobdeng.base.rbac.repos.UserAccountDAO;
import cn.bobdeng.base.rbac.repos.UserDAO;
import cn.bobdeng.base.rbac.repos.UserRoleDAO;
import cn.bobdeng.base.rbac.user.NewUserForm;
import cn.bobdeng.base.rbac.user.UserIdVO;
import cn.bobdeng.base.role.RoleAlreadyExistException;
import cn.bobdeng.base.user.UserDO;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.stream.Collectors;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


public class NewUserControllerTest extends IntegrationTest {
    @Autowired
    UserDAO userDAO;
    @Autowired
    UserAccountDAO userAccountDAO;
    @Autowired
    PermissionSessionUserGetter permissionSessionUserGetter;
    @Autowired
    UserRoleDAO userRoleDAO;
    @Autowired
    RoleDAO roleDAO;
    @Autowired
    UserPermissionSetter userPermissionSetter;

    @BeforeEach
    public void setup() throws RoleAlreadyExistException {
        userDAO.deleteAll();
        userAccountDAO.deleteAll();
        userRoleDAO.deleteAll();
        roleDAO.deleteAll();
        super.setup();
        super.setSessionUser();
    }

    @Test
    public void should_has_1_user_when_new_user() throws Exception {
        super.setPermission("rbac.user.create");
        NewUserForm form = new NewUserForm();
        form.setName("李四");
        String result = mockMvc.perform(post("/rbac/users")
                        .content(new Gson().toJson(form))
                        .contentType(MediaType.APPLICATION_JSON)
                ).andExpect(status().isOk()).andReturn()
                .getResponse().getContentAsString(StandardCharsets.UTF_8);
        UserIdVO userId = new Gson().fromJson(result, UserIdVO.class);
        List<UserDO> users = userDAO.findAllByTenantId(PermissionSessionUserGetter.TENANT_ID).collect(Collectors.toList());
        assertThat(users.size(), is(2));
        UserDO savedUser = userDAO.findById(userId.getId()).orElseThrow();
        assertThat(savedUser.getName(), is("李四"));
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
