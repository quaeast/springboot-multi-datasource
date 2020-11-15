package com.imooc.dao.repository;


import com.imooc.entity.WebAuthority;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorityRepository extends JpaRepository<WebAuthority, Long> {
}
