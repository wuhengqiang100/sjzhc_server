package com.kexin.admin.entity.vo;

import lombok.Data;

/**
 * @version v1.0
 * @ProjectName: server
 * @ClassName: QaInspectChange
 * @Description: TODO(核查信息审核的数据接收实体咧)
 * @Author: 13015
 * @Date: 2020/3/19 14:27
 */
@Data
public class QaInspectChange {

    private String direction;//审核的方向  left 回退,right 审核

    private Integer[] movedKeys;//审核的质量信息id数组

    private Integer tokenId;//用户id


}
