package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.TbUserInfo;
import com.ruoyi.system.domain.TbUserSole;
import com.ruoyi.system.domain.dto.TbUserDescDto;
import com.ruoyi.system.mapper.*;
import com.ruoyi.system.service.ITbUserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户信息Service业务层处理
 *
 * @author zzz
 * @date 2021-09-13
 */
@Service
public class TbUserInfoServiceImpl implements ITbUserInfoService
{
    @Autowired
    private TbUserInfoMapper tbUserInfoMapper;
    @Autowired
    private TbUserSoleMapper tbUserSoleMapper;
    @Autowired
    private TbUserLevelMapper tbUserLevelMapper;
    @Autowired
    private TbUserCouponMapper tbUserCouponMapper;
    @Autowired
    private TbCollectionMapper tbCollectionMapper;

    /**
     * 查询用户信息
     *
     * @param id 用户信息ID
     * @return 用户信息
     */
    @Override
    public TbUserInfo selectTbUserInfoById(Long id)
    {
        return tbUserInfoMapper.selectTbUserInfoById(id);
    }


    @Override
    public TbUserInfo selectTbUserInfoByOtherParam(TbUserInfo tbUserInfo) {
        return tbUserInfoMapper.selectTbUserInfoByOtherParam(tbUserInfo);
    }
    /**
     * 用户信息详情
     * @param phoneNumber
     * @return
     */
    @Override
    public TbUserDescDto selectTbUserDescBy(String phoneNumber) {
        TbUserDescDto tbUserDescDto = new TbUserDescDto();
        //用户查询
        TbUserInfo tbUserInfo = tbUserInfoMapper.selectTbUserInfoByPhoneNumber(phoneNumber);
        tbUserDescDto.setTbUserInfo(tbUserInfo);
        //用户等级查询
        tbUserDescDto.setTbUserLevel(tbUserLevelMapper.selectTbUserLevelById(tbUserInfo.getLevelId()));
        //用户优惠卷数量查询
        tbUserDescDto.setCouponNumber(tbUserCouponMapper.selectTbUserCouponCountByUserId(tbUserInfo.getId().intValue()));
        //用户收藏数量查询
        tbUserDescDto.setCollectionNumber(tbCollectionMapper.selectTbCollectionCountByUserId(tbUserInfo.getId().intValue()));
        return tbUserDescDto;
    }

    /**
     * 查询用户信息列表
     *
     * @param tbUserInfo 用户信息
     * @return 用户信息
     */
    @Override
    public List<TbUserInfo> selectTbUserInfoList(TbUserInfo tbUserInfo)
    {
        return tbUserInfoMapper.selectTbUserInfoList(tbUserInfo);
    }

    /**
     * 新增用户信息
     *
     * @param tbUserInfo 用户信息
     * @return 结果
     */
    @Override
    public int insertTbUserInfo(TbUserInfo tbUserInfo)
    {
        return tbUserInfoMapper.insertTbUserInfo(tbUserInfo);
    }

    /**
     * 修改用户信息
     *
     * @param tbUserInfo 用户信息
     * @return 结果
     */
    @Override
    public int updateTbUserInfo(TbUserInfo tbUserInfo)
    {
        tbUserInfo.setUpdateTime(DateUtils.getNowDate());
        return tbUserInfoMapper.updateTbUserInfo(tbUserInfo);
    }

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的用户信息ID
     * @return 结果
     */
    @Override
    public int deleteTbUserInfoByIds(Long[] ids)
    {
        return tbUserInfoMapper.deleteTbUserInfoByIds(ids);
    }

    /**
     * 删除用户信息信息
     *
     * @param id 用户信息ID
     * @return 结果
     */
    @Override
    public int deleteTbUserInfoById(Long id)
    {
        return tbUserInfoMapper.deleteTbUserInfoById(id);
    }

    /**
     * 查询用户信息
     *
     * @param phoneNumber 用户信息手机号
     * @return 用户信息
     */
    @Override
    public TbUserInfo selectTbUserInfoByPhoneNumber(String phoneNumber)
    {
        TbUserInfo tbUserInfo = tbUserInfoMapper.selectTbUserInfoByPhoneNumber(phoneNumber);
        //当前用户等级
        //tbUserInfo.setTbUserLevel(tbUserLevelMapper.selectTbUserLevelById(tbUserInfo.getLevelId()));
        return tbUserInfo;
    }

    @Override
    public TbUserSole sleectTbUserSolePhoneNumber(String phoneNumber) {

        return tbUserSoleMapper.sleectTbUserSolePhoneNumber(phoneNumber);
    }

}
