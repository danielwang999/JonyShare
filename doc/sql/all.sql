-- 所有的sql语句脚本，可以直接在IDEA中执行

-- 对test表测试的脚本
drop table if exists 'test';
create table 'test' (
    'id' varchar(255) not null default '' comment 'id字段',
    'name' varchar(255) comment '名称',
    primary key ('id')
) engine=innodb default charset=utf8mb4 comment='测试表';

insert into test values ('2', '测试');