CREATE TABLE `rbac_user`
(
    `id`        int(11) NOT NULL AUTO_INCREMENT,
    `tenant_id` varchar(36) DEFAULT NULL,
    `name`      varchar(50),
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `rbac_role`
(
    `id`        int(11) NOT NULL AUTO_INCREMENT,
    `tenant_id` varchar(36) DEFAULT NULL,
    `name`      varchar(20)   NOT NULL,
    `functions` varchar(2000) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `rbac_user_role`
(
    `id`      int(11) NOT NULL,
    `roles`   varchar(500) NOT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `rbac_password`
(
    `id`       int(11) NOT NULL,
    `password` varchar(100) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;

CREATE TABLE `rbac_account`
(
    `id`      int(11) NOT NULL,
    `name` varchar(20) DEFAULT NULL,
    `tenant_id` varchar(36) DEFAULT NULL,
    PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8mb4;
