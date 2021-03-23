package com.example.business_server.model.nosql;

import com.example.business_server.model.domain.User;
import com.example.business_server.model.dto.LoginType;
import lombok.Data;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;

@Data
public class LoginUser implements UserDetails, CredentialsContainer {
    private static final long serialVersionUID = -2608763392984715652L;

    private User user;

    private String loginIp;

    private LocalDateTime loginTime;

    private LoginType loginType;

    public LoginUser() {
    }

    public LoginUser(User user, String loginIp, LocalDateTime loginTime, LoginType loginType) {
        this.user = user;
        this.loginIp = loginIp;
        this.loginTime = loginTime;
        this.loginType = loginType;
    }

    @Override
    public void eraseCredentials() {

    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return null;
    }

    @Override
    public String getPassword() {
        return user.getPassword();
    }

    @Override
    public String getUsername() {
        return user.getAccount();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
