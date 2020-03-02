-------------------------------------------
-- Export file for user BJAQXHC          --
-- Created by East on 2020-2-18, 9:44:45 --
-------------------------------------------

spool bjaqxhc.log

prompt
prompt Creating table DIC_MACHINES
prompt ===========================
prompt
create table DIC_MACHINES
(
  MACHINE_ID   NUMBER(16) not null,
  MACHINE_CODE VARCHAR2(20) not null,
  MACHINE_NAME VARCHAR2(40) not null,
  MACHINE_IP   VARCHAR2(20),
  USE_FLAG     NUMBER default 0 not null,
  START_DATE   DATE,
  END_DATE     DATE,
  NOTE         VARCHAR2(128)
)
;
comment on table DIC_MACHINES
  is '�����豸��';
comment on column DIC_MACHINES.MACHINE_ID
  is '����';
comment on column DIC_MACHINES.MACHINE_CODE
  is '��������';
comment on column DIC_MACHINES.MACHINE_NAME
  is '��������';
comment on column DIC_MACHINES.MACHINE_IP
  is '����IP';
comment on column DIC_MACHINES.USE_FLAG
  is '����״̬';
comment on column DIC_MACHINES.START_DATE
  is '�������ڣ�һ��Ϊinsert��ʱ��';
comment on column DIC_MACHINES.END_DATE
  is '��������';
comment on column DIC_MACHINES.NOTE
  is '˵��';
alter table DIC_MACHINES
  add constraint PK_DIC_MACHINES primary key (MACHINE_ID);

prompt
prompt Creating table DIC_OPERATION_TYPES
prompt ==================================
prompt
create table DIC_OPERATION_TYPES
(
  OPERATION_TYPE_ID    NUMBER(16) not null,
  OPERATION_TYPE_ICODE VARCHAR2(20) not null,
  OPERATION_TYPE_INAME VARCHAR2(40) not null,
  USE_FLAG             NUMBER default 0 not null,
  START_DATE           DATE default SYSDATE not null,
  END_DATE             DATE,
  NOTE                 VARCHAR2(128)
)
;
comment on table DIC_OPERATION_TYPES
  is '�������ͱ���¼��Ʒ��������й�������';
comment on column DIC_OPERATION_TYPES.OPERATION_TYPE_ID
  is '����';
comment on column DIC_OPERATION_TYPES.OPERATION_TYPE_ICODE
  is '�������ʹ���';
comment on column DIC_OPERATION_TYPES.OPERATION_TYPE_INAME
  is '������������';
comment on column DIC_OPERATION_TYPES.USE_FLAG
  is '����״̬';
comment on column DIC_OPERATION_TYPES.START_DATE
  is '�������ڣ�һ��Ϊinsert��ʱ��';
comment on column DIC_OPERATION_TYPES.END_DATE
  is '��������';
comment on column DIC_OPERATION_TYPES.NOTE
  is '˵��';
alter table DIC_OPERATION_TYPES
  add constraint PK_DIC_OPERATION_TYPES primary key (OPERATION_TYPE_ID);

prompt
prompt Creating table DIC_OPERATIONS
prompt =============================
prompt
create table DIC_OPERATIONS
(
  OPERATION_ID      NUMBER(16) not null,
  OPERATION_TYPE_ID NUMBER(16) not null,
  OPERATION_CODE    VARCHAR2(20) not null,
  OPERATION_NAME    VARCHAR2(40) not null,
  USE_FLAG          NUMBER default 0 not null,
  START_DATE        DATE,
  END_DATE          DATE,
  NOTE              VARCHAR2(128)
)
;
comment on table DIC_OPERATIONS
  is '�����������¼��Ʒ������������й���';
comment on column DIC_OPERATIONS.OPERATION_ID
  is '����';
comment on column DIC_OPERATIONS.OPERATION_TYPE_ID
  is '�������ʹ���';
comment on column DIC_OPERATIONS.OPERATION_CODE
  is '�������';
comment on column DIC_OPERATIONS.OPERATION_NAME
  is '��������';
comment on column DIC_OPERATIONS.USE_FLAG
  is '����״̬';
comment on column DIC_OPERATIONS.START_DATE
  is '�������ڣ�һ��Ϊinsert��ʱ��';
comment on column DIC_OPERATIONS.END_DATE
  is '��������';
comment on column DIC_OPERATIONS.NOTE
  is '˵��';
