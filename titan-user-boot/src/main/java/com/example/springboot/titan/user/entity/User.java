package com.example.springboot.titan.user.entity;

import lombok.Data;

import javax.persistence.*;
import java.util.Date;

/**
 * @author Eric.Mo
 * @since 2020/3/8
 */
@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column
    private String userId;

    @Column
    private String username;

    @Column
    private Date createTime;

    @Column
    private Date updateTime;

}
