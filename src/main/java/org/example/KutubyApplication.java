package org.example;

import org.example.services.AccountService;
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
    CommandLineRunner commandLineRunnerUserDetails(AccountService accountService){
        return args-> {
            accountService.addNewRole("USER");
            accountService.addNewRole("ADMIN");
            accountService.addNewUser("user1", "1234", "user1@gmail.com", "1234");
            accountService.addNewUser("user2", "1234", "user2@gmail.com", "1234");
            accountService.addNewUser("admin", "1234", "admin@gmail.com", "1234");
            accountService.addRoleToUser("user1","USER");
            accountService.addRoleToUser("user2","USER");
            accountService.addRoleToUser("admin","USER");
            accountService.addRoleToUser("admin","Admin");
        };
    }
    @Bean
    PasswordEncoder passwordEncoder(){
        return new BCryptPasswordEncoder();
    }
}
