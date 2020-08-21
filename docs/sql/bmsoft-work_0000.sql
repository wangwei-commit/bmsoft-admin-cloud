-- ----------------------------
-- Table structure for certificate
-- ----------------------------
DROP TABLE IF EXISTS `certificate`;
CREATE TABLE `certificate`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `description` varchar(128) NULL DEFAULT NULL COMMENT '描述',
  `org_id` bigint(20) NOT NULL COMMENT '组织ID\r\n@InjectionField(api = ORG_ID_FEIGN_CLASS, method = ORG_ID_NAME_METHOD) RemoteData<Long, String>',
  `type` varchar(32) NOT NULL COMMENT '类型',
  `type_details` json NOT NULL COMMENT '类型详细',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UN_NAME_KEY`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '凭证' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inventory
-- ----------------------------
DROP TABLE IF EXISTS `inventory`;
CREATE TABLE `inventory`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `type` varchar(10) NOT NULL COMMENT '类型\r\n#InventoryType{STANDARD:标准清单;SMART:智能清单}',
  `description` varchar(128) NULL DEFAULT NULL COMMENT '描述',
  `syn_status` varchar(10) NOT NULL COMMENT '同步状态\r\n#SynStatus{NORMAL:正常;FAILURE:失败;UNKNOWN:未知}',
  `org_id` bigint(20) NOT NULL COMMENT '组织ID\r\n@InjectionField(api = ORG_ID_FEIGN_CLASS, method = ORG_ID_NAME_METHOD) RemoteData<Long, String>',
  `certificate_ids` json NULL DEFAULT NULL COMMENT '凭证ID\r\n@InjectionField(api = CERTIFICATE_ID_CLASS, method = CERTIFICATE_ID_NAME_METHOD) RemoteData<Long, String>',
  `variable_type` varchar(10) NULL DEFAULT NULL COMMENT '变量类型\r\n#VariableType{YAML:yaml;JSON:json}',
  `variable_value` text NULL COMMENT '变量值',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UN_NAME_KEY`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '清单' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inventory_group
-- ----------------------------
DROP TABLE IF EXISTS `inventory_group`;
CREATE TABLE `inventory_group`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `inventory_id` bigint(20) NOT NULL COMMENT '清单ID\r\n@InjectionField(api = INVENTORY_ID_CLASS, method = INVENTORY_ID_NAME_METHOD) RemoteData<Long, String>',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `description` varchar(128) NULL DEFAULT NULL COMMENT '描述',
  `variable_type` varchar(10) NULL DEFAULT NULL COMMENT '变量类型\r\n#VariableType{YAML:yaml;JSON:json}',
  `variable_value` text NULL COMMENT '变量值',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UN_NAME_KEY`(`inventory_id`, `name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '清单组' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inventory_group_host
-- ----------------------------
DROP TABLE IF EXISTS `inventory_group_host`;
CREATE TABLE `inventory_group_host`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `group_id` bigint(20) NOT NULL COMMENT '组ID',
  `host_id` bigint(20) NOT NULL COMMENT '主机ID',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UN_GROUP_HOST_KEY`(`group_id`, `host_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '清单组与主机关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inventory_group_parent
-- ----------------------------
DROP TABLE IF EXISTS `inventory_group_parent`;
CREATE TABLE `inventory_group_parent`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `from_id` bigint(20) NOT NULL COMMENT '子点',
  `to_id` bigint(20) NOT NULL COMMENT '父点',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UN_FROM_TO_KEY`(`from_id`, `to_id`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '清单组父子关系' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inventory_host
-- ----------------------------
DROP TABLE IF EXISTS `inventory_host`;
CREATE TABLE `inventory_host`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `inventory_id` bigint(20) NOT NULL COMMENT '清单ID\r\n@InjectionField(api = INVENTORY_ID_CLASS, method = INVENTORY_ID_NAME_METHOD) RemoteData<Long, String>',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `description` varchar(128) NULL DEFAULT NULL COMMENT '描述',
  `variable_type` varchar(10) NULL DEFAULT NULL COMMENT '变量类型\r\n#VariableType{YAML:yaml;JSON:json}',
  `variable_value` text NULL COMMENT '变量值',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UN_NAME_KEY`(`inventory_id`, `name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '清单主机' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inventory_script
-- ----------------------------
DROP TABLE IF EXISTS `inventory_script`;
CREATE TABLE `inventory_script`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `description` varchar(128) NULL DEFAULT NULL COMMENT '描述',
  `org_id` bigint(20) NOT NULL COMMENT '组织ID\r\n@InjectionField(api = ORG_ID_FEIGN_CLASS, method = ORG_ID_NAME_METHOD) RemoteData<Long, String>',
  `script` text NOT NULL COMMENT '脚本',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UN_NAME_KEY`(`name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '清单脚本' ROW_FORMAT = Dynamic;

-- ----------------------------
-- Table structure for inventory_source
-- ----------------------------
DROP TABLE IF EXISTS `inventory_source`;
CREATE TABLE `inventory_source`  (
  `id` bigint(20) NOT NULL COMMENT '主键',
  `inventory_id` bigint(20) NOT NULL COMMENT '清单ID\r\n@InjectionField(api = INVENTORY_ID_CLASS, method = INVENTORY_ID_NAME_METHOD) RemoteData<Long, String>',
  `name` varchar(32) NOT NULL COMMENT '名称',
  `description` varchar(128) NULL DEFAULT NULL COMMENT '描述',
  `source` varchar(32) NOT NULL COMMENT '来源',
  `source_details` json NOT NULL COMMENT '来源详细',
  `create_time` datetime(0) NULL DEFAULT NULL COMMENT '创建时间',
  `create_user` bigint(20) NULL DEFAULT NULL COMMENT '创建人ID',
  `update_time` datetime(0) NULL DEFAULT NULL COMMENT '修改时间',
  `update_user` bigint(20) NULL DEFAULT NULL COMMENT '修改人ID',
  PRIMARY KEY (`id`) USING BTREE,
  UNIQUE INDEX `UN_NAME_KEY`(`inventory_id`, `name`) USING BTREE
) ENGINE = InnoDB CHARACTER SET = utf8mb4 COLLATE = utf8mb4_general_ci COMMENT = '清单源' ROW_FORMAT = Dynamic;