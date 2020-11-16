package com.imooc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

@DynamicInsert
@DynamicUpdate
@Data
@Table(name = "t_user")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)


    private Long userId;

    private String userName;

    private Integer gender;

    private Long phone;

    private String email;

    private Date createTime;

    private Date updateTime;

    public User(String userName, Integer gender, Long phone, String email, Date createTime) {
        this.userName = userName;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.createTime = createTime;
    }
}
