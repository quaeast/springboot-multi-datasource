package com.imooc.dao.repository;

import com.imooc.entity.WebUser;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WebUserRepository extends CrudRepository<WebUser, Long> {
    WebUser findByUsername(String username);
}