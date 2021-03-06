import request from '@/utils/request'

// 查询图案部件中间列表
export function listComponentPattern(query) {
  return request({
    url: '/system/componentPattern/list',
    method: 'get',
    params: query
  })
}

// 查询图案部件中间详细
export function getComponentPattern(id) {
  return request({
    url: '/system/componentPattern/' + id,
    method: 'get'
  })
}

// 新增图案部件中间
export function addComponentPattern(data) {
  return request({
    url: '/system/componentPattern',
    method: 'post',
    data: data
  })
}

// 修改图案部件中间
export function updateComponentPattern(data) {
  return request({
    url: '/system/componentPattern',
    method: 'put',
    data: data
  })
}

// 删除图案部件中间
export function delComponentPattern(id) {
  return request({
    url: '/system/componentPattern/' + id,
    method: 'delete'
  })
}

// 导出图案部件中间
// export function exportComponentPattern(query) {
//   return request({
//     url: '/system/componentPattern/getClassifyPictureById',
//     method: 'get',
//     params: query
//   })
// }

// 分类下的所有面料  分类ID
export function getClassifyPictureById(id) {
  const data = {
    id
  }
  return request({
    url: '/system/componentPattern/getClassifyPictureById',
    method: 'get',
    params: data
  })
}


// 角色状态修改
export function updateComponentImg(val,id,classId) {
  const data = {
    val,id,classId
  }
  return request({
    url: '/system/componentPattern/updateComponentImg',
    method: 'get',
    params: data
  })
}
