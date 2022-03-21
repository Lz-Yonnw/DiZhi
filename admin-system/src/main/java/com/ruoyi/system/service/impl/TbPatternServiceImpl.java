package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TbPatternMapper;
import com.ruoyi.system.domain.TbPattern;
import com.ruoyi.system.service.ITbPatternService;

/**
 * 材料图片Service业务层处理
 * 
 * @author zzz
 * @date 2021-09-13
 */
@Service
public class TbPatternServiceImpl implements ITbPatternService 
{
    @Autowired
    private TbPatternMapper tbPatternMapper;

    /**
     * 查询材料图片
     * 
     * @param id 材料图片ID
     * @return 材料图片
     */
    @Override
    public TbPattern selectTbPatternById(Long id)
    {
        return tbPatternMapper.selectTbPatternById(id);
    }

    @Override
    public List<TbPattern> selectTbPatternByAllClassPicture(TbPattern tbPattern) {
        return tbPatternMapper.selectTbPatternByAllClassPicture(tbPattern);
    }

    /**
     * 查询材料图片列表
     * 
     * @param tbPattern 材料图片
     * @return 材料图片
     */
    @Override
    public List<TbPattern> selectTbPatternList(TbPattern tbPattern)
    {
        return tbPatternMapper.selectTbPatternList(tbPattern);
    }

    /**
     * 新增材料图片
     * 
     * @param tbPattern 材料图片
     * @return 结果
     */
    @Override
    public int insertTbPattern(TbPattern tbPattern)
    {
        return tbPatternMapper.insertTbPattern(tbPattern);
    }

    /**
     * 修改材料图片
     * 
     * @param tbPattern 材料图片
     * @return 结果
     */
    @Override
    public int updateTbPattern(TbPattern tbPattern)
    {
        return tbPatternMapper.updateTbPattern(tbPattern);
    }

    /**
     * 批量删除材料图片
     * 
     * @param ids 需要删除的材料图片ID
     * @return 结果
     */
    @Override
    public int deleteTbPatternByIds(Long[] ids)
    {
        return tbPatternMapper.deleteTbPatternByIds(ids);
    }

    /**
     * 删除材料图片信息
     * 
     * @param id 材料图片ID
     * @return 结果
     */
    @Override
    public int deleteTbPatternById(Long id)
    {
        return tbPatternMapper.deleteTbPatternById(id);
    }
}
