package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.TbPlateClass;

/**
 * 板块大类-部位Service接口
 * 
 * @author zzz
 * @date 2021-08-10
 */
public interface ITbPlateClassService 
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
     * 批量删除板块大类-部位
     * 
     * @param ids 需要删除的板块大类-部位ID
     * @return 结果
     */
    public int deleteTbPlateClassByIds(Long[] ids);

    /**
     * 删除板块大类-部位信息
     * 
     * @param id 板块大类-部位ID
     * @return 结果
     */
    public int deleteTbPlateClassById(Long id);
}
