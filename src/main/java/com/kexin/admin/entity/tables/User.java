package com.kexin.admin.entity.tables;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.kexin.admin.entity.vo.CheckOptionsGroup;
import com.kexin.admin.entity.vo.CheckOptionsType;
import com.kexin.common.base.TableEntity;

import java.util.Date;
import java.util.List;

/**
 * 用户实体类
 */
@TableName("dic_operators")
public class User{
    @TableId(type = IdType.AUTO)
    @TableField(value = "OPERATOR_ID")
    private int operatorId;//人员序号

    @TableField(value = "OPERATOR_TYPE_ID")
    private int operatorTypeId; //人员类型 (外键)

    @TableField(value = "OPERATOR_CODE")
    private String operatorCode; //人员编号

    @TableField(value = "OPERATOR_NAME")
    private String operatorName; //人员名称

    @TableField(value = "OPERATOR_LOGIN_NAME")
    private String operatorLoginName; //人员登录名

    @TableField(value = "OPERATOR_LOGIN_PASS")
    private String operatorLoginPass; //人员登录密码

    @TableField(value = "ROLE_STRING")
    private String roleString; //用户角色临时保存字段

    @TableField(exist = false)
    private Identity identity;//人员的身份,在工作中的真实身份

    @TableField(exist = false)
    private List<Role> roleList;//用户的角色集合


    @TableField(exist = false)
    private int[] checkUserRole;//用户角色数组

    @TableField(exist = false)
    private int[] checkUserIdentity;//用户身份数组

    @TableField(exist = false)
    private CheckOptionsGroup checkOptionsRole;//角色options

    @TableField(exist = false)
    private CheckOptionsType checkOptionsIdentity;//分组options
    /**
     * 启用状态:0 禁止,1 启用
     */
    @TableField(value = "USE_FLAG", fill = FieldFill.INSERT_UPDATE)
    protected Boolean useFlag;
    /**
     * 启用时间,写入时间
     */
    @TableField(value = "START_DATE",strategy= FieldStrategy.IGNORED)
    protected Date startDate;
    /**
     * 禁用时间,结束时间
     */
    @TableField(value = "END_DATE",  strategy = FieldStrategy.IGNORED)
    protected Date endDate;

    /**
     * 说明
     */
    @TableField(value = "NOTE")
    protected String note;

    public Boolean getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(Boolean useFlag) {
        this.useFlag = useFlag;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }


    public int[] getCheckUserRole() {
        return checkUserRole;
    }

    public void setCheckUserRole(int[] checkUserRole) {
        this.checkUserRole = checkUserRole;
    }

    public int[] getCheckUserIdentity() {
        return checkUserIdentity;
    }

    public void setCheckUserIdentity(int[] checkUserIdentity) {
        this.checkUserIdentity = checkUserIdentity;
    }

    public CheckOptionsGroup getCheckOptionsRole() {
        return checkOptionsRole;
    }

    public void setCheckOptionsRole(CheckOptionsGroup checkOptionsRole) {
        this.checkOptionsRole = checkOptionsRole;
    }

    public CheckOptionsType getCheckOptionsIdentity() {
        return checkOptionsIdentity;
    }

    public void setCheckOptionsIdentity(CheckOptionsType checkOptionsIdentity) {
        this.checkOptionsIdentity = checkOptionsIdentity;
    }

    public String getRoleString() {
        return roleString;
    }

    public void setRoleString(String roleString) {
        this.roleString = roleString;
    }

    public Identity getIdentity() {
        return identity;
    }

    public void setIdentity(Identity identity) {
        this.identity = identity;
    }

    public List<Role> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<Role> roleList) {
        this.roleList = roleList;
    }

    public int getOperatorId() {
        return operatorId;
    }

    public void setOperatorId(int operatorId) {
        this.operatorId = operatorId;
    }

    public int getOperatorTypeId() {
        return operatorTypeId;
    }

    public void setOperatorTypeId(int operatorTypeId) {
        this.operatorTypeId = operatorTypeId;
    }

    public String getOperatorCode() {
        return operatorCode;
    }

    public void setOperatorCode(String operatorCode) {
        this.operatorCode = operatorCode;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getOperatorLoginName() {
        return operatorLoginName;
    }

    public void setOperatorLoginName(String operatorLoginName) {
        this.operatorLoginName = operatorLoginName;
    }

    public String getOperatorLoginPass() {
        return operatorLoginPass;
    }

    public void setOperatorLoginPass(String operatorLoginPass) {
        this.operatorLoginPass = operatorLoginPass;
    }


}
