package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.TbClothesStyle;

/**
 * 爆款列表Service接口
 * 
 * @author zzz
 * @date 2021-09-25
 */
public interface ITbClothesStyleService 
{
    /**
     * 查询爆款列表
     * 
     * @param id 爆款列表ID
     * @return 爆款列表
     */
    public TbClothesStyle selectTbClothesStyleById(Long id);

    /**
     * 查询爆款列表列表
     * 
     * @param tbClothesStyle 爆款列表
     * @return 爆款列表集合
     */
    public List<TbClothesStyle> selectTbClothesStyleList(TbClothesStyle tbClothesStyle);

    /**
     * 新增爆款列表
     * 
     * @param tbClothesStyle 爆款列表
     * @return 结果
     */
    public int insertTbClothesStyle(TbClothesStyle tbClothesStyle);

    /**
     * 修改爆款列表
     * 
     * @param tbClothesStyle 爆款列表
     * @return 结果
     */
    public int updateTbClothesStyle(TbClothesStyle tbClothesStyle);

    /**
     * 批量删除爆款列表
     * 
     * @param ids 需要删除的爆款列表ID
     * @return 结果
     */
    public int deleteTbClothesStyleByIds(Long[] ids);

    /**
     * 删除爆款列表信息
     * 
     * @param id 爆款列表ID
     * @return 结果
     */
    public int deleteTbClothesStyleById(Long id);
}
