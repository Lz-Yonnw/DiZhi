package com.ruoyi.system.service;

import java.util.List;
import com.ruoyi.system.domain.TbClassify;

/**
 * 分类Service接口
 * 
 * @author zzz
 * @date 2021-08-17
 */
public interface ITbClassifyService 
{
    /**
     * 查询分类
     * 
     * @param id 分类ID
     * @return 分类
     */
    public TbClassify selectTbClassifyById(Long id);

    /**
     * 查询分类列表
     * 
     * @param tbClassify 分类
     * @return 分类集合
     */
    public List<TbClassify> selectTbClassifyList(TbClassify tbClassify);


    public List<TbClassify> selectTbClassifyListByPId(TbClassify tbClassify);

    /**
     * 新增分类
     * 
     * @param tbClassify 分类
     * @return 结果
     */
    public int insertTbClassify(TbClassify tbClassify);

    /**
     * 修改分类
     * 
     * @param tbClassify 分类
     * @return 结果
     */
    public int updateTbClassify(TbClassify tbClassify);

    /**
     * 批量删除分类
     * 
     * @param ids 需要删除的分类ID
     * @return 结果
     */
    public int deleteTbClassifyByIds(Long[] ids);

    /**
     * 删除分类信息
     * 
     * @param id 分类ID
     * @return 结果
     */
    public int deleteTbClassifyById(Long id);
}
