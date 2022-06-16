import request from '@/utils/request'

// 查询板块大类-部位列表
export function listPlateClass(query) {
  return request({
    url: '/system/class/list',
    method: 'get',
    params: query
  })
}

// 查询板块大类-部位详细
export function getPlateClass(id) {
  return request({
    url: '/system/class/' + id,
    method: 'get'
  })
}

// 查询板块大类详细列表
export function getPlateClassAsList(query) {
  return request({
    url: '/system/class/asList',
    method: 'get',
    params: query
  })
}

// 新增板块大类-部位
export function addPlateClass(data) {
  return request({
    url: '/system/class',
    method: 'post',
    data: data
  })
}

// 修改板块大类-部位
export function updatePlateClass(data) {
  return request({
    url: '/system/class',
    method: 'put',
    data: data
  })
}

// 删除板块大类-部位
export function delPlateClass(id) {
  return request({
    url: '/system/class/' + id,
    method: 'delete'
  })
}

// 导出板块大类-部位
export function exportPlateClass(query) {
  return request({
    url: '/system/class/export',
    method: 'get',
    params: query
  })
}
