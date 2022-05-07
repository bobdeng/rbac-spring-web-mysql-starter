package cn.bobdeng.base.rbac;

import cn.bobdeng.base.PermissionSessionUserGetter;
import cn.bobdeng.base.SessionUser;
import cn.bobdeng.base.TenantId;
import cn.bobdeng.base.role.*;
import cn.bobdeng.base.user.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

@Service
public class UserPermissionSetter {
    @Autowired
    PermissionSessionUserGetter permissionSessionUserGetter;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserRoleRepository userRoleRepository;

    public void setPermission(String functionName) throws RoleAlreadyExistException {
        this.setPermission(Arrays.asList(functionName));
    }

    public void setPermission(List<String> functions) throws RoleAlreadyExistException {
        String roleName = "test";
        TenantId tenantId = permissionSessionUserGetter.sessionUser().map(SessionUser::getTenantId).orElse(null);
        Roles roles = new Roles(tenantId);
        Role role = new Role(new RoleName(roleName), new RoleFunctions(functions));
        roles.saveRole(role, roleRepository);
        UserId userId = permissionSessionUserGetter.sessionUser().map(SessionUser::getUserId).orElse(null);
        User user = userRepository.findById(userId).orElseThrow();
        user.setRoles(Arrays.asList(roleName), userRoleRepository);

    }
}
