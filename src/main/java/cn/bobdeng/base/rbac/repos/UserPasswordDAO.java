package cn.bobdeng.base.rbac.repos;

import cn.bobdeng.base.user.PasswordDO;
import org.springframework.data.repository.CrudRepository;

public interface UserPasswordDAO extends CrudRepository<PasswordDO, Integer> {
}
