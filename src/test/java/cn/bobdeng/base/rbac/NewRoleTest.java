package cn.bobdeng.base.rbac;

import cn.bobdeng.base.IntegrationTest;
import cn.bobdeng.base.PermissionSessionUserGetter;
import cn.bobdeng.base.rbac.repos.RoleDAO;
import cn.bobdeng.base.role.RoleDO;
import cn.bobdeng.testtools.SnapshotMatcher;
import cn.bobdeng.testtools.TestResource;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MvcResult;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static cn.bobdeng.base.PermissionSessionUserGetter.TENANT_ID;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class NewRoleTest extends IntegrationTest {
    @Autowired
    RoleDAO roleDAO;

    @BeforeEach
    public void setup() {
        super.setup();
        super.setSessionUser();
        super.setPermission(Arrays.asList("rbac.role.create", "rbac.role.update"));
    }

    @Override
    protected List<String> tablesNeedClear() {
        return List.of("rbac_role", "rbac_user", "rbac_user_role");
    }

    @Test
    public void should_create_role() throws Exception {
        mockMvc.perform(post("/rbac/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new TestResource(this, "new_role.json").readString()))
                .andExpect(status().isOk());
        List<RoleDO> roles = roleDAO.findAllByTenantId(TENANT_ID).collect(Collectors.toList());
        assertThat(roles.size(), CoreMatchers.is(2));
        assertThat(roles, SnapshotMatcher.snapshotMatch(this, "new_role"));
    }

    @Test
    public void should_save_role() throws Exception {
        RoleDO roleDO = roleDAO.save(RoleDO.builder().name("新角色1").tenantId(TENANT_ID).functions("[]").build());
        MvcResult mvcResult = mockMvc.perform(put("/rbac/role/" + roleDO.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new TestResource(this, "new_role.json").readString()))
                .andExpect(status().isOk())
                .andReturn();
        List<RoleDO> roles = roleDAO.findAllByTenantId(TENANT_ID).collect(Collectors.toList());
        assertThat(roles.size(), CoreMatchers.is(2));
        assertThat(roles, SnapshotMatcher.snapshotMatch(this, "new_role"));

    }

    @Test
    public void should_fail_create_role_with_same_name() throws Exception {
        roleDAO.save(RoleDO.builder().name("新角色").tenantId(TENANT_ID).functions("[]").build());
        MvcResult mvcResult = mockMvc.perform(post("/rbac/roles")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new TestResource(this, "new_role.json").readString()))
                .andExpect(status().is4xxClientError())
                .andReturn();
        assertThat(mvcResult.getResponse().getContentAsString(StandardCharsets.UTF_8), CoreMatchers.is("角色名称已经存在：新角色"));
    }
}
