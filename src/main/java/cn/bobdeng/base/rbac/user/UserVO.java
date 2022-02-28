package cn.bobdeng.base.rbac.user;

import cn.bobdeng.base.user.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVO {
    private String id;
    private String name;
    private String status;
    private String level;

    public UserVO(User user) {
        this.id = user.id();
        this.name = user.name();
        this.status = user.statusName();
        this.level = user.levelName();
    }
}
