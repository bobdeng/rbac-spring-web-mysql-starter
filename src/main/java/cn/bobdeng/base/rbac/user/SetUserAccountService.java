package cn.bobdeng.base.rbac.user;

import cn.bobdeng.base.user.Account;
import cn.bobdeng.base.user.User;
import cn.bobdeng.base.user.UserId;
import cn.bobdeng.base.user.Users;
import org.springframework.stereotype.Service;

@Service
public class SetUserAccountService {
    private CurrentUser currentUser;

    public SetUserAccountService(CurrentUser currentUser) {
        this.currentUser = currentUser;
    }


    public void execute(UserId userId, Account account) {
        User user = Users.userRepository.findById(currentUser.tenantId(), userId).orElseThrow();
        user.bindAccount(account);
    }
}
