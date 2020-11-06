-- ------------商品表----------------
DROP TABLE IF EXISTS `sm_product`;
CREATE TABLE `sm_product`
(
  `product_id`   bigint(20)    NOT NULL COMMENT '主键id',
  `product_no`   varchar(250)  NOT NULL COMMENT '商品编号',
  `product_name` varchar(1000) NOT NULL COMMENT '商品名',
  `photo`        mediumtext NOT NULL COMMENT '商品名',
  `manufacturer` varchar(2000)  DEFAULT NULL COMMENT '厂家',
  `model`        varchar(1000)  DEFAULT NULL COMMENT '型号',
  `standard`     varchar(4000)  DEFAULT NULL COMMENT '规格',
  `inventory`    bigint(10)     DEFAULT 0 COMMENT '库存（现有量）',
  `buy_price`     decimal(18, 2) DEFAULT 0 COMMENT '购入单价、进货单价',
  `sale_price`    decimal(18, 2) DEFAULT 0 COMMENT '销售单价',
  `location`     varchar(4000)  DEFAULT NULL COMMENT '存放位置',
  `version`      int(11)        DEFAULT NULL COMMENT '乐观锁',
  `created_time` datetime       DEFAULT NULL COMMENT '创建时间',
  `updated_time` datetime       DEFAULT NULL COMMENT '更新时间',
  PRIMARY KEY (`product_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='商品表';

create index IX_SM_PRODUCT_PRODUCT_NO on sm_product (product_no);
create index IX_SM_PRODUCT_PRODUCT_NAME on sm_product (product_name);
-- ----------------------------

-- ------------采购、调入、调出表----------------
DROP TABLE IF EXISTS `sm_purchase`;
CREATE TABLE `sm_purchase`
(
  `purchase_id`  bigint(20) NOT NULL COMMENT '主键id',
  `purchase_no`  varchar(1000) NOT NULL COMMENT '进货单号',
  `total`        decimal(18, 2) DEFAULT NULL COMMENT '合计',
  `purchase_date` date           DEFAULT NULL COMMENT '操作时间',
  `type`         int            DEFAULT 0 COMMENT '操作类型 0:进货 1:调入 2:调出',
  `created_time` datetime       DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`purchase_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='采购、调入、调出表';

create index IX_SM_PURCHASE_OPERATE_DATE on sm_purchase (purchase_date);
-- ----------------------------

-- ------------商品采购记录表----------------
DROP TABLE IF EXISTS `sm_product_purchase`;
CREATE TABLE `sm_product_purchase`
(
  `id`           bigint(20) NOT NULL COMMENT '主键id',
  `purchase_id`      bigint(20) NOT NULL COMMENT '采购ID',
  `purchase_no`      varchar(250) NOT NULL COMMENT '销售编码',
  `product_id`   bigint(20)     NOT NULL COMMENT '产品ID',
  `product_name` varchar(1000)  DEFAULT NULL COMMENT '冗余的产品名称（产品名称+型号）',
  `amount`       int            DEFAULT NULL COMMENT '数量',
  `price`        decimal(18, 2) DEFAULT NULL COMMENT '销售地址',
  `total`        decimal(18, 2) DEFAULT NULL COMMENT '创建时间',
  `created_time` datetime       DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='商品采购记录表';
create index IX_SM_PRODUCT_PURCHASE_PURCHASEID on sm_product_purchase (purchase_id);
create index IX_SM_PRODUCT_PURCHASE_PRODUCT_ID on sm_product_purchase (product_id);
-- ----------------------------

-- ------------销售表----------------
DROP TABLE IF EXISTS `sm_sale`;
CREATE TABLE `sm_sale`
(
  `sale_id`      bigint(20)   NOT NULL COMMENT '主键id',
  `sale_no`      varchar(250) NOT NULL COMMENT '销售编码',
  `sale_total`   decimal(18, 2) DEFAULT NULL COMMENT '销售合计',
  `cost_total`   decimal(18, 2) DEFAULT NULL COMMENT '成本合计',
  `profits`      decimal(18, 2) DEFAULT NULL COMMENT '利润',
  `sell_id`      bigint(20)     DEFAULT NULL COMMENT '销售人员ID',
  `sell_name`    varchar(250)   DEFAULT NULL COMMENT '销售人员名称',
  `address`      varchar(4000)  DEFAULT NULL COMMENT '销售地址',
  `contact_info` varchar(4000)  DEFAULT NULL COMMENT '创建时间',
  `sale_date`    date           DEFAULT NULL COMMENT '销售时间',
  `created_time` datetime       DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`sale_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='销售表';

create index IX_SM_SALE_SALE_NO on sm_sale (sale_no);
-- ----------------------------

-- ------------商品销售记录表----------------
DROP TABLE IF EXISTS `sm_product_sale`;
CREATE TABLE `sm_product_sale`
(
  `id`           bigint(20)   NOT NULL COMMENT '主键id',
  `sale_id`      bigint(20)   NOT NULL COMMENT '销售ID',
  `sale_no`      varchar(250) NOT NULL COMMENT '销售编码',
  `product_id`   bigint(20)     DEFAULT NULL COMMENT '产品ID',
  `product_name` varchar(1000)  DEFAULT NULL COMMENT '冗余的产品名称（产品名称+型号）',
  `amount`       int            DEFAULT NULL COMMENT '数量',
  `sale_price`   decimal(18, 2) DEFAULT NULL COMMENT '销售价格',
  `cost_price`   decimal(18, 2) DEFAULT NULL COMMENT '成本价',
  `sale_total`   decimal(18, 2) DEFAULT NULL COMMENT '销售总价',
  `cost_total`   decimal(18, 2) DEFAULT NULL COMMENT '成本总价',
  `profits`      decimal(18, 2) DEFAULT NULL COMMENT '利润',
  `created_time` datetime       DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='商品销售记录表';
create index IX_SM_PRODUCT_SALE_SALE_ID on sm_product_sale (sale_id);
create index IX_SM_PRODUCT_SALE_PRODUCT_ID on sm_product_sale (product_id);
-- ----------------------------


-- ------------退货表----------------
DROP TABLE IF EXISTS `sm_returned`;
CREATE TABLE `sm_returned`
(
  `returned_id`     bigint(20)    NOT NULL COMMENT '主键id',
  `return_reason`   varchar(4000) NOT NULL COMMENT '退货原因',
  `returned_date`   date           DEFAULT NULL COMMENT '退货时间',
  `sale_id`         bigint(20)     DEFAULT NULL COMMENT '关联销售单',
  `sale_no`         bigint(20)     DEFAULT NULL COMMENT '销售单号',
  `sale_date`       date           DEFAULT NULL COMMENT '销售日期',
  `product_sale_id` bigint(20)     DEFAULT NULL COMMENT '关联商品销售记录',
  `product_id`      bigint(20)     DEFAULT NULL COMMENT '关联商品',
  `amount`          int            DEFAULT NULL COMMENT '数量',
  `price`           decimal(18, 2) DEFAULT NULL COMMENT '单价',
  `total`           decimal(18, 2) DEFAULT NULL COMMENT '合计',
  `created_time`    datetime       DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`returned_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='退货表';
create index IX_SM_RETURNED_SALE_ID on sm_returned (sale_id);
create index IX_SM_RETURNED_SALE_NO on sm_returned (sale_no);
create index IX_SM_RETURNED_PRODUCT_SALE_ID on sm_returned (product_sale_id);
create index IX_SM_RETURNED_PRODUCT_ID on sm_returned (product_id);
-- ----------------------------

-- ------------支出类型表----------------
DROP TABLE IF EXISTS `sm_expenses_type`;
CREATE TABLE `sm_expenses_type`
(
  `type_id`      bigint(20)    NOT NULL COMMENT '主键id',
  `type_name`    varchar(1000) NOT NULL COMMENT '类型名',
  `type_code`    varchar(250)  DEFAULT NULL COMMENT '类型编码',
  `created_time` datetime      DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`type_id`) USING BTREE
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='支出类型表';

create index IX_SM_EXPENSES_TYPE_TYPE_NAME on sm_expenses_type (type_name);
-- ----------------------------


-- ------------支出表----------------
DROP TABLE IF EXISTS `sm_expenses`;
CREATE TABLE `sm_expenses`
(
  `expenses_id`   bigint(9) NOT NULL COMMENT '主键id',
  `type_id`       bigint(20)NOT NULL COMMENT '支出类型ID',
  `amount`        int       NOT NULL COMMENT '数量',
  `price`         decimal(18, 2) DEFAULT NULL COMMENT '单价',
  `total`         decimal(18, 2) DEFAULT NULL COMMENT '合计',
  `expenses_date` date           DEFAULT NULL COMMENT '支出日期',
  `remark`        varchar(4000)  DEFAULT NULL COMMENT '备注',
  `created_time`  datetime       DEFAULT NULL COMMENT '创建时间',
  PRIMARY KEY (`expenses_id`) USING BTREE,
  foreign key (`type_id`) references sm_expenses_type (`type_id`)
) ENGINE = InnoDB
  DEFAULT CHARSET = utf8
  ROW_FORMAT = DYNAMIC COMMENT ='支出表';
create index IX_SM_EXPENSES_TYPE_ID on sm_expenses (type_id);
-- ----------------------------