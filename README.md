# springboot-demo
自用springboot web框架

## 接口统一异常封装
+ 自定义ServiceException业务异常类
+ 通过aop在controller层统一处理异常
+ 统一封装接口返回对象
+ 异常编码常量类

## 多环境配置
放弃自带properties文件，全部采用typesafe config实现
maven profile自动切换

## 基于注解使用的读写双数据源
采用性能不错的HikariCP数据库连接池
controller上添加注解即可定义整个请求采用数据源为读或者写

## 集成zipkin，支持黑名单过滤
基于brave-springmvc
日志打印zipkin的traceId&spanId用于追踪

## 常用工具
gson工具包
typesafe config读取工具包
结合异常框架的常用断言工具包
joda-time
commons-lang3

## logback日志
controller层注解控制请求报文，返回报文打印

## spring async注解异步执行方法
基于AsyncExecutor统一管理异步线程

## 纯class实现的redis & mybatis 配置
无需写xml文件

## lombok 对象类添加@DATA注解即可省去get set方法

##health check接口

# 代码层次说明
controller(参数校验 简单逻辑) -> service(复杂逻辑 公共逻辑等 可选 service可以调用其他service) -> dao(数据库) / integration(调用其他API接口)
