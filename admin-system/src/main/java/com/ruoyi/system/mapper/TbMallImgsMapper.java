package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.TbMallImgs;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

public interface TbMallImgsMapper {

    //根据商品id查询商品详细信息
    List<TbMallImgs> selectMallId(long id);

    /**
     * 添加商品详情信息
     * @param tbMallImgs
     * @return
     */
    int insertTbMallImgs(TbMallImgs tbMallImgs);

    /**
     * 循环添加商品详情信息
     * @return
     */
    int insertTbMallImagsList(@Param("mallImgsList") List<Map> mallImgsList);

    /**
     * 根据商品Id 删除商品详情图
     * @param mallId
     * @return
     */
    int deleteTbMallImgsByMallId(@Param("mallId")Long mallId);

    /**
     * 修改商品详情图
     * @param tbMallImgsList
     * @return
     */
    int updateTbMallImagsList(@Param("mallImgsList") List<TbMallImgs> tbMallImgsList);


    /**
     * 批量删除商品信息
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbMallImgsByIds(Long[] ids);
}
