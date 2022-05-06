package cn.bobdeng.base.rbac.user;

import cn.bobdeng.base.user.Account;
import cn.bobdeng.base.user.UserId;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SetUserAccountController {
    private final SetUserAccountService setUserAccountService;

    public SetUserAccountController(SetUserAccountService setUserAccountService) {
        this.setUserAccountService = setUserAccountService;
    }

    @PutMapping("/rbac/user/{id}/account")
    public void setUserAccount(@PathVariable int id, @RequestBody SetUserAccountForm form) {
        Account account = new Account(form.getAccount());
        setUserAccountService.execute(new UserId(id), account);
    }
}
