package com.ruoyi.system.service;

import com.ruoyi.system.domain.TbMallSku;

import java.util.List;

/**
 * 商品规格（SKU）Service接口
 *
 * @author liaozan8888@163.com
 * @date 2022-05-17
 */
public interface ITbMallSkuService
{

    /**
     * 添加sku规格属性
     * @return
     */
    public int insertTbMallSkuSpecsValue(TbMallSku tbMallSku);

    /**
     * 获取Sku 属性
     * @param mallId 商品Id
     * @return
     */
    public List<TbMallSku> selectTbMallSpecsValueByMallid(Long mallId);

    /**
     * 查询商品规格（SKU）
     *
     * @param id 商品规格（SKU）主键
     * @return 商品规格（SKU）
     */
    public TbMallSku selectTbMallSkuById(Long id);

    /**
     * 查询商品规格（SKU）列表
     *
     * @param tbMallSku 商品规格（SKU）
     * @return 商品规格（SKU）集合
     */
    public List<TbMallSku> selectTbMallSkuList(TbMallSku tbMallSku);

    /**
     * 新增商品规格（SKU）
     *
     * @param tbMallSku 商品规格（SKU）
     * @return 结果
     */
    public int insertTbMallSku(TbMallSku tbMallSku);

    /**
     * 修改商品规格（SKU）
     *
     * @param tbMallSku 商品规格（SKU）
     * @return 结果
     */
    public int updateTbMallSku(TbMallSku tbMallSku);

    /**
     * 批量删除商品规格（SKU）
     *
     * @param ids 需要删除的商品规格（SKU）主键集合
     * @return 结果
     */
    public int deleteTbMallSkuByIds(Long[] ids);

    /**
     * 删除商品规格（SKU）信息
     *
     * @param id 商品规格（SKU）主键
     * @return 结果
     */
    public int deleteTbMallSkuById(Long id);
}
