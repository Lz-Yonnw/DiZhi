import request from '@/utils/request'

// 查询部件列表
export function listComponent(query) {
  return request({
    url: '/system/component/list',
    method: 'get',
    params: query
  })
}

// 查询部件详细
export function getComponent(id) {
  return request({
    url: '/system/component/' + id,
    method: 'get'
  })
}

// 新增部件
export function addComponent(data) {
  return request({
    url: '/system/component',
    method: 'post',
    data: data
  })
}

// 修改部件
export function updateComponent(data) {
  return request({
    url: '/system/component',
    method: 'put',
    data: data
  })
}

// 删除部件
export function delComponent(id) {
  return request({
    url: '/system/component/' + id,
    method: 'delete'
  })
}

// 导出部件
export function exportComponent(query) {
  return request({
    url: '/system/component/export',
    method: 'get',
    params: query
  })
}