alter table DIC_OPERATIONS
  add constraint PK_DIC_OPERATIONS primary key (OPERATION_ID);
alter table DIC_OPERATIONS
  add constraint FK1_DIC_OPERATIONS foreign key (OPERATION_TYPE_ID)
  references DIC_OPERATION_TYPES (OPERATION_TYPE_ID);

prompt
prompt Creating table DIC_OPERATORS
prompt ============================
prompt
create table DIC_OPERATORS
(
  OPERATOR_ID   NUMBER(16) not null,
  OPERATOR_CODE VARCHAR2(20) not null,
  OPERATOR_NAME VARCHAR2(40) not null,
  USE_FLAG      NUMBER default 0 not null,
  START_DATE    DATE,
  END_DATE      DATE,
  NOTE          VARCHAR2(128)
)
;
comment on table DIC_OPERATORS
  is 'ϵͳ��Ա�嵥';
comment on column DIC_OPERATORS.OPERATOR_ID
  is '����';
comment on column DIC_OPERATORS.OPERATOR_CODE
  is '��Ա����';
comment on column DIC_OPERATORS.OPERATOR_NAME
  is '��Ա����';
comment on column DIC_OPERATORS.USE_FLAG
  is '����״̬';
comment on column DIC_OPERATORS.START_DATE
  is '�������ڣ�һ��Ϊinsert��ʱ��';
comment on column DIC_OPERATORS.END_DATE
  is '��������';
comment on column DIC_OPERATORS.NOTE
  is '˵��';
alter table DIC_OPERATORS
  add constraint PK_DIC_OPERATORS primary key (OPERATOR_ID);

prompt
prompt Creating table DIC_PRODUCTS
prompt ===========================
prompt
create table DIC_PRODUCTS
(
  PRODUCT_ID   NUMBER(16) not null,
  PRODUCT_CODE VARCHAR2(20) not null,
  PRODUCT_NAME VARCHAR2(40) not null,
  USE_FLAG     NUMBER default 0 not null,
  START_DATE   DATE default SYSDATE not null,
  END_DATE     DATE,
  NOTE         VARCHAR2(128)
)
;
comment on table DIC_PRODUCTS
  is '��Ʒ��Ϣ����Ҫ�洢��Ʒ����';
comment on column DIC_PRODUCTS.PRODUCT_ID
  is '����';
comment on column DIC_PRODUCTS.PRODUCT_CODE
  is '��Ʒ����';
comment on column DIC_PRODUCTS.PRODUCT_NAME
  is '��Ʒ����';
comment on column DIC_PRODUCTS.USE_FLAG
  is '����״̬';
comment on column DIC_PRODUCTS.START_DATE
  is '�������ڣ�һ��Ϊinsert��ʱ��';
comment on column DIC_PRODUCTS.END_DATE
  is '��������';
comment on column DIC_PRODUCTS.NOTE
  is '˵��';
alter table DIC_PRODUCTS
  add constraint PK_DIC_PRODUCTS primary key (PRODUCT_ID);

prompt
prompt Creating table DIC_SYSTEM_SET
prompt =============================
prompt
create table DIC_SYSTEM_SET
(
  FACTORY_ID   NUMBER(4) not null,
  FACTORY_CODE VARCHAR2(40) not null,
  FACTORY_NAME VARCHAR2(40) not null
)
;
comment on table DIC_SYSTEM_SET
  is 'ϵͳ�������ñ�';
comment on column DIC_SYSTEM_SET.FACTORY_ID
  is '��˾ID';
comment on column DIC_SYSTEM_SET.FACTORY_CODE
  is '��˾����';
comment on column DIC_SYSTEM_SET.FACTORY_NAME
  is '��˾����';

prompt
prompt Creating table ROUTE_DEF_TBL
prompt ============================
prompt
create table ROUTE_DEF_TBL
(
  ROUTE_DEF_ID   NUMBER(16) not null,
  ROUTE_DEF_CODE VARCHAR2(20) not null,
  ROUTE_DEF_NAME VARCHAR2(40) not null,
  USE_FLAG       NUMBER default 0 not null,
  START_DATE     DATE,
  END_DATE       DATE,
  NOTE           VARCHAR2(128)
)
;
comment on table ROUTE_DEF_TBL
  is '����·�߶����';
