package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.TbComment;
import com.ruoyi.system.mapper.TbCommentMapper;
import com.ruoyi.system.service.ITbCommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评论Service业务层处理
 *
 * @author ruoyi
 * @date 2022-06-08
 */
@Service
public class TbCommentServiceImpl implements ITbCommentService
{
    @Autowired
    private TbCommentMapper tbCommentMapper;

    /**
     * 查询评论
     *
     * @param id 评论主键
     * @return 评论
     */
    @Override
    public TbComment selectTbCommentById(Integer id)
    {
        return tbCommentMapper.selectTbCommentById(id);
    }

    /**
     * 查询评论列表
     *
     * @param tbComment 评论
     * @return 评论
     */
    @Override
    public List<TbComment> selectTbCommentList(TbComment tbComment)
    {
        return tbCommentMapper.selectTbCommentList(tbComment);
    }

    /**
     * 新增评论
     *
     * @param tbComment 评论
     * @return 结果
     */
    @Override
    public int insertTbComment(TbComment tbComment)
    {
        return tbCommentMapper.insertTbComment(tbComment);
    }

    /**
     * 修改评论
     *
     * @param tbComment 评论
     * @return 结果
     */
    @Override
    public int updateTbComment(TbComment tbComment)
    {
        return tbCommentMapper.updateTbComment(tbComment);
    }

    /**
     * 批量删除评论
     *
     * @param ids 需要删除的评论主键
     * @return 结果
     */
    @Override
    public int deleteTbCommentByIds(Integer[] ids)
    {
        return tbCommentMapper.deleteTbCommentByIds(ids);
    }

    /**
     * 删除评论信息
     *
     * @param id 评论主键
     * @return 结果
     */
    @Override
    public int deleteTbCommentById(Integer id)
    {
        return tbCommentMapper.deleteTbCommentById(id);
    }

    /**
     * 根据商品id查询评价
     * @param mallId
     * @return
     */
    @Override
    public List<TbComment> selectTbCommentListByMallId(Long mallId) {
        return tbCommentMapper.selectTbCommentByMallId(mallId);
    }
}
