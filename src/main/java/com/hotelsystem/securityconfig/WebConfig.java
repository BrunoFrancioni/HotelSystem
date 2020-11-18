package com.hotelsystem.securityconfig;

import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configuration
public class WebConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception{
        http.httpBasic()
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.GET, "/room/").permitAll()
                .antMatchers(HttpMethod.DELETE, "/room/").permitAll()
                .antMatchers(HttpMethod.PUT, "/room/").permitAll()
                .antMatchers(HttpMethod.POST, "/room/").permitAll()
                .antMatchers(HttpMethod.GET, "/user/").permitAll()
                .antMatchers(HttpMethod.DELETE, "/user/").permitAll()
                .antMatchers(HttpMethod.PUT, "/user/").permitAll()
                .antMatchers(HttpMethod.POST, "/user/").permitAll()
                .antMatchers(HttpMethod.GET, "/booking/").permitAll()
                .antMatchers(HttpMethod.DELETE, "/booking/").permitAll()
                .antMatchers(HttpMethod.PUT, "/booking/").permitAll()
                .antMatchers(HttpMethod.POST, "/booking/").permitAll()
                .and()
                .csrf().disable()
                .formLogin().disable();
    }
}