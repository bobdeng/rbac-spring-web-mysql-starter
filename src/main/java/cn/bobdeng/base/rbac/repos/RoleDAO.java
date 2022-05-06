package cn.bobdeng.base.rbac.repos;

import cn.bobdeng.base.role.RoleDO;
import org.springframework.data.repository.CrudRepository;

import java.util.stream.Stream;

public interface RoleDAO extends CrudRepository<RoleDO, Integer> {
    Stream<RoleDO> findAllByTenantId(String tenantId);
}
