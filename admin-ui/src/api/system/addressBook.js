import request from '@/utils/request'

// 查询地址详细列表
export function listBook(query) {
  return request({
    url: '/system/book/list',
    method: 'get',
    params: query
  })
}

// 查询地址详细详细
export function getBook(id) {
  return request({
    url: '/system/book/' + id,
    method: 'get'
  })
}

// 新增地址详细
export function addBook(data) {
  return request({
    url: '/system/book',
    method: 'post',
    data: data
  })
}

// 修改地址详细
export function updateBook(data) {
  return request({
    url: '/system/book',
    method: 'put',
    data: data
  })
}

// 删除地址详细
export function delBook(id) {
  return request({
    url: '/system/book/' + id,
    method: 'delete'
  })
}

// 省市级地址查询
export function getRegionParentId(id) {
  return request({
    url: '/system/book/region/' + id,
    method: 'get'
  })
}

