package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.TbPlateCutWay;

import java.util.List;

/**
 * 板块小类-裁剪方式Mapper接口
 * 
 * @author zzz
 * @date 2021-08-10
 */
public interface TbPlateCutWayMapper 
{
    /**
     * 查询板块小类-裁剪方式
     * 
     * @param id 板块小类-裁剪方式ID
     * @return 板块小类-裁剪方式
     */
    public TbPlateCutWay selectTbPlateCutWayById(Long id);

    /**
     * 查询板块小类-裁剪方式列表
     * 
     * @param tbPlateCutWay 板块小类-裁剪方式
     * @return 板块小类-裁剪方式集合
     */
    public List<TbPlateCutWay> selectTbPlateCutWayList(TbPlateCutWay tbPlateCutWay);

    /**
     * 根据上级id查询板块小类-裁剪方式列表
     * @param parentId 上级id
     * @return 板块小类-裁剪方式集合
     */
    List<TbPlateCutWay> selectTbPlateCutWayByList(long parentId);


    /**
     * 新增板块小类-裁剪方式
     * 
     * @param tbPlateCutWay 板块小类-裁剪方式
     * @return 结果
     */
    public int insertTbPlateCutWay(TbPlateCutWay tbPlateCutWay);

    /**
     * 修改板块小类-裁剪方式
     * 
     * @param tbPlateCutWay 板块小类-裁剪方式
     * @return 结果
     */
    public int updateTbPlateCutWay(TbPlateCutWay tbPlateCutWay);

    /**
     * 删除板块小类-裁剪方式
     * 
     * @param id 板块小类-裁剪方式ID
     * @return 结果
     */
    public int deleteTbPlateCutWayById(Long id);

    /**
     * 批量删除板块小类-裁剪方式
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTbPlateCutWayByIds(Long[] ids);

}
