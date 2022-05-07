package cn.bobdeng.base.rbac.user;

import cn.bobdeng.base.user.*;
import org.springframework.stereotype.Service;

@Service
public class NewUserService {
    private CurrentUser currentUser;
    private final UserRepository userRepository;

    public NewUserService(CurrentUser currentUser, UserRepository userRepository) {
        this.currentUser = currentUser;
        this.userRepository = userRepository;
    }

    @Permission(value = "rbac.user.create")
    public UserIdVO execute(String name) throws UserAlreadyExistException {
        NewUserRequest request = new NewUserRequest(name);
        User user = new Users(currentUser.tenantId()).newUser(request, userRepository);
        return new UserIdVO(user.id());
    }
}
