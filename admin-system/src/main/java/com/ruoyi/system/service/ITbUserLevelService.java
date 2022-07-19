package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.TbUserLevel;

/**
 * 用户等级Service接口
 * 
 * @author ruoyi
 * @date 2022-07-18
 */
public interface ITbUserLevelService 
{
    /**
     * 查询用户等级
     * 
     * @param id 用户等级主键
     * @return 用户等级
     */
    public TbUserLevel selectTbUserLevelById(Long id);

    /**
     * 查询用户等级列表
     * 
     * @param tbUserLevel 用户等级
     * @return 用户等级集合
     */
    public List<TbUserLevel> selectTbUserLevelList(TbUserLevel tbUserLevel);

    /**
     * 新增用户等级
     * 
     * @param tbUserLevel 用户等级
     * @return 结果
     */
    public int insertTbUserLevel(TbUserLevel tbUserLevel);

    /**
     * 修改用户等级
     * 
     * @param tbUserLevel 用户等级
     * @return 结果
     */
    public int updateTbUserLevel(TbUserLevel tbUserLevel);

    /**
     * 批量删除用户等级
     * 
     * @param ids 需要删除的用户等级主键集合
     * @return 结果
     */
    public int deleteTbUserLevelByIds(Long[] ids);

    /**
     * 删除用户等级信息
     * 
     * @param id 用户等级主键
     * @return 结果
     */
    public int deleteTbUserLevelById(Long id);
}
