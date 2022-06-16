package com.ruoyi.system.service.impl;

import java.util.List;
import com.ruoyi.common.utils.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TbStoreMapper;
import com.ruoyi.system.domain.TbStore;
import com.ruoyi.system.service.ITbStoreService;

/**
 * 店铺Service业务层处理
 * 
 * @author liaozan8888@163.com
 * @date 2022-05-06
 */
@Service
public class TbStoreServiceImpl implements ITbStoreService 
{
    @Autowired
    private TbStoreMapper tbStoreMapper;

    /**
     * 查询店铺
     * 
     * @param id 店铺主键
     * @return 店铺
     */
    @Override
    public TbStore selectTbStoreById(Long id)
    {
        return tbStoreMapper.selectTbStoreById(id);
    }

    /**
     * 查询店铺列表
     * 
     * @param tbStore 店铺
     * @return 店铺
     */
    @Override
    public List<TbStore> selectTbStoreList(TbStore tbStore)
    {
        return tbStoreMapper.selectTbStoreList(tbStore);
    }

    /**
     * 新增店铺
     * 
     * @param tbStore 店铺
     * @return 结果
     */
    @Override
    public int insertTbStore(TbStore tbStore)
    {
        return tbStoreMapper.insertTbStore(tbStore);
    }

    /**
     * 修改店铺
     * 
     * @param tbStore 店铺
     * @return 结果
     */
    @Override
    public int updateTbStore(TbStore tbStore)
    {
        tbStore.setUpdateTime(DateUtils.getNowDate());
        return tbStoreMapper.updateTbStore(tbStore);
    }

    /**
     * 批量删除店铺
     * 
     * @param ids 需要删除的店铺主键
     * @return 结果
     */
    @Override
    public int deleteTbStoreByIds(Long[] ids)
    {
        return tbStoreMapper.deleteTbStoreByIds(ids);
    }

    /**
     * 删除店铺信息
     * 
     * @param id 店铺主键
     * @return 结果
     */
    @Override
    public int deleteTbStoreById(Long id)
    {
        return tbStoreMapper.deleteTbStoreById(id);
    }
}
