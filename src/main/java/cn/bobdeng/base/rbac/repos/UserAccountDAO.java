package cn.bobdeng.base.rbac.repos;

import cn.bobdeng.base.user.AccountDO;
import org.springframework.data.repository.CrudRepository;

public interface UserAccountDAO extends CrudRepository<AccountDO, Integer> {
}
