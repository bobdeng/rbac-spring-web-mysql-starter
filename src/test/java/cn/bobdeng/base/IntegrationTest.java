package cn.bobdeng.base;

import cn.bobdeng.base.rbac.UserPermissionSetter;
import cn.bobdeng.base.rbac.repos.RoleDAO;
import cn.bobdeng.base.rbac.repos.UserDAO;
import cn.bobdeng.base.rbac.repos.UserRoleDAO;
import cn.bobdeng.base.role.RoleAlreadyExistException;
import cn.bobdeng.base.user.UserDO;
import cn.bobdeng.base.user.UserId;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;


@ExtendWith(SpringExtension.class)
@SpringBootTest()
@AutoConfigureMockMvc
@Transactional
@Slf4j
public abstract class IntegrationTest {
    protected MockMvc mockMvc;
    @Autowired
    private WebApplicationContext webApplicationContext;
    @Autowired
    UserDAO userDAO;
    @Autowired
    UserRoleDAO userRoleDAO;
    @Autowired
    RoleDAO roleDAO;
    @Autowired
    PermissionSessionUserGetter permissionSessionUserGetter;
    @Autowired
    UserPermissionSetter userPermissionSetter;
    @Autowired
    JdbcTemplate jdbcTemplate;

    @BeforeEach()
    public void setup() {
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
        this.tablesNeedClear().forEach(table -> {
            jdbcTemplate.execute("truncate table " + table);
        });
    }

    protected void setSessionUser() {
        TenantId tenantId = new TenantId(PermissionSessionUserGetter.TENANT_ID);
        UserDO admin = userDAO.save(UserDO.builder()
                .name("admin")
                .tenantId(tenantId.id())
                .build());
        permissionSessionUserGetter.setSessionUser(new SessionUser(new UserId(admin.getId()), tenantId));
    }

    protected SessionUser getSessionUser(){
        return permissionSessionUserGetter.sessionUser().orElse(null);
    }

    protected void setPermission(String function) {
        try {
            userPermissionSetter.setPermission(function);
        } catch (RoleAlreadyExistException e) {
            throw new RuntimeException(e);
        }
    }

    protected void setPermission(List<String> functions) {
        try {
            userPermissionSetter.setPermission(functions);
        } catch (RoleAlreadyExistException e) {
            throw new RuntimeException(e);
        }
    }

    protected List<String> tablesNeedClear() {
        return Collections.emptyList();
    }
}