# springboot-multi-datasource 根据用户切换数据源

## 已经完成的工作

* 升级 Spring Boot 的版本到 2.3.1，删除多余的依赖
* 配置两个数据源
* HTTP basic 验证
* 更具用户的 ROLE 切换数据库源，使用 AOP 的方式进行切换，可以做到不修改之前的接口代码

## TODO

* 补全用户 entity 和 service
* 选择一种适合 Restful API 的验证方式

