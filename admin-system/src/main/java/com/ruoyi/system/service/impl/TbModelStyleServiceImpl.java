package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TbModelStyleMapper;
import com.ruoyi.system.domain.TbModelStyle;
import com.ruoyi.system.service.ITbModelStyleService;

/**
 * 版型小类Service业务层处理
 * 
 * @author zzz
 * @date 2021-08-10
 */
@Service
public class TbModelStyleServiceImpl implements ITbModelStyleService 
{
    @Autowired
    private TbModelStyleMapper tbModelStyleMapper;

    /**
     * 查询版型小类
     * 
     * @param id 版型小类ID
     * @return 版型小类
     */
    @Override
    public TbModelStyle selectTbModelStyleById(Long id)
    {
        return tbModelStyleMapper.selectTbModelStyleById(id);
    }

    @Override
    public List<TbModelStyle> selectTbModelStyleByList(Long id) {
        return tbModelStyleMapper.selectTbModelStyleByList(id);
    }

    /**
     * 查询版型小类列表
     * 
     * @param tbModelStyle 版型小类
     * @return 版型小类
     */
    @Override
    public List<TbModelStyle> selectTbModelStyleList(TbModelStyle tbModelStyle)
    {
        return tbModelStyleMapper.selectTbModelStyleList(tbModelStyle);
    }

    /**
     * 新增版型小类
     * 
     * @param tbModelStyle 版型小类
     * @return 结果
     */
    @Override
    public int insertTbModelStyle(TbModelStyle tbModelStyle)
    {
        return tbModelStyleMapper.insertTbModelStyle(tbModelStyle);
    }

    /**
     * 修改版型小类
     * 
     * @param tbModelStyle 版型小类
     * @return 结果
     */
    @Override
    public int updateTbModelStyle(TbModelStyle tbModelStyle)
    {
        return tbModelStyleMapper.updateTbModelStyle(tbModelStyle);
    }

    /**
     * 批量删除版型小类
     * 
     * @param ids 需要删除的版型小类ID
     * @return 结果
     */
    @Override
    public int deleteTbModelStyleByIds(Long[] ids)
    {
        return tbModelStyleMapper.deleteTbModelStyleByIds(ids);
    }

    /**
     * 删除版型小类信息
     * 
     * @param id 版型小类ID
     * @return 结果
     */
    @Override
    public int deleteTbModelStyleById(Long id)
    {
        return tbModelStyleMapper.deleteTbModelStyleById(id);
    }
}
