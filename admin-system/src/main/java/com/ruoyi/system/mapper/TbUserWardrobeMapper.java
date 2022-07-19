package com.ruoyi.system.mapper;

import com.ruoyi.system.domain.TbUserWardrobe;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 衣柜列表Mapper接口
 *
 * @author zzz
 * @date 2021-09-25
 */
public interface TbUserWardrobeMapper
{
    /**
     * 查询衣柜列表
     *
     * @param id 衣柜列表ID
     * @return 衣柜列表
     */
    public TbUserWardrobe selectTbUserWardrobeById(Long id);

    /**
     * 查询衣柜列表列表
     *
     * @param tbUserWardrobe 衣柜列表
     * @return 衣柜列表集合
     */
    public List<TbUserWardrobe> selectTbUserWardrobeList(TbUserWardrobe tbUserWardrobe);

    /**
     * 新增衣柜列表
     *
     * @param tbUserWardrobe 衣柜列表
     * @return 结果
     */
    public int insertTbUserWardrobe(TbUserWardrobe tbUserWardrobe);

    /**
     * 修改衣柜列表
     *
     * @param tbUserWardrobe 衣柜列表
     * @return 结果
     */
    public int updateTbUserWardrobe(TbUserWardrobe tbUserWardrobe);

    /**
     * 删除衣柜列表
     *
     * @param id 衣柜列表ID
     * @return 结果
     */
    public int deleteTbUserWardrobeById(Long id);

    /**
     * 批量删除衣柜列表
     *
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTbUserWardrobeByIds(Long[] ids);
}
