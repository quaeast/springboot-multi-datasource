package com.imooc.aop;

import com.imooc.config.datasource.DataSourceContextHolder;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
public class DynamicDataSourceAspect {

    @Pointcut("execution(* com.imooc.service..*.*(..))")
    private void aspect() {

    }

    @Around("aspect()")
    public Object around(ProceedingJoinPoint joinPoint) throws Throwable {

        SecurityContext securityContext = SecurityContextHolder.getContext();
        System.out.println("AOP output");

//        String method = joinPoint.getSignature().getName();
        String userRole = securityContext.getAuthentication().getAuthorities().toString();

        if (userRole.equals("[ROLE_USER]")) {
            DataSourceContextHolder.setDataSource("slaveDataSource");
            log.info("switch to slave datasource...");
        } else {
            DataSourceContextHolder.setDataSource("masterDataSource");
            log.info("switch to master datasource...");
        }

        try {
            return joinPoint.proceed();
        } finally {
            log.info("清除 datasource router...");
            DataSourceContextHolder.clear();
        }
    }
}
