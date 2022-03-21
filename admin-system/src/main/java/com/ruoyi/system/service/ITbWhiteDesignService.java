package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.TbWhiteDesign;

/**
 * 白膜Service接口
 * 
 * @author zzz
 * @date 2021-10-25
 */
public interface ITbWhiteDesignService 
{
    /**
     * 查询白膜
     * 
     * @param id 白膜ID
     * @return 白膜
     */
    public TbWhiteDesign selectTbWhiteDesignById(Long id);

    public TbWhiteDesign selectTbWhiteDesignByStyleId(Long styleId);

    /**
     * 查询白膜列表
     * 
     * @param tbWhiteDesign 白膜
     * @return 白膜集合
     */
    public List<TbWhiteDesign> selectTbWhiteDesignList(TbWhiteDesign tbWhiteDesign);

    /**
     * 新增白膜
     * 
     * @param tbWhiteDesign 白膜
     * @return 结果
     */
    public int insertTbWhiteDesign(TbWhiteDesign tbWhiteDesign);

    /**
     * 修改白膜
     * 
     * @param tbWhiteDesign 白膜
     * @return 结果
     */
    public int updateTbWhiteDesign(TbWhiteDesign tbWhiteDesign);

    /**
     * 批量删除白膜
     * 
     * @param ids 需要删除的白膜ID
     * @return 结果
     */
    public int deleteTbWhiteDesignByIds(Long[] ids);

    /**
     * 删除白膜信息
     * 
     * @param id 白膜ID
     * @return 结果
     */
    public int deleteTbWhiteDesignById(Long id);
}
