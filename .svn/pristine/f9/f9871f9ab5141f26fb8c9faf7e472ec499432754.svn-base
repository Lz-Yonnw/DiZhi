import request from '@/utils/request'

// 查询分类列表
export function listClassify(query) {
  return request({
    url: '/system/classify/list',
    method: 'get',
    params: query
  })
}

// 查询分类详细
export function getClassify(id) {
  return request({
    url: '/system/classify/' + id,
    method: 'get'
  })
}

// 新增分类
export function addClassify(data) {
  return request({
    url: '/system/classify',
    method: 'post',
    data: data
  })
}

// 修改分类
export function updateClassify(data) {
  return request({
    url: '/system/classify',
    method: 'put',
    data: data
  })
}

// 删除分类
export function delClassify(id) {
  return request({
    url: '/system/classify/' + id,
    method: 'delete'
  })
}

// 导出分类
export function exportClassify(query) {
  return request({
    url: '/system/classify/export',
    method: 'get',
    params: query
  })
}

export function getClassifyByComponentId(parentId,pId) {
  const data = {
    parentId,
    pId
  }
  return request({
    url: '/system/classify/getClassifyByComponentId',
    method: 'get',
    params: data
  })
}

export function removeClassById(id){
  const data = {
    id
  }
  return request({
    url: '/system/classify/removeClassById',
    method: 'get',
    params: data
  })
}
