CREATE TABLE `xxl_job_namespace`
(
    `id`            int(11) NOT NULL AUTO_INCREMENT,
    `namespace`     varchar(50) NOT NULL COMMENT '命名空间',
    `description` varchar(500) DEFAULT NULL  COMMENT '描述',
    PRIMARY KEY (`id`),
    UNIQUE KEY `namespace_key` (`namespace`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4;

alter table xxl_job_info add column namespace varchar(50) not null comment '命名空间';

alter table xxl_job_info add index(`namespace`);

insert xxl_job_namespace value (1, "default", "默认命名空间");

update xxl_job_info set namespace = "default" where namespace is null or namespace = '';


alter table xxl_job_group add column namespace varchar(50) not null comment '命名空间';

alter table xxl_job_group add index(`namespace`);
update xxl_job_group set namespace = "default" where namespace is null or namespace = '';


alter table xxl_job_log add column namespace varchar(50) not null comment '命名空间';
alter table xxl_job_log add index(`namespace`);
update xxl_job_log set namespace = "default" where namespace is null or namespace = '';