package cn.bobdeng.base.rbac.repos;

import cn.bobdeng.base.user.PasswordDO;
import cn.bobdeng.base.user.PasswordEncoder;
import cn.bobdeng.base.user.PasswordRepository;
import cn.bobdeng.base.user.User;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserPasswordRepositoryImpl implements PasswordRepository {
    private final UserPasswordDAO userPasswordDAO;
    private final PasswordEncoder passwordEncoder;

    public UserPasswordRepositoryImpl(UserPasswordDAO userPasswordDAO, PasswordEncoder passwordEncoder) {
        this.userPasswordDAO = userPasswordDAO;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public void save(PasswordDO passwordDO) {
        userPasswordDAO.save(passwordDO);
    }

    @Override
    public Optional<PasswordDO> findByUser(User user) {
        return userPasswordDAO.findById(user.id());
    }

    @Override
    public PasswordEncoder passwordEncoder() {
        return passwordEncoder;
    }
}
