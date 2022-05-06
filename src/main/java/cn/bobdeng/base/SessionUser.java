package cn.bobdeng.base;

import cn.bobdeng.base.user.UserId;
import lombok.Getter;

public class SessionUser {
    @Getter
    private UserId userId;
    @Getter
    private TenantId tenantId;

    public SessionUser(UserId userId, TenantId tenantId) {

        this.userId = userId;
        this.tenantId = tenantId;
    }
}