comment on column ROUTE_DEF_TBL.ROUTE_DEF_ID
  is '����';
comment on column ROUTE_DEF_TBL.ROUTE_DEF_CODE
  is '����·�߱���';
comment on column ROUTE_DEF_TBL.ROUTE_DEF_NAME
  is '����·������';
comment on column ROUTE_DEF_TBL.USE_FLAG
  is '����״̬';
comment on column ROUTE_DEF_TBL.START_DATE
  is '�������ڣ�һ��Ϊinsert��ʱ��';
comment on column ROUTE_DEF_TBL.END_DATE
  is '��������';
comment on column ROUTE_DEF_TBL.NOTE
  is '˵��';
alter table ROUTE_DEF_TBL
  add constraint PK_ROUTE_DEF_TBL primary key (ROUTE_DEF_ID);

prompt
prompt Creating table PROD_TR_DEF_TBL
prompt ==============================
prompt
create table PROD_TR_DEF_TBL
(
  PROD_TR_DEF_ID NUMBER(10) not null,
  ROUTE_DEF_ID   NUMBER(16) not null,
  CART_NUMBER    VARCHAR2(20) not null,
  PRODUCT_ID     NUMBER(4) not null,
  PROD_TR_FLAG   NUMBER(2) default 0 not null,
  NOTE           VARCHAR2(100)
)
;
alter table PROD_TR_DEF_TBL
  add constraint PK_PROD_TR_DEF_TBL primary key (PROD_TR_DEF_ID);
alter table PROD_TR_DEF_TBL
  add constraint FK1_PROD_TR_DEF_TBL foreign key (ROUTE_DEF_ID)
  references ROUTE_DEF_TBL (ROUTE_DEF_ID);
alter table PROD_TR_DEF_TBL
  add constraint FK2_PROD_TR_DEF_TBL foreign key (PRODUCT_ID)
  references DIC_PRODUCTS (PRODUCT_ID);

prompt
prompt Creating table PROD_TR_LINK_TBL
prompt ===============================
prompt
create table PROD_TR_LINK_TBL
(
  PROD_TR_LINK_ID     NUMBER(10) not null,
  PROD_TR_DEF_ID      NUMBER(10) not null,
  FORE_OPERATION_ID   NUMBER(4) not null,
  FORE_OPERATION_NAME VARCHAR2(40) not null,
  FORE_OPERATION_FLAG NUMBER(2) not null,
  NEXT_OPERATION_ID   NUMBER(4) not null,
  NEXT_OPERATION_NAME VARCHAR2(40) not null,
  NEXT_OPERATION_FLAG NUMBER(2) not null,
  PROD_TR_LINK_FLAG   NUMBER(1) default 0
)
;
alter table PROD_TR_LINK_TBL
  add constraint PK_PROD_TR_LINK_TBL primary key (PROD_TR_LINK_ID);
alter table PROD_TR_LINK_TBL
  add constraint FK1_PROD_TR_LINK_TBL foreign key (PROD_TR_DEF_ID)
  references PROD_TR_DEF_TBL (PROD_TR_DEF_ID);

prompt
prompt Creating table PROD_TR_OPERATION_TBL
prompt ====================================
prompt
create table PROD_TR_OPERATION_TBL
(
  PROD_TR_OPERATION_ID NUMBER(10) not null,
  PROD_TR_DEF_ID       NUMBER(10) not null,
  OPERATION_ID         NUMBER(10),
  OPERATION_NAME       VARCHAR2(40) not null,
  OPERATION_FLAG       NUMBER(1) not null,
  OPERATION_POS        NUMBER(1) not null,
  OPERATION_STATE      NUMBER(1) default 0 not null,
  ERR_FLAG             NUMBER(1) default 0 not null,
  ASSO_LIST_ID         NUMBER(10)
)
;
alter table PROD_TR_OPERATION_TBL
  add constraint PK_PROD_TR_OPERATION_TBL primary key (PROD_TR_OPERATION_ID);
alter table PROD_TR_OPERATION_TBL
  add constraint FK1_PROD_TR_OPERATION_TBL foreign key (PROD_TR_DEF_ID)
  references PROD_TR_DEF_TBL (PROD_TR_DEF_ID);

