package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TbComponentMapper;
import com.ruoyi.system.domain.TbComponent;
import com.ruoyi.system.service.ITbComponentService;

/**
 * 部件Service业务层处理
 * 
 * @author zzz
 * @date 2021-08-16
 */
@Service
public class TbComponentServiceImpl implements ITbComponentService 
{
    @Autowired
    private TbComponentMapper tbComponentMapper;

    /**
     * 查询部件
     * 
     * @param id 部件ID
     * @return 部件
     */
    @Override
    public TbComponent selectTbComponentById(Long id)
    {
        return tbComponentMapper.selectTbComponentById(id);
    }

    /**
     * 查询部件列表
     * 
     * @param tbComponent 部件
     * @return 部件
     */
    @Override
    public List<TbComponent> selectTbComponentList(TbComponent tbComponent)
    {
        return tbComponentMapper.selectTbComponentList(tbComponent);
    }

    /**
     * 新增部件
     * 
     * @param tbComponent 部件
     * @return 结果
     */
    @Override
    public int insertTbComponent(TbComponent tbComponent)
    {
        return tbComponentMapper.insertTbComponent(tbComponent);
    }

    /**
     * 修改部件
     * 
     * @param tbComponent 部件
     * @return 结果
     */
    @Override
    public int updateTbComponent(TbComponent tbComponent)
    {
        return tbComponentMapper.updateTbComponent(tbComponent);
    }

    /**
     * 批量删除部件
     * 
     * @param ids 需要删除的部件ID
     * @return 结果
     */
    @Override
    public int deleteTbComponentByIds(Long[] ids)
    {
        return tbComponentMapper.deleteTbComponentByIds(ids);
    }

    /**
     * 删除部件信息
     * 
     * @param id 部件ID
     * @return 结果
     */
    @Override
    public int deleteTbComponentById(Long id)
    {
        return tbComponentMapper.deleteTbComponentById(id);
    }
}
