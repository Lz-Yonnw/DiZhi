package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.TbMallSpecsValue;

/**
 * 商品属性Mapper接口
 * 
 * @author liaozan8888@163.com
 * @date 2022-05-17
 */
public interface TbMallSpecsValueMapper 
{
    /**
     * 查询商品属性
     * 
     * @param id 商品属性主键
     * @return 商品属性
     */
    public TbMallSpecsValue selectTbMallSpecsValueById(Long id);

    /**
     * 查询商品属性列表
     * 
     * @param tbMallSpecsValue 商品属性
     * @return 商品属性集合
     */
    public List<TbMallSpecsValue> selectTbMallSpecsValueList(TbMallSpecsValue tbMallSpecsValue);

    /**
     * 新增商品属性
     * 
     * @param tbMallSpecsValue 商品属性
     * @return 结果
     */
    public int insertTbMallSpecsValue(TbMallSpecsValue tbMallSpecsValue);

    /**
     * 修改商品属性
     * 
     * @param tbMallSpecsValue 商品属性
     * @return 结果
     */
    public int updateTbMallSpecsValue(TbMallSpecsValue tbMallSpecsValue);

    /**
     * 删除商品属性
     * 
     * @param id 商品属性主键
     * @return 结果
     */
    public int deleteTbMallSpecsValueById(Long id);

    /**
     * 批量删除商品属性
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbMallSpecsValueByIds(Long[] ids);
}
