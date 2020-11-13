package com.imooc.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

//@DynamicInsert
//@DynamicUpdate
@Data
@Table(name = "t_user")
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    @Column
    private Long userId;
    @Column
    private String userName;
    @Column
    private Integer gender;
    @Column
    private Long phone;
    @Column
    private String email;
    @Column
    private Date createTime;
    @Column
    private Date updateTime;

    public User(String userName, Integer gender, Long phone, String email, Date createTime) {
        this.userName = userName;
        this.gender = gender;
        this.phone = phone;
        this.email = email;
        this.createTime = createTime;
    }
}
