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

    public SetPasswordController(SetPasswordService setPasswordService) {
        this.setPasswordService = setPasswordService;
    }

    @PutMapping("/rbac/user/{id}/password")
    public void setPassword(@PathVariable int id, @RequestBody SetPasswordForm setPasswordForm) {
        setPasswordService.execute(new UserId(id), new Password(setPasswordForm.getPassword()));
    }
}