prompt
prompt Creating table WIP_PROD_LOGS
prompt ============================
prompt
create table WIP_PROD_LOGS
(
  LOG_ID         NUMBER(10) not null,
  PROD_TR_DEF_ID NUMBER(10) not null,
  OPERATION_ID   NUMBER(4) not null,
  MACHINE_ID     NUMBER(4) not null,
  WORK_UNIT_ID   NUMBER(4) not null,
  OPERATOR_ID    NUMBER(10) not null,
  START_DATE     DATE,
  END_DATE       DATE,
  ITEM_FLAG      NUMBER(1),
  NOTE           VARCHAR2(128)
)
;
alter table WIP_PROD_LOGS
  add constraint PK_WIP_PROD_LOGS primary key (LOG_ID);
alter table WIP_PROD_LOGS
  add constraint FK1_WIP_PROD_LOGS foreign key (PROD_TR_DEF_ID)
  references PROD_TR_DEF_TBL (PROD_TR_DEF_ID);
alter table WIP_PROD_LOGS
  add constraint FK2_WIP_PROD_LOGS foreign key (OPERATION_ID)
  references DIC_OPERATIONS (OPERATION_ID);
alter table WIP_PROD_LOGS
  add constraint FK3_WIP_PROD_LOGS foreign key (MACHINE_ID)
  references DIC_MACHINES (MACHINE_ID);

prompt
prompt Creating table QA_INSPECT_MASTER
prompt ================================
prompt
create table QA_INSPECT_MASTER
(
  INSPECTM_ID           NUMBER(16) not null,
  LOG_ID                NUMBER(16) not null,
  MACHINE_WASTER_NUMBER NUMBER(16) not null,
  ITEM_FLAG             NUMBER(1) not null
)
;
alter table QA_INSPECT_MASTER
  add constraint PK_QA_INSPECT_MASTER primary key (INSPECTM_ID);
alter table QA_INSPECT_MASTER
  add constraint FK1_QA_INSPECT_MASTER foreign key (LOG_ID)
  references WIP_PROD_LOGS (LOG_ID);

prompt
prompt Creating table QA_INSPECT_SLAVE
prompt ===============================
prompt
create table QA_INSPECT_SLAVE
(
  INSPECTS_ID          NUMBER(16) not null,
  INSPECTM_ID          NUMBER(16) not null,
  SHEET_NUM            VARCHAR2(20),
  CONVERT_SHEET_NUMBER NUMBER(4) not null,
  ITEM_FLAG            NUMBER default 0,
  ERROR_GRADE          NUMBER,
  ERROR_TYPE           NUMBER(4),
  ERROR_VALUE          NUMBER(4),
  ERROR_NOTE           VARCHAR2(100)
)
;
alter table QA_INSPECT_SLAVE
  add constraint PK_QA_INSPECT_SLAVE primary key (INSPECTS_ID);
alter table QA_INSPECT_SLAVE
  add constraint FK1_QA_INSPECT_SLAVE foreign key (INSPECTM_ID)
  references QA_INSPECT_MASTER (INSPECTM_ID);

prompt
prompt Creating table ROUTE_LINK_TBL
prompt =============================
prompt
create table ROUTE_LINK_TBL
(
  ROUTE_LINK_ID       NUMBER(16) not null,
  ROUTE_DEF_ID        NUMBER(16) not null,
  FORE_OPERATION_ID   NUMBER(4) not null,
  FORE_OPERATION_NAME VARCHAR2(40) not null,
  FORE_OPERATION_FLAG NUMBER(2) not null,
  NEXT_OPERATION_ID   NUMBER(4) not null,
  NEXT_OPERATION_NAME VARCHAR2(40) not null,
  NEXT_OPERATION_FLAG NUMBER(2) not null
)
;
comment on table ROUTE_LINK_TBL
  is '����·�����ӱ�';
comment on column ROUTE_LINK_TBL.ROUTE_LINK_ID
  is '����';
comment on column ROUTE_LINK_TBL.ROUTE_DEF_ID
  is '����·��ID';
comment on column ROUTE_LINK_TBL.FORE_OPERATION_ID
  is 'ǰ�������';
comment on column ROUTE_LINK_TBL.FORE_OPERATION_NAME
  is 'ǰ��������';
comment on column ROUTE_LINK_TBL.FORE_OPERATION_FLAG
  is 'ǰ�����־';
comment on column ROUTE_LINK_TBL.NEXT_OPERATION_ID
  is '�������';
comment on column ROUTE_LINK_TBL.NEXT_OPERATION_NAME
  is '��������';
