package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.TbCollection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收藏商品
 */
public interface TbCollectionMapper {

    void updataStatus(TbCollection tbCollection);


    TbCollection selectone(TbCollection tbCollection);
    TbCollection selectone(@Param("userID") Integer id, @Param("mallId") Long mallId);

    void insertOne(TbCollection tbCollection);

    List<Long> selectMallIds(Long id);


}
