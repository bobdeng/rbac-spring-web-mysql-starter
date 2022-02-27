package cn.bobdeng.base.rbac;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ListUserController {
    private ListUserService listUserService;

    public ListUserController(ListUserService listUserService) {
        this.listUserService = listUserService;
    }

    @GetMapping("/rbac/users")
    public List<UserVO> listUser() {
        return listUserService.listUser();
    }
}
