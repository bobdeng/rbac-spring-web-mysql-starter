package cn.bobdeng.base.rbac.role;

import cn.bobdeng.base.role.Role;
import cn.bobdeng.base.role.RoleFunctions;
import cn.bobdeng.base.role.RoleId;
import cn.bobdeng.base.role.RoleName;
import lombok.Data;

import java.util.List;

@Data
public class NewRoleRequest {
    private String name;
    private List<String> functions;

    public Role toEntity() {
        return new Role(new RoleName(name), new RoleFunctions(functions));
    }

    public Role toEntity(int id) {
        return new Role(new RoleId(id), new RoleName(name), new RoleFunctions(functions));
    }
}
