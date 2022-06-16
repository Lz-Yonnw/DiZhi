import request from '@/utils/request'

// 查询版型大类列表列表
export function listModelType(query) {
  return request({
    url: '/system/modelType/list',
    method: 'get',
    params: query
  })
}

// 查询版型大类列表详细
export function getModelType(id) {
  return request({
    url: '/system/modelType/' + id,
    method: 'get'
  })
}

// 新增版型大类列表
export function addModelType(data) {
  return request({
    url: '/system/modelType',
    method: 'post',
    data: data
  })
}

// 修改版型大类列表
export function updateModelType(data) {
  return request({
    url: '/system/modelType',
    method: 'put',
    data: data
  })
}

// 删除版型大类列表
export function delModelType(id) {
  return request({
    url: '/system/modelType/' + id,
    method: 'delete'
  })
}

// 导出版型大类列表
export function exportModelType(query) {
  return request({
    url: '/system/modelType/export',
    method: 'get',
    params: query
  })
}