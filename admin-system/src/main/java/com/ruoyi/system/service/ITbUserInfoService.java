package com.ruoyi.system.service;

import com.ruoyi.system.domain.TbUserInfo;
import com.ruoyi.system.domain.TbUserSole;
import com.ruoyi.system.domain.dto.TbUserDescDto;

import java.util.List;

/**
 * 用户信息Service接口
 *
 * @author zzz
 * @date 2021-09-13
 */
public interface ITbUserInfoService
{
    /**
     * 查询用户信息
     *
     * @param id 用户信息ID
     * @return 用户信息
     */
    public TbUserInfo selectTbUserInfoById(Long id);

    public TbUserInfo selectTbUserInfoByOtherParam(TbUserInfo tbUserInfo);

    /**
     * 用户信息详情
     * @param phoneNumber
     * @return
     */
    public TbUserDescDto selectTbUserDescBy(String phoneNumber);

    /**
     * 查询用户信息列表
     *
     * @param tbUserInfo 用户信息
     * @return 用户信息集合
     */
    public List<TbUserInfo> selectTbUserInfoList(TbUserInfo tbUserInfo);

    /**
     * 新增用户信息
     *
     * @param tbUserInfo 用户信息
     * @return 结果
     */
    public int insertTbUserInfo(TbUserInfo tbUserInfo);

    /**
     * 修改用户信息
     *
     * @param tbUserInfo 用户信息
     * @return 结果
     */
    public int updateTbUserInfo(TbUserInfo tbUserInfo);

    /**
     * 批量删除用户信息
     *
     * @param ids 需要删除的用户信息ID
     * @return 结果
     */
    public int deleteTbUserInfoByIds(Long[] ids);

    /**
     * 删除用户信息信息
     *
     * @param id 用户信息ID
     * @return 结果
     */
    public int deleteTbUserInfoById(Long id);

    public TbUserInfo selectTbUserInfoByPhoneNumber(String phoneNumber);

    public TbUserSole sleectTbUserSolePhoneNumber(String phoneNumber);
}
