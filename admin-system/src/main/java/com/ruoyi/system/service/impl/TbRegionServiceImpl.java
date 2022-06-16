package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TbRegionMapper;
import com.ruoyi.system.domain.TbRegion;
import com.ruoyi.system.service.ITbRegionService;

/**
 * 地址Service业务层处理
 * 
 * @author liaozan8888@163.com
 * @date 2022-05-16
 */
@Service
public class TbRegionServiceImpl implements ITbRegionService 
{
    @Autowired
    private TbRegionMapper tbRegionMapper;

    /**
     * 查询地址
     * 
     * @param id 地址主键
     * @return 地址
     */
    @Override
    public TbRegion selectTbRegionById(Long id)
    {
        return tbRegionMapper.selectTbRegionById(id);
    }

    /**
     * 查询地址列表
     * 
     * @param tbRegion 地址
     * @return 地址
     */
    @Override
    public List<TbRegion> selectTbRegionList(TbRegion tbRegion)
    {
        return tbRegionMapper.selectTbRegionList(tbRegion);
    }

    /**
     * 新增地址
     * 
     * @param tbRegion 地址
     * @return 结果
     */
    @Override
    public int insertTbRegion(TbRegion tbRegion)
    {
        return tbRegionMapper.insertTbRegion(tbRegion);
    }

    /**
     * 修改地址
     * 
     * @param tbRegion 地址
     * @return 结果
     */
    @Override
    public int updateTbRegion(TbRegion tbRegion)
    {
        return tbRegionMapper.updateTbRegion(tbRegion);
    }

    /**
     * 批量删除地址
     * 
     * @param ids 需要删除的地址主键
     * @return 结果
     */
    @Override
    public int deleteTbRegionByIds(Long[] ids)
    {
        return tbRegionMapper.deleteTbRegionByIds(ids);
    }

    /**
     * 删除地址信息
     * 
     * @param id 地址主键
     * @return 结果
     */
    @Override
    public int deleteTbRegionById(Long id)
    {
        return tbRegionMapper.deleteTbRegionById(id);
    }
}
