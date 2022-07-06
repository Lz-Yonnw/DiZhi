package com.ruoyi.system.service;

import com.ruoyi.system.domain.TbClothesStyle;

import java.util.List;

/**
 * 爆款列表Service接口
 *
 * @author zzz
 * @date 2021-09-25
 */
public interface ITbClothesStyleService
{
    /**
     * 查询衣服爆款
     *
     * @param id 衣服爆款主键
     * @return 衣服爆款
     */
    public TbClothesStyle selectTbClothesStyleById(Long id);

    /**
     * 查询衣服爆款列表
     *
     * @param tbClothesStyle 衣服爆款
     * @return 衣服爆款集合
     */
    public List<TbClothesStyle> selectTbClothesStyleList(TbClothesStyle tbClothesStyle);

    /**
     * 新增衣服爆款
     *
     * @param tbClothesStyle 衣服爆款
     * @return 结果
     */
    public int insertTbClothesStyle(TbClothesStyle tbClothesStyle);

    /**
     * 修改衣服爆款
     *
     * @param tbClothesStyle 衣服爆款
     * @return 结果
     */
    public int updateTbClothesStyle(TbClothesStyle tbClothesStyle);

    /**
     * 批量删除衣服爆款
     *
     * @param ids 需要删除的衣服爆款主键集合
     * @return 结果
     */
    public int deleteTbClothesStyleByIds(Long[] ids);

    /**
     * 删除衣服爆款信息
     *
     * @param id 衣服爆款主键
     * @return 结果
     */
    public int deleteTbClothesStyleById(Long id);
}
