package com.example.business_server.config;

import com.example.business_server.security.*;
import com.example.business_server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(PermitAllUrlProperties.class)
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private AnonymousAuthenticationEntryPoint anonymousAuthenticationEntryPoint;

    /**
     * 登录用户没有权限访问资源
     */
    @Autowired
    private LoginUserAccessDeniedHandler accessDeniedHandler;

    @Autowired
    private LoginSuccessHandler loginSuccessHandler;

    @Autowired
    private LoginFailureHandler loginFailureHandler;

    @Autowired
    private IheLogoutSuccessHandler logoutSuccessHandler;

    @Autowired
    private InvalidSessionHandler invalidSessionHandler;

    /**
     * 放行URL
     */
    @Autowired
    private PermitAllUrlProperties permitAllUrlProperties;

    /**
     * 顶号处理
     */
    @Autowired
    private SessionInformationExpiredHandler sessionInformationExpiredHandler;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.cors().and().csrf().disable();
        http
                .authorizeRequests()
                // 发行接口
                .antMatchers(permitAllUrlProperties.getIgnoreUrls().toArray(new String[0])).permitAll()
                // 所有请求都需要认证
                .anyRequest().authenticated()
                // 异常处理（权限拒绝、登录失效等）
                .and().exceptionHandling()
                // 匿名用户访问无权限资源时处理
                .authenticationEntryPoint(anonymousAuthenticationEntryPoint)
                // 登录用户没有权限访问
                .accessDeniedHandler(accessDeniedHandler)
                // ----loginIn-----
                .and().formLogin()
                // 允许所有用户
                .permitAll()
                // 登录成功处理逻辑
                .successHandler(loginSuccessHandler)
                // 登录失败处理逻辑
                .failureHandler(loginFailureHandler)
                // -------logout------
                .and().logout()
                .permitAll()
                // 登出成功处理逻辑
                .logoutSuccessHandler(logoutSuccessHandler)
                .deleteCookies(SessionResolver.AUTH_TOKEN)
                // -----session manage
                .and().sessionManagement()
                .invalidSessionStrategy(invalidSessionHandler)
                // 同账号最大登录用户数
                .maximumSessions(2)
                // 顶号处理策略
                .expiredSessionStrategy(sessionInformationExpiredHandler)
                ;
    }


    @Override
    protected void configure(AuthenticationManagerBuilder auth) {
        DaoAuthenticationProvider provider=new DaoAuthenticationProvider();
        provider.setUserDetailsService(userDetailsService());
//        provider.setPasswordEncoder(bcryptPasswordEncoder());
        auth.authenticationProvider(provider);
//        auth.userDetailsService(userService);
    }

    @Bean
    protected UserDetailsService userDetailsService(){
        return new UserDetailsServiceImpl();
    }

    @Bean
    public PasswordEncoder bcryptPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

    @Bean
    public AuthenticationManager authenticationManager() throws Exception{
        return super.authenticationManager();
    }
}
