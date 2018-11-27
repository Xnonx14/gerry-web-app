package app.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@Configuration
@EnableWebSecurity
public class SecurityController extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
            .authorizeRequests()
            .antMatchers("/","/css/**","/js/**","/webjars/**","/index","/register","/about","/loggedIn").permitAll().anyRequest().authenticated()
        .and()
            .formLogin()
            .loginPage("/login").defaultSuccessUrl("/loggedIn").permitAll()
        .and()
            .logout()
            .logoutSuccessUrl("/index").permitAll();
        http.csrf().disable();
    }

     @Autowired
    public void configureGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.inMemoryAuthentication()
            .withUser("user1").password("{noop}user1Pass").roles("USER")
            .and()
            .withUser("admin1").password("{noop}admin1Pass").roles("ADMIN")
            .and()
            .withUser("kevin").password("{noop}chang").roles("USER");
    }
}