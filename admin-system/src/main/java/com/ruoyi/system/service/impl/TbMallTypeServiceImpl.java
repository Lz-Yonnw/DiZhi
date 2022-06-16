package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TbMallTypeMapper;
import com.ruoyi.system.domain.TbMallType;
import com.ruoyi.system.service.ITbMallTypeService;

/**
 * 评价类型Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-06-08
 */
@Service
public class TbMallTypeServiceImpl implements ITbMallTypeService 
{
    @Autowired
    private TbMallTypeMapper tbMallTypeMapper;

    /**
     * 查询评价类型
     * 
     * @param typeId 评价类型主键
     * @return 评价类型
     */
    @Override
    public TbMallType selectTbMallTypeByTypeId(Long typeId)
    {
        return tbMallTypeMapper.selectTbMallTypeByTypeId(typeId);
    }

    /**
     * 查询评价类型列表
     * 
     * @param tbMallType 评价类型
     * @return 评价类型
     */
    @Override
    public List<TbMallType> selectTbMallTypeList(TbMallType tbMallType)
    {
        return tbMallTypeMapper.selectTbMallTypeList(tbMallType);
    }

    /**
     * 新增评价类型
     * 
     * @param tbMallType 评价类型
     * @return 结果
     */
    @Override
    public int insertTbMallType(TbMallType tbMallType)
    {
        return tbMallTypeMapper.insertTbMallType(tbMallType);
    }

    /**
     * 修改评价类型
     * 
     * @param tbMallType 评价类型
     * @return 结果
     */
    @Override
    public int updateTbMallType(TbMallType tbMallType)
    {
        return tbMallTypeMapper.updateTbMallType(tbMallType);
    }

    /**
     * 批量删除评价类型
     * 
     * @param typeIds 需要删除的评价类型主键
     * @return 结果
     */
    @Override
    public int deleteTbMallTypeByTypeIds(Long[] typeIds)
    {
        return tbMallTypeMapper.deleteTbMallTypeByTypeIds(typeIds);
    }

    /**
     * 删除评价类型信息
     * 
     * @param typeId 评价类型主键
     * @return 结果
     */
    @Override
    public int deleteTbMallTypeByTypeId(Long typeId)
    {
        return tbMallTypeMapper.deleteTbMallTypeByTypeId(typeId);
    }
}
