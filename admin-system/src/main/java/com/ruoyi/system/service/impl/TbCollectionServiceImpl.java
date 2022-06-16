package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.TbCollection;
import com.ruoyi.system.mapper.TbCollectionMapper;
import com.ruoyi.system.mapper.TbMallMapper;
import com.ruoyi.system.mapper.TbUserInfoMapper;
import com.ruoyi.system.service.ITbCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "TbCollectionServiceImpl")
public class TbCollectionServiceImpl implements ITbCollectionService {

    @Autowired
    private TbCollectionMapper tbCollectionMapper;

    @Autowired
    private TbMallMapper tbMallMapper;

    @Autowired
    private TbUserInfoMapper tbUserInfoMapper;

    /**
     * 查询收藏列表
     *
     * @param tbCollection 收藏
     * @return 收藏集合
     */
    @Override
    public List<TbCollection> selectTbCollectionList(TbCollection tbCollection) {
        List<TbCollection> tbCollections = tbCollectionMapper.selectTbCollectionList(tbCollection);
        tbCollections.forEach(item->{
            //商品
            item.setMallName(tbMallMapper.selectTbMallById(item.getMallId()).getName());
            //用户
            item.setUserName(tbUserInfoMapper.selectTbUserInfoById(item.getUserId()).getUserNickname());
        });
        return tbCollections;
    }

    /**
     * 批量删除收藏
     *
     * @param ids 需要删除的收藏主键集合
     * @return 结果
     */
    @Override
    public int deleteTbCollectionByIds(Long[] ids) {
        return tbCollectionMapper.deleteTbCollectionByIds(ids);
    }
}
