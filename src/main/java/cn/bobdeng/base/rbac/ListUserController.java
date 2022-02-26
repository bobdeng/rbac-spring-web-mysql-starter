package cn.bobdeng.base.rbac;

import cn.bobdeng.base.rbac.permission.SessionUser;
import cn.bobdeng.base.user.Users;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

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
