package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.TbComment;

import java.util.List;

/**
 * 评论Mapper接口
 *
 * @author ruoyi
 * @date 2022-06-08
 */
public interface TbCommentMapper
{
    /**
     * 查询评论
     *
     * @param id 评论主键
     * @return 评论
     */
    public TbComment selectTbCommentById(Integer id);

    /**
     * 查询评论列表
     *
     * @param tbComment 评论
     * @return 评论集合
     */
    public List<TbComment> selectTbCommentList(TbComment tbComment);

    /**
     * 新增评论
     *
     * @param tbComment 评论
     * @return 结果
     */
    public int insertTbComment(TbComment tbComment);

    /**
     * 修改评论
     *
     * @param tbComment 评论
     * @return 结果
     */
    public int updateTbComment(TbComment tbComment);

    /**
     * 删除评论
     *
     * @param id 评论主键
     * @return 结果
     */
    public int deleteTbCommentById(Integer id);

    /**
     * 批量删除评论
     *
     * @param ids 需要删除的数据主键集合
     * @return 结果
     */
    public int deleteTbCommentByIds(Integer[] ids);

    /**
     * 商品下的评价
     * @param mallId
     * @return
     */
    public List<TbComment> selectTbCommentByMallId(Long mallId);
}
