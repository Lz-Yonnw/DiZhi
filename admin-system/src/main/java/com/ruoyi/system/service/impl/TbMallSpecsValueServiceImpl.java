package com.ruoyi.system.service.impl;

import com.ruoyi.common.core.redis.RedisCache;
import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.TbMallSpecsValue;
import com.ruoyi.system.mapper.TbMallSpecsValueMapper;
import com.ruoyi.system.service.ITbMallSpecsValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * 商品属性Service业务层处理
 *
 * @author liaozan8888@163.com
 * @date 2022-05-17
 */
@Service
public class TbMallSpecsValueServiceImpl implements ITbMallSpecsValueService
{
    @Autowired
    private TbMallSpecsValueMapper tbMallSpecsValueMapper;

    @Autowired
    private RedisCache redisCache;

    //SKU属性集合
    private String SPECS_VALUE_LIST = "SPECS_VALUE_LIST";

    /**
     * 查询商品属性
     *
     * @param id 商品属性主键
     * @return 商品属性
     */
    @Override
    public TbMallSpecsValue selectTbMallSpecsValueById(Long id)
    {
        return tbMallSpecsValueMapper.selectTbMallSpecsValueById(id);
    }

    /**
     * 查询商品属性列表
     *
     * @param tbMallSpecsValue 商品属性
     * @return 商品属性
     */
    @Override
    public List<TbMallSpecsValue> selectTbMallSpecsValueList(TbMallSpecsValue tbMallSpecsValue)
    {
        return tbMallSpecsValueMapper.selectTbMallSpecsValueList(tbMallSpecsValue);
    }

    /**
     * 新增商品属性
     *
     * @param tbMallSpecsValue 商品属性
     * @return 结果
     */
    @Override
    public int insertTbMallSpecsValue(TbMallSpecsValue tbMallSpecsValue)
    {
        tbMallSpecsValue.setCreateTime(DateUtils.getNowDate());
        return tbMallSpecsValueMapper.insertTbMallSpecsValue(tbMallSpecsValue);
    }

    /**
     * 修改商品属性
     *
     * @param tbMallSpecsValue 商品属性
     * @return 结果
     */
    @Override
    public int updateTbMallSpecsValue(TbMallSpecsValue tbMallSpecsValue)
    {
        return tbMallSpecsValueMapper.updateTbMallSpecsValue(tbMallSpecsValue);
    }

    /**
     * 批量删除商品属性
     *
     * @param ids 需要删除的商品属性主键
     * @return 结果
     */
    @Override
    public int deleteTbMallSpecsValueByIds(Long[] ids)
    {
        return tbMallSpecsValueMapper.deleteTbMallSpecsValueByIds(ids);
    }

    /**
     * 删除商品属性信息
     *
     * @param id 商品属性主键
     * @return 结果
     */
    @Override
    public int deleteTbMallSpecsValueById(Long id)
    {
        return tbMallSpecsValueMapper.deleteTbMallSpecsValueById(id);
    }

    /**
     * 规格属性层级查询  根据specsName查询
     * @param specsName
     * @return
     */
    @Override
    public List<TbMallSpecsValue> selectTbMallSpecsChildsBySpecsName(String specsName) {
        return null;
    }

    /**
     * 规格属性层级查询
     * @param specsId 父id
     * @return
     */
    @Override
    public List<TbMallSpecsValue> selectTbMallSpecsChildsBySpecsId(Long specsId) {
        try {
            //返回结果集
            List<TbMallSpecsValue> tbMallSpecsValueList = new ArrayList<>();
            //查询结果集
            List<TbMallSpecsValue> tbMallSpecsValues = new ArrayList<>();
            //规格属性存放redis中
            if(redisCache.getCacheList(SPECS_VALUE_LIST).size()>0){
                tbMallSpecsValues = redisCache.getCacheList(SPECS_VALUE_LIST);
            }else {
                tbMallSpecsValues = tbMallSpecsValueMapper.selectTbMallSpecsValueList(null);
                //把分类规格属性存入缓存中
                redisCache.setCacheList(SPECS_VALUE_LIST,tbMallSpecsValues);
            }
            //过滤条件
            tbMallSpecsValues.forEach((tbMallSpecsValue -> {
                if(tbMallSpecsValue.getSpecsId()==specsId){
                    tbMallSpecsValueList.add(tbMallSpecsValue);
                }
            }));
            return tbMallSpecsValueList;
        }catch (Exception e){
            e.printStackTrace();
            System.out.println("获取规格属性异常！");
        }
        return null;
    }
}
