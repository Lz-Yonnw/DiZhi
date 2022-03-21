import request from '@/utils/request'

// 查询衣柜列表列表
export function listUserWardrobe(query) {
  return request({
    url: '/system/userWardrobe/list',
    method: 'get',
    params: query
  })
}

// 查询衣柜列表详细
export function getUserWardrobe(id) {
  return request({
    url: '/system/userWardrobe/' + id,
    method: 'get'
  })
}

// 新增衣柜列表
export function addUserWardrobe(data) {
  return request({
    url: '/system/userWardrobe',
    method: 'post',
    data: data
  })
}

// 修改衣柜列表
export function updateUserWardrobe(data) {
  return request({
    url: '/system/userWardrobe',
    method: 'put',
    data: data
  })
}

// 删除衣柜列表
export function delUserWardrobe(id) {
  return request({
    url: '/system/userWardrobe/' + id,
    method: 'delete'
  })
}

// 导出衣柜列表
export function exportUserWardrobe(query) {
  return request({
    url: '/system/userWardrobe/export',
    method: 'get',
    params: query
  })
}