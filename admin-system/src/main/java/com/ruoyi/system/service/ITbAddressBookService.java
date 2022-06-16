package com.ruoyi.system.service;

import com.ruoyi.system.domain.TbAddressBook;

import java.util.List;

/**
 * 地址详细Service接口
 *
 * @author ruoyi
 * @date 2022-05-22
 */
public interface ITbAddressBookService
{
    /**
     * 查询地址详细
     *
     * @param id 地址详细主键
     * @return 地址详细
     */
    public TbAddressBook selectTbAddressBookById(Long id);

    /**
     * 查询地址详细列表
     *
     * @param tbAddressBook 地址详细
     * @return 地址详细集合
     */
    public List<TbAddressBook> selectTbAddressBookList(TbAddressBook tbAddressBook);

    /**
     * 新增地址详细
     *
     * @param tbAddressBook 地址详细
     * @return 结果
     */
    public int insertTbAddressBook(TbAddressBook tbAddressBook);

    /**
     * 修改地址详细
     *
     * @param tbAddressBook 地址详细
     * @return 结果
     */
    public int updateTbAddressBook(TbAddressBook tbAddressBook);

    /**
     * 批量删除地址详细
     *
     * @param ids 需要删除的地址详细主键集合
     * @return 结果
     */
    public int deleteTbAddressBookByIds(Long[] ids);

    /**
     * 删除地址详细信息
     *
     * @param id 地址详细主键
     * @return 结果
     */
    public int deleteTbAddressBookById(Long id);
}
