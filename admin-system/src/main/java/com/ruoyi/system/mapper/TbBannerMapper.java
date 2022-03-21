package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.TbBanner;

/**
 * banner列表Mapper接口
 * 
 * @author zzz
 * @date 2021-09-22
 */
public interface TbBannerMapper 
{
    /**
     * 查询banner列表
     * 
     * @param id banner列表ID
     * @return banner列表
     */
    public TbBanner selectTbBannerById(Long id);

    /**
     * 查询banner列表列表
     * 
     * @param tbBanner banner列表
     * @return banner列表集合
     */
    public List<TbBanner> selectTbBannerList(TbBanner tbBanner);

    /**
     * 新增banner列表
     * 
     * @param tbBanner banner列表
     * @return 结果
     */
    public int insertTbBanner(TbBanner tbBanner);

    /**
     * 修改banner列表
     * 
     * @param tbBanner banner列表
     * @return 结果
     */
    public int updateTbBanner(TbBanner tbBanner);

    /**
     * 删除banner列表
     * 
     * @param id banner列表ID
     * @return 结果
     */
    public int deleteTbBannerById(Long id);

    /**
     * 批量删除banner列表
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTbBannerByIds(Long[] ids);
}
