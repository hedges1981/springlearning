package com.hedges.config;

import org.slf4j.LoggerFactory;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.annotation.web.servlet.configuration.EnableWebMvcSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;


import javax.sql.DataSource;

/**
 * Created by rhall on 10/08/2016.
 */
@EnableWebSecurity
@Configuration
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private static final Logger LOGGER = LoggerFactory.getLogger( SecurityConfig.class );

    @Autowired
    DataSource dataSource;

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        
        LOGGER.info("Configuring security");

        http
                .rememberMe()
                /**
                 * NOTE: we specify an explicit remember-me key here. A remember me key is used to tie the browser
                 * cookie to this running app and is checked as part of validation.
                 * If not specified a random one is generated each time an instance is started, meaning that the
                 * remember me authentication cannot be validated across browser restarts. Note also that specifying
                 * an explicit key will allow it to work in a load balanced / cluster situation.
                 */
                .key("29758360-6cee-45fd-bc32-1ecec0ea2bff")
                .and()
                .formLogin()
                .loginPage("/login")
                .loginProcessingUrl("/doLogin")
                .permitAll()
                .and()
                .logout()
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout"))
                .logoutSuccessUrl("/login")
                .deleteCookies("remember-me")
                .and()
                .exceptionHandling()
                .accessDeniedPage("/accessDenied.html")
                .and()
                .authorizeRequests()
                .antMatchers("/**").hasAnyRole("ADMIN","READ-ONLY");

       LOGGER.info("Configuring security: Complete");
    }

    @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {

        LOGGER.info("Configuring global security");

        auth.inMemoryAuthentication()
                .withUser("admin").password("password").roles("ADMIN");

        //CONFIG FOR A DB SECURITY SYSTEM

//        String usersByUserNameQuery = "select " +
//                "username" +
//                ",password," +
//                "enabled " +
//                "from auth_user " +
//                "where username =? " +
//                "and account_expired = 'false' " +
//                "and account_locked = 'false' ";
//
//        String authoritiesByUserNameQuery =
//                " select username,authority from auth_user " +
//                        "inner join user_role on auth_user.id = user_role.auth_user_id " +
//                        "inner join role on role.id = user_role.role_id " +
//                        "where username = ? ";
//
//        auth
//                .jdbcAuthentication()
//                .passwordEncoder( new BCryptPasswordEncoder())
//                .dataSource( dataSource )
//                .usersByUsernameQuery( usersByUserNameQuery )
//                .authoritiesByUsernameQuery( authoritiesByUserNameQuery );

        LOGGER.info("Configuring global security: Complete");
    }
}
