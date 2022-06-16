import request from '@/utils/request'

// 查询白膜列表
export function listWhiteDesign(query) {
  return request({
    url: '/system/whiteDesign/list',
    method: 'get',
    params: query
  })
}

// 查询白膜详细
export function getWhiteDesign(id) {
  return request({
    url: '/system/whiteDesign/' + id,
    method: 'get'
  })
}

// 新增白膜
export function addWhiteDesign(data) {
  return request({
    url: '/system/whiteDesign',
    method: 'post',
    data: data
  })
}

// 修改白膜
export function updateWhiteDesign(data) {
  return request({
    url: '/system/whiteDesign',
    method: 'put',
    data: data
  })
}

// 删除白膜
export function delWhiteDesign(id) {
  return request({
    url: '/system/whiteDesign/' + id,
    method: 'delete'
  })
}

// 导出白膜
export function exportWhiteDesign(query) {
  return request({
    url: '/system/whiteDesign/export',
    method: 'get',
    params: query
  })
}