package com.ruoyi.system.mapper;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.system.domain.TbCollection;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 收藏商品
 */
@DataSource()
public interface TbCollectionMapper {

    void updataStatus(TbCollection tbCollection);


    TbCollection selectone(TbCollection tbCollection);
    TbCollection selectone(@Param("id") Long id, @Param("mallId") Long mallId);

    void insertOne(TbCollection tbCollection);

    List<Long> selectMallIds(Long id);


}
