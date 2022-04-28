package com.ruoyi.system.service;

import com.ruoyi.system.domain.TbPlateCutWay;

import java.util.List;

/**
 * 板块小类-裁剪方式Service接口
 * 
 * @author zzz
 * @date 2021-08-10
 */
public interface ITbPlateCutWayService 
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
    List<TbPlateCutWay> selectTbPlateCutWayList(long parentId);


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
     * 批量删除板块小类-裁剪方式
     * 
     * @param ids 需要删除的板块小类-裁剪方式ID
     * @return 结果
     */
    public int deleteTbPlateCutWayByIds(Long[] ids);

    /**
     * 删除板块小类-裁剪方式信息
     * 
     * @param id 板块小类-裁剪方式ID
     * @return 结果
     */
    public int deleteTbPlateCutWayById(Long id);

}
