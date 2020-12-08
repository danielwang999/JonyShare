-- 所有的sql语句脚本，可以直接在IDEA中执行

-- 课程
drop table if exists course;
create table course (
    id char(8) not null default '' comment 'id',
    name varchar(50) not null comment '名称',
    summary varchar(2000) comment '概述',
    time int default 0 comment '时长|单位秒',
    price decimal(8,2) default 0.00 comment '价格（元）',
    image varchar(100) comment '封面',
    level char(1) comment '级别|枚举[CourseLevelEnum]：ONE("1", "初级"),TWO("2", "中级"),THREE("3", "高级")',
    charge char(1) comment '收费|枚举[CourseChargeEnum]：CHARGE("C", "收费"),FREE("F", "免费")',
    status char(1) comment '状态|枚举[CourseStatusEnum]：PUBLISH("P", "发布"),DRAFT("D", "草稿")',
    enroll integer default 0 comment '报名数',
    sort int comment '顺序',
    created_at datetime(3) comment '创建时间',
    updated_at datetime(3) comment '修改时间',
    primary key (id)
) engine=innodb default charset=utf8mb4 comment='课程';

alter table `course` add column (`teacher_id` char(8) comment '讲师|teacher.id');

insert into course (id, name, summary, time, price, image, level, charge, status, enroll, sort, created_at, updated_at)
values ('00000001', '测试课程01', '这是一门测试课程', 7200, 19.9, '', 1, 'C', 'P', 100, 0, now(), now());


-- 大章
drop table if exists `chapter`;
create table `chapter`
(
    `id`        char(8) not null comment 'id',
    `course_id` char(8) comment '课程id',
    `name`      varchar(50) comment '名称',
    primary key (`id`)
) engine = innodb
  default charset = utf8mb4 comment ='大章';

insert into `chapter` (id, course_id, name)
values ('00000001', '00000000', '测试大章01');
insert into `chapter` (id, course_id, name)
values ('00000002', '00000000', '测试大章02');


