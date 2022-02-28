package cn.bobdeng.base.rbac;

import cn.bobdeng.base.IntegrationTest;
import cn.bobdeng.base.PermissionSessionUserGetter;
import cn.bobdeng.base.rbac.user.SetUserAccountForm;
import cn.bobdeng.base.role.*;
import cn.bobdeng.base.user.*;
import com.google.gson.Gson;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SetUserAccountControllerTest extends IntegrationTest {
    @Autowired
    MockMvc mockMvc;
    @Autowired
    PermissionSessionUserGetter permissionSessionUserGetter;

    @Test
    public void should_bind_account_when_set_account() throws Exception {
        Function function = new Function("user.setAccount");
        Role role = new Roles().newRole(new RoleName(""), new Functions(Arrays.asList(function)));
        User user = getUsers().newUser(UserName.empty());
        user.setRoles(new UserRoles(Arrays.asList(role.getId())));
        permissionSessionUserGetter.init(user);

        SetUserAccountForm form = new SetUserAccountForm();
        form.setAccount("zhangsan");
        mockMvc.perform(put("/rbac/user/" + user.id() + "/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(form))
        ).andExpect(status().isOk());
        UserId userId = Users.accountRepository.findUserByAccount(new Account(form.getAccount())).orElse(null);
        assertThat(userId, is(user.getId()));
    }

    @Test
    public void should_no_permission_when_set_account_has_no_role() throws Exception {
        permissionSessionUserGetter.init();
        User user = getUsers().newUser(UserName.empty());
        SetUserAccountForm form = new SetUserAccountForm();
        form.setAccount("zhangsan");
        mockMvc.perform(put("/rbac/user/" + user.id() + "/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(form))
        )
                .andExpect(status().isForbidden());
    }

    private Users getUsers() {
        return new Users(TenantId.of(PermissionSessionUserGetter.TENANT_ID));
    }
}
