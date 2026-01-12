# 🚗 汽车租赁管理系统

基于 Spring Boot 3 的汽车租赁管理系统后端项目。

## 📋 项目简介

汽车租赁管理系统，提供车辆管理、客户管理、订单管理、违章管理、维保管理等核心业务功能，以及完整的 RBAC 权限管理体系。

## 🛠️ 技术栈

- **框架**: Spring Boot 3.0.10
- **ORM**: MyBatis-Plus 3.5.5
- **安全**: Spring Security + JWT
- **缓存**: Redis
- **数据库**: MySQL 8.0
- **文件存储**: 阿里云 OSS
- **工具类**: Hutool 5.8.26
- **文档**: Swagger 3.0

## ⚙️ 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+

## 🚀 快速开始

### 1. 克隆项目

```bash
git clone [项目地址]
cd auto_rental
```

### 2. 配置数据库

创建数据库 `car_project`，导入 SQL 脚本。

### 3. 修改配置

修改 `src/main/resources/application.yml` 中的配置：

- 数据库连接信息
- Redis 连接信息
- 阿里云 OSS 配置（可选）

### 4. 启动项目

```bash
mvn spring-boot:run
```

或使用 IDE 直接运行 `AutoRentalApplication.java`

### 5. 访问接口

- 服务地址: http://localhost:8888
- API 文档: http://localhost:8888/swagger-ui.html（如已配置）

## 📦 核心功能

- ✅ **系统管理**: 用户、角色、权限、部门管理
- ✅ **车辆管理**: 车辆信息、品牌、厂商管理
- ✅ **业务管理**: 客户、订单、违章、维保管理
- ✅ **数据统计**: 日/月维度的租车、收入统计
- ✅ **权限控制**: 基于 RBAC 的权限体系，支持动态菜单
- ✅ **文件上传**: 阿里云 OSS 图片上传

## 🔐 认证说明

- 登录接口: `POST /auto/user/login`
- Token 放在请求头: `token: xxxxx`
- Token 有效期: 30 分钟
- 刷新 Token: `POST /auto/auth/refreshToken`

## 📁 项目结构

```
auto_rental/
├── src/main/java/com/xzit/
│   ├── config/          # 配置类
│   ├── controller/      # 控制器
│   ├── entity/          # 实体类
│   ├── mapper/          # 数据访问层
│   ├── security/        # 安全认证
│   ├── service/         # 业务逻辑层
│   ├── utils/           # 工具类
│   └── vo/              # 视图对象
└── src/main/resources/
    ├── application.yml  # 配置文件
    └── mapper/          # MyBatis XML
```

## 📝 开发规范

- 统一返回格式: `Result<T>`
- 异常处理: 全局异常处理器
- 时间字段: 自动填充创建/更新时间
- 逻辑删除: 使用 `deleted` 字段

## 👤 作者

Gule

## 📄 License

MIT

