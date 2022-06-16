package com.ruoyi.system.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.TbMallSku;
import com.ruoyi.system.domain.TbMallSpecsValue;
import com.ruoyi.system.mapper.TbMallSkuMapper;
import com.ruoyi.system.mapper.TbMallSpecsValueMapper;
import com.ruoyi.system.service.ITbMallSkuService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

/**
 * 商品规格（SKU）Service业务层处理
 *
 * @author liaozan8888@163.com
 * @date 2022-05-17
 */
@Service
public class TbMallSkuServiceImpl implements ITbMallSkuService
{
    @Autowired
    private TbMallSkuMapper tbMallSkuMapper;

    @Autowired
    private TbMallSpecsValueMapper tbMallSpecsValueMapper;


    /**
     * 添加SKU 属性
     * @param tbMallSku
     * @return
     */
    @Override
    public int insertTbMallSkuSpecsValue(TbMallSku tbMallSku) {
        tbMallSku.setStatus(1);
        tbMallSku.setCreateTime(new Date());
        //获取到当前商品SKU
        TbMallSku sku = new TbMallSku();
        sku.setMallId(tbMallSku.getMallId());
        List<TbMallSku> tbMallSkus = tbMallSkuMapper.selectTbMallSkuList(sku);

        for(TbMallSku item : tbMallSkus){
            //判断当前SKU 属性是否存在
            if(item.getSpecsData().contains(tbMallSku.getSpecsData())){
                item.setAmount(item.getAmount()+tbMallSku.getAmount()); // 库存累加
                item.setPrice(item.getPrice().add(tbMallSku.getPrice())); // 进而累加
                tbMallSkuMapper.updateTbMallSku(item);
                return 1;
            }
        }
        //SKU 属性不存在 添加SKU属性
        tbMallSkuMapper.insertTbMallSku(tbMallSku);
        return 1;
    }

    /**
     * 获取Sku 属性
     * @param mallId 商品Id
     * @return
     */
    @Override
    public List<TbMallSku> selectTbMallSpecsValueByMallid(Long mallId) {
        //当前商品SKU
        TbMallSku tbMallSku = new TbMallSku();
        tbMallSku.setMallId(mallId);
        tbMallSku.setStatus(1);
        List<TbMallSku> tbMallSkus = tbMallSkuMapper.selectTbMallSkuList(tbMallSku);
        tbMallSkus.forEach(item->{
            item.setSpecsValueIdJSon(getParsingSpecsValueJson(item.getSpecsData()));
        });
        return tbMallSkus;
    }
    /**
     * 解析sku多属性字符
     * @param specsData 规格属性字符
     * @return
     */
    private String getParsingSpecsValueJson(String specsData){
        //规格属性键值 黑色-M,.....
        String[] split = specsData.split(",");
        HashMap<String,String> hashMap = new HashMap<>();
        for (String specsStr : split){
            //规格属性Id
            String[] specsArray = specsStr.split("-");
            //规格属性Key
            TbMallSpecsValue tbMallSpecsKey = tbMallSpecsValueMapper.selectTbMallSpecsValueById(Long.parseLong(specsArray[0]));
            //规格属性Value
            TbMallSpecsValue tbMallSpecsValue = tbMallSpecsValueMapper.selectTbMallSpecsValueById(Long.parseLong(specsArray[1]));
            hashMap.put(tbMallSpecsKey.getSpecsName(),tbMallSpecsValue.getSpecsName());
        }
        return JSONObject.toJSONString(hashMap);
    }

    /**
     * 查询商品规格（SKU）
     *
     * @param id 商品规格（SKU）主键
     * @return 商品规格（SKU）
     */
    @Override
    public TbMallSku selectTbMallSkuById(Long id)
    {
        TbMallSku tbMallSku = tbMallSkuMapper.selectTbMallSkuById(id);
        tbMallSku.setSpecsValueIdJSon(getParsingSpecsValueJson(tbMallSku.getSpecsData()));
        return tbMallSku;
    }

    /**
     * 查询商品规格（SKU）列表
     *
     * @param tbMallSku 商品规格（SKU）
     * @return 商品规格（SKU）
     */
    @Override
    public List<TbMallSku> selectTbMallSkuList(TbMallSku tbMallSku)
    {
        return tbMallSkuMapper.selectTbMallSkuList(tbMallSku);
    }

    /**
     * 新增商品规格（SKU）
     *
     * @param tbMallSku 商品规格（SKU）
     * @return 结果
     */
    @Override
    public int insertTbMallSku(TbMallSku tbMallSku)
    {
        tbMallSku.setCreateTime(DateUtils.getNowDate());
        return tbMallSkuMapper.insertTbMallSku(tbMallSku);
    }

    /**
     * 修改商品规格（SKU）
     *
     * @param tbMallSku 商品规格（SKU）
     * @return 结果
     */
    @Override
    public int updateTbMallSku(TbMallSku tbMallSku)
    {
        tbMallSku.setUpdateTime(DateUtils.getNowDate());
        return tbMallSkuMapper.updateTbMallSku(tbMallSku);
    }

    /**
     * 批量删除商品规格（SKU）
     *
     * @param ids 需要删除的商品规格（SKU）主键
     * @return 结果
     */
    @Override
    public int deleteTbMallSkuByIds(Long[] ids)
    {
        return tbMallSkuMapper.deleteTbMallSkuByIds(ids);
    }

    /**
     * 删除商品规格（SKU）信息
     *
     * @param id 商品规格（SKU）主键
     * @return 结果
     */
    @Override
    public int deleteTbMallSkuById(Long id)
    {
        return tbMallSkuMapper.deleteTbMallSkuById(id);
    }
}
