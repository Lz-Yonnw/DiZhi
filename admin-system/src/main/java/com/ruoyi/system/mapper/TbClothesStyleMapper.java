package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.TbClothesStyle;

/**
 * 爆款列表Mapper接口
 * 
 * @author zzz
 * @date 2021-09-25
 */
public interface TbClothesStyleMapper 
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
     * 删除爆款列表
     * 
     * @param id 爆款列表ID
     * @return 结果
     */
    public int deleteTbClothesStyleById(Long id);

    /**
     * 批量删除爆款列表
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTbClothesStyleByIds(Long[] ids);
}
