package com.example.security.inout.dao;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * @author phone
 */
@Data
@NoArgsConstructor
public class SysUser {
    private Long id;

    private String userName;

    private String nickName;

    private String password;

    private String status;

    private String email;

    private String mobile;

    private String sex;

    private String avatar;

    private String userType;

    private Long createBy;

    private Date createTime;

    private Long updateBy;

    private Date updateTime;

    private Integer delFlag;
}