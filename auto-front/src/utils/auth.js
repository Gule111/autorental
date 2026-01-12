import Cookies from 'js-cookie'

const TokenKey = 'Admin-Token'

export function getToken() {
  return Cookies.get(TokenKey)
}

export function setToken(token) {
  return Cookies.set(TokenKey, token)
}

export function removeToken() {
  return Cookies.remove(TokenKey)
}


/**
 * 清除sessionStorage中的所有数据
 * 
 * 此函数旨在简化sessionStorage的清除操作，通过调用sessionStorage.clear()方法
 * 来清除当前窗口的sessionStorage中的所有数据此函数没有输入参数和返回值
 */
export function removeSessionStorage() {
  return sessionStorage.clear();
}