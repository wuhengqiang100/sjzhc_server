package com.kexin.admin.entity.vo.webQuery;

import lombok.Data;

/**
 * 角色赋予权限时使用的实体类
 */
@Data
public class RoleChange {

    private String direction;//授权的方向  left 回退,right 审核

    private Integer[] movedKeys;//(右侧)授权的id数组

    private Integer roleId;//角色id


}
