package com.kexin.admin.component;

import com.kexin.admin.entity.tables.*;
import com.kexin.admin.mapper.*;
import com.kexin.admin.service.RoleService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 查询时,外键插入实体null判断component
 */
@Component
public class EntityNullComponent {

    @Resource
    ProductsMapper productsMapper;//产品mapper

    @Resource
    OperationMapper operationMapper;//工序mapper

    @Resource
    OperationTypeMapper operationTypeMapper;//工序类型mapper

    @Resource
    MachineMapper machineMapper;//设备mapper

    @Resource
    DicWorkUnitsMapper dicWorkUnitsMapper;//机台mapper

    @Resource
    OperatorMapper operatorMapper;//人员mapper

    @Resource
    CartNumFirstMapper cartNumFirstMapper;//车号首字母mapper

    @Resource
    RoleMapper roleMapper;//角色mapper

    @Resource
    AuditParameterTypeMapper auditParameterTypeMapper;//审核参数类型mapper

    @Resource
    AuditParameterMapper auditParameterMapper;//审核参数mapper

    @Resource
    RoleService roleService;//角色service


    /**
     * 审核参数外键,null判断
     * @param r
     * @return
     */
    public AuditParameter nullCheckAuditParameter(AuditParameter r){
        if(r.getJudgeCheckTypeId()!=null){
            if (auditParameterTypeMapper.selectById(r.getJudgeCheckTypeId())!=null) {
                r.setJudgeCheckType(auditParameterTypeMapper.selectById(r.getJudgeCheckTypeId()));
            }
        }if (r.getOperationId()!=null){
            if (operationMapper.selectById(r.getOperationId())!=null) {
                r.setOperation(operationMapper.selectById(r.getOperationId()));
            }
        }if (r.getProductId()!=null){
            if (productsMapper.selectById(r.getProductId())!=null){
                r.setProducts(productsMapper.selectById(r.getProductId()));
            }
        }if (r.getMachineId()!=null){
            if (machineMapper.selectById(r.getMachineId())!=null){
                r.setMachine(machineMapper.selectById(r.getMachineId()));
            }
        }
        return r;
    }

    /**
     * 设备模型外键,null判断
     * @param r
     * @return
     */
    public MachineModel nullCheckMachineModel(MachineModel r){
        if (r.getOperationId()!=null){
            if (operationMapper.selectById(r.getOperationId())!=null) {
                r.setOperation(operationMapper.selectById(r.getOperationId()));
            }
        }if (r.getProductId()!=null){
            if (productsMapper.selectById(r.getProductId())!=null){
                r.setProduct(productsMapper.selectById(r.getProductId()));
            }
        }if (r.getMachineId()!=null){
            if (machineMapper.selectById(r.getMachineId())!=null){
                r.setMachine(machineMapper.selectById(r.getMachineId()));
            }
        }
        return r;
    }

    /**
     * 工序外键,null判断
     * @param r
     * @return
     */
    public Operation nullCheckOperation(Operation r){
        if (r.getOperationTypeId()!=null){
            if (operationTypeMapper.selectById(r.getOperationTypeId())!=null) {
                r.setOperationType(operationTypeMapper.selectById(r.getOperationTypeId()));
            }
        }
        return r;
    }

    /**
     * 产品外键,null判断
     * @param r
     * @return
     */
    public Products nullCheckProduct(Products r){
        if (r.getCartnumFirstId()!=null){
            if (cartNumFirstMapper.selectById(r.getCartnumFirstId())!=null){
                r.setCartNumFirst(cartNumFirstMapper.selectById(r.getCartnumFirstId()));
            }
        }
        return r;
    }


    /**
     * 账户外键,null判断
     * @param r
     * @return
     */
    public LoginUser nullCheckUser(LoginUser r){
        if (r.getOperatorId()!=null){
            if (operatorMapper.selectById(r.getOperatorId())!=null) {
                r.setOperator(operatorMapper.selectById(r.getOperatorId()));
            }
        }if (r.getLoginId()!=null){
            r.setRoleIds(roleService.getRoleOptionOwn(r.getLoginId()));
        }if (r.getRoleIds()!=null){
            r.setRoleString( roleService.getRoleString(r.getRoleIds()));
        }
        return r;
    }

    /**
     * 设备日志外键,null判断
     * @param r
     * @return
     */
    public MachineLog nullCheckMachineLog(MachineLog r){
        if (r.getOperatorId()!=null){
            if (operatorMapper.selectById(r.getOperatorId())!=null) {
                r.setOperator(operatorMapper.selectById(r.getOperatorId()));
            }
        }
        return r;
    }
    /**
     * 生产日志外键,null判断
     * @param r
     * @return
     */
    public ProduceLog nullCheckProduceLog(ProduceLog r){
        if (r.getProductId()!=null){
            if (productsMapper.selectById(r.getProductId())!=null){
                r.setProduct(productsMapper.selectById(r.getProductId()));
            }
        }if (r.getOperationId()!=null){
            if (operationMapper.selectById(r.getOperationId())!=null) {
                r.setOperation(operationMapper.selectById(r.getOperationId()));
            }
        }
        return r;
    }

    /**
     * 判费类型外键,null判断
     * @param r
     * @return
     */
    public WasterReason nullCheckWasterReason(WasterReason r){
        if (r.getOperationId()!=null){
            if (operationMapper.selectById(r.getOperationId())!=null) {
                r.setOperation(operationMapper.selectById(r.getOperationId()));
            }
        }
        return r;
    }


    /**
     * 机台外键,null判断
     * @param r
     * @return
     */
    public WorkUnit nullCheckWorkUnit(WorkUnit r){
        if (r.getOperatorId()!=null){
            if (operatorMapper.selectById(r.getOperatorId())!=null) {
                r.setOperator(operatorMapper.selectById(r.getOperatorId()));
            }
        }
        return r;
    }
}
