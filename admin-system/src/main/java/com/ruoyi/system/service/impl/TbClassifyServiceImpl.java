package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TbClassifyMapper;
import com.ruoyi.system.domain.TbClassify;
import com.ruoyi.system.service.ITbClassifyService;

/**
 * 分类Service业务层处理
 * 
 * @author zzz
 * @date 2021-08-17
 */
@Service
public class TbClassifyServiceImpl implements ITbClassifyService 
{
    @Autowired
    private TbClassifyMapper tbClassifyMapper;

    /**
     * 查询分类
     * 
     * @param id 分类ID
     * @return 分类
     */
    @Override
    public TbClassify selectTbClassifyById(Long id)
    {
        return tbClassifyMapper.selectTbClassifyById(id);
    }

    /**
     * 查询分类列表
     * 
     * @param tbClassify 分类
     * @return 分类
     */
    @Override
    public List<TbClassify> selectTbClassifyList(TbClassify tbClassify)
    {
        return tbClassifyMapper.selectTbClassifyList(tbClassify);
    }

    @Override
    public List<TbClassify> selectTbClassifyListByPId(TbClassify tbClassify) {
        return tbClassifyMapper.selectTbClassifyListByPId(tbClassify);
    }

    /**
     * 新增分类
     * 
     * @param tbClassify 分类
     * @return 结果
     */
    @Override
    public int insertTbClassify(TbClassify tbClassify)
    {
        return tbClassifyMapper.insertTbClassify(tbClassify);
    }

    /**
     * 修改分类
     * 
     * @param tbClassify 分类
     * @return 结果
     */
    @Override
    public int updateTbClassify(TbClassify tbClassify)
    {
        return tbClassifyMapper.updateTbClassify(tbClassify);
    }

    /**
     * 批量删除分类
     * 
     * @param ids 需要删除的分类ID
     * @return 结果
     */
    @Override
    public int deleteTbClassifyByIds(Long[] ids)
    {
        return tbClassifyMapper.deleteTbClassifyByIds(ids);
    }

    /**
     * 删除分类信息
     * 
     * @param id 分类ID
     * @return 结果
     */
    @Override
    public int deleteTbClassifyById(Long id)
    {
        return tbClassifyMapper.deleteTbClassifyById(id);
    }
}
