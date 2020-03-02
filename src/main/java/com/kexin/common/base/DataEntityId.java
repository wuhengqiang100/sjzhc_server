package com.kexin.common.base;

import com.baomidou.mybatisplus.annotation.*;
import com.baomidou.mybatisplus.extension.activerecord.Model;

public abstract class DataEntityId< T extends Model<?>> extends BaseEntity<T> {

    @TableId(type = IdType.AUTO)
    private String id;


}
