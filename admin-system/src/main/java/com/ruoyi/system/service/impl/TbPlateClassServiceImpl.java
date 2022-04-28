package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.TbPlateClass;
import com.ruoyi.system.mapper.TbPlateClassMapper;
import com.ruoyi.system.service.ITbPlateClassService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 板块大类-部位Service业务层处理
 * 
 * @author zzz
 * @date 2021-08-10
 */
@Service
public class TbPlateClassServiceImpl implements ITbPlateClassService 
{
    @Autowired
    private TbPlateClassMapper tbPlateClassMapper;

    /**
     * 查询板块大类-部位
     * 
     * @param id 板块大类-部位ID
     * @return 板块大类-部位
     */
    @Override
    public TbPlateClass selectTbPlateClassById(Long id)
    {
        return tbPlateClassMapper.selectTbPlateClassById(id);
    }

    /**
     * 查询板块大类-部位列表
     * 
     * @param tbPlateClass 板块大类-部位
     * @return 板块大类-部位
     */
    @Override
    public List<TbPlateClass> selectTbPlateClassList(TbPlateClass tbPlateClass)
    {
        return tbPlateClassMapper.selectTbPlateClassList(tbPlateClass);
    }

    @Override
    public List<TbPlateClass> selectTbPlateClassByList(long parentId) {
        return tbPlateClassMapper.selectTbPlateClassByList(parentId);
    }

    /**
     * 新增板块大类-部位
     * 
     * @param tbPlateClass 板块大类-部位
     * @return 结果
     */
    @Override
    public int insertTbPlateClass(TbPlateClass tbPlateClass)
    {
        return tbPlateClassMapper.insertTbPlateClass(tbPlateClass);
    }

    /**
     * 修改板块大类-部位
     * 
     * @param tbPlateClass 板块大类-部位
     * @return 结果
     */
    @Override
    public int updateTbPlateClass(TbPlateClass tbPlateClass)
    {
        return tbPlateClassMapper.updateTbPlateClass(tbPlateClass);
    }

    /**
     * 批量删除板块大类-部位
     * 
     * @param ids 需要删除的板块大类-部位ID
     * @return 结果
     */
    @Override
    public int deleteTbPlateClassByIds(Long[] ids)
    {
        return tbPlateClassMapper.deleteTbPlateClassByIds(ids);
    }

    /**
     * 删除板块大类-部位信息
     * 
     * @param id 板块大类-部位ID
     * @return 结果
     */
    @Override
    public int deleteTbPlateClassById(Long id)
    {
        return tbPlateClassMapper.deleteTbPlateClassById(id);
    }
}
