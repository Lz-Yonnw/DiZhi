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

    /**
     * 查询订单信息

     *
     * @param id 订单信息
    主键
     * @return 订单信息

     */
    TbMall selectTbMallById(Long id);

    /**
     * 修改商品
     * @param tbMall
     * @return
     */
    int updateMallById(TbMall tbMall);

    /**
     * 查询商品列表
     *
     * @param tbMall 商品
     * @return 商品集合
     */
    public List<TbMall> selectTbMallList(TbMall tbMall);

    /**
     * 新增商品
     *
     * @param tbMall 商品
     * @return 结果
     */
    public int insertTbMall(TbMall tbMall);

    /**
     * 删除商品
     *
     * @param id 商品主键
     * @return 结果
     */
    public int deleteTbMallById(Long id);

    /**
     * 批量删除商品
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbMallByIds(Long[] ids);

}