comment on column ROUTE_LINK_TBL.NEXT_OPERATION_FLAG
  is '�����־';
alter table ROUTE_LINK_TBL
  add constraint PK_ROUTE_LINK_TBL primary key (ROUTE_LINK_ID);
alter table ROUTE_LINK_TBL
  add constraint FK1_ROUTE_LINK_TBL foreign key (ROUTE_DEF_ID)
  references ROUTE_DEF_TBL (ROUTE_DEF_ID);
alter table ROUTE_LINK_TBL
  add constraint FK2_ROUTE_LINK_TBL foreign key (FORE_OPERATION_ID)
  references DIC_OPERATIONS (OPERATION_ID);
alter table ROUTE_LINK_TBL
  add constraint FK3_ROUTE_LINK_TBL foreign key (NEXT_OPERATION_ID)
  references DIC_OPERATIONS (OPERATION_ID);

prompt
prompt Creating table SYS_FUNCTONS
prompt ===========================
prompt
create table SYS_FUNCTONS
(
  FUNCTON_ID         NUMBER(16) not null,
  FUNCTON_TYPE_ID    NUMBER(16) not null,
  FUNCTON_CODE       VARCHAR2(20) not null,
  FUNCTON_NAME       VARCHAR2(40) not null,
  FUNCTON_PARENT_ID  NUMBER(16),
  FUNCTON_LEVEL_ID   NUMBER(4),
  FUNCTON_PARENT_IDS VARCHAR2(2000),
  FUNCTON_SORT       NUMBER(6),
  FUNCTON_HREF       VARCHAR2(2000),
  IS_SHOW            NUMBER(2),
  PERMISSION         VARCHAR2(200),
  CREATE_BY          NUMBER(36),
  CREATE_DATE        DATE,
  UPDATE_BY          NUMBER(36),
  UPDATE_DATE        DATE,
  DEL_FLAG           NUMBER(2) default 0
)
;
comment on table SYS_FUNCTONS
  is 'ϵͳ�˵���';
comment on column SYS_FUNCTONS.FUNCTON_ID
  is '����';
comment on column SYS_FUNCTONS.FUNCTON_TYPE_ID
  is 'ģ�����ͣ�1  B��Ȩ�� 2 C��Ȩ��';
comment on column SYS_FUNCTONS.FUNCTON_CODE
  is 'ģ�����';
comment on column SYS_FUNCTONS.FUNCTON_NAME
  is 'ģ������';
comment on column SYS_FUNCTONS.FUNCTON_PARENT_ID
  is '��ģ��';
comment on column SYS_FUNCTONS.FUNCTON_LEVEL_ID
  is 'ģ��㼶�����ڲ˵���';
comment on column SYS_FUNCTONS.FUNCTON_PARENT_IDS
  is '��ģ�����������ڲ˵���';
comment on column SYS_FUNCTONS.FUNCTON_SORT
  is 'ģ���������ڲ˵���';
comment on column SYS_FUNCTONS.FUNCTON_HREF
  is '���ӵ�ַ�����ڲ˵���';
comment on column SYS_FUNCTONS.IS_SHOW
  is '�Ƿ���ʾ';
comment on column SYS_FUNCTONS.PERMISSION
  is 'Ȩ�ޱ�ʶ';
alter table SYS_FUNCTONS
  add constraint PK_SYS_FUNCTONS primary key (FUNCTON_ID);

prompt
prompt Creating table SYS_ROLES
prompt ========================
prompt
create table SYS_ROLES
(
  ROLE_ID    NUMBER(16) not null,
  ROLE_CODE  VARCHAR2(20) not null,
  ROLE_NAME  VARCHAR2(40) not null,
  USE_FLAG   NUMBER default 0 not null,
  START_DATE DATE,
  END_DATE   DATE,
  NOTE       VARCHAR2(128)
)
;
comment on table SYS_ROLES
  is 'ϵͳ��ɫ��';
comment on column SYS_ROLES.ROLE_ID
  is '����';
comment on column SYS_ROLES.ROLE_CODE
  is '��ɫ����';
comment on column SYS_ROLES.ROLE_NAME
  is '��ɫ����';
comment on column SYS_ROLES.USE_FLAG
  is '����״̬';
comment on column SYS_ROLES.START_DATE
  is '�������ڣ�һ��Ϊinsert��ʱ��';
