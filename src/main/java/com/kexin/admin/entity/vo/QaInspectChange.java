package com.kexin.admin.entity.vo;

/**
 * @version v1.0
 * @ProjectName: server
 * @ClassName: QaInspectChange
 * @Description: TODO(核查信息审核的数据接收实体咧)
 * @Author: 13015
 * @Date: 2020/3/19 14:27
 */
public class QaInspectChange {

    private String direction;//审核的方向  left 回退,right 审核

    private Integer[] movedKeys;//审核的质量信息id数组

    public String getDirection() {
        return direction;
    }

    public void setDirection(String direction) {
        this.direction = direction;
    }

    public Integer[] getMovedKeys() {
        return movedKeys;
    }

    public void setMovedKeys(Integer[] movedKeys) {
        this.movedKeys = movedKeys;
    }
}
