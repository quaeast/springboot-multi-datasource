package com.imooc.config.jpa;

import com.imooc.config.datasource.DynamicDataSourceRouter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.orm.jpa.JpaProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;

import javax.annotation.Resource;
import javax.persistence.EntityManagerFactory;
import javax.servlet.Servlet;
import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Slf4j
@SuppressWarnings("all")
@Configuration
//@EnableConfigurationProperties(JpaProperties.class)
@EnableJpaRepositories(value = "com.imooc.dao.repository",
        entityManagerFactoryRef = "entityManagerFactory",
        transactionManagerRef = "transactionManager")
public class JpaEntityManager {

    @Autowired
    private JpaProperties jpaProperties;

    @Resource(name = "masterDataSource")
    private DataSource masterDataSource;

    @Resource(name = "slaveDataSource")
    private DataSource slaveDataSource;

    @Autowired
    private Environment env;

    //    @Primary
    @Bean(name = "routingDataSource")
    public AbstractRoutingDataSource routingDataSource() {
        DynamicDataSourceRouter proxy = new DynamicDataSourceRouter();
        Map<Object, Object> targetDataSources = new HashMap<>(2);
        targetDataSources.put("masterDataSource", masterDataSource);
        targetDataSources.put("slaveDataSource", slaveDataSource);

        proxy.setDefaultTargetDataSource(masterDataSource);
        proxy.setTargetDataSources(targetDataSources);
        return proxy;
    }

//    @Primary
    @Bean(name = "entityManagerFactoryBean")
    public LocalContainerEntityManagerFactoryBean entityManagerFactoryBean(EntityManagerFactoryBuilder builder) {
        // 不明白为什么这里获取不到 application.yml 里的配置
        Map<String, String> properties = jpaProperties.getProperties();
        System.out.println("############ properties");
        System.out.println(properties);
//        properties.put("hibernate.physical_naming_strategy",
//                "org.springframework.boot.orm.jpa.hibernate.SpringPhysicalNamingStrategy");
        properties.put("hibernate.hbm2ddl.auto", "create-drop");

        System.out.println(properties);

        return builder
                .dataSource(routingDataSource())//关键：注入routingDataSource
                .properties(properties)
                .packages("com.imooc.entity")
                .persistenceUnit("myPersistenceUnit")
                .build();
    }

    @Primary
    @Bean(name = "entityManagerFactory")
    public EntityManagerFactory entityManagerFactory(EntityManagerFactoryBuilder builder) {
        return this.entityManagerFactoryBean(builder).getObject();
    }

    @Primary
    @Bean(name = "transactionManager")
    public PlatformTransactionManager transactionManager(EntityManagerFactoryBuilder builder) {
        return new JpaTransactionManager(entityManagerFactory(builder));
    }


}
