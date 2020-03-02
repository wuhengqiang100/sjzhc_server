package com.kexin.admin.entity.vo;

public class AddUser {

    private String roleCode;

    private String roleName;

    private AllFunction functions;

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public AllFunction getFunctions() {
        return functions;
    }

    public void setFunctions(AllFunction functions) {
        this.functions = functions;
    }
}
