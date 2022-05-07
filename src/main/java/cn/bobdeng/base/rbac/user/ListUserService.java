package cn.bobdeng.base.rbac.user;

import cn.bobdeng.base.PermissionSessionGetter;
import cn.bobdeng.base.SessionUser;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public class ListUserService {
    public static final String SQL = """
            SELECT rbac_user.id,rbac_user.name,rbac_account.name account from rbac_user
            left join rbac_account on rbac_account.id=rbac_user.id
            where rbac_user.tenant_id=:tenantId
            """;
    private PermissionSessionGetter permissionSessionGetter;
    private NamedParameterJdbcTemplate jdbcTemplate;

    public ListUserService(PermissionSessionGetter permissionSessionGetter, NamedParameterJdbcTemplate jdbcTemplate) {
        this.permissionSessionGetter = permissionSessionGetter;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UserVO> listUser() {
        SessionUser sessionUser = permissionSessionGetter.sessionUser().orElseThrow();
        Map<String, ?> args = Map.of(
                "tenantId", sessionUser.getTenantId().id()
        );
        return jdbcTemplate.query(SQL, args, new DataClassRowMapper<>(UserVO.class));
    }

}
