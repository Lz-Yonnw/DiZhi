package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TbPatternComponentMapper;
import com.ruoyi.system.domain.TbPatternComponent;
import com.ruoyi.system.service.ITbPatternComponentService;

/**
 * 图案部件中间Service业务层处理
 * 
 * @author zzz
 * @date 2021-09-13
 */
@Service
public class TbPatternComponentServiceImpl implements ITbPatternComponentService 
{
    @Autowired
    private TbPatternComponentMapper tbPatternComponentMapper;

    /**
     * 查询图案部件中间
     * 
     * @param id 图案部件中间ID
     * @return 图案部件中间
     */
    @Override
    public TbPatternComponent selectTbPatternComponentById(Long id)
    {
        return tbPatternComponentMapper.selectTbPatternComponentById(id);
    }

    @Override
    public int deleteTbPatternComponent(TbPatternComponent tbPatternComponent) {
        return tbPatternComponentMapper.deleteTbPatternComponent(tbPatternComponent);
    }

    /**
     * 查询图案部件中间列表
     * 
     * @param tbPatternComponent 图案部件中间
     * @return 图案部件中间
     */
    @Override
    public List<TbPatternComponent> selectTbPatternComponentList(TbPatternComponent tbPatternComponent)
    {
        return tbPatternComponentMapper.selectTbPatternComponentList(tbPatternComponent);
    }

    @Override
    public List<TbPatternComponent> selectTbPatternComponentAllList(TbPatternComponent tbPatternComponent) {
        return tbPatternComponentMapper.selectTbPatternComponentAllList(tbPatternComponent);
    }

    /**
     * 新增图案部件中间
     * 
     * @param tbPatternComponent 图案部件中间
     * @return 结果
     */
    @Override
    public int insertTbPatternComponent(TbPatternComponent tbPatternComponent)
    {
        tbPatternComponent.setCreateTime(DateUtils.getNowDate());
        return tbPatternComponentMapper.insertTbPatternComponent(tbPatternComponent);
    }

    /**
     * 修改图案部件中间
     * 
     * @param tbPatternComponent 图案部件中间
     * @return 结果
     */
    @Override
    public int updateTbPatternComponent(TbPatternComponent tbPatternComponent)
    {
        return tbPatternComponentMapper.updateTbPatternComponent(tbPatternComponent);
    }

    /**
     * 批量删除图案部件中间
     * 
     * @param ids 需要删除的图案部件中间ID
     * @return 结果
     */
    @Override
    public int deleteTbPatternComponentByIds(Long[] ids)
    {
        return tbPatternComponentMapper.deleteTbPatternComponentByIds(ids);
    }

    @Override
    public int deleteTbPatternComponentByPatternId(Long patternId) {
        return tbPatternComponentMapper.deleteTbPatternComponentByPatternId(patternId);
    }

    @Override
    public int deleteTbPatternComponentByClassId(Long componentId) {
        return tbPatternComponentMapper.deleteTbPatternComponentByClassId(componentId);
    }

    /**
     * 删除图案部件中间信息
     * 
     * @param id 图案部件中间ID
     * @return 结果
     */
    @Override
    public int deleteTbPatternComponentById(Long id)
    {
        return tbPatternComponentMapper.deleteTbPatternComponentById(id);
    }
}
