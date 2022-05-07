package cn.bobdeng.base.rbac.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class ListUserController {
    private ListUserService listUserService;

    public ListUserController(ListUserService listUserService) {
        this.listUserService = listUserService;
    }

    @GetMapping("/rbac/users")
    public List<UserVO> listUser(@RequestParam(required = false,name = "keyword") String keyword) {
        return listUserService.listUser(keyword);
    }
}
