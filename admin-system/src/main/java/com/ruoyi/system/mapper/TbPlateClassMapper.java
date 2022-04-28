package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.TbPlateClass;

import java.util.List;

/**
 * 板块大类-部位Mapper接口
 * 
 * @author zzz
 * @date 2021-08-10
 */
public interface TbPlateClassMapper 
{
    /**
     * 查询板块大类-部位
     * 
     * @param id 板块大类-部位ID
     * @return 板块大类-部位
     */
    public TbPlateClass selectTbPlateClassById(Long id);

    /**
     * 查询板块大类-部位列表
     * 
     * @param tbPlateClass 板块大类-部位
     * @return 板块大类-部位集合
     */
    public List<TbPlateClass> selectTbPlateClassList(TbPlateClass tbPlateClass);

    /**
     * 根据id查询板块大类-部位列表
     * @param parentId 上级id
     * @return 板块大类部位集合
     */
    List<TbPlateClass> selectTbPlateClassByList(long parentId);


    /**
     * 新增板块大类-部位
     * 
     * @param tbPlateClass 板块大类-部位
     * @return 结果
     */
    public int insertTbPlateClass(TbPlateClass tbPlateClass);

    /**
     * 修改板块大类-部位
     * 
     * @param tbPlateClass 板块大类-部位
     * @return 结果
     */
    public int updateTbPlateClass(TbPlateClass tbPlateClass);

    /**
     * 删除板块大类-部位
     * 
     * @param id 板块大类-部位ID
     * @return 结果
     */
    public int deleteTbPlateClassById(Long id);

    /**
     * 批量删除板块大类-部位
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTbPlateClassByIds(Long[] ids);

}
