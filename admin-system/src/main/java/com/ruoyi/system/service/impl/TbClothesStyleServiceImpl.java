package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.TbClothesStyle;
import com.ruoyi.system.mapper.TbClothesStyleMapper;
import com.ruoyi.system.service.ITbClothesStyleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
     * 查询衣服爆款
     *
     * @param id 衣服爆款主键
     * @return 衣服爆款
     */
    @Override
    public TbClothesStyle selectTbClothesStyleById(Long id)
    {
        return tbClothesStyleMapper.selectTbClothesStyleById(id);
    }

    /**
     * 查询衣服爆款列表
     *
     * @param tbClothesStyle 衣服爆款
     * @return 衣服爆款
     */
    @Override
    public List<TbClothesStyle> selectTbClothesStyleList(TbClothesStyle tbClothesStyle)
    {
        return tbClothesStyleMapper.selectTbClothesStyleList(tbClothesStyle);
    }

    /**
     * 新增衣服爆款
     *
     * @param tbClothesStyle 衣服爆款
     * @return 结果
     */
    @Override
    public int insertTbClothesStyle(TbClothesStyle tbClothesStyle)
    {
        tbClothesStyle.setCreateTime(DateUtils.getNowDate());
        return tbClothesStyleMapper.insertTbClothesStyle(tbClothesStyle);
    }

    /**
     * 修改衣服爆款
     *
     * @param tbClothesStyle 衣服爆款
     * @return 结果
     */
    @Override
    public int updateTbClothesStyle(TbClothesStyle tbClothesStyle)
    {
        tbClothesStyle.setUpdateTime(DateUtils.getNowDate());
        return tbClothesStyleMapper.updateTbClothesStyle(tbClothesStyle);
    }

    /**
     * 批量删除衣服爆款
     *
     * @param ids 需要删除的衣服爆款主键
     * @return 结果
     */
    @Override
    public int deleteTbClothesStyleByIds(Long[] ids)
    {
        return tbClothesStyleMapper.deleteTbClothesStyleByIds(ids);
    }

    /**
     * 删除衣服爆款信息
     *
     * @param id 衣服爆款主键
     * @return 结果
     */
    @Override
    public int deleteTbClothesStyleById(Long id)
    {
        return tbClothesStyleMapper.deleteTbClothesStyleById(id);
    }
}
