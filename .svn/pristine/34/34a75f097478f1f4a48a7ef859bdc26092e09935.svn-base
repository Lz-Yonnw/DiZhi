package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TbModelTypeMapper;
import com.ruoyi.system.domain.TbModelType;
import com.ruoyi.system.service.ITbModelTypeService;

/**
 * 版型大类Service业务层处理
 * 
 * @author zzz
 * @date 2021-08-10
 */
@Service
public class TbModelTypeServiceImpl implements ITbModelTypeService 
{
    @Autowired
    private TbModelTypeMapper tbModelTypeMapper;

    /**
     * 查询版型大类
     * 
     * @param id 版型大类ID
     * @return 版型大类
     */
    @Override
    public TbModelType selectTbModelTypeById(Long id)
    {
        return tbModelTypeMapper.selectTbModelTypeById(id);
    }

    /**
     * 查询版型大类列表
     * 
     * @param tbModelType 版型大类
     * @return 版型大类
     */
    @Override
    public List<TbModelType> selectTbModelTypeList(TbModelType tbModelType)
    {
        return tbModelTypeMapper.selectTbModelTypeList(tbModelType);
    }

    /**
     * 新增版型大类
     * 
     * @param tbModelType 版型大类
     * @return 结果
     */
    @Override
    public int insertTbModelType(TbModelType tbModelType)
    {
        return tbModelTypeMapper.insertTbModelType(tbModelType);
    }

    /**
     * 修改版型大类
     * 
     * @param tbModelType 版型大类
     * @return 结果
     */
    @Override
    public int updateTbModelType(TbModelType tbModelType)
    {
        return tbModelTypeMapper.updateTbModelType(tbModelType);
    }

    /**
     * 批量删除版型大类
     * 
     * @param ids 需要删除的版型大类ID
     * @return 结果
     */
    @Override
    public int deleteTbModelTypeByIds(Long[] ids)
    {
        return tbModelTypeMapper.deleteTbModelTypeByIds(ids);
    }

    /**
     * 删除版型大类信息
     * 
     * @param id 版型大类ID
     * @return 结果
     */
    @Override
    public int deleteTbModelTypeById(Long id)
    {
        return tbModelTypeMapper.deleteTbModelTypeById(id);
    }
}
