package org.example;

import org.example.services.RoleService;
import org.example.services.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class KutubyApplication {
    public static void main(String[] args) {
        SpringApplication.run(KutubyApplication.class, args);
    }

    @Bean
    CommandLineRunner commandLineRunnerUserDetails(UserService userService, RoleService roleService){
        return args-> {
            roleService.addNewRole("USER");
            roleService.addNewRole("ADMIN");
            userService.addUser("user1", "1234", "user1@gmail.com", "1234");
            userService.addUser("user2", "1234", "user2@gmail.com", "1234");
            userService.addUser("admin", "1234", "admin@gmail.com", "1234");
            userService.addRoleToUser("user1","USER");
            userService.addRoleToUser("user2","USER");
            userService.addRoleToUser("admin","USER");
            userService.addRoleToUser("admin","Admin");
        };
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
