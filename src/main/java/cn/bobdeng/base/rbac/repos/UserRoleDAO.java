package cn.bobdeng.base.rbac.repos;

import cn.bobdeng.base.user.UserRoleDO;
import org.springframework.data.repository.CrudRepository;

public interface UserRoleDAO extends CrudRepository<UserRoleDO, Integer> {
}
