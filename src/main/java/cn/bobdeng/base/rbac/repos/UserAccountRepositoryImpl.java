package cn.bobdeng.base.rbac.repos;

import cn.bobdeng.base.TenantId;
import cn.bobdeng.base.user.Account;
import cn.bobdeng.base.user.AccountDO;
import cn.bobdeng.base.user.UserAccountRepository;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAccountRepositoryImpl implements UserAccountRepository {
    private final UserAccountDAO userAccountDAO;

    public UserAccountRepositoryImpl(UserAccountDAO userAccountDAO) {
        this.userAccountDAO = userAccountDAO;
    }

    @Override
    public void save(AccountDO userDO) {
        userAccountDAO.save(userDO);
    }

    @Override
    public Optional<AccountDO> findAccount(TenantId tenantId, Account account) {
        return Optional.empty();
    }
}
