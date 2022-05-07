package cn.bobdeng.base.rbac.role;

import cn.bobdeng.base.PermissionSessionGetter;
import cn.bobdeng.base.SessionUser;
import cn.bobdeng.base.role.RoleAlreadyExistException;
import cn.bobdeng.base.role.RoleRepository;
import cn.bobdeng.base.role.Roles;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

@RestController
public class NewRoleController {
    final PermissionSessionGetter permissionSessionGetter;
    final RoleRepository roleRepository;

    public NewRoleController(PermissionSessionGetter permissionSessionGetter, RoleRepository roleRepository) {
        this.permissionSessionGetter = permissionSessionGetter;
        this.roleRepository = roleRepository;
    }

    @PostMapping("/rbac/roles")
    public void newRole(@RequestBody @Valid NewRoleRequest request) throws RoleAlreadyExistException {
        SessionUser sessionUser = permissionSessionGetter.sessionUser().orElseThrow();
        new Roles(sessionUser.getTenantId()).saveRole(request.toEntity(), roleRepository);
    }

    @ExceptionHandler(RoleAlreadyExistException.class)
    public String handleRoleAlreadyExistException(RoleAlreadyExistException e, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return "角色名称已经存在：" + e.getName();
    }
}
