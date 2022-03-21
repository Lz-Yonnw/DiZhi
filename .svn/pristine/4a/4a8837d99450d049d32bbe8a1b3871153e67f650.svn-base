package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TbWhiteDesignMapper;
import com.ruoyi.system.domain.TbWhiteDesign;
import com.ruoyi.system.service.ITbWhiteDesignService;

/**
 * 白膜Service业务层处理
 * 
 * @author zzz
 * @date 2021-10-25
 */
@Service
public class TbWhiteDesignServiceImpl implements ITbWhiteDesignService 
{
    @Autowired
    private TbWhiteDesignMapper tbWhiteDesignMapper;

    /**
     * 查询白膜
     * 
     * @param id 白膜ID
     * @return 白膜
     */
    @Override
    public TbWhiteDesign selectTbWhiteDesignById(Long id)
    {
        return tbWhiteDesignMapper.selectTbWhiteDesignById(id);
    }

    @Override
    public TbWhiteDesign selectTbWhiteDesignByStyleId(Long styleId) {
        return tbWhiteDesignMapper.selectTbWhiteDesignByStyleId(styleId);
    }

    /**
     * 查询白膜列表
     * 
     * @param tbWhiteDesign 白膜
     * @return 白膜
     */
    @Override
    public List<TbWhiteDesign> selectTbWhiteDesignList(TbWhiteDesign tbWhiteDesign)
    {
        return tbWhiteDesignMapper.selectTbWhiteDesignList(tbWhiteDesign);
    }

    /**
     * 新增白膜
     * 
     * @param tbWhiteDesign 白膜
     * @return 结果
     */
    @Override
    public int insertTbWhiteDesign(TbWhiteDesign tbWhiteDesign)
    {
        tbWhiteDesign.setCreateTime(DateUtils.getNowDate());
        return tbWhiteDesignMapper.insertTbWhiteDesign(tbWhiteDesign);
    }

    /**
     * 修改白膜
     * 
     * @param tbWhiteDesign 白膜
     * @return 结果
     */
    @Override
    public int updateTbWhiteDesign(TbWhiteDesign tbWhiteDesign)
    {
        tbWhiteDesign.setUpdateTime(DateUtils.getNowDate());
        return tbWhiteDesignMapper.updateTbWhiteDesign(tbWhiteDesign);
    }

    /**
     * 批量删除白膜
     * 
     * @param ids 需要删除的白膜ID
     * @return 结果
     */
    @Override
    public int deleteTbWhiteDesignByIds(Long[] ids)
    {
        return tbWhiteDesignMapper.deleteTbWhiteDesignByIds(ids);
    }

    /**
     * 删除白膜信息
     * 
     * @param id 白膜ID
     * @return 结果
     */
    @Override
    public int deleteTbWhiteDesignById(Long id)
    {
        return tbWhiteDesignMapper.deleteTbWhiteDesignById(id);
    }
}
