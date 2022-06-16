import request from '@/utils/request'

// 查询收藏列表
export function listCollection(query) {
  return request({
    url: '/system/collection/list',
    method: 'get',
    params: query
  })
}
// 删除收藏
export function delCollection(id) {
  return request({
    url: '/system/collection/' + id,
    method: 'delete'
  })
}
