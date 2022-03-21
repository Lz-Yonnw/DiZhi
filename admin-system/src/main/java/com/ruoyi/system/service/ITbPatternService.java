package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.TbPattern;

/**
 * 材料图片Service接口
 * 
 * @author zzz
 * @date 2021-09-13
 */
public interface ITbPatternService 
{
    /**
     * 查询材料图片
     * 
     * @param id 材料图片ID
     * @return 材料图片
     */
    public TbPattern selectTbPatternById(Long id);

    public List<TbPattern> selectTbPatternByAllClassPicture(TbPattern tbPattern);

    /**
     * 查询材料图片列表
     * 
     * @param tbPattern 材料图片
     * @return 材料图片集合
     */
    public List<TbPattern> selectTbPatternList(TbPattern tbPattern);

    /**
     * 新增材料图片
     * 
     * @param tbPattern 材料图片
     * @return 结果
     */
    public int insertTbPattern(TbPattern tbPattern);

    /**
     * 修改材料图片
     * 
     * @param tbPattern 材料图片
     * @return 结果
     */
    public int updateTbPattern(TbPattern tbPattern);

    /**
     * 批量删除材料图片
     * 
     * @param ids 需要删除的材料图片ID
     * @return 结果
     */
    public int deleteTbPatternByIds(Long[] ids);

    /**
     * 删除材料图片信息
     * 
     * @param id 材料图片ID
     * @return 结果
     */
    public int deleteTbPatternById(Long id);
}
