package cn.bobdeng.base.rbac.user;

import cn.bobdeng.base.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVO {
    private String id;
    private String name;

    public UserVO(User user) {
        this.name = user.name();
    }
}
