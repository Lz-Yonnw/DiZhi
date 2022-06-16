package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.TbMallType;

/**
 * 评价类型Mapper接口
 * 
 * @author ruoyi
 * @date 2022-06-08
 */
public interface TbMallTypeMapper 
{
    /**
     * 查询评价类型
     * 
     * @param typeId 评价类型主键
     * @return 评价类型
     */
    public TbMallType selectTbMallTypeByTypeId(Long typeId);

    /**
     * 查询评价类型列表
     * 
     * @param tbMallType 评价类型
     * @return 评价类型集合
     */
    public List<TbMallType> selectTbMallTypeList(TbMallType tbMallType);

    /**
     * 新增评价类型
     * 
     * @param tbMallType 评价类型
     * @return 结果
     */
    public int insertTbMallType(TbMallType tbMallType);

    /**
     * 修改评价类型
     * 
     * @param tbMallType 评价类型
     * @return 结果
     */
    public int updateTbMallType(TbMallType tbMallType);

    /**
     * 删除评价类型
     * 
     * @param typeId 评价类型主键
     * @return 结果
     */
    public int deleteTbMallTypeByTypeId(Long typeId);

    /**
     * 批量删除评价类型
     * 
     * @param typeIds 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbMallTypeByTypeIds(Long[] typeIds);
}
