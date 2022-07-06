package com.ruoyi.system.service.impl;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.ruoyi.system.mapper.TbOrderDerailsMapper;
import com.ruoyi.system.domain.TbOrderDerails;
import com.ruoyi.system.service.ITbOrderDerailsService;

/**
 * 订单详情Service业务层处理
 * 
 * @author ruoyi
 * @date 2022-07-05
 */
@Service
public class TbOrderDerailsServiceImpl implements ITbOrderDerailsService 
{
    @Autowired
    private TbOrderDerailsMapper tbOrderDerailsMapper;

    /**
     * 查询订单详情
     * 
     * @param id 订单详情主键
     * @return 订单详情
     */
    @Override
    public TbOrderDerails selectTbOrderDerailsById(Long id)
    {
        return tbOrderDerailsMapper.selectTbOrderDerailsById(id);
    }

    /**
     * 查询订单详情列表
     * 
     * @param tbOrderDerails 订单详情
     * @return 订单详情
     */
    @Override
    public List<TbOrderDerails> selectTbOrderDerailsList(TbOrderDerails tbOrderDerails)
    {
        return tbOrderDerailsMapper.selectTbOrderDerailsList(tbOrderDerails);
    }

    /**
     * 新增订单详情
     * 
     * @param tbOrderDerails 订单详情
     * @return 结果
     */
    @Override
    public int insertTbOrderDerails(TbOrderDerails tbOrderDerails)
    {
        return tbOrderDerailsMapper.insertTbOrderDerails(tbOrderDerails);
    }

    /**
     * 修改订单详情
     * 
     * @param tbOrderDerails 订单详情
     * @return 结果
     */
    @Override
    public int updateTbOrderDerails(TbOrderDerails tbOrderDerails)
    {
        return tbOrderDerailsMapper.updateTbOrderDerails(tbOrderDerails);
    }

    /**
     * 批量删除订单详情
     * 
     * @param ids 需要删除的订单详情主键
     * @return 结果
     */
    @Override
    public int deleteTbOrderDerailsByIds(Long[] ids)
    {
        return tbOrderDerailsMapper.deleteTbOrderDerailsByIds(ids);
    }

    /**
     * 删除订单详情信息
     * 
     * @param id 订单详情主键
     * @return 结果
     */
    @Override
    public int deleteTbOrderDerailsById(Long id)
    {
        return tbOrderDerailsMapper.deleteTbOrderDerailsById(id);
    }
}