comment on column SYS_ROLES.END_DATE
  is '��������';
comment on column SYS_ROLES.NOTE
  is '˵��';
alter table SYS_ROLES
  add constraint PK_SYS_ROLES primary key (ROLE_ID);

prompt
prompt Creating table SYS_LOGIN_USERS
prompt ==============================
prompt
create table SYS_LOGIN_USERS
(
  LOGIN_ID    NUMBER(16) not null,
  OPERATOR_ID NUMBER(16),
  LOGIN_NAME  VARCHAR2(20),
  LOGIN_PASS  VARCHAR2(20),
  ROLE_ID     NUMBER(16)
)
;
comment on table SYS_LOGIN_USERS
  is '��¼�û�Ȩ�ޱ�';
comment on column SYS_LOGIN_USERS.LOGIN_ID
  is '����';
comment on column SYS_LOGIN_USERS.OPERATOR_ID
  is '�û�ID';
comment on column SYS_LOGIN_USERS.LOGIN_NAME
  is '��¼����';
comment on column SYS_LOGIN_USERS.LOGIN_PASS
  is '��¼����';
comment on column SYS_LOGIN_USERS.ROLE_ID
  is '��ɫID';
alter table SYS_LOGIN_USERS
  add constraint PK_SYS_LOGIN_USERS primary key (LOGIN_ID);
alter table SYS_LOGIN_USERS
  add constraint FK1_LOGIN_FROM_OPERATORS foreign key (OPERATOR_ID)
  references DIC_OPERATORS (OPERATOR_ID)
  disable;
alter table SYS_LOGIN_USERS
  add constraint FK2_LOGIN_FROM_OPERATORS foreign key (ROLE_ID)
  references SYS_ROLES (ROLE_ID);

prompt
prompt Creating table SYS_ROLE_FUNCTONS
prompt ================================
prompt
create table SYS_ROLE_FUNCTONS
(
  ROLE_FUNCTON_ID NUMBER(16) not null,
  ROLE_ID         NUMBER(16) not null,
  FUNCTON_ID      NUMBER(16) not null
)
;
comment on table SYS_ROLE_FUNCTONS
  is '��ɫ���ܹ�ϵ��';
comment on column SYS_ROLE_FUNCTONS.ROLE_FUNCTON_ID
  is '����';
comment on column SYS_ROLE_FUNCTONS.ROLE_ID
  is '��ɫID';
comment on column SYS_ROLE_FUNCTONS.FUNCTON_ID
  is '����ID';
alter table SYS_ROLE_FUNCTONS
  add constraint PK_SYS_ROLE_FUNCTONS primary key (ROLE_FUNCTON_ID);
alter table SYS_ROLE_FUNCTONS
  add constraint FK1_SYS_ROLE_FUNCTONS foreign key (ROLE_ID)
  references SYS_ROLES (ROLE_ID);
alter table SYS_ROLE_FUNCTONS
  add constraint FK2_SYS_ROLE_FUNCTONS foreign key (FUNCTON_ID)
  references SYS_FUNCTONS (FUNCTON_ID);

prompt
prompt Creating table WIP_ACTION_LOGS
prompt ==============================
prompt
create table WIP_ACTION_LOGS
(
  ACTION_ID      NUMBER(10) not null,
  PROD_TR_DEF_ID NUMBER(10) not null,
  OPERATION_ID   NUMBER(4) not null,
  OPERATOR_ID    NUMBER(4) not null,
  START_DATE     DATE,
  END_DATE       DATE,
  ITEM_FLAG      NUMBER(1),
  NOTE           VARCHAR2(128)
)
;
alter table WIP_ACTION_LOGS
  add constraint PK_WIP_ACTION_LOGS primary key (ACTION_ID);
alter table WIP_ACTION_LOGS
  add constraint FK1_WIP_ACTION_LOGS foreign key (PROD_TR_DEF_ID)
  references PROD_TR_DEF_TBL (PROD_TR_DEF_ID);
alter table WIP_ACTION_LOGS
  add constraint FK2_WIP_ACTION_LOGS foreign key (OPERATION_ID)
  references DIC_OPERATIONS (OPERATION_ID);
alter table WIP_ACTION_LOGS
  add constraint FK3_WIP_ACTION_LOGS foreign key (OPERATOR_ID)
  references DIC_OPERATORS (OPERATOR_ID);


spool off
