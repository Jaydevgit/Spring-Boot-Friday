package com.example.demo.security;

import com.example.demo.security.authentication.MyAuthenctiationFailureHandler;
import com.example.demo.security.authentication.MyAuthenticationSuccessHandler;
import com.example.demo.security.authentication.MyLogoutSuccessHandler;
import com.example.demo.security.authentication.RestAuthenticationAccessDeniedHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Author: Jay
 * Date: 2019/12/31 15:50
 */
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    private UserDetailsService userDetailsService;

    @Autowired
    private MyAuthenticationSuccessHandler myAuthenticationSuccessHandler;

    @Autowired
    private MyAuthenctiationFailureHandler myAuthenctiationFailureHandler;

    @Autowired
    private RestAuthenticationAccessDeniedHandler restAuthenticationAccessDeniedHandler;

    @Autowired
    private MyLogoutSuccessHandler myLogoutSuccessHandler;

    @Bean
    public PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }

    /*进行真正的用户信息处理*/
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService).passwordEncoder(passwordEncoder());
    }

    //http请求的安全处理
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        /*关闭csrf跨站请求伪造*/
        http.csrf().disable();

        http.headers().frameOptions().sameOrigin();
        /*配置哪些http请求不需要验证，其他都需要验证*/
        http.authorizeRequests()
                .antMatchers("/login.html",
                        "/xadmin/**",
                        "/treeable-lay/**",
                        "/ztree/**",
                        "/static/**")
                .permitAll()
                .anyRequest()
                .authenticated();
        http.formLogin() //  定义当需要提交表单进行用户登录时候，转到的登录页面
                .loginPage("/login")
                .loginProcessingUrl("/login")/*拦截处理*/
                .successHandler(myAuthenticationSuccessHandler)
                .failureHandler(myAuthenctiationFailureHandler)

                .and().logout()
                .permitAll()
                .invalidateHttpSession(true)
                .deleteCookies("JSESSIONID")
                .logoutSuccessHandler(myLogoutSuccessHandler);
        //异常处理
        http.exceptionHandling().accessDeniedHandler(restAuthenticationAccessDeniedHandler);
    }
}
