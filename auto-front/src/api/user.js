import requestHttp from '@/utils/request'
export async function login(data){
  return await requestHttp.login('/auto/user/login',data)
}
export async function getInfo(){
  return await requestHttp.get('/auto/auth/getInfo')
}
export async function logout(params){
  return await requestHttp.post('/auto/auth/logout',params)
}
export async function getMenuList(){
  return await requestHttp.get('/auto/auth/menuList')
}

// export function login(data) {
//   return request({
//     url: '/vue-element-admin/user/login',
//     method: 'post',
//     data
//   })
// }

// export function getInfo(token) {
//   return request({
//     url: '/vue-element-admin/user/info',
//     method: 'get',
//     params: { token }
//   })
// }

// export function logout() {
//   return request({
//     url: '/vue-element-admin/user/logout',
//     method: 'post'
//   })
// }
