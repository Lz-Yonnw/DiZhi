package com.ruoyi.system.service;

import com.ruoyi.system.domain.TbModelType;

import java.util.List;

/**
 * 版型大类Service接口
 *
 * @author zzz
 * @date 2021-08-10
 */
public interface ITbModelTypeService
{



    /**
     * 查询版型大类
     *
     * @param id 版型大类ID
     * @return 版型大类
     */
    public TbModelType selectTbModelTypeById(Long id);

    /**
     * 查询版型大类列表
     *
     * @param tbModelType 版型大类
     * @return 版型大类集合
     */
    public List<TbModelType> selectTbModelTypeList(TbModelType tbModelType);

    /**
     * 新增版型大类
     *
     * @param tbModelType 版型大类
     * @return 结果
     */
    public int insertTbModelType(TbModelType tbModelType);

    /**
     * 修改版型大类
     *
     * @param tbModelType 版型大类
     * @return 结果
     */
    public int updateTbModelType(TbModelType tbModelType);

    /**
     * 批量删除版型大类
     *
     * @param ids 需要删除的版型大类ID
     * @return 结果
     */
    public int deleteTbModelTypeByIds(Long[] ids);

    /**
     * 删除版型大类信息
     *
     * @param id 版型大类ID
     * @return 结果
     */
    public int deleteTbModelTypeById(Long id);

    /**
     * 随机生成一套版型衣服   版型小类参数 顺机
     * @param id
     * @return
     */
    public int insertTbModelComplete(Long id);
}
