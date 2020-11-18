# springboot-multi-datasource 根据用户切换数据源

使用springboot + JPA 实现多数据源动态切换，从而实现简单的数据库读写分离

### 问题 1

在 `JpaEntityManager.java` JPA 供应商 hibernate 的 properties 不能使用 Spring boot 参数里面的名字，而应该使用 hibernate 自己的。

```java
private Properties additionalProperties() {
    Properties properties = new Properties();
    properties.setProperty("hibernate.hbm2ddl.auto", env.getProperty("spring.jpa.hibernate.ddl-auto"));
    properties.setProperty("hibernate.dialect", env.getProperty("spring.jpa.properties.hibernate.dialect"));
    properties.setProperty("hibernate.current_session_context_class", env.getProperty("spring.jpa.properties.hibernate.current_session_context_class"));
    properties.setProperty("hibernate.jdbc.lob.non_contextual_creation", env.getProperty("spring.jpa.properties.hibernate.jdbc.lob.non_contextual_creation"));
    properties.setProperty("hibernate.show_sql", env.getProperty("spring.jpa.show-sql"));
    properties.setProperty("hibernate.format_sql", env.getProperty("spring.jpa.properties.hibernate.format_sql"));
    return properties;
}
```

[link](https://www.thomasvitale.com/spring-data-jpa-hibernate-java-configuration/)
