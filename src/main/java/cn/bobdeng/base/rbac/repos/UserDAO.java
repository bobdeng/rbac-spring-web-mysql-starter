package cn.bobdeng.base.rbac.repos;

import cn.bobdeng.base.user.UserDO;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.stream.Stream;

public interface UserDAO extends CrudRepository<UserDO, Integer> {
    Optional<UserDO> findAllByTenantIdAndName(String tenantId, String name);

    Stream<UserDO> findAllByTenantId(String tenantId);
}
