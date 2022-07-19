package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TbUserLevelMapper;
import com.ruoyi.system.domain.TbUserLevel;
import com.ruoyi.system.service.ITbUserLevelService;

/**
 * 用户等级Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-07-18
 */
@Service
public class TbUserLevelServiceImpl implements ITbUserLevelService 
{
    @Autowired
    private TbUserLevelMapper tbUserLevelMapper;

    /**
     * 查询用户等级
     * 
     * @param id 用户等级主键
     * @return 用户等级
     */
    @Override
    public TbUserLevel selectTbUserLevelById(Long id)
    {
        return tbUserLevelMapper.selectTbUserLevelById(id);
    }

    /**
     * 查询用户等级列表
     * 
     * @param tbUserLevel 用户等级
     * @return 用户等级
     */
    @Override
    public List<TbUserLevel> selectTbUserLevelList(TbUserLevel tbUserLevel)
    {
        return tbUserLevelMapper.selectTbUserLevelList(tbUserLevel);
    }

    /**
     * 新增用户等级
     * 
     * @param tbUserLevel 用户等级
     * @return 结果
     */
    @Override
    public int insertTbUserLevel(TbUserLevel tbUserLevel)
    {
        return tbUserLevelMapper.insertTbUserLevel(tbUserLevel);
    }

    /**
     * 修改用户等级
     * 
     * @param tbUserLevel 用户等级
     * @return 结果
     */
    @Override
    public int updateTbUserLevel(TbUserLevel tbUserLevel)
    {
        return tbUserLevelMapper.updateTbUserLevel(tbUserLevel);
    }

    /**
     * 批量删除用户等级
     * 
     * @param ids 需要删除的用户等级主键
     * @return 结果
     */
    @Override
    public int deleteTbUserLevelByIds(Long[] ids)
    {
        return tbUserLevelMapper.deleteTbUserLevelByIds(ids);
    }

    /**
     * 删除用户等级信息
     * 
     * @param id 用户等级主键
     * @return 结果
     */
    @Override
    public int deleteTbUserLevelById(Long id)
    {
        return tbUserLevelMapper.deleteTbUserLevelById(id);
    }
}
