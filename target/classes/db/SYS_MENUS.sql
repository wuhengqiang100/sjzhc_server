/*
 Navicat Premium Data Transfer

 Source Server         : 石家庄核查
 Source Server Type    : Oracle
 Source Server Version : 110200
 Source Host           : 192.168.137.200:1521
 Source Schema         : SJZHC

 Target Server Type    : Oracle
 Target Server Version : 110200
 File Encoding         : 65001

 Date: 12/03/2020 14:11:49
*/


-- ----------------------------
-- Table structure for SYS_MENUS
-- ----------------------------
DROP TABLE "SJZHC"."SYS_MENUS";
CREATE TABLE "SJZHC"."SYS_MENUS" (
  "ID" NUMBER(36,0) DEFAULT NULL NOT NULL,
  "PARENT_ID" NUMBER(36,0),
  "PATH" VARCHAR2(200 BYTE),
  "COMPONENT" VARCHAR2(2000 CHAR) DEFAULT NULL NOT NULL,
  "REDIRECT" VARCHAR2(2000 BYTE),
  "META_ID" NUMBER(36,0),
  "CHILDREN_IDS" VARCHAR2(200 BYTE),
  "NAME" VARCHAR2(50 BYTE),
  "HIDDEN" NUMBER(1,0) DEFAULT NULL,
  "ALWAYS_SHOW" NUMBER(1,0) DEFAULT NULL
)
TABLESPACE "HC_TABLESPACE"
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SJZHC"."SYS_MENUS"."ID" IS '主键id';
COMMENT ON COLUMN "SJZHC"."SYS_MENUS"."PARENT_ID" IS '父id';
COMMENT ON COLUMN "SJZHC"."SYS_MENUS"."PATH" IS '请求路径';
COMMENT ON COLUMN "SJZHC"."SYS_MENUS"."COMPONENT" IS '请求的组件';
COMMENT ON COLUMN "SJZHC"."SYS_MENUS"."REDIRECT" IS '重定向';
COMMENT ON COLUMN "SJZHC"."SYS_MENUS"."META_ID" IS '路由描述外键';
COMMENT ON COLUMN "SJZHC"."SYS_MENUS"."CHILDREN_IDS" IS '子类ids,以,逗号隔开';

