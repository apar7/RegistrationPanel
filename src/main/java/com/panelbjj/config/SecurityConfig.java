package com.panelbjj.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;


@EnableWebMvcSecurity
@PropertySource("classpath:emailAndDatabase.properties")
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Autowired
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }

    private Environment environment;


    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .headers().frameOptions().disable()
                .authorizeRequests().antMatchers("/admin/**").hasRole("ADMIN")
                .anyRequest().permitAll()
                .and().formLogin().and().httpBasic();
    }

    @Override
    protected void configure(AuthenticationManagerBuilder auth)
            throws Exception {


        auth
                .inMemoryAuthentication()
                .withUser("admin").password(environment.getProperty("adminPassword")).roles("ADMIN");

    }
}