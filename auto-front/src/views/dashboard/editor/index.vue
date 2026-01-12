<template>
  <div class="dashboard-editor-container">
    <!-- 欢迎区域 -->
    <el-card class="welcome-card">
      <div class="welcome-header">
        <pan-thumb :image="avatar" class="user-avatar">
          <span class="role-label">角色:</span>
          <span v-for="item in roles" :key="item" class="pan-info-roles">{{ item }}</span>
        </pan-thumb>
        <github-corner style="position: absolute; top: 0px; border: 0; right: 0;" />
        <div class="info-container">
          <h1 class="display_name">欢迎回来, {{ name }}!</h1>
          <p class="welcome-text">{{ greetingText }}</p>
          <div class="quick-info">
            <span class="info-item">
              <i class="el-icon-time"></i>
              {{ currentTime }}
            </span>
            <span class="info-item">
              <i class="el-icon-date"></i>
              {{ currentDate }}
            </span>
          </div>
        </div>
      </div>
    </el-card>

    <!-- 数据统计卡片 -->
    <el-row :gutter="20" class="stats-row">
      <el-col :xs="24" :sm="12" :md="6">
        <el-card class="stat-card stat-card-1">
          <div class="stat-content">
            <div class="stat-icon">
              <i class="el-icon-s-order"></i>
            </div>
            <div class="stat-info">
              <p class="stat-label">汽车出租数量</p>
              <h3 class="stat-value">{{ stats.todayOrders }}</h3>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <el-card class="stat-card stat-card-2">
          <div class="stat-content">
            <div class="stat-icon">
              <i class="el-icon-user"></i>
            </div>
            <div class="stat-info">
              <p class="stat-label">用户数量</p>
              <h3 class="stat-value">{{ stats.newCustomers }}</h3>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <el-card class="stat-card stat-card-3">
          <div class="stat-content">
            <div class="stat-icon">
              <i class="el-icon-s-goods"></i>
            </div>
            <div class="stat-info">
              <p class="stat-label">车辆总数</p>
              <h3 class="stat-value">{{ stats.totalVehicles }}</h3>
            </div>
          </div>
        </el-card>
      </el-col>
      <el-col :xs="24" :sm="12" :md="6">
        <el-card class="stat-card stat-card-4">
          <div class="stat-content">
            <div class="stat-icon">
              <i class="el-icon-money"></i>
            </div>
            <div class="stat-info">
              <p class="stat-label">今日收入</p>
              <h3 class="stat-value">¥{{ stats.todayRevenue }}</h3>
            </div>
          </div>
        </el-card>
      </el-col>
    </el-row>

    <!-- 快捷操作 -->
    <el-card class="quick-actions-card">
      <div slot="header" class="card-header">
        <span class="card-title">快捷操作</span>
      </div>
      <el-row :gutter="20">
        <el-col :xs="12" :sm="8" :md="6" v-for="action in quickActions" :key="action.name">
          <router-link :to="action.path" class="action-link">
            <div class="action-item">
              <div class="action-icon" :style="{ background: action.color }">
                <i :class="action.icon"></i>
              </div>
              <p class="action-name">{{ action.name }}</p>
            </div>
          </router-link>
        </el-col>
      </el-row>
    </el-card>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import PanThumb from '@/components/PanThumb'
import GithubCorner from '@/components/GithubCorner'
import financeApi from '@/api/finance'
import autoInfoApi from '@/api/auto_info'
import authUserApi from '@/api/auth_user'

export default {
  name: 'DashboardEditor',
  components: { PanThumb, GithubCorner },
  data() {
    return {
      currentTime: '',
      currentDate: '',
      stats: {
        todayOrders: 0,
        newCustomers: 0,
        totalVehicles: 0,
        todayRevenue: '0'
      },
      quickActions: [
        { name: '租赁订单', icon: 'el-icon-document-add', color: 'linear-gradient(135deg, #667eea 0%, #764ba2 100%)', path: '/orderList' },
        { name: '客户管理', icon: 'el-icon-user-solid', color: 'linear-gradient(135deg, #f093fb 0%, #f5576c 100%)', path: '/customerList' },
        { name: '车辆管理', icon: 'el-icon-truck', color: 'linear-gradient(135deg, #4facfe 0%, #00f2fe 100%)', path: '/infoList' },
        { name: '日统计', icon: 'el-icon-s-data', color: 'linear-gradient(135deg, #43e97b 0%, #38f9d7 100%)', path: '/day' },
        { name: '维修记录', icon: 'el-icon-setting', color: 'linear-gradient(135deg, #fa709a 0%, #fee140 100%)', path: '/maintainList' },
        { name: '违章查询', icon: 'el-icon-warning', color: 'linear-gradient(135deg, #30cfd0 0%, #330867 100%)', path: '/violationList' },
        { name: '品牌管理', icon: 'el-icon-medal', color: 'linear-gradient(135deg, #a8edea 0%, #fed6e3 100%)', path: '/brandList' },
        { name: '用户管理', icon: 'el-icon-s-custom', color: 'linear-gradient(135deg, #ffecd2 0%, #fcb69f 100%)', path: '/userList' }
      ]
    }
  },
  computed: {
    ...mapGetters([
      'name',
      'avatar',
      'roles'
    ]),
    greetingText() {
      const hour = new Date().getHours()
      if (hour < 9) return '早上好！新的一天开始了'
      if (hour < 12) return '上午好！工作顺利'
      if (hour < 18) return '下午好！继续加油'
      return '晚上好！辛苦了'
    }
  },
  created() {
    this.updateTime()
    setInterval(this.updateTime, 1000)
    this.getStatistics()
  },
  methods: {
    updateTime() {
      const now = new Date()
      this.currentTime = now.toLocaleTimeString('zh-CN', { hour12: false })
      this.currentDate = now.toLocaleDateString('zh-CN', {
        year: 'numeric',
        month: 'long',
        day: 'numeric',
        weekday: 'long'
      })
    },
    async getStatistics() {
      try {
        // 获取当日出租数量
        const rentalRes = await financeApi.countDayRental()
        if (rentalRes.success && rentalRes.data && rentalRes.data[1]) {
          // data[1] 是出租数量数组，计算总和
          this.stats.todayOrders = rentalRes.data[1].reduce((sum, num) => sum + num, 0)
        }

        // 获取当日收入
        const costRes = await financeApi.countDayCost()
        if (costRes.success && costRes.data) {
          this.stats.todayRevenue = costRes.data.countRentActual || 0
        }

        // 获取车辆总数
        const autoRes = await autoInfoApi.search(1, 1, {})
        if (autoRes.success && autoRes.data) {
          this.stats.totalVehicles = autoRes.data.total || 0
        }

        // 获取用户总数
        const userRes = await authUserApi.search(1, 1, {})
        if (userRes.success && userRes.data) {
          this.stats.newCustomers = userRes.data.total || 0
        }
      } catch (error) {
        console.error('获取统计数据失败:', error)
      }
    }
  }
}
</script>

<style lang="scss" scoped>
.dashboard-editor-container {
  background-color: #f0f2f5;
  min-height: 100vh;
  padding: 20px;

  .welcome-card {
    margin-bottom: 20px;
    border-radius: 8px;
    box-shadow: 0 2px 12px 0 rgba(0, 0, 0, 0.1);

    .welcome-header {
      display: flex;
      align-items: center;
      position: relative;
      padding: 20px;

      .user-avatar {
        flex-shrink: 0;
      }

      .role-label {
        font-size: 12px;
        color: #909399;
        display: block;
        margin-bottom: 5px;
      }

      .pan-info-roles {
        font-size: 12px;
        font-weight: 700;
        color: #409EFF;
        display: block;
        margin-top: 5px;
      }

      .info-container {
        margin-left: 30px;
        flex: 1;

        .display_name {
          font-size: 28px;
          font-weight: 600;
          color: #303133;
          margin: 0 0 10px 0;
        }

        .welcome-text {
          font-size: 14px;
          color: #606266;
          margin: 0 0 15px 0;
        }

        .quick-info {
          display: flex;
          gap: 30px;

          .info-item {
            font-size: 14px;
            color: #909399;

            i {
              margin-right: 5px;
            }
          }
        }
      }
    }
  }

  .stats-row {
    margin-bottom: 20px;

    .stat-card {
      border-radius: 8px;
      transition: all 0.3s;
      cursor: pointer;

      &:hover {
        transform: translateY(-5px);
        box-shadow: 0 4px 20px rgba(0, 0, 0, 0.15);
      }

      .stat-content {
        display: flex;
        align-items: center;
        padding: 10px 0;

        .stat-icon {
          width: 60px;
          height: 60px;
          border-radius: 12px;
          display: flex;
          align-items: center;
          justify-content: center;
          font-size: 28px;
          color: white;
          margin-right: 20px;
        }

        .stat-info {
          flex: 1;

          .stat-label {
            font-size: 14px;
            color: #909399;
            margin: 0 0 8px 0;
          }

          .stat-value {
            font-size: 24px;
            font-weight: 600;
            color: #303133;
            margin: 0;
          }
        }
      }

      &.stat-card-1 .stat-icon {
        background: linear-gradient(135deg, #667eea 0%, #764ba2 100%);
      }

      &.stat-card-2 .stat-icon {
        background: linear-gradient(135deg, #f093fb 0%, #f5576c 100%);
      }

      &.stat-card-3 .stat-icon {
        background: linear-gradient(135deg, #4facfe 0%, #00f2fe 100%);
      }

      &.stat-card-4 .stat-icon {
        background: linear-gradient(135deg, #43e97b 0%, #38f9d7 100%);
      }
    }
  }

  .quick-actions-card {
    margin-bottom: 20px;
    border-radius: 8px;

    .card-header {
      display: flex;
      justify-content: space-between;
      align-items: center;

      .card-title {
        font-size: 16px;
        font-weight: 600;
        color: #303133;
      }
    }

    .action-link {
      text-decoration: none;
      display: block;
    }

    .action-item {
      text-align: center;
      padding: 20px;
      cursor: pointer;
      transition: all 0.3s;
      border-radius: 8px;

      &:hover {
        background-color: #f5f7fa;
        transform: translateY(-3px);

        .action-icon {
          transform: scale(1.1);
        }
      }

      .action-icon {
        width: 60px;
        height: 60px;
        margin: 0 auto 10px;
        border-radius: 12px;
        display: flex;
        align-items: center;
        justify-content: center;
        font-size: 24px;
        color: white;
        transition: all 0.3s;
      }

      .action-name {
        font-size: 14px;
        color: #606266;
        margin: 0;
      }
    }
  }
}

@media (max-width: 768px) {
  .dashboard-editor-container {
    padding: 10px;

    .welcome-card .welcome-header {
      flex-direction: column;
      text-align: center;

      .info-container {
        margin-left: 0;
        margin-top: 20px;

        .quick-info {
          justify-content: center;
        }
      }
    }
  }
}
</style>
