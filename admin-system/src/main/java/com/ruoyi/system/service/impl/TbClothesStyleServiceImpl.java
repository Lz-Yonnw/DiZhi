package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TbClothesStyleMapper;
import com.ruoyi.system.domain.TbClothesStyle;
import com.ruoyi.system.service.ITbClothesStyleService;

/**
 * 爆款列表Service业务层处理
 * 
 * @author zzz
 * @date 2021-09-25
 */
@Service
public class TbClothesStyleServiceImpl implements ITbClothesStyleService 
{
    @Autowired
    private TbClothesStyleMapper tbClothesStyleMapper;

    /**
     * 查询爆款列表
     * 
     * @param id 爆款列表ID
     * @return 爆款列表
     */
    @Override
    public TbClothesStyle selectTbClothesStyleById(Long id)
    {
        return tbClothesStyleMapper.selectTbClothesStyleById(id);
    }

    /**
     * 查询爆款列表列表
     * 
     * @param tbClothesStyle 爆款列表
     * @return 爆款列表
     */
    @Override
    public List<TbClothesStyle> selectTbClothesStyleList(TbClothesStyle tbClothesStyle)
    {
        return tbClothesStyleMapper.selectTbClothesStyleList(tbClothesStyle);
    }

    /**
     * 新增爆款列表
     * 
     * @param tbClothesStyle 爆款列表
     * @return 结果
     */
    @Override
    public int insertTbClothesStyle(TbClothesStyle tbClothesStyle)
    {
        tbClothesStyle.setCreateTime(DateUtils.getNowDate());
        return tbClothesStyleMapper.insertTbClothesStyle(tbClothesStyle);
    }

    /**
     * 修改爆款列表
     * 
     * @param tbClothesStyle 爆款列表
     * @return 结果
     */
    @Override
    public int updateTbClothesStyle(TbClothesStyle tbClothesStyle)
    {
        tbClothesStyle.setUpdateTime(DateUtils.getNowDate());
        return tbClothesStyleMapper.updateTbClothesStyle(tbClothesStyle);
    }

    /**
     * 批量删除爆款列表
     * 
     * @param ids 需要删除的爆款列表ID
     * @return 结果
     */
    @Override
    public int deleteTbClothesStyleByIds(Long[] ids)
    {
        return tbClothesStyleMapper.deleteTbClothesStyleByIds(ids);
    }

    /**
     * 删除爆款列表信息
     * 
     * @param id 爆款列表ID
     * @return 结果
     */
    @Override
    public int deleteTbClothesStyleById(Long id)
    {
        return tbClothesStyleMapper.deleteTbClothesStyleById(id);
    }
}
