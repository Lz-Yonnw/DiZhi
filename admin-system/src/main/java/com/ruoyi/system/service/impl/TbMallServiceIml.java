package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.TbCollection;
import com.ruoyi.system.domain.TbMall;
import com.ruoyi.system.domain.TbMallImgs;
import com.ruoyi.system.domain.TbMallSku;
import com.ruoyi.system.mapper.TbCollectionMapper;
import com.ruoyi.system.mapper.TbMallImgsMapper;
import com.ruoyi.system.mapper.TbMallMapper;
import com.ruoyi.system.service.ITbMallSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

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

    @Autowired
    private ITbMallSkuService iTbMallSkuService;


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

        TbCollection tbCollection = new TbCollection();
        tbCollection.setUserId(id);
        tbCollection.setMallId(mallId);

        return tbCollectionMapper.selectone(tbCollection);
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

    /**
     * 查询商品列表
     *
     * @param tbMall 商品
     * @return 商品
     */
    @Override
    public List<TbMall> selectTbMallList(TbMall tbMall)
    {
        return tbMallMapper.selectTbMallList(tbMall);
    }
    /**
     * 商品详情
     * @param id 订单信息
    主键
     * @return
     */
    @Override
    public TbMall selectTbMallById(Long id) {
        try {
            TbMall tbMall = tbMallMapper.selectTbMallById(id);
            //商品图片详情
            List<TbMallImgs> tbMallImgs = selectTbMallImgs(id);
            tbMall.setImgsList(tbMallImgs);
            //SKU 规格属性
            List<TbMallSku> tbMallSkus = iTbMallSkuService.selectTbMallSpecsValueByMallid(id);
            tbMall.setTbMallSkuList(tbMallSkus);
            return tbMall;
        }catch (RuntimeException e){
            throw new RuntimeException("数据查询异常");
        }
    }

    /**
     * 添加商品
     * @param tbMall 商品
     * @param tbMallImagJson 详情图JSON
     * @param skuJson skuJSON
     * @return
     */
    @Override
    @Transactional
    public int insertTbMall(TbMall tbMall,String tbMallImagJson,String skuJson)
    {
            tbMall.setMallType("卫衣");
            tbMall.setCreateTime(DateUtils.getNowDate());
            tbMallMapper.insertTbMall(tbMall);
            //添加商品详情图
            List<Map> maps = JSONArray.parseArray(tbMallImagJson, Map.class);
            maps.forEach(item->{
                item.put("mallId",tbMall.getId());
            });
            int i = tbMallImgsMapper.insertTbMallImagsList(maps);
            if(i>0){
                return 1;
            }
            throw new RuntimeException("数据添加异常");
    }

    /**
     * 修改商品
     *
     * @param tbMall 商品
     * @return 结果
     */
    @Override
    @Transactional
    public int updateTbMall(TbMall tbMall,String tbMallImagJson,String skuJson)
    {
        //商品修改
        tbMall.setUpdateTime(DateUtils.getNowDate());
        tbMallMapper.updateMallById(tbMall);
        //商品详情图修改
        List<Map> maps = JSONArray.parseArray(tbMallImagJson, Map.class);
        for(int i =0;i<maps.size();i++){
            maps.get(i).put("mallId",tbMall.getId());
        }
        tbMallImgsMapper.deleteTbMallImgsByMallId(tbMall.getId());
        if(maps.size()>0){
            tbMallImgsMapper.insertTbMallImagsList(maps);
            return 1;
        }
        return 1;
    }

    /**
     * 批量删除商品
     *
     * @param ids 需要删除的商品主键
     * @return 结果
     */
    @Override
    public int deleteTbMallByIds(Long[] ids)
    {
        return tbMallMapper.deleteTbMallByIds(ids);
    }

    /**
     * 删除商品信息
     *
     * @param id 商品主键
     * @return 结果
     */
    @Override
    public int deleteTbMallById(Long id)
    {
        return tbMallMapper.deleteTbMallById(id);
    }

}
