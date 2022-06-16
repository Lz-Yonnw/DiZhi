import request from '@/utils/request'

// 查询部门下拉树结构
export function treeselect() {
  return request({
    url: '/system/material/treeselect',
    method: 'get'
  })
}


// 角色状态修改
export function getComponents(parentId) {
  console.log(parentId)
  const data = {
    parentId
  }
  return request({
    url: '/system/material/getComponents',
    method: 'get',
    params: data
  })
}

