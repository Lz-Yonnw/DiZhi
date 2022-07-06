package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.TbOrderDerails;

/**
 * 订单详情Mapper接口
 * 
 * @author ruoyi
 * @date 2022-07-05
 */
public interface TbOrderDerailsMapper 
{
    /**
     * 查询订单详情
     * 
     * @param id 订单详情主键
     * @return 订单详情
     */
    public TbOrderDerails selectTbOrderDerailsById(Long id);

    /**
     * 查询订单详情列表
     * 
     * @param tbOrderDerails 订单详情
     * @return 订单详情集合
     */
    public List<TbOrderDerails> selectTbOrderDerailsList(TbOrderDerails tbOrderDerails);

    /**
     * 新增订单详情
     * 
     * @param tbOrderDerails 订单详情
     * @return 结果
     */
    public int insertTbOrderDerails(TbOrderDerails tbOrderDerails);

    /**
     * 修改订单详情
     * 
     * @param tbOrderDerails 订单详情
     * @return 结果
     */
    public int updateTbOrderDerails(TbOrderDerails tbOrderDerails);

    /**
     * 删除订单详情
     * 
     * @param id 订单详情主键
     * @return 结果
     */
    public int deleteTbOrderDerailsById(Long id);

    /**
     * 批量删除订单详情
     * 
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbOrderDerailsByIds(Long[] ids);
}
