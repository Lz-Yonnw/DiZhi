package com.ruoyi.system.service.impl;

import com.ruoyi.system.domain.TbModelStyle;
import com.ruoyi.system.domain.TbModelType;
import com.ruoyi.system.mapper.TbModelStyleMapper;
import com.ruoyi.system.mapper.TbModelTypeMapper;
import com.ruoyi.system.service.ITbModelTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 版型大类Service业务层处理
 * 
 * @author zzz
 * @date 2021-08-10
 */
@Service
public class TbModelTypeServiceImpl implements ITbModelTypeService 
{
    @Autowired
    private TbModelTypeMapper tbModelTypeMapper;

    @Autowired
    private TbModelStyleMapper tbModelStyleMapper;

    /**
     * 查询版型大类
     * 
     * @param id 版型大类ID
     * @return 版型大类
     */
    @Override
    public TbModelType selectTbModelTypeById(Long id)
    {
        return tbModelTypeMapper.selectTbModelTypeById(id);
    }

    /**
     * 查询版型大类列表
     * 
     * @param tbModelType 版型大类
     * @return 版型大类
     */
    @Override
    public List<TbModelType> selectTbModelTypeList(TbModelType tbModelType)
    {
        return tbModelTypeMapper.selectTbModelTypeList(tbModelType);
    }

    /**
     * 新增版型大类
     * 
     * @param tbModelType 版型大类
     * @return 结果
     */
    @Override
    public int insertTbModelType(TbModelType tbModelType)
    {
        return tbModelTypeMapper.insertTbModelType(tbModelType);
    }

    /**
     * 修改版型大类
     * 
     * @param tbModelType 版型大类
     * @return 结果
     */
    @Override
    public int updateTbModelType(TbModelType tbModelType)
    {
        return tbModelTypeMapper.updateTbModelType(tbModelType);
    }

    /**
     * 批量删除版型大类
     * 
     * @param ids 需要删除的版型大类ID
     * @return 结果
     */
    @Override
    public int deleteTbModelTypeByIds(Long[] ids)
    {
        return tbModelTypeMapper.deleteTbModelTypeByIds(ids);
    }

    /**
     * 删除版型大类信息
     * 
     * @param id 版型大类ID
     * @return 结果
     */
    @Override
    public int deleteTbModelTypeById(Long id)
    {
        return tbModelTypeMapper.deleteTbModelTypeById(id);
    }

    /**
     * 随机生成一套版型衣服   版型小类参数 顺机
     * @param id
     * @return
     */
    @Override
    public int insertTbModelComplete(Long id) {
        TbModelStyle tbModelStyle = new TbModelStyle();
        tbModelStyle.setParentId(id);
        //根据大类版型ID获取版型小类
        List<TbModelStyle> modelStyles = tbModelStyleMapper.selectTbModelStyleList(tbModelStyle);
        //随机一套小类版型
        int ran = (int) Math.random()*(modelStyles.size()-1)+1;
        TbModelStyle tbModelStyleMin = modelStyles.get(ran);


        return 0;
    }
}
