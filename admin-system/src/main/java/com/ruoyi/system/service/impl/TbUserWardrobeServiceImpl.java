package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.TbUserWardrobe;
import com.ruoyi.system.mapper.TbUserWardrobeMapper;
import com.ruoyi.system.service.ITbUserWardrobeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 衣柜列表Service业务层处理
 *
 * @author zzz
 * @date 2021-09-25
 */
@Service
public class TbUserWardrobeServiceImpl implements ITbUserWardrobeService
{
    @Autowired
    private TbUserWardrobeMapper tbUserWardrobeMapper;

    /**
     * 查询衣柜列表
     *
     * @param id 衣柜列表ID
     * @return 衣柜列表
     */
    @Override
    public TbUserWardrobe selectTbUserWardrobeById(Long id)
    {
        return tbUserWardrobeMapper.selectTbUserWardrobeById(id);
    }

    /**
     * 查询衣柜列表列表
     *
     * @param tbUserWardrobe 衣柜列表
     * @return 衣柜列表
     */
    @Override
    public List<TbUserWardrobe> selectTbUserWardrobeList(TbUserWardrobe tbUserWardrobe)
    {
        return tbUserWardrobeMapper.selectTbUserWardrobeList(tbUserWardrobe);
    }

    /**
     * 新增衣柜列表
     *
     * @param tbUserWardrobe 衣柜列表
     * @return 结果
     */
    @Override
    public int insertTbUserWardrobe(TbUserWardrobe tbUserWardrobe)
    {
        tbUserWardrobe.setCreateTime(DateUtils.getNowDate());
        return tbUserWardrobeMapper.insertTbUserWardrobe(tbUserWardrobe);
    }

    /**
     * 修改衣柜列表
     *
     * @param tbUserWardrobe 衣柜列表
     * @return 结果
     */
    @Override
    public int updateTbUserWardrobe(TbUserWardrobe tbUserWardrobe)
    {
        tbUserWardrobe.setUpdateTime(DateUtils.getNowDate());
        return tbUserWardrobeMapper.updateTbUserWardrobe(tbUserWardrobe);
    }

    /**
     * 批量删除衣柜列表
     *
     * @param ids 需要删除的衣柜列表ID
     * @return 结果
     */
    @Override
    public int deleteTbUserWardrobeByIds(Long[] ids)
    {
        return tbUserWardrobeMapper.deleteTbUserWardrobeByIds(ids);
    }

    /**
     * 删除衣柜列表信息
     *
     * @param id 衣柜列表ID
     * @return 结果
     */
    @Override
    public int deleteTbUserWardrobeById(Long id)
    {
        return tbUserWardrobeMapper.deleteTbUserWardrobeById(id);
    }
}