-- 小节
drop table if exists `section`;
create table `section` (
    `id` char(8) not null default '' comment 'id',
    `title` varchar(50) not null comment '标题',
    `course_id` char(8) comment '课程|course.id',
    `chapter_id` char(8) comment '大章|chapter.id',
    `video` varchar(200) comment '视频',
    `time` int comment '时长|单位秒',
    `charge` char(1) comment '收费|C 收费；F 免费',
    `sort` int comment '顺序',
    `created_at` datetime(3) comment '创建时间',
    `updated_at` datetime(3) comment '修改时间',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='小节';

insert into `section` (id, title, course_id, chapter_id, video, time, charge, sort, created_at, updated_at)
values ('00000001', '测试小节01', '00000001', '00000000', '', 500, 'f', 1, now(), now());


-- 分类
drop table if exists `category`;
create table `category` (
    `id` char(8) not null default '' comment 'id',
    `parent` char(8) not null default '' comment '父id',
    `name` varchar(50) not null comment '名称',
    `sort` int comment '顺序',
    primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='分类';
-- 一级分类的parent为00000000
insert into `category` (id, parent, name, sort) values ('00000100', '00000000', '前端技术', 100);
insert into `category` (id, parent, name, sort) values ('00000101', '00000100', 'html/css', 101);
insert into `category` (id, parent, name, sort) values ('00000102', '00000100', 'javascript', 102);
insert into `category` (id, parent, name, sort) values ('00000103', '00000100', 'vue.js', 103);
insert into `category` (id, parent, name, sort) values ('00000104', '00000100', 'react.js', 104);
insert into `category` (id, parent, name, sort) values ('00000105', '00000100', 'angular', 105);
insert into `category` (id, parent, name, sort) values ('00000106', '00000100', 'node.js', 106);
insert into `category` (id, parent, name, sort) values ('00000107', '00000100', 'jquery', 107);
insert into `category` (id, parent, name, sort) values ('00000108', '00000100', '小程序', 108);
insert into `category` (id, parent, name, sort) values ('00000200', '00000000', '后端技术', 200);
insert into `category` (id, parent, name, sort) values ('00000201', '00000200', 'java', 201);
insert into `category` (id, parent, name, sort) values ('00000202', '00000200', 'springboot', 202);
insert into `category` (id, parent, name, sort) values ('00000203', '00000200', 'spring cloud', 203);
insert into `category` (id, parent, name, sort) values ('00000204', '00000200', 'ssm', 204);
insert into `category` (id, parent, name, sort) values ('00000205', '00000200', 'python', 205);
insert into `category` (id, parent, name, sort) values ('00000206', '00000200', '爬虫', 206);
insert into `category` (id, parent, name, sort) values ('00000300', '00000000', '移动开发', 300);
insert into `category` (id, parent, name, sort) values ('00000301', '00000300', 'android', 301);
insert into `category` (id, parent, name, sort) values ('00000302', '00000300', 'ios', 302);
insert into `category` (id, parent, name, sort) values ('00000303', '00000300', 'react native', 303);
insert into `category` (id, parent, name, sort) values ('00000304', '00000300', 'ionic', 304);
insert into `category` (id, parent, name, sort) values ('00000400', '00000000', '前沿技术', 400);
insert into `category` (id, parent, name, sort) values ('00000401', '00000400', '微服务', 401);
insert into `category` (id, parent, name, sort) values ('00000402', '00000400', '区块链', 402);
insert into `category` (id, parent, name, sort) values ('00000403', '00000400', '机器学习', 403);
insert into `category` (id, parent, name, sort) values ('00000404', '00000400', '深度学习', 404);
insert into `category` (id, parent, name, sort) values ('00000405', '00000400', '数据分析&挖掘', 405);
insert into `category` (id, parent, name, sort) values ('00000500', '00000000', '云计算&大数据', 500);
insert into `category` (id, parent, name, sort) values ('00000501', '00000500', '大数据', 501);
insert into `category` (id, parent, name, sort) values ('00000502', '00000500', 'hadoop', 502);
insert into `category` (id, parent, name, sort) values ('00000503', '00000500', 'spark', 503);
insert into `category` (id, parent, name, sort) values ('00000504', '00000500', 'hbase', 504);
insert into `category` (id, parent, name, sort) values ('00000505', '00000500', '阿里云', 505);
insert into `category` (id, parent, name, sort) values ('00000506', '00000500', 'docker', 506);
insert into `category` (id, parent, name, sort) values ('00000507', '00000500', 'kubernetes', 507);
insert into `category` (id, parent, name, sort) values ('00000600', '00000000', '运维&测试', 600);
insert into `category` (id, parent, name, sort) values ('00000601', '00000600', '运维', 601);
insert into `category` (id, parent, name, sort) values ('00000602', '00000600', '自动化运维', 602);
insert into `category` (id, parent, name, sort) values ('00000603', '00000600', '中间件', 603);
insert into `category` (id, parent, name, sort) values ('00000604', '00000600', 'linux', 604);
insert into `category` (id, parent, name, sort) values ('00000605', '00000600', '测试', 605);
insert into `category` (id, parent, name, sort) values ('00000606', '00000600', '自动化测试', 606);
insert into `category` (id, parent, name, sort) values ('00000700', '00000000', '数据库', 700);
insert into `category` (id, parent, name, sort) values ('00000701', '00000700', 'mysql', 701);
insert into `category` (id, parent, name, sort) values ('00000702', '00000700', 'redis', 702);
insert into `category` (id, parent, name, sort) values ('00000703', '00000700', 'mongodb', 703);

# 课程分类
drop table if exists `course_category`;
create table `course_category` (
   `id` char(8) not null default '' comment 'id',
   `course_id` char(8) comment '课程|course.id',
   `category_id` char(8) comment '分类|course.id',
   primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='课程分类';

-- 课程内容(大字段拆分为单独的一张表，提高效率，同时方便DBA运维)
drop table if exists `course_content`;
create table `course_content` (
  `id` char(8) not null default '' comment '课程id',
  `content` mediumtext not null comment '课程内容',
  primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='课程内容';

-- 讲师
drop table if exists `teacher`;
create table `teacher` (
   `id` char(8) not null default '' comment 'id',
   `name` varchar(50) not null comment '姓名',
   `nickname` varchar(50) comment '昵称',
   `image` varchar(100) comment '头像',
   `position` varchar(50) comment '职位',
   `motto` varchar(50) comment '座右铭',
   `intro` varchar(500) comment '简介',
   primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='讲师';

-- 文件
drop table if exists `file`;
create table `file` (
  `id` char(8) not null default '' comment 'id',
  `path` varchar(100) not null comment '相对路径',
  `name` varchar(100) comment '文件名',
  `suffix` varchar(10) comment '后缀',
  `size` int comment '大小|字节B',
  `use` char(1) comment '用途|枚举[FileUseEnum]：COURSE("C", "讲师"), TEACHER("T", "课程")',
  `created_at` datetime(3) comment '创建时间',
  `updated_at` datetime(3) comment '修改时间',
  primary key (`id`),
  unique key `path_unique` (`path`)
) engine=innodb default charset=utf8mb4 comment='文件';

-- 课程内容文件
drop table if exists `course_content_file`;
create table `course_content_file` (
   `id` char(8) not null default '' comment 'id',
   `course_id` char(8) not null comment '课程id',
   `url` varchar(100) comment '地址',
   `name` varchar(100) comment '文件名',
   `size` int comment '大小|字节b',
   primary key (`id`)
) engine=innodb default charset=utf8mb4 comment='课程内容文件';






-- 对test表测试的脚本
drop table if exists 'test';
create table 'test'
(
    'id'   varchar(255) not null default '' comment 'id字段',
    'name' varchar(255) comment '名称',
    primary key ('id')
) engine = innodb
  default charset = utf8mb4 comment ='测试表';

insert into test
values ('2', '测试');