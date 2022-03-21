package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TbPlateCutWayMapper;
import com.ruoyi.system.domain.TbPlateCutWay;
import com.ruoyi.system.service.ITbPlateCutWayService;

/**
 * 板块小类-裁剪方式Service业务层处理
 * 
 * @author zzz
 * @date 2021-08-10
 */
@Service
public class TbPlateCutWayServiceImpl implements ITbPlateCutWayService 
{
    @Autowired
    private TbPlateCutWayMapper tbPlateCutWayMapper;

    /**
     * 查询板块小类-裁剪方式
     * 
     * @param id 板块小类-裁剪方式ID
     * @return 板块小类-裁剪方式
     */
    @Override
    public TbPlateCutWay selectTbPlateCutWayById(Long id)
    {
        return tbPlateCutWayMapper.selectTbPlateCutWayById(id);
    }

    /**
     * 查询板块小类-裁剪方式列表
     * 
     * @param tbPlateCutWay 板块小类-裁剪方式
     * @return 板块小类-裁剪方式
     */
    @Override
    public List<TbPlateCutWay> selectTbPlateCutWayList(TbPlateCutWay tbPlateCutWay)
    {
        return tbPlateCutWayMapper.selectTbPlateCutWayList(tbPlateCutWay);
    }

    /**
     * 新增板块小类-裁剪方式
     * 
     * @param tbPlateCutWay 板块小类-裁剪方式
     * @return 结果
     */
    @Override
    public int insertTbPlateCutWay(TbPlateCutWay tbPlateCutWay)
    {
        return tbPlateCutWayMapper.insertTbPlateCutWay(tbPlateCutWay);
    }

    /**
     * 修改板块小类-裁剪方式
     * 
     * @param tbPlateCutWay 板块小类-裁剪方式
     * @return 结果
     */
    @Override
    public int updateTbPlateCutWay(TbPlateCutWay tbPlateCutWay)
    {
        return tbPlateCutWayMapper.updateTbPlateCutWay(tbPlateCutWay);
    }

    /**
     * 批量删除板块小类-裁剪方式
     * 
     * @param ids 需要删除的板块小类-裁剪方式ID
     * @return 结果
     */
    @Override
    public int deleteTbPlateCutWayByIds(Long[] ids)
    {
        return tbPlateCutWayMapper.deleteTbPlateCutWayByIds(ids);
    }

    /**
     * 删除板块小类-裁剪方式信息
     * 
     * @param id 板块小类-裁剪方式ID
     * @return 结果
     */
    @Override
    public int deleteTbPlateCutWayById(Long id)
    {
        return tbPlateCutWayMapper.deleteTbPlateCutWayById(id);
    }
}
