import request from '@/utils/request'

// 查询订单列表
export function listOrder(query) {
  return request({
    url: '/system/order/list',
    method: 'get',
    params: query
  })
}

// 查询订单详细
export function getOrder(orderSn) {
  return request({
    url: '/system/order/' + orderSn,
    method: 'get'
  })
}

// 新增订单
export function addOrder(data) {
  return request({
    url: '/system/order',
    method: 'post',
    data: data
  })
}

// 修改订单
export function updateOrder(data) {
  return request({
    url: '/system/order',
    method: 'put',
    data: data
  })
}

// 删除订单
export function delOrder(id) {
  return request({
    url: '/system/order/' + id,
    method: 'delete'
  })
}

//退单 退货
export function refundAudit(orderSn) {
  return request({
    url: '/system/order/refund/'+orderSn,
    method: 'post',
  })
}
