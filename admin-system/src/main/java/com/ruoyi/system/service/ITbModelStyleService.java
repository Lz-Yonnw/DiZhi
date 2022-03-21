package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.TbModelStyle;

/**
 * 版型小类Service接口
 * 
 * @author zzz
 * @date 2021-08-10
 */
public interface ITbModelStyleService 
{
    /**
     * 查询版型小类
     * 
     * @param id 版型小类ID
     * @return 版型小类
     */
    public TbModelStyle selectTbModelStyleById(Long id);

    public List<TbModelStyle> selectTbModelStyleByList(Long id);

    /**
     * 查询版型小类列表
     * 
     * @param tbModelStyle 版型小类
     * @return 版型小类集合
     */
    public List<TbModelStyle> selectTbModelStyleList(TbModelStyle tbModelStyle);

    /**
     * 新增版型小类
     * 
     * @param tbModelStyle 版型小类
     * @return 结果
     */
    public int insertTbModelStyle(TbModelStyle tbModelStyle);

    /**
     * 修改版型小类
     * 
     * @param tbModelStyle 版型小类
     * @return 结果
     */
    public int updateTbModelStyle(TbModelStyle tbModelStyle);

    /**
     * 批量删除版型小类
     * 
     * @param ids 需要删除的版型小类ID
     * @return 结果
     */
    public int deleteTbModelStyleByIds(Long[] ids);

    /**
     * 删除版型小类信息
     * 
     * @param id 版型小类ID
     * @return 结果
     */
    public int deleteTbModelStyleById(Long id);
}
