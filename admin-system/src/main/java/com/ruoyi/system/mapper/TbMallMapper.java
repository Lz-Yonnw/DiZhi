package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.TbMall;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 商品查询mapper接口
 */
public interface TbMallMapper {

    /**
     * 查询所有商品
     */
//    @Select("select * from tb_mall")
    List<TbMall> selectList();

    /**
     * 根据id查询商品
     * @param mallIds
     * @return
     */
    List<TbMall> selectListMall(List<Long> mallIds);

    /**
     * 查询商品信息
     * @param mallId
     * @return
     */
    TbMall select(@Param("mallId") int mallId);
}
