package org.example.Configuration;




import org.example.services.UserDetailsServiceImpl;
import org.example.services.UserDetailsServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;


import org.springframework.security.core.userdetails.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.provisioning.JdbcUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

import javax.sql.DataSource;

@Configuration // definit dans le context spring cette classe en tant que bean de configuration
@EnableWebSecurity // définit la classe SpringSecurityConfig en tant que configuration Spring Security
@EnableMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig {
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserDetailsServiceImpl userDetailsServiceImpl;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity) throws Exception{
        httpSecurity.formLogin().loginPage("/login").permitAll();
        httpSecurity.formLogin().defaultSuccessUrl("/profile", true);
        httpSecurity.authorizeHttpRequests().requestMatchers("/webjars/**", "/styles/**","/images/**","https://unpkg.com/boxicons@2.0.7/css/boxicons.min.css", "https://cdnjs.cloudflare.com/**", "http://www.w3.org/**").permitAll();
        //URLs accessible
        httpSecurity.authorizeHttpRequests().requestMatchers("/", "/books/**", "/collections/**", "/about_us", "/signup", "/leaderboard/**", "/admin/login", "/users/{id}", "/error").permitAll();

        //httpSecurity.authorizeHttpRequests().requestMatchers("/user/**").hasRole("USER");
        httpSecurity.authorizeHttpRequests().requestMatchers("/admin/**").hasRole("ADMIN");
        httpSecurity.authorizeHttpRequests().anyRequest().authenticated();
        httpSecurity.exceptionHandling().accessDeniedPage("/unauthorised");
        httpSecurity.userDetailsService(userDetailsServiceImpl);
        return httpSecurity.build();
    }

}