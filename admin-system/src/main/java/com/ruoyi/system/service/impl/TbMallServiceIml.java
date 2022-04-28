package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.TbCollection;
import com.ruoyi.system.domain.TbMall;
import com.ruoyi.system.domain.TbMallImgs;
import com.ruoyi.system.mapper.TbCollectionMapper;
import com.ruoyi.system.mapper.TbMallImgsMapper;
import com.ruoyi.system.mapper.TbMallMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 商品接口实现
 */
@Service
public class TbMallServiceIml implements com.ruoyi.system.service.TbMallService {
    @Autowired
    private TbMallMapper tbMallMapper;
    @Autowired
    private TbCollectionMapper tbCollectionMapper;
    @Autowired
    private TbMallImgsMapper tbMallImgsMapper;

    /**
     * 展示全部商品
     * @return
     */
    @Override
    public List<TbMall> selectTbMallList() {

        return tbMallMapper.selectList();

    }


    /**
     * 商品收藏
     * @param tbCollection
     */
    @Override
    public void updataStatus(TbCollection tbCollection) {
        if(tbCollection==null){
            throw new RuntimeException("无效参数");
        }
        if (tbCollection.getStatus()==1){
            TbCollection tbCollection1 = tbCollectionMapper.selectone(tbCollection);
            if (tbCollection1==null){
                tbCollectionMapper.insertOne(tbCollection);
                return;
            }
        }
        tbCollectionMapper.updataStatus(tbCollection);
    }

    /**
     * 根据用户id查询收藏的商品
     */
    @Override
    public TbCollection selectTbCollection(Long id,Long mallId) {

        return tbCollectionMapper.selectone(id,mallId);
    }


    /**
     * 展示用户的全部收藏
     */
    @Override
    public List<TbMall> selectTbMallListStatus(Long id) {
        List<Long> mallIds = tbCollectionMapper.selectMallIds(id);
        List<TbMall> mallList = tbMallMapper.selectListMall(mallIds);
        return mallList;
    }


    /**
     * 查询单个商品的·详细信息
     */
    @Override
    public List<TbMallImgs> selectTbMallImgs(Long id) {
        return tbMallImgsMapper.selectMallId(id);

    }

}
