import request from '@/utils/request'

// 查询爆款列表列表
export function listClothesStyle(query) {
  return request({
    url: '/system/clothesStyle/list',
    method: 'get',
    params: query
  })
}

// 查询爆款列表详细
export function getClothesStyle(id) {
  return request({
    url: '/system/clothesStyle/' + id,
    method: 'get'
  })
}

// 新增爆款列表
export function addClothesStyle(data) {
  return request({
    url: '/system/clothesStyle',
    method: 'post',
    data: data
  })
}

// 修改爆款列表
export function updateClothesStyle(data) {
  return request({
    url: '/system/clothesStyle',
    method: 'put',
    data: data
  })
}

// 删除爆款列表
export function delClothesStyle(id) {
  return request({
    url: '/system/clothesStyle/' + id,
    method: 'delete'
  })
}

// 导出爆款列表
export function exportClothesStyle(query) {
  return request({
    url: '/system/clothesStyle/export',
    method: 'get',
    params: query
  })
}