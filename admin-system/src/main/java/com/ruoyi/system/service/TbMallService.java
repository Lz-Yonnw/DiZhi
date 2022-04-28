package com.ruoyi.system.service;

import com.ruoyi.system.domain.TbCollection;
import com.ruoyi.system.domain.TbMall;
import com.ruoyi.system.domain.TbMallImgs;

import java.util.List;

/**
 * 商品接口
 */
public interface TbMallService {
    /**
     * 商品展示
     * @return
     */
    List<TbMall> selectTbMallList();

    /**
     * 商品收藏 0为取消收藏 1为收藏
     */
    void updataStatus(TbCollection tbCollection);

    /**
     * 根据用户id查询收藏的商品
     */
    TbCollection selectTbCollection(Long id,Long mallId);



    /**
     * 展示用户的全部收藏
     */
    List<TbMall> selectTbMallListStatus(Long id);

    /**
     * 查询单个商品的·详细信息
     */
    List<TbMallImgs> selectTbMallImgs(Long id);



}
