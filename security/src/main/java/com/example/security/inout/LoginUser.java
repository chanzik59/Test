package com.example.security.inout;

import com.example.security.inout.dao.SysUser;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author chenzhiqin
 * @date 2022/8/23 11:29
 * @info XX
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LoginUser {

    private SysUser sysUser;


    public String getPassword() {
        return sysUser.getPassword();
    }

    public String getUsername() {
        return sysUser.getUserName();
    }

    public boolean isAccountNonExpired() {
        return true;
    }

    public boolean isAccountNonLocked() {
        return true;
    }


    public boolean isCredentialsNonExpired() {
        return true;
    }


    public boolean isEnabled() {
        return true;
    }
}
