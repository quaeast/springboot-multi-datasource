package com.imooc.aop;

import com.imooc.config.datasource.DataSourceContextHolder;
import com.imooc.entity.WebAuthority;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Collection;

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

        Collection<? extends GrantedAuthority> userRole = securityContext.getAuthentication().getAuthorities();
        Boolean hasRoleUser = userRole.contains(new WebAuthority("ROLE_USER"));
        System.out.println("---------AOP output-------");
        System.out.println(securityContext.getAuthentication().isAuthenticated());
        System.out.println(userRole);

        if (!hasRoleUser) {
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
