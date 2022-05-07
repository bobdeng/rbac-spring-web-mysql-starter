package cn.bobdeng.base.rbac.repos;

import cn.bobdeng.base.TenantId;
import cn.bobdeng.base.user.User;
import cn.bobdeng.base.user.UserDO;
import cn.bobdeng.base.user.UserId;
import cn.bobdeng.base.user.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserRepositoryImpl implements UserRepository {
    private final UserDAO userDAO;

    public UserRepositoryImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public UserDO save(UserDO userDO) {
        return userDAO.save(userDO);
    }

    @Override
    public Optional<User> findById(UserId id, TenantId tenantId) {
        return userDAO.findByTenantIdAndId(tenantId.id(), id.id()).map(User::new);
    }

    @Override
    public Optional<User> findByName(TenantId tenantId, String name) {
        return userDAO.findAllByTenantIdAndName(tenantId.id(), name).map(User::new);
    }
}
