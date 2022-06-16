import request from '@/utils/request'

// 店铺列表
export function storeList() {
  return request({
    url: '/system/store/list',
    method: 'get',
  })
}

// 查询商品列表
export function listMall(query) {
  return request({
    url: '/system/mall/malllist',
    method: 'get',
    params: query
  })
}

// 查询商品详细
export function getMall(id) {
  return request({
    url: '/system/mall/' + id,
    method: 'get'
  })
}

// 新增商品
export function addMall(data,tbMallImag) {
  return request({
    url: '/system/mall',
    method: 'post',
    data: {'tbMall':data,'tbMallImag':tbMallImag}
  })
}

// 修改商品
export function updateMall(data,tbMallImag) {
  return request({
    url: '/system/mall',
    method: 'put',
    data: {'tbMall':data,'tbMallImag':tbMallImag}
  })
}

// 删除商品
export function delMall(id) {
  return request({
    url: '/system/mall/' + id,
    method: 'delete'
  })
}

// // 规格信息
// export function getMallSpecs() {
//   return request({
//     url: '/system/value/nodeList/'+0,
//     method: 'get',
//   })
// }

//获取商品SKU
export function getMallSku(mallId) {
  return request({
    url: '/system/sku/attribute/'+mallId,
    method: 'get',
  })
}

//商品sku修改
export function updateMallSku(data) {
  return request({
    url: '/system/sku',
    method: 'put',
    data: data
  })
}
//获取sku详情
export function getMallSkuById(id) {
  return request({
    url: '/system/sku/'+id,
    method: 'get',
  })
}

//删除当前商品的sku
export function delTbMallSku(id) {
  return request({
    url: '/system/sku/'+id,
    method: 'delete',
  })
}

//联级规格属性查询
export function getMallSpecsChilds(id) {
  return request({
    url: '/system/value/list/childs/'+id,
    method: 'get',
  })
}

//添加商品SKU
export function insertMallSku(data) {
  return request({
    url: '/system/sku',
    method: 'post',
    data:data
  })
}

//商品优惠劵
export function getMallCoupon(id) {
  return request({
    url: '/system/mall/listCoupon/'+id,
    method: 'get',
  })
}

//获取未关联的优惠劵
export function getIsNoMallCoupon(id) {
  return request({
    url: '/system/mallCoupon/isNoCouponList/'+id,
    method: 'get',
  })
}

//商品优惠劵关联
export function insertMallCoupon(ids,mallId) {
  const data = {
    ids,
    mallId
  }
  return request({
    url: '/system/mallCoupon/addMultiple',
    method: 'post',
    data:data
  })
}

//取消商品优惠劵关联
