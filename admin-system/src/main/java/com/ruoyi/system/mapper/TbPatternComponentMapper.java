package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.TbPatternComponent;

/**
 * 图案部件中间Mapper接口
 * 
 * @author zzz
 * @date 2021-09-13
 */
public interface TbPatternComponentMapper 
{
    /**
     * 查询图案部件中间
     * 
     * @param id 图案部件中间ID
     * @return 图案部件中间
     */
    public TbPatternComponent selectTbPatternComponentById(Long id);

    public int deleteTbPatternComponent(TbPatternComponent tbPatternComponent);

    /**
     * 查询图案部件中间列表
     * 
     * @param tbPatternComponent 图案部件中间
     * @return 图案部件中间集合
     */
    public List<TbPatternComponent> selectTbPatternComponentList(TbPatternComponent tbPatternComponent);

    public List<TbPatternComponent> selectTbPatternComponentAllList(TbPatternComponent tbPatternComponent);

    /**
     * 新增图案部件中间
     * 
     * @param tbPatternComponent 图案部件中间
     * @return 结果
     */
    public int insertTbPatternComponent(TbPatternComponent tbPatternComponent);

    /**
     * 修改图案部件中间
     * 
     * @param tbPatternComponent 图案部件中间
     * @return 结果
     */
    public int updateTbPatternComponent(TbPatternComponent tbPatternComponent);

    /**
     * 删除图案部件中间
     * 
     * @param id 图案部件中间ID
     * @return 结果
     */
    public int deleteTbPatternComponentById(Long id);

    public int deleteTbPatternComponentByPatternId(Long patternId);

    public int deleteTbPatternComponentByClassId(Long componentId);

    /**
     * 批量删除图案部件中间
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTbPatternComponentByIds(Long[] ids);
}
