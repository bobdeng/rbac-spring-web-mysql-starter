package cn.bobdeng.base.rbac.user;

import cn.bobdeng.base.TenantId;
import cn.bobdeng.base.user.*;
import org.springframework.stereotype.Service;

@Service
public class SetPasswordService {
    private final CurrentUser currentUser;
    private final UserRepository userRepository;
    private final PasswordRepository passwordRepository;

    public SetPasswordService(CurrentUser currentUser, UserRepository userRepository, PasswordRepository passwordRepository) {
        this.currentUser = currentUser;
        this.userRepository = userRepository;
        this.passwordRepository = passwordRepository;
    }

    public void execute(UserId userId, Password password) {
        userRepository.findById(userId, currentUser.tenantId())
                .ifPresent(user -> user.setPassword(password, passwordRepository));
    }

}
