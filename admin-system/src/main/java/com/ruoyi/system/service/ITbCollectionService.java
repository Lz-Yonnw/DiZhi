package com.ruoyi.system.service;

import com.ruoyi.system.domain.TbCollection;

import java.util.List;

/**
 * 收藏Service接口
 *
 * @author ruoyi
 * @date 2022-05-23
 */
public interface ITbCollectionService
{
    /**
     * 查询收藏列表
     *
     * @param tbCollection 收藏
     * @return 收藏集合
     */
    public List<TbCollection> selectTbCollectionList(TbCollection tbCollection);


    /**
     * 批量删除收藏
     *
     * @param ids 需要删除的收藏主键集合
     * @return 结果
     */
    public int deleteTbCollectionByIds(Long[] ids);
}
