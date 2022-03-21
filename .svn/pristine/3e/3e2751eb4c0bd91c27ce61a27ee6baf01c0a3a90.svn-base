package com.ruoyi.system.mapper;

import java.util.List;
import com.ruoyi.system.domain.TbUserInfo;

/**
 * 用户信息Mapper接口
 * 
 * @author zzz
 * @date 2021-09-13
 */
public interface TbUserInfoMapper 
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
     * 删除用户信息
     * 
     * @param id 用户信息ID
     * @return 结果
     */
    public int deleteTbUserInfoById(Long id);

    /**
     * 批量删除用户信息
     * 
     * @param ids 需要删除的数据ID
     * @return 结果
     */
    public int deleteTbUserInfoByIds(Long[] ids);


    /**
     * 根据手机号查询用户
     * @param phoneNumber
     * @return
     */
    public TbUserInfo selectTbUserInfoByPhoneNumber(String phoneNumber);
}
