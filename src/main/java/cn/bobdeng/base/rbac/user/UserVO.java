package cn.bobdeng.base.rbac.user;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserVO {
    private int id;
    private String name;
    private String account;

}
