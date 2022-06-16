package com.ruoyi.system.service.impl;

import com.ruoyi.common.utils.DateUtils;
import com.ruoyi.system.domain.TbAddressBook;
import com.ruoyi.system.mapper.TbAddressBookMapper;
import com.ruoyi.system.service.ITbAddressBookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 地址详细Service业务层处理
 *
 * @author ruoyi
 * @date 2022-05-22
 */
@Service
public class TbAddressBookServiceImpl implements ITbAddressBookService
{
    @Autowired
    private TbAddressBookMapper tbAddressBookMapper;

    /**
     * 查询地址详细
     *
     * @param id 地址详细主键
     * @return 地址详细
     */
    @Override
    public TbAddressBook selectTbAddressBookById(Long id)
    {
        return tbAddressBookMapper.selectTbAddressBookById(id);
    }

    /**
     * 查询地址详细列表
     *
     * @param tbAddressBook 地址详细
     * @return 地址详细
     */
    @Override
    public List<TbAddressBook> selectTbAddressBookList(TbAddressBook tbAddressBook)
    {
        return tbAddressBookMapper.selectTbAddressBookList(tbAddressBook);
    }

    /**
     * 新增地址详细
     *
     * @param tbAddressBook 地址详细
     * @return 结果
     */
    @Override
    public int insertTbAddressBook(TbAddressBook tbAddressBook)
    {
        tbAddressBook.setCreateTime(DateUtils.getNowDate());
        return tbAddressBookMapper.insertTbAddressBook(tbAddressBook);
    }

    /**
     * 修改地址详细
     *
     * @param tbAddressBook 地址详细
     * @return 结果
     */
    @Override
    public int updateTbAddressBook(TbAddressBook tbAddressBook)
    {
        tbAddressBook.setUpdateTime(DateUtils.getNowDate());
        return tbAddressBookMapper.updateTbAddressBook(tbAddressBook);
    }

    /**
     * 批量删除地址详细
     *
     * @param ids 需要删除的地址详细主键
     * @return 结果
     */
    @Override
    public int deleteTbAddressBookByIds(Long[] ids)
    {
        return tbAddressBookMapper.deleteTbAddressBookByIds(ids);
    }

    /**
     * 删除地址详细信息
     *
     * @param id 地址详细主键
     * @return 结果
     */
    @Override
    public int deleteTbAddressBookById(Long id)
    {
        return tbAddressBookMapper.deleteTbAddressBookById(id);
    }
}
