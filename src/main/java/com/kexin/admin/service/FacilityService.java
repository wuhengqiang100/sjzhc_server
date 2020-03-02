package com.kexin.admin.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.monitor.MonitorAssignPo;
import com.kexin.admin.entity.monitor.MonitorSearch;
import com.kexin.admin.entity.monitor.MonitorView;
import com.kexin.admin.entity.tables.Facility;
import com.kexin.admin.entity.tables.FacilityGroup;
import com.kexin.admin.entity.tables.User;
import com.kexin.common.util.ResponseEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * 设备服务接口类
 */
public interface FacilityService extends IService<Facility> {

    IPage<Facility> selecFacilityPage(Page<Facility> page, Integer useFlag, String facilityName);

    ResponseEntity forbiddenFacility(int facilityId);

    Boolean updateGroupString(FacilityGroup oldGroup,FacilityGroup newGroup);

    List<MonitorView> selectMonitorList(@Param("groupId") Integer groupId);

    List<Facility> listByGroupId(@Param("groupId") Integer groupId);

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
     * @Description:获取没有分配给设备的所有用户
     * @Author: 巫恒强  @Date: 2019/10/22 15:42
     * @Param: []
     * @Return: com.kexin.common.util.ResponseEntity
     */
    ResponseEntity getUnallocatedUser();


    /**
     * @Description:获取没有分配给设备的所有用户
     * @Author: 巫恒强  @Date: 2019/10/23 15:07
     * @Param: []
     * @Return: java.util.List<com.kexin.admin.entity.tables.User>
     */
    List<User> getUnallocatedUserList();


    /**
     * @Description:获取没有分配给设备的所有用户包括当前用户
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
    ResponseEntity searchFacility(MonitorSearch monitorSearch);

    /**
     * @Description:获取排序序号最大的值
     * @Author: 巫恒强  @Date: 2019/10/23 11:29
     * @Param: []
     * @Return: com.kexin.common.util.ResponseEntity
     */
    ResponseEntity getOrderMax();
}
