package com.kexin.admin.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.kexin.admin.entity.tables.CartNumFirst;
import org.apache.ibatis.annotations.Param;


/**
 * 车号首字母服务接口类
 */
public interface CartNumFirstService extends IService<CartNumFirst> {


    /**
     * 根据车号首字母编码计算数量,当前机器的code的数量
     * @param cartNumFirstCode
     * @return
     */
    Integer cartNumFirstCountByCode(@Param("cartNumFirstCode") String cartNumFirstCode);


    /**
     * 根据车号首字母名称计算数量
     * @param cartNumFirstName
     * @return
     */
    Integer cartNumFirstCountByName(@Param("cartNumFirstName") String cartNumFirstName);

    /**
     * 保存车号首字母
     * @param cartNumFirst
     */
    void saveCartNumFirst(@Param("cartNumFirst") CartNumFirst cartNumFirst);


    /**
     * 修改更新车号首字母
     * @param cartNumFirst
     */
    void updateCartNumFirst(@Param("cartNumFirst") CartNumFirst cartNumFirst);

    /**
     * 删除车号首字母(单个)
     * @param cartNumFirst
     */
    void deleteCartNumFirst(@Param("cartNumFirst") CartNumFirst cartNumFirst);

    /**
     * 禁用或者启用车号首字母
     * @param cartNumFirst
     */
//    void lockCartNumFirst(@Param("cartNumFirst") CartNumFirst cartNumFirst);

}