-- ----------------------------
-- Records of SYS_MENUS
-- ----------------------------
INSERT INTO "SJZHC"."SYS_MENUS" VALUES ('1', NULL, '/base', 'Layout', 'noRedirect', '1', '2,3,4', 'base', NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS" VALUES ('2', '1', 'product', 'product', NULL, '2', NULL, 'product', NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS" VALUES ('3', '1', 'operation', 'operation', NULL, '3', NULL, 'operation', NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS" VALUES ('4', '1', 'machine', 'machine', NULL, '4', NULL, 'machine', NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS" VALUES ('5', NULL, '/log', 'Layout', 'noRedirect', '5', '6,7,8,9', 'log', NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS" VALUES ('6', '5', 'dataupLog', 'dataupLog', NULL, '6', NULL, 'dataupLog', NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS" VALUES ('7', '5', 'operationLog', 'operationLog', NULL, '7', NULL, 'operationLog', NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS" VALUES ('8', '5', 'machineLog', 'machineLog', NULL, '8', NULL, 'machineLog', NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS" VALUES ('9', '5', 'produceLog', 'produceLog', NULL, '9', NULL, 'produceLog', NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS" VALUES ('10', NULL, '/machine', 'Layout', 'noRedirect', '10', '11,12', 'machine', NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS" VALUES ('11', '10', 'check', 'check', NULL, '11', NULL, 'check', NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS" VALUES ('12', '10', 'template', 'template', NULL, '12', NULL, 'template', NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS" VALUES ('13', NULL, '/system', 'Layout', 'noRedirect', '13', '14,15,16', 'system', NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS" VALUES ('14', '13', 'menu', 'menu', NULL, '14', NULL, 'menu', NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS" VALUES ('15', '13', 'role', 'role', NULL, '15', NULL, 'role', NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS" VALUES ('16', '13', 'user', 'user', NULL, '16', NULL, 'user', NULL, NULL);

-- ----------------------------
-- Table structure for SYS_MENUS_META
-- ----------------------------
DROP TABLE "SJZHC"."SYS_MENUS_META";
CREATE TABLE "SJZHC"."SYS_MENUS_META" (
  "ID" NUMBER(36,0) DEFAULT NULL NOT NULL,
  "TITLE" VARCHAR2(200 BYTE),
  "ROLES" VARCHAR2(300 BYTE),
  "ICON" VARCHAR2(100 BYTE),
  "NO_CACHE" NUMBER(4,0) DEFAULT NULL,
  "BREADCRUMB" NUMBER(4,0) DEFAULT NULL
)
TABLESPACE "HC_TABLESPACE"
LOGGING
NOCOMPRESS
PCTFREE 10
INITRANS 1
STORAGE (
  INITIAL 65536 
  NEXT 1048576 
  MINEXTENTS 1
  MAXEXTENTS 2147483645
  BUFFER_POOL DEFAULT
)
PARALLEL 1
NOCACHE
DISABLE ROW MOVEMENT
;
COMMENT ON COLUMN "SJZHC"."SYS_MENUS_META"."ID" IS '主键id';
COMMENT ON COLUMN "SJZHC"."SYS_MENUS_META"."TITLE" IS '设置该路由在侧边栏和面包屑中展示的名字';
COMMENT ON COLUMN "SJZHC"."SYS_MENUS_META"."ROLES" IS '设置该路由进入的权限，支持多个权限叠加';
COMMENT ON COLUMN "SJZHC"."SYS_MENUS_META"."ICON" IS '设置该路由的图标';
COMMENT ON COLUMN "SJZHC"."SYS_MENUS_META"."NO_CACHE" IS '如果设置为true，则不会被 <keep-alive> 缓存(默认 false)';
COMMENT ON COLUMN "SJZHC"."SYS_MENUS_META"."BREADCRUMB" IS '如果设置为false，则不会在breadcrumb面包屑中显示';

-- ----------------------------
-- Records of SYS_MENUS_META
-- ----------------------------
INSERT INTO "SJZHC"."SYS_MENUS_META" VALUES ('1', '基础信息配置', NULL, 'table', NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS_META" VALUES ('2', '产品信息管理', NULL, NULL, NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS_META" VALUES ('3', '工序信息管理', NULL, NULL, NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS_META" VALUES ('4', '设备信息管理', NULL, NULL, NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS_META" VALUES ('5', '核查信息管理', NULL, 'component', NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS_META" VALUES ('6', '上传日志查询', NULL, NULL, NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS_META" VALUES ('7', '操作日志查询', NULL, NULL, NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS_META" VALUES ('8', '设备日志查询', NULL, NULL, NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS_META" VALUES ('9', '生产日志查询', NULL, NULL, NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS_META" VALUES ('10', '机检信息管理', NULL, 'guide', NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS_META" VALUES ('11', '机检信息审核', NULL, NULL, NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS_META" VALUES ('12', '机检模板管理', NULL, NULL, NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS_META" VALUES ('13', '系统管理', NULL, 'documentation', NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS_META" VALUES ('14', '菜单管理', NULL, NULL, NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS_META" VALUES ('15', '角色管理', NULL, NULL, NULL, NULL);
INSERT INTO "SJZHC"."SYS_MENUS_META" VALUES ('16', '用户管理', NULL, NULL, NULL, NULL);

-- ----------------------------
-- Primary Key structure for table SYS_MENUS
-- ----------------------------
ALTER TABLE "SJZHC"."SYS_MENUS" ADD CONSTRAINT "PK_SYS_MENUS" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_MENUS
-- ----------------------------
ALTER TABLE "SJZHC"."SYS_MENUS" ADD CONSTRAINT "SYS_C0012414" CHECK ("COMPONENT" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Triggers structure for table SYS_MENUS
-- ----------------------------
CREATE TRIGGER "SJZHC"."BEFER_MENU_ID_copy1" BEFORE INSERT ON "SJZHC"."SYS_MENUS" REFERENCING OLD AS "OLD" NEW AS "NEW" FOR EACH ROW DISABLE 
declare
  -- local variables here
begin
  select SQ_SYS_MENU.nextval into :new.ID from dual;
end BEFER_MENU_ID;
/

-- ----------------------------
-- Primary Key structure for table SYS_MENUS_META
-- ----------------------------
ALTER TABLE "SJZHC"."SYS_MENUS_META" ADD CONSTRAINT "SYS_C0012428" PRIMARY KEY ("ID");

-- ----------------------------
-- Checks structure for table SYS_MENUS_META
-- ----------------------------
ALTER TABLE "SJZHC"."SYS_MENUS_META" ADD CONSTRAINT "SYS_C0012419" CHECK ("ID" IS NOT NULL) NOT DEFERRABLE INITIALLY IMMEDIATE NORELY VALIDATE;

-- ----------------------------
-- Triggers structure for table SYS_MENUS_META
-- ----------------------------
CREATE TRIGGER "SJZHC"."BEFER_MENU_ID_copy1_copy1" BEFORE INSERT ON "SJZHC"."SYS_MENUS_META" REFERENCING OLD AS "OLD" NEW AS "NEW" FOR EACH ROW DISABLE 
declare
  -- local variables here
begin
  select SQ_SYS_MENU.nextval into :new.ID from dual;
end BEFER_MENU_ID;
/
