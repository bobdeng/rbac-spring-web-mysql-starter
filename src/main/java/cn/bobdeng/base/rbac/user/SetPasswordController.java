package cn.bobdeng.base.rbac.user;

import cn.bobdeng.base.user.Password;
import cn.bobdeng.base.user.UserId;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SetPasswordController {
    private final SetPasswordService setPasswordService;
    private final CurrentUser currentUser;

    public SetPasswordController(SetPasswordService setPasswordService, CurrentUser currentUser) {
        this.setPasswordService = setPasswordService;
        this.currentUser = currentUser;
    }

    @PutMapping("/rbac/user/{id}/password")
    @Permission("rbac.user.set_password")
    public void setPassword(@PathVariable int id, @RequestBody SetPasswordForm setPasswordForm) {
        setPasswordService.execute(new UserId(id), new Password(setPasswordForm.getPassword()));
    }

    @PutMapping("/rbac/password")
    public void setMyPassword(@RequestBody SetPasswordForm setPasswordForm) {
        setPasswordService.execute(currentUser.userId(), new Password(setPasswordForm.getPassword()));
    }
}
