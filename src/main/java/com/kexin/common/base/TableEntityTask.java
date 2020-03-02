package com.kexin.common.base;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public abstract class TableEntityTask< T extends Model<?>> extends Model<T>{

//    /**
//     * 启用状态:0 禁止,1 启用
//     */
//    @TableField(value = "USE_FLAG", fill = FieldFill.INSERT_UPDATE)
//    protected int useFlag;
    /**
     * 启用时间,写入时间
     */
    @TableField(value = "START_DATE", fill = FieldFill.INSERT)
    protected Date startDate;
  /**
     * 禁用时间,结束时间
     */
    @TableField(value = "END_DATE", fill = FieldFill.UPDATE)
    protected Date endDate;

    /**
     * 说明
     */
    @TableField(value = "NOTE")
    protected String note;



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
}
