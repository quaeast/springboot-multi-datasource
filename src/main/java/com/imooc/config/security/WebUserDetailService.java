package com.imooc.config.security;



import com.imooc.dao.repository.WebUserRepository;
import com.imooc.entity.WebUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class WebUserDetailService implements UserDetailsService {

    @Autowired
    private WebUserRepository webUserRepository;


    public String getUsername() {
        SecurityContext securityContext = SecurityContextHolder.getContext();
        System.out.println(securityContext.getAuthentication().getAuthorities());
        return securityContext.getAuthentication().getName();
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        WebUser user = webUserRepository.findByUsername(username);
        if (user!=null){
            return user;
        }
        throw new UsernameNotFoundException( "User '" + username + "' not found");
    }
}
