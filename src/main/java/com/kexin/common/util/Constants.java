package com.kexin.common.util;

public class Constants {

    /**
     * 角色权限保存专用
     */
    public static final int USER_FUNCTION=1;   //* 用户管理1
    public static final int FACILITY=2;   //* 设备管理2
    public static final int ROLE_FUNCTION=3;   //* 角色管理3
    public static final int SYSRAPAIR_FUNCTION=4;   //* 系统维护4
    public static final int MONITOR_FUNCTION=5;   //* 现场监控5
    public static final int IDENTITY_FUNCTION=6;   //* 身份管理6
    public static final int FACILITY_TYPE=7;   //* 设备类别7
    public static final int FACILITY_GROUP=8;   //* 设备分组8
    public static final int ANALYZE_FUNCTION=9;   //* 数据分析9
    public static final int RT_TASK=10;   //* 实时任务10
    /**
     * shiro采用加密算法
     */
    public static final String HASH_ALGORITHM = "SHA-1";

    /**
     * 生成Hash值的迭代次数
     */
    public static final int HASH_INTERATIONS = 1024;

    /**
     * 生成盐的长度
     */
    public static final int SALT_SIZE = 8;

    /**
     * 验证码
     */
    public static final String VALIDATE_CODE = "validateCode";

    /**
     *系统用户默认密码
     */
    public static final String DEFAULT_PASSWORD = "123456";
}
