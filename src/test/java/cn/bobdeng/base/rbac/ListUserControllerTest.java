package cn.bobdeng.base.rbac;

import cn.bobdeng.base.IntegrationTest;
import cn.bobdeng.base.PermissionSessionUserGetter;
import cn.bobdeng.base.SessionUser;
import cn.bobdeng.base.TenantId;
import cn.bobdeng.base.rbac.repos.UserAccountDAO;
import cn.bobdeng.base.rbac.repos.UserDAO;
import cn.bobdeng.base.rbac.user.ListUserController;
import cn.bobdeng.base.rbac.user.UserVO;
import cn.bobdeng.base.role.RoleAlreadyExistException;
import cn.bobdeng.base.user.AccountDO;
import cn.bobdeng.base.user.UserDO;
import cn.bobdeng.base.user.UserId;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

public class ListUserControllerTest extends IntegrationTest {
    public static final String TENANT_ID = "10000";
    @Autowired
    ListUserController listUserController;
    @Autowired
    UserDAO userDAO;
    @Autowired
    UserAccountDAO userAccountDAO;
    @Autowired
    PermissionSessionUserGetter permissionSessionUserGetter;
    private SessionUser sessionUser = new SessionUser(new UserId(1), new TenantId(TENANT_ID));

    @BeforeEach
    public void setup() throws RoleAlreadyExistException {
        super.setup();
        permissionSessionUserGetter.setSessionUser(sessionUser);
    }

    @Test
    public void should_return_empty_when_has_no_user() throws Exception {
        MvcResult mvcResult = listUser();
        JSONAssert.assertEquals(mvcResult.getResponse().getContentAsString(), "[]", true);
    }

    private MvcResult listUser() throws Exception {
        return mockMvc.perform(get("/rbac/users")
                        .contentType(MediaType.APPLICATION_JSON))
                .andReturn();
    }

    @Test
    public void should_return_users_when_has_has_1_user() throws Exception {
        userAccountDAO.save(AccountDO.builder()
                .id(1)
                .tenantId(TENANT_ID)
                .name("zhangsan")
                .build());
        UserDO userDO = userDAO.save(UserDO.builder()
                .id(1)
                .tenantId(TENANT_ID)
                .name("张三")
                .build());

        UserVO userVO = new UserVO();
        userVO.setId(userDO.getId());
        userVO.setName("张三");
        userVO.setAccount("zhangsan");
        String expectJsonResult = new Gson().toJson(Arrays.asList(userVO));
        JSONAssert.assertEquals(listUser().getResponse().getContentAsString(StandardCharsets.UTF_8), expectJsonResult, true);

    }
}
