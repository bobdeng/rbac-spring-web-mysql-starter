package cn.bobdeng.base.rbac;

import cn.bobdeng.base.IntegrationTest;
import cn.bobdeng.base.PermissionSessionUserGetter;
import cn.bobdeng.base.TenantId;
import cn.bobdeng.base.rbac.repos.UserAccountDAO;
import cn.bobdeng.base.rbac.repos.UserDAO;
import cn.bobdeng.base.rbac.user.SetUserAccountForm;
import cn.bobdeng.base.role.*;
import cn.bobdeng.base.user.*;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static cn.bobdeng.base.PermissionSessionUserGetter.TENANT_ID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class SetUserAccountControllerTest extends IntegrationTest {
    @Autowired
    PermissionSessionUserGetter permissionSessionUserGetter;
    @Autowired
    UserPermissionSetter userPermissionSetter;
    @Autowired
    UserDAO userDAO;
    @Autowired
    UserAccountDAO userAccountDAO;

    @BeforeEach
    public void setup() throws RoleAlreadyExistException {
        super.setup();
        super.setSessionUser();
    }

    @Override
    protected List<String> tablesNeedClear() {
        return List.of("rbac_user", "rbac_account", "rbac_role", "rbac_user_role");
    }

    @Test
    public void should_bind_account_when_set_account() throws Exception {
        userPermissionSetter.setPermission("rbac.user.set_account");
        SetUserAccountForm form = new SetUserAccountForm();
        form.setAccount("zhangsan");
        UserDO zhangsan = createZhangsan();
        mockMvc.perform(put("/rbac/user/" + zhangsan.getId() + "/account")
                .contentType(MediaType.APPLICATION_JSON)
                .content(new Gson().toJson(form))
        ).andExpect(status().isOk());
        AccountDO accountOfZhangsan = userAccountDAO.findById(zhangsan.getId()).orElseThrow();
        assertThat(accountOfZhangsan.getName(), is("zhangsan"));
        assertThat(accountOfZhangsan.getTenantId(), is(TENANT_ID));
    }

    private UserDO createZhangsan() {
        UserDO zhangsan = userDAO.save(UserDO.builder()
                .tenantId(TENANT_ID)
                .name("张三")
                .build());
        return zhangsan;
    }

    @Test
    public void should_no_permission_when_set_account_has_no_role() throws Exception {
        UserDO zhangsan = createZhangsan();
        SetUserAccountForm form = new SetUserAccountForm();
        form.setAccount("zhangsan");
        mockMvc.perform(put("/rbac/user/" + zhangsan.getId() + "/account")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new Gson().toJson(form))
                )
                .andExpect(status().isForbidden());
    }

}
