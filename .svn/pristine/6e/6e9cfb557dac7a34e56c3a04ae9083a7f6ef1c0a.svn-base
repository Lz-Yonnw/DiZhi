package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TbBannerMapper;
import com.ruoyi.system.domain.TbBanner;
import com.ruoyi.system.service.ITbBannerService;

/**
 * banner列表Service业务层处理
 * 
 * @author zzz
 * @date 2021-09-22
 */
@Service
public class TbBannerServiceImpl implements ITbBannerService 
{
    @Autowired
    private TbBannerMapper tbBannerMapper;

    /**
     * 查询banner列表
     * 
     * @param id banner列表ID
     * @return banner列表
     */
    @Override
    public TbBanner selectTbBannerById(Long id)
    {
        return tbBannerMapper.selectTbBannerById(id);
    }

    /**
     * 查询banner列表列表
     * 
     * @param tbBanner banner列表
     * @return banner列表
     */
    @Override
    public List<TbBanner> selectTbBannerList(TbBanner tbBanner)
    {
        return tbBannerMapper.selectTbBannerList(tbBanner);
    }

    /**
     * 新增banner列表
     * 
     * @param tbBanner banner列表
     * @return 结果
     */
    @Override
    public int insertTbBanner(TbBanner tbBanner)
    {
        tbBanner.setCreateTime(DateUtils.getNowDate());
        return tbBannerMapper.insertTbBanner(tbBanner);
    }

    /**
     * 修改banner列表
     * 
     * @param tbBanner banner列表
     * @return 结果
     */
    @Override
    public int updateTbBanner(TbBanner tbBanner)
    {
        tbBanner.setUpdateTime(DateUtils.getNowDate());
        return tbBannerMapper.updateTbBanner(tbBanner);
    }

    /**
     * 批量删除banner列表
     * 
     * @param ids 需要删除的banner列表ID
     * @return 结果
     */
    @Override
    public int deleteTbBannerByIds(Long[] ids)
    {
        return tbBannerMapper.deleteTbBannerByIds(ids);
    }

    /**
     * 删除banner列表信息
     * 
     * @param id banner列表ID
     * @return 结果
     */
    @Override
    public int deleteTbBannerById(Long id)
    {
        return tbBannerMapper.deleteTbBannerById(id);
    }
}
