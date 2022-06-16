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


    /**
     * 查询订单信息

     *
     * @param id 订单信息
    主键
     * @return 订单信息

     */
    public TbMall selectTbMallById(Long id);

    /**
     * 查询商品列表
     *
     * @param tbMall 商品
     * @return 商品集合
     */
    public List<TbMall> selectTbMallList(TbMall tbMall);


    /**
     * 添加商品
     * @param tbMall 商品
     * @param tbMallImagJson 详情图JSON
     * @param skuJson skuJSON   规格
     * @return
     */
    public int insertTbMall(TbMall tbMall,String tbMallImagJson,String skuJson);

    /**
     * 修改商品
     * @param tbMall 商品
     * @param tbMallImagJson 详情图JSON
     * @param skuJson 规格
     * @return
     */
    public int updateTbMall(TbMall tbMall,String tbMallImagJson,String skuJson);

    /**
     * 批量删除商品
     *
     * @param ids 需要删除的商品主键集合
     * @return 结果
     */
    public int deleteTbMallByIds(Long[] ids);

    /**
     * 删除商品信息
     *
     * @param id 商品主键
     * @return 结果
     */
    public int deleteTbMallById(Long id);
}
