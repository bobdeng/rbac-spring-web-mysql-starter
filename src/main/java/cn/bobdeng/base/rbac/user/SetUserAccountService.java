package cn.bobdeng.base.rbac.user;

import cn.bobdeng.base.TenantId;
import cn.bobdeng.base.user.*;
import org.springframework.stereotype.Service;

@Service
public class SetUserAccountService {
    private CurrentUser currentUser;
    private final UserRepository userRepository;
    private final UserAccountRepository userAccountRepository;
    public SetUserAccountService(CurrentUser currentUser, UserRepository userRepository, UserAccountRepository userAccountRepository) {
        this.currentUser = currentUser;
        this.userRepository = userRepository;
        this.userAccountRepository = userAccountRepository;
    }

    @Permission(value = "rbac.user.set_account")
    public void execute(UserId userId, Account account) {
        User user = userRepository.findById(userId, currentUser.tenantId()).orElseThrow();
        user.bindAccount(new Account(account.name()), userAccountRepository);
    }
}
