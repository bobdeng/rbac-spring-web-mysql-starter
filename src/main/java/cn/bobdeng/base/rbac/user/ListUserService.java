package cn.bobdeng.base.rbac.user;

import cn.bobdeng.base.PermissionSessionGetter;
import cn.bobdeng.base.SessionUser;
import org.springframework.jdbc.core.DataClassRowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class ListUserService {
    public static final String SQL = """
            SELECT rbac_user.id,rbac_user.name,rbac_account.name account from rbac_user
            left join rbac_account on rbac_account.id=rbac_user.id
            where rbac_user.tenant_id=:tenantId
            and (rbac_user.name like :keyword or rbac_account.name like :keyword or :keyword is null)
            """;
    private final PermissionSessionGetter permissionSessionGetter;
    private final NamedParameterJdbcTemplate jdbcTemplate;

    public ListUserService(PermissionSessionGetter permissionSessionGetter, NamedParameterJdbcTemplate jdbcTemplate) {
        this.permissionSessionGetter = permissionSessionGetter;
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<UserVO> listUser(String keyword) {
        Map<String, Object> args = buildSQLArgs(keyword);
        return jdbcTemplate.query(SQL, args, new DataClassRowMapper<>(UserVO.class));
    }

    private Map<String, Object> buildSQLArgs(String keyword) {
        SessionUser sessionUser = permissionSessionGetter.sessionUser().orElseThrow();
        Map<String, Object> args = new HashMap<>(2);
        args.put("tenantId", sessionUser.getTenantId().id());
        args.put("keyword", Optional.ofNullable(keyword).map(k -> "%" + k + "%").orElse(null));
        return args;
    }

}
