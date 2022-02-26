package cn.bobdeng.base.rbac;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.List;

@RestController
public class ListUserController {
    @GetMapping("/rbac/users")
    public List listUser() {
        return Collections.emptyList();
    }
}
