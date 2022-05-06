package cn.bobdeng.base.rbac.repos;

import cn.bobdeng.base.role.RoleRepository;
import cn.bobdeng.base.user.UserRoleDO;
import cn.bobdeng.base.user.UserRoleRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRoleRepositoryImpl implements UserRoleRepository {
    private final UserRoleDAO userRoleDAO;
    private final RoleRepository roleRepository;

    public UserRoleRepositoryImpl(UserRoleDAO userRoleDAO, RoleRepository roleRepository) {
        this.userRoleDAO = userRoleDAO;
        this.roleRepository = roleRepository;
    }

    @Override
    public void save(UserRoleDO userRoleDO) {
        userRoleDAO.save(userRoleDO);
    }

    @Override
    public Optional<UserRoleDO> findById(Integer id) {
        return userRoleDAO.findById(id);
    }

    @Override
    public RoleRepository roleRepository() {
        return roleRepository;
    }
}
