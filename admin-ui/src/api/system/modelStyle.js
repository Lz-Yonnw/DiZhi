import request from '@/utils/request'

// 查询版型小类列表
export function listModelStyle(query) {
  return request({
    url: '/system/modelStyle/list',
    method: 'get',
    params: query
  })
}

export function getList(query) {
  return request({
    url: '/system/modelStyle/getList',
    method: 'get',
    params: query
  })
}

// 查询版型小类详细
export function getModelStyle(id) {
  return request({
    url: '/system/modelStyle/' + id,
    method: 'get'
  })
}
// 查询版型全部类列表
export function getFourList() {
  return request({
    url: '/system/modelStyle/fourList',
    method: 'get'
  })
}

// 查询版型小类详细列表
export function getModelStyleAsList(query) {
  return request({
    url: '/system/modelStyle/asList',
    method: 'get',
    params: query
  })
}



// 新增版型小类
export function addModelStyle(data) {
  return request({
    url: '/system/modelStyle',
    method: 'post',
    data: data
  })
}

// 修改版型小类
export function updateModelStyle(data) {
  return request({
    url: '/system/modelStyle',
    method: 'put',
    data: data
  })
}

// 删除版型小类
export function delModelStyle(id) {
  return request({
    url: '/system/modelStyle/' + id,
    method: 'delete'
  })
}

// 导出版型小类
export function exportModelStyle(query) {
  return request({
    url: '/system/modelStyle/export',
    method: 'get',
    params: query
  })
}
