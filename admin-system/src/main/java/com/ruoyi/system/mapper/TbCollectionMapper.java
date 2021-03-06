package com.ruoyi.system.mapper;

import com.ruoyi.common.annotation.DataSource;
import com.ruoyi.system.domain.TbCollection;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 收藏商品
 */
@DataSource()
public interface TbCollectionMapper {

    void updataStatus(TbCollection tbCollection);

    TbCollection selectone(TbCollection tbCollection);

    void insertOne(TbCollection tbCollection);

    List<Long> selectMallIds(Long id);

    /**
     * 收藏数量
     * @param userId
     * @return
     */
    @Select("select count(*) from tb_collection where user_id = #{userId}")
    public int selectTbCollectionCountByUserId(Integer userId);

    /**
     * 查询收藏列表
     *
     * @param tbCollection 收藏
     * @return 收藏集合
     */
    public List<TbCollection> selectTbCollectionList(TbCollection tbCollection);


    /**
     * 批量删除收藏
     *
     * @param ids 需要删除的收藏主键集合
     * @return 结果
     */
    public int deleteTbCollectionByIds(Long[] ids);

}
