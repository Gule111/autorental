import { asyncRoutes, constantRoutes } from '@/router'
import { getMenuList } from '@/api/user'
import Layout from '@/layout'

/**
 * 使用meta.role来判断当前用户是否具有权限
 * @param {Array} roles - 当前用户的角色数组
 * @param {Object} route - 当前路由对象
 * @returns {boolean} - 如果用户有权限则返回true，否则返回false
 */
function hasPermission(roles, route) {
  // 检查路由对象是否包含meta属性以及meta中的roles属性
  if (route.meta && route.meta.roles) {
    // 遍历用户角色数组，检查是否有匹配的权限角色
    return roles.some(role => route.meta.roles.includes(role))
  } else {
    // 如果路由没有设置权限角色，直接返回true，表示有权限
    return true
  }
}

/**
 * Filter asynchronous routing tables by recursion
 * @param routes asyncRoutes
 * @param roles
 */
export function filterAsyncRoutes(routes, roles) {
  const res = []

  routes.forEach(route => {
    const tmp = { ...route }
    if (hasPermission(roles, tmp)) {
      let component = tmp.component
      if (route.component) {
        if (component === 'Layout') {
          tmp.component = Layout
        } else {
          tmp.component = (resolve) => require([`@/views${component}`], resolve)
        }
      }
      if (tmp.children) {
        tmp.children = filterAsyncRoutes(tmp.children, roles)
      }
      res.push(tmp)
    }
  })

  return res
}

const state = {
  routes: [],
  addRoutes: []
}

const mutations = {
  SET_ROUTES: (state, routes) => {
    state.addRoutes = routes
    state.routes = constantRoutes.concat(routes)
  }
}

const actions = {
  generateRoutes({ commit }, roles) {
    return new Promise((resolve, reject) => {
      getMenuList().then(response => {
        if (response.code === 200) {
          const accessedRoutes = filterAsyncRoutes(response.data, roles)
          // alert(roles)
          commit('SET_ROUTES', accessedRoutes)
          resolve(accessedRoutes)
        } else {
          reject(response.msg)
        }
      }).catch(
        error => {
          reject(error)
        })
    })
  }
}

export default {
  namespaced: true,
  state,
  mutations,
  actions
}
