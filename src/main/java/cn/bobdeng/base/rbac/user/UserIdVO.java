package cn.bobdeng.base.rbac.user;

import lombok.Data;

@Data
public class UserIdVO {
    private int id;

    public UserIdVO(int id) {
        this.id = id;
    }
}
