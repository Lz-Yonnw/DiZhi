import request from '@/utils/request'

// 查询订单信息列表
export function listInfo(query) {
  return request({
    url: '/system/order/info/list',
    method: 'get',
    params: query
  })
}
// 查询订单信息详细
export function getInfo(id) {
  return request({
    url: '/system/order/info/' + id,
    method: 'get'
  })
}

// 新增订单信息
export function addInfo(data) {
  return request({
    url: '/system/order/info/',
    method: 'post',
    data: data
  })
}

// 修改订单信息
export function updateInfo(info,list) {
  return request({
    url: '/system/order/info/update',
    method: 'post',
    data:{"orderInfo":info,"list":list}
  })
}

// 删除订单信息
export function delInfo(id,name) {
  const data = {
    orderInfoId:id,
    delName:name,
  }
  return request({
    url: '/system/order/info/del',
    method: 'post',
    data:data
  })
}

// 修改订单状态
export function updateStatus(id,status){
  const data = {
    id,
    status,
  }
  return request({
    url: '/system/order/info/updateStatus',
    method: 'put',
    params:data
  })
}
