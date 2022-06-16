package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.TbRegion;

/**
 * 地址Mapper接口
 *
 * @author liaozan8888@163.com
 * @date 2022-05-16
 */
public interface TbRegionMapper
{
    /**
     * 查询地址
     *
     * @param id 地址主键
     * @return 地址
     */
    public TbRegion selectTbRegionById(Long id);

    /**
     * 查询地址列表
     *
     * @param tbRegion 地址
     * @return 地址集合
     */
    public List<TbRegion> selectTbRegionList(TbRegion tbRegion);

    /**
     * 新增地址
     *
     * @param tbRegion 地址
     * @return 结果
     */
    public int insertTbRegion(TbRegion tbRegion);

    /**
     * 修改地址
     *
     * @param tbRegion 地址
     * @return 结果
     */
    public int updateTbRegion(TbRegion tbRegion);

    /**
     * 删除地址
     *
     * @param id 地址主键
     * @return 结果
     */
    public int deleteTbRegionById(Long id);

    /**
     * 批量删除地址
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbRegionByIds(Long[] ids);
}
