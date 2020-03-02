package com.kexin.admin.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.kexin.admin.entity.monitor.MonitorAssignPo;
import com.kexin.admin.entity.monitor.MonitorSearch;
import com.kexin.admin.entity.monitor.MonitorView;
import com.kexin.admin.entity.tables.Facility;
import com.kexin.admin.entity.tables.User;
import com.kexin.common.util.ResponseEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface FacilityMapper extends BaseMapper<Facility> {


    /**
     * <p>
     * 查询 : 根据state状态查询用户列表，分页显示
     * 注意!!: 如果入参是有多个,需要加注解指定参数名才能在xml中取值
     * </p>
     *
     * @param page 分页对象,xml中可以从里面进行取值,传递参数 Page 即自动分页,必须放在第一位(你可以继承Page实现自己的分页对象)
     * @param state 状态
     * @return 分页对象
     */
//    IPage<Facility> selectPageFacility(Page page, @Param("USE_FLAG") Integer useFlag);
    IPage<Facility> selectPageFacility(Page<Facility> page, Integer useFlag, String facilityName);

    /**
     * 查询分组下面的设备
     * @param groupId
     * @return
     */
    List<Facility> selectFacilityByGroupId(int groupId);

    /**
     * 获取监控页面的设备list
     * @return
     */
    List<MonitorView> selectMonitorList(@Param("groupId") Integer groupId);

    /**
     * 根据设备id获取设备的显示信息
     * @return
     */
    MonitorView selectMonitorByMachineId(int machineId);

    /**
     *  获取分配的工作台以及分配人员的信息
     * @return
     */
    List<MonitorAssignPo> selectMonitorPubList();

    List<Facility> listByGroupId(@Param("groupId") Integer groupId);
    /**
     * 获得最大的排序值
     * @return
     */
    Integer selectMaxOrder();

    Integer getTotal(@Param("groupId") Integer groupId);

    Integer getOnline(@Param("groupId") Integer groupId);

    /**
     * 根据控制台人员查设备信息
     * @param operatorId
     * @return
     */
    Facility getFacilityByOperatorId(int operatorId);


    /**
     * @Description:获取机台没有分配的用户
     * @Author: 巫恒强  @Date: 2019/10/22 15:28
     * @Param: []
     * @Return: java.util.List<com.kexin.admin.entity.tables.User>
     */
    List<User> getUnallocatedUser();

    /**
     * @Description:获取机台没有分配的用户包括当前用户
     * @Author: 巫恒强  @Date: 2019/10/23 15:44
     * @Param: []
     * @Return: java.util.List<com.kexin.admin.entity.tables.User>
     */
    List<User> getUnallocatedAndCurrentUser(@Param("operatorId") Integer operatorId);


    /**
     * @Description:按条件搜索设备功能
     * @Author: 巫恒强  @Date: 2019/10/22 16:14
     * @Param: [map]
     * @Return: com.kexin.common.util.ResponseEntity
     */
    List<MonitorView> searchFacility(MonitorSearch monitorSearch);


//    IPage<Facility> selectUserPageNotGroupById(Page<Facility> page, Integer useFlag);
//
//    Boolean updateGrantFacilityAndFunctions(int facilityId, int functionId);
//
//    Facility getByMap(Facility facility);
//
//    Facility getByMapOr(Facility facility);
//
//    Facility saveNewFacility(Facility facility);
//
//    Integer updateFacility(Facility facility);
//
//    Integer deleteGrantFacility(int facilityId);
//
//    Integer forbiddenFacility(int facilityId);
}
