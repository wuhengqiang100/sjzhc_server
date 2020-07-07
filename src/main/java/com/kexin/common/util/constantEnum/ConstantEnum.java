package com.kexin.common.util.constantEnum;

public class ConstantEnum {



    /**
     *系统用户默认密码
     */
//    public static final String DEFAULT_PASSWORD = "123456";

    /**
     * 报警信息未处理状态
     */
    public static final int NOT_DEAL_STATE = 0;

    /**
     * 报警信息已处理状态
     */
    public static final int DEAL_STATE = 1;

    /**
     * 报警大屏的id,北京防伪暂时报警大屏id为1,可能南昌会有多块大屏
     */
    public static final int DISPLAY_PLAT_FORM=1;
    /**
     * 南昌大屏监控id配置
     */
    public static final int DISPLAY_PLAT_FORM_NC1=1;
    public static final int DISPLAY_PLAT_FORM_NC2=2;
    public static final int DISPLAY_PLAT_FORM_NC3=3;



    /**
     * 报警大屏的一排显示几个设备的数据,防伪是3个,南昌监控为5个
     */
//    public static final int DISPLAY_PLAT_FORM_COUNT=3;
    public static final int DISPLAY_PLAT_FORM_COUNT=5;

    /**
     * 系统id
     * 5:海南核查
     */
    public static final int FACTORY_ID=5;
}
