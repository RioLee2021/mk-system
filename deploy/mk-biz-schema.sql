set names utf8mb4;
set foreign_key_checks = 0;


-- ----------------------------
-- Table structure for 会员信息表
-- ----------------------------
drop table if exists `mbr_info`;
create table `mbr_info`
(
    id                 int(11)      not null auto_increment,
    create_at          timestamp    null comment '创建时间',
    update_at          timestamp    null comment '最后更新时间',
    create_by          varchar(100) not null comment '创建自',
    disabled           boolean      not null default false comment '删除标识',
    merchant_id        int(11)      not null comment '商户ID',
    login_password     varchar(100) not null comment '会员登录密码',
    withdraw_password  varchar(100) null comment '会员提现密码',
    account            varchar(100) not null comment '会员账号/昵称',
    nickname           varchar(100) not null comment '会员昵称',
    phone              varchar(100) not null comment '会员手机号',
    invite_code        varchar(10)  not null comment '会员邀请码',
    curr_token         varchar(100) null comment '会员当前令牌',
    last_login_at      timestamp    null comment '会员最后登录时间',
    register_ip        varchar(100) not null comment '会员注册IP',
    register_region    varchar(300) not null comment '会员注册IP所属区域',
    login_ip           varchar(100) null comment '会员登录IP',
    login_region       varchar(300) null comment '会员登录IP所属区域',
    status             int(4)       not null comment '会员状态(MbrStatus)',
    parent_id          int(11)      null comment '会员上级ID',
    vip_level          int(4)       not null comment '会员VIP等级(VipLevel)',
    mbr_type           int(4)       not null comment '会员类型(MbrType)',
    virtual_flg        boolean      not null default false comment '会员虚拟会员标识',
    cp_mark            varchar(50)  null comment '会员CP标识',
    relationship_route text         null comment '会员关系路径',
    actual_name        varchar(100) null comment '会员真实姓名',
    bank_name          varchar(100) null comment '会员银行名称',
    bank_card_no       varchar(50)  null comment '会员银行卡号',
    customer_account   varchar(100) null comment '会员客服',
    logo_number        varchar(50)  null comment '会员logo编号',
    daily_order_limit  int(5)       not null default 0 comment '会员每日订单限制',
    primary key (id) using btree,
    unique key uk_account (account) using btree,
    unique key uk_phone (phone) using btree,
    unique key uk_invite_code (invite_code) using btree,
    index idx_merchant_id (merchant_id) using btree,
    index idx_create_at (create_at) using btree,
    index idx_last_login_at (last_login_at) using btree,
    index idx_register_ip (register_ip) using btree,
    index idx_login_ip (login_ip) using btree,
    index idx_status (status) using btree,
    index idx_parent_id (parent_id) using btree,
    index idx_vip_level (vip_level) using btree,
    index idx_mbr_type (mbr_type) using btree,
    index idx_virtual_flg (virtual_flg) using btree,
    index idx_cp_mark (cp_mark) using btree,
    index idx_customer_account (customer_account) using btree
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin
  ROW_FORMAT = DYNAMIC comment '会员基础信息表';

-- ----------------------------
-- Table structure for 会员资产表
-- ----------------------------
drop table if exists `mbr_assets`;
create table `mbr_assets`
(
    id        int(11)        not null auto_increment,
    create_at timestamp      null comment '创建时间',
    update_at timestamp      null comment '最后更新时间',
    create_by varchar(100)   not null comment '创建自',
    disabled  boolean        not null default false comment '删除标识',
    mbr_id    int(11)        not null comment '会员ID',
    balance   decimal(20, 2) not null default 0 comment '会员余额',
    integral  int(11)        not null default 0 comment '会员积分',
    primary key (id) using btree,
    unique key uk_mbr_id (mbr_id) using btree,
    index idx_balance (balance) using btree,
    index idx_integral (integral) using btree
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin
  ROW_FORMAT = DYNAMIC comment '会员资产表';

-- ----------------------------
-- Table structure for 用户充值记录
-- ----------------------------
drop table if exists `mbr_recharge_record`;
create table `mbr_recharge_record`
(
    id        int(11)        not null auto_increment,
    create_at timestamp      null comment '创建时间',
    update_at timestamp      null comment '最后更新时间',
    create_by varchar(100)   not null comment '创建自',
    disabled  boolean        not null default false comment '删除标识',
    rec_no    varchar(100)   not null comment '充值记录编号',
    mbr_id    int(11)        not null comment '会员ID',
    amount    decimal(20, 2) not null comment '充值金额',
    status    int(4)         not null comment '记录状态(RecordStatus)',
    memo      varchar(300)   null comment '充值备注',
    primary key (id) using btree,
    unique key uk_rec_no (rec_no) using btree,
    index idx_mbr_id (mbr_id) using btree,
    index idx_status (status) using btree
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin
  ROW_FORMAT = DYNAMIC comment '用户充值记录';

-- ----------------------------
-- Table structure for 用户提现记录
-- ----------------------------
drop table if exists `mbr_withdraw_record`;
create table `mbr_withdraw_record`
(
    id        int(11)        not null auto_increment,
    create_at timestamp      null comment '创建时间',
    update_at timestamp      null comment '最后更新时间',
    create_by varchar(100)   not null comment '创建自',
    disabled  boolean        not null default false comment '删除标识',
    rec_no    varchar(100)   not null comment '提现记录编号',
    mbr_id    int(11)        not null comment '会员ID',
    amount    decimal(20, 2) not null comment '提现金额',
    status    int(4)         not null comment '记录状态(RecordStatus)',
    memo      varchar(300)   null comment '提现备注',
    primary key (id) using btree,
    unique key uk_rec_no (rec_no) using btree,
    index idx_mbr_id (mbr_id) using btree,
    index idx_status (status) using btree
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin
  ROW_FORMAT = DYNAMIC comment '用户提现记录';

-- ----------------------------
-- Table structure for 会员资产流水
-- ----------------------------
drop table if exists `mbr_assets_flw`;
create table `mbr_assets_flw`
(
    id         int(11)        not null auto_increment,
    create_at  timestamp      null comment '创建时间',
    create_by  varchar(100)   not null comment '创建自',
    flw_no     varchar(100)   not null comment '流水编号',
    mbr_id     int(11)        not null comment '会员ID',
    amount     decimal(20, 2) not null comment '金额',
    type       int(4)         not null comment '流水类型(AssetsFlwType)',
    before_amt decimal(20, 2) not null comment '流水前金额',
    after_amt  decimal(20, 2) not null comment '流水后金额',
    remark     varchar(300)   null comment '流水备注',
    related_no varchar(100)   null comment '关联编号',
    related_id int(11)        null comment '关联ID',
    primary key (id) using btree,
    unique key uk_flw_no (flw_no) using btree,
    index idx_mbr_id (mbr_id) using btree,
    index idx_amount (amount) using btree,
    index idx_type (type) using btree,
    index idx_related_id (related_id) using btree,
    index idx_related_no (related_no) using btree
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin
  ROW_FORMAT = DYNAMIC comment '会员资金流水';

-- ----------------------------
-- Table structure for 公告记录
-- ----------------------------
drop table if exists `notice_record`;
create table `notice_record`
(
    id        int(11)      not null auto_increment,
    create_at timestamp    null comment '创建时间',
    update_at timestamp    null comment '最后更新时间',
    create_by varchar(100) not null comment '创建自',
    disabled  boolean      not null default false comment '删除标识',
    mbr_id    int(11)      not null comment '会员ID',
    lang_type int(4)       not null comment '语言类型(LangType)',
    rich_txt  longtext     not null comment '富文本内容',
    primary key (id) using btree,
    index idx_mbr_id (mbr_id) using btree,
    index idx_lang_type (lang_type) using btree
) ENGINE = InnoDB
  CHARACTER SET = utf8mb4
  COLLATE = utf8mb4_bin
  ROW_FORMAT = DYNAMIC comment '公告管理';

set foreign_key_checks = 1;