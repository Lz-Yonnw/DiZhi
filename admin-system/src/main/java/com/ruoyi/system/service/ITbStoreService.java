package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.TbStore;

/**
 * 店铺Service接口
 *
 * @author liaozan8888@163.com
 * @date 2022-05-06
 */
public interface ITbStoreService
{
    /**
     * 查询店铺
     *
     * @param id 店铺主键
     * @return 店铺
     */
    public TbStore selectTbStoreById(Long id);

    /**
     * 查询店铺列表
     *
     * @param tbStore 店铺
     * @return 店铺集合
     */
    public List<TbStore> selectTbStoreList(TbStore tbStore);

    /**
     * 新增店铺
     *
     * @param tbStore 店铺
     * @return 结果
     */
    public int insertTbStore(TbStore tbStore);

    /**
     * 修改店铺
     *
     * @param tbStore 店铺
     * @return 结果
     */
    public int updateTbStore(TbStore tbStore);

    /**
     * 批量删除店铺
     *
     * @param ids 需要删除的店铺主键集合
     * @return 结果
     */
    public int deleteTbStoreByIds(Long[] ids);

    /**
     * 删除店铺信息
     *
     * @param id 店铺主键
     * @return 结果
     */
    public int deleteTbStoreById(Long id);
}
