package com.ruoyi.system.service;

import com.ruoyi.system.domain.TbMallSpecsValue;

import java.util.List;

/**
 * 商品属性Service接口
 *
 * @author liaozan8888@163.com
 * @date 2022-05-17
 */
public interface ITbMallSpecsValueService
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
     * 批量删除商品属性
     *
     * @param ids 需要删除的商品属性主键集合
     * @return 结果
     */
    public int deleteTbMallSpecsValueByIds(Long[] ids);

    /**
     * 删除商品属性信息
     *
     * @param id 商品属性主键
     * @return 结果
     */
    public int deleteTbMallSpecsValueById(Long id);

    /**
     * 规格属性层级查询  根据specsName查询
     * @param specsName
     * @return
     */
    public List<TbMallSpecsValue>  selectTbMallSpecsChildsBySpecsName(String specsName);

    /**
     * 规格属性层级查询
     * @param specsId 父id
     * @return
     */
    public List<TbMallSpecsValue> selectTbMallSpecsChildsBySpecsId(Long specsId);
}
