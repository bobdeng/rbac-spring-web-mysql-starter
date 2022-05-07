package cn.bobdeng.base.rbac.user;

import cn.bobdeng.base.user.UserAlreadyExistException;
import cn.bobdeng.base.user.UserName;
import org.apache.tomcat.util.http.ResponseUtil;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;

@RestController
public class NewUserController {
    private final NewUserService newUserService;

    public NewUserController(NewUserService newUserService) {
        this.newUserService = newUserService;
    }

    @PostMapping("/rbac/users")
    public UserIdVO newUser(@RequestBody NewUserForm form) throws UserAlreadyExistException {
        return newUserService.execute(form.getName());
    }

    @ExceptionHandler(UserAlreadyExistException.class)
    public String onUserAlreadyExistException(UserAlreadyExistException e, HttpServletResponse response) {
        response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        return "用户名已存在：" + e.getName();
    }

}
