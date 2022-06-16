import request from '@/utils/request'

// 查询材料图片列表
export function listPattern(query) {
  return request({
    url: '/system/pattern/list',
    method: 'get',
    params: query
  })
}

// 查询材料图片详细
export function getPattern(id) {
  return request({
    url: '/system/pattern/' + id,
    method: 'get'
  })
}

// 新增材料图片
export function addPattern(data) {
  return request({
    url: '/system/pattern',
    method: 'post',
    data: data
  })
}

// 修改材料图片
export function updatePattern(data) {
  return request({
    url: '/system/pattern',
    method: 'put',
    data: data
  })
}

// 删除材料图片
export function delPattern(id) {
  return request({
    url: '/system/pattern/' + id,
    method: 'delete'
  })
}

// 导出材料图片
export function exportPattern(query) {
  return request({
    url: '/system/pattern/export',
    method: 'get',
    params: query
  })
}

export function watChPicture(parentId,pId,type) {
  console.log(parentId)
  const data = {
    parentId,
    pId,
    type
  }
  return request({
    url: '/system/pattern/watChPicture',
    method: 'get',
    params: data
  })
}

export function removePatternImg(id) {
  const data = {
    id
  }
  return request({
    url: '/system/pattern/removePatternImg',
    method: 'get',
    params: data
  })
}
