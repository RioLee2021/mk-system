set names utf8mb4;
set foreign_key_checks = 0;

-- ----------------------------
-- Table structure for 动态表
-- ----------------------------
drop table if exists `dynamic_tb`;
create table `dynamic_tb`
(
    id          int(11) not null auto_increment,
    merchant_id int(11) not null comment '商户id',
    primary key (id) using btree
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin
  ROW_FORMAT = DYNAMIC comment '动态表';

-- ----------------------------
-- Table structure for 商户配置
-- ----------------------------
drop table if exists `merchant_config`;
create table `merchant_config`
(
    id                int(11)       not null auto_increment,
    create_at         timestamp     null comment '创建时间',
    update_at         timestamp     null comment '最后更新时间',
    create_by         varchar(100)  not null comment '创建自',
    disabled          boolean       not null default false comment '删除标识',
    mch_code          varchar(10)   not null comment '商户编号',
    mch_name          varchar(100)  not null comment '商户名称',
    api_white_list    text          null comment '商户api白名单',
    memo              text          null comment '商户备注',
    primary key (id) using btree,
    unique key uk_mch_code (mch_code) using btree,
    index idx_mch_name (mch_name) using btree
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin
  ROW_FORMAT = DYNAMIC comment '商户配置';


-- ----------------------------
-- Table structure for 管理员表
-- ----------------------------
drop table if exists `perm_user`;
create table `perm_user`
(
    id          int(11)      not null auto_increment,
    create_at   timestamp    null comment '创建时间',
    update_at   timestamp    null comment '最后更新时间',
    create_by   varchar(100) not null comment '创建自',
    disabled    boolean      not null default false comment '删除标识',
    merchant_id int(11)      not null comment '商户ID',
    account     varchar(100) not null comment '账号',
    password    varchar(100) not null comment '密码',
    role_type   int(4)       not null comment '角色类型(RoleType)',
    token       varchar(100) null comment '登录token',
    otp_code    varchar(100) null comment 'otp验证码',
    primary key (id) using btree,
    unique key uk_merchant_account (account, merchant_id) using btree,
    index idx_merchant_id (merchant_id) using btree,
    index idx_account (account) using btree,
    index idx_role_type (role_type) using btree
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin
  ROW_FORMAT = DYNAMIC comment '管理员表';

-- ----------------------------
-- Table structure for 管理员登录日志
-- ----------------------------
drop table if exists `perm_user_login_log`;
create table `perm_user_login_log`
(
    id           int(11)      not null auto_increment,
    create_at    timestamp    null comment '创建时间',
    perm_user_id int(11)      not null comment '用户ID',
    from_ip      varchar(100) not null comment '登录IP',
    from_region  varchar(200) not null comment '登录地区',
    from_ua      varchar(500) not null comment '登录UA',
    primary key (id) using btree,
    index idx_perm_user_id (perm_user_id) using btree,
    index idx_from_ip (from_ip) using btree,
    index idx_create_at (create_at) using btree
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin
  ROW_FORMAT = DYNAMIC comment '管理员登录日志';

-- ----------------------------
-- Table structure for 管理员操作日志
-- ----------------------------
drop table if exists `perm_user_operation_log`;
create table `perm_user_operation_log`
(
    id             int(11)      not null auto_increment,
    create_at      timestamp    null comment '创建时间',
    perm_user_id   int(11)      not null comment '用户ID',
    from_ip        varchar(100) not null comment '操作IP',
    from_region    varchar(200) not null comment '操作地区',
    request_uri    varchar(400) not null comment '请求URI',
    action         varchar(100) not null comment '操作动作',
    action_content text         not null comment '操作内容',
    related_no     varchar(100) null comment '关联业务编号',
    primary key (id) using btree,
    index idx_perm_user_id (perm_user_id) using btree,
    index idx_from_ip (from_ip) using btree,
    index idx_create_at (create_at) using btree,
    index idx_action (action) using btree,
    index idx_request_uri (request_uri) using btree,
    index idx_related_no (related_no) using btree
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin
  ROW_FORMAT = DYNAMIC comment '管理员操作日志';

-- ----------------------------
-- Table structure for 菜单
-- ----------------------------
drop table if exists `perm_menu`;
create table `perm_menu`
(
    id              int(11)      not null auto_increment,
    create_at       timestamp    null comment '创建时间',
    update_at       timestamp    null comment '最后更新时间',
    create_by       varchar(100) not null comment '创建自',
    disabled        boolean      not null default false comment '删除标识',
    perm_menu_group int(4)       not null comment '菜单组(PermMenuGroup)',
    menu_name       varchar(100) not null comment '菜单名称',
    menu_path       varchar(100) not null comment '菜单URL路径',
    icon            varchar(100) not null comment '菜单图标',
    sort_no         int(11)      not null comment '排序号',
    menu_scope      int(4)       not null comment '菜单作用域(MenuScope)',
    uri_flag        boolean      not null comment '菜单是否为URI',
    primary key (id) using btree,
    unique key uk_group_menu_path (perm_menu_group, menu_path) using btree,
    index idx_sort_no (sort_no) using btree,
    index idx_menu_scope (menu_scope) using btree
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin
  ROW_FORMAT = DYNAMIC comment '菜单';

-- ----------------------------
-- Table structure for 权限菜单配置
-- ----------------------------
drop table if exists `auth_menu_config`;
create table `auth_menu_config`
(
    id        int(11)      not null auto_increment,
    create_at timestamp    null comment '创建时间',
    update_at timestamp    null comment '最后更新时间',
    create_by varchar(100) not null comment '创建自',
    disabled  boolean      not null default false comment '删除标识',
    role_type int(4)       not null comment '角色类型(RoleType)',
    menu_id   int(11)      not null comment '菜单ID',
    primary key (id) using btree,
    unique key uk_role_menu (role_type, menu_id) using btree,
    index idx_role_type (role_type) using btree
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin
  ROW_FORMAT = DYNAMIC comment '权限菜单配置';





set foreign_key_checks = 1;