import request from '@/utils/request'

// 查询商品属性列表
export function listValue(query) {
  return request({
    url: '/system/value/list',
    method: 'get',
    params: query
  })
}

// 查询商品属性详细
export function getValue(id) {
  return request({
    url: '/system/value/' + id,
    method: 'get'
  })
}

// 新增商品属性
export function addValue(data) {
  return request({
    url: '/system/value',
    method: 'post',
    data: data
  })
}

// 修改商品属性
export function updateValue(data) {
  return request({
    url: '/system/value',
    method: 'put',
    data: data
  })
}

// 删除商品属性
export function delValue(id) {
  return request({
    url: '/system/value/' + id,
    method: 'delete'
  })
}
