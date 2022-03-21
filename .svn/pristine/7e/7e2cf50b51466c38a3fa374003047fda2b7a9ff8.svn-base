package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TbDesignRecommendMapper;
import com.ruoyi.system.domain.TbDesignRecommend;
import com.ruoyi.system.service.ITbDesignRecommendService;

/**
 * 设计推荐Service业务层处理
 * 
 * @author zzz
 * @date 2021-10-09
 */
@Service
public class TbDesignRecommendServiceImpl implements ITbDesignRecommendService 
{
    @Autowired
    private TbDesignRecommendMapper tbDesignRecommendMapper;

    /**
     * 查询设计推荐
     * 
     * @param id 设计推荐ID
     * @return 设计推荐
     */
    @Override
    public TbDesignRecommend selectTbDesignRecommendById(Long id)
    {
        return tbDesignRecommendMapper.selectTbDesignRecommendById(id);
    }

    /**
     * 查询设计推荐列表
     * 
     * @param tbDesignRecommend 设计推荐
     * @return 设计推荐
     */
    @Override
    public List<TbDesignRecommend> selectTbDesignRecommendList(TbDesignRecommend tbDesignRecommend)
    {
        return tbDesignRecommendMapper.selectTbDesignRecommendList(tbDesignRecommend);
    }

    /**
     * 新增设计推荐
     * 
     * @param tbDesignRecommend 设计推荐
     * @return 结果
     */
    @Override
    public int insertTbDesignRecommend(TbDesignRecommend tbDesignRecommend)
    {
        tbDesignRecommend.setCreateTime(DateUtils.getNowDate());
        return tbDesignRecommendMapper.insertTbDesignRecommend(tbDesignRecommend);
    }

    /**
     * 修改设计推荐
     * 
     * @param tbDesignRecommend 设计推荐
     * @return 结果
     */
    @Override
    public int updateTbDesignRecommend(TbDesignRecommend tbDesignRecommend)
    {
        tbDesignRecommend.setUpdateTime(DateUtils.getNowDate());
        return tbDesignRecommendMapper.updateTbDesignRecommend(tbDesignRecommend);
    }

    /**
     * 批量删除设计推荐
     * 
     * @param ids 需要删除的设计推荐ID
     * @return 结果
     */
    @Override
    public int deleteTbDesignRecommendByIds(Long[] ids)
    {
        return tbDesignRecommendMapper.deleteTbDesignRecommendByIds(ids);
    }

    /**
     * 删除设计推荐信息
     * 
     * @param id 设计推荐ID
     * @return 结果
     */
    @Override
    public int deleteTbDesignRecommendById(Long id)
    {
        return tbDesignRecommendMapper.deleteTbDesignRecommendById(id);
    }
}
