package com.imooc.interceptors;

import com.imooc.config.datasource.DataSourceContextHolder;
import com.imooc.entity.WebAuthority;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Collection;

public class HeaderModifyInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        SecurityContext securityContext = SecurityContextHolder.getContext();

        Collection<? extends GrantedAuthority> userRole = securityContext.getAuthentication().getAuthorities();
        Boolean hasRoleUser = userRole.contains(new WebAuthority("ROLE_USER"));
        response.setHeader(
                "Has-Role-User", String.valueOf(hasRoleUser));
        System.out.println(request.getHeader("Database-ID"));
        return true;
    }
}
