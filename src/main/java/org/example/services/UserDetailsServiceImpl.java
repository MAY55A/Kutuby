package org.example.services;

import lombok.AllArgsConstructor;
import org.example.entities.User;
import org.example.entities.AppRole;
import org.example.repositories.AppRoleRepository;
import org.example.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private AccountService accountService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = accountService.loadUserByUsername(username);

        if (user == null) throw new UsernameNotFoundException(String.format("USER %s n'existe pas", username));

        String[] roles = user.getRoles().stream().map(u -> u.getRole()).toArray(String[]::new);
        UserDetails userDetails = org.springframework.security.core.userdetails.User
                .withUsername(user.getUserName())
                .password(user.getPasswordHash())
                .roles(roles).build();
        return userDetails;
    }
}
