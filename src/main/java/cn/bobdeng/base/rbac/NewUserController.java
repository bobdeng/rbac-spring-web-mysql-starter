package cn.bobdeng.base.rbac;

import cn.bobdeng.base.user.UserName;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NewUserController {
    private final NewUserService newUserService;

    public NewUserController(NewUserService newUserService) {
        this.newUserService = newUserService;
    }

    @PostMapping("/rbac/users")
    public UserIdVO newUser(@RequestBody NewUserForm form) {
        UserName name = new UserName(form.getName());
        return newUserService.execute(name);
    }
}
