package cn.bobdeng.base.rbac.repos;

import cn.bobdeng.base.TenantId;
import cn.bobdeng.base.role.Role;
import cn.bobdeng.base.role.RoleDO;
import cn.bobdeng.base.role.RoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.stream.Stream;

@Service
public class RoleRepositoryImpl implements RoleRepository {
    private final RoleDAO roleDAO;

    public RoleRepositoryImpl(RoleDAO roleDAO) {
        this.roleDAO = roleDAO;
    }

    @Override
    public void save(RoleDO roleDO) {
        roleDAO.save(roleDO);
    }

    @Override
    public Stream<Role> findAll(TenantId tenantId) {
        return roleDAO.findAllByTenantId(tenantId.id()).map(Role::new);
    }

    @Override
    public Optional<Role> findById(TenantId tenantId, Integer id) {
        return roleDAO.findByTenantIdAndId(tenantId.id(), id).map(Role::new);
    }
}
