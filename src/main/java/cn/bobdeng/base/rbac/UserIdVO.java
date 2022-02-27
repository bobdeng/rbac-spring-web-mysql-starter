package cn.bobdeng.base.rbac;

import lombok.Data;

@Data
public class UserIdVO {
    private String id;

    public UserIdVO(String id) {
        this.id = id;
    }
}
