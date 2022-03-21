package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.TbDesignRecommend;

/**
 * 设计推荐Mapper接口
 * 
 * @author zzz
 * @date 2021-10-09
 */
public interface TbDesignRecommendMapper 
{
    /**
     * 查询设计推荐
     * 
     * @param id 设计推荐ID
     * @return 设计推荐
     */
    public TbDesignRecommend selectTbDesignRecommendById(Long id);

    /**
     * 查询设计推荐列表
     * 
     * @param tbDesignRecommend 设计推荐
     * @return 设计推荐集合
     */
    public List<TbDesignRecommend> selectTbDesignRecommendList(TbDesignRecommend tbDesignRecommend);

    /**
     * 新增设计推荐
     * 
     * @param tbDesignRecommend 设计推荐
     * @return 结果
     */
    public int insertTbDesignRecommend(TbDesignRecommend tbDesignRecommend);

    /**
     * 修改设计推荐
     * 
     * @param tbDesignRecommend 设计推荐
     * @return 结果
     */
    public int updateTbDesignRecommend(TbDesignRecommend tbDesignRecommend);

    /**
     * 删除设计推荐
     * 
     * @param id 设计推荐ID
     * @return 结果
     */
    public int deleteTbDesignRecommendById(Long id);

    /**
     * 批量删除设计推荐
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTbDesignRecommendByIds(Long[] ids);
}
