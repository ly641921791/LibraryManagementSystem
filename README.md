# LibraryManagementSystem
图书管理系统（Library management system）

# 图书管理系统

[![License](https://img.shields.io/badge/SpringBoot-v1.5.22.RELEASE-green.svg)](https://docs.spring.io/spring-boot/docs/1.5.22.RELEASE/reference/htmlsingle)
[![License](https://img.shields.io/badge/Mybatis-v3.4.6-blue.svg)]()
[![License](https://img.shields.io/badge/AdminLTE-v2.4.10-blue.svg)]()
[![License](https://img.shields.io/badge/EasyUI-v1.7.5-green.svg)]()

图书管理系统 是基于 Spring Boot + Mybatis + JSP 开发的图书管理系统, 系统具有用户管理、角色管理、应用管理、图书分类管理、图书信息管理、借书管理、还书管理、图书检索、借还记录等功能。

#### 如何预览

1. 安装数据库，新建数据库 book ，将 SQL 文件导入 （src/main/resources/schema.sql）

2. 下载代码

3. 启动程序，访问 127.0.0.1

3.1 启动方式1 ：运行类 LibraryManagementSystemApplication
3.2 启动方式2 ：执行 mvn package 命令打包，进入 target 目录，通过 java -jar 命令运行

- 账号密码：管理员 admin-123456
- 账号密码：借阅者 ly-123456

## 技术架构

#### 后端
- 基础框架：Spring Boot 1.5.22.RELEASE

- 持久层框架：Mybatis 3.4.6

- 数据库连接池：Druid 1.1.10

- 模板引擎：jsp

- 其他：gson，guava，lombok（简化代码）等。

#### 前端

- 基于Bootstrap的前端框架：[AdminLTE](https://adminlte.io/)

- 数据表格及其他组件：[EasyUI](http://www.jeasyui.com/)

- 弹出层插件：[Layer](http://layer.layui.com/)

- 树插件：[zTree](http://www.treejs.cn/v3/main.php#_zTreeInfo)

- 富文本编辑器：[CKEditor 4](https://ckeditor.com/)

- 手势验证码：[VAPTCHA](https://www.vaptcha.com/)

#### 开发环境

- JDK：1.8

- 开发工具：IDEA

- 数据库：MySQL5.7

## 功能模块
```
├─系统管理
│  ├─用户管理
│  ├─角色管理
│  ├─应用管理
├─图书管理
│  ├─图书分类管理
│  ├─图书信息管理
│  ├─借书管理
│  ├─还书管理
│─借阅者
│  ├─图书检索
│  └─借还记录
└─其他模块
   └─更多功能开发中（欢迎提出宝贵意见）。。。 
```

## 系统效果
### 系统登录页
![]()
### 管理员
#### 用户管理
![]()
#### 角色管理
![]()
#### 应用管理
![]()
#### 图书分类管理
![]()
#### 图书信息管理
![]()
#### 借书管理
![]()
#### 还书管理
![]()


### 借阅者
#### 图书检索
![]()

#### 借还记录
![]()