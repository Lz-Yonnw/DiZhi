import request from '@/utils/request'

// 查询设计推荐列表
export function listRecommend(query) {
  return request({
    url: '/system/recommend/list',
    method: 'get',
    params: query
  })
}

// 查询设计推荐详细
export function getRecommend(id) {
  return request({
    url: '/system/recommend/' + id,
    method: 'get'
  })
}

// 新增设计推荐
export function addRecommend(data) {
  return request({
    url: '/system/recommend',
    method: 'post',
    data: data
  })
}

// 修改设计推荐
export function updateRecommend(data) {
  return request({
    url: '/system/recommend',
    method: 'put',
    data: data
  })
}

// 删除设计推荐
export function delRecommend(id) {
  return request({
    url: '/system/recommend/' + id,
    method: 'delete'
  })
}

// 导出设计推荐
export function exportRecommend(query) {
  return request({
    url: '/system/recommend/export',
    method: 'get',
    params: query
  })
}