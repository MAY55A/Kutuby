package org.example.services;

import org.example.entities.AppRole;
import org.example.repositories.AppRoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RoleService {
    @Autowired
    private AppRoleRepository appRoleRepository;
    @Autowired
    public RoleService(AppRoleRepository appRoleRepository) {
        this.appRoleRepository = appRoleRepository;
    }
    public AppRole addNewRole(String role) {
        AppRole appRole = appRoleRepository.findById(role).orElse(null);
        if (appRole != null) throw new RuntimeException("Role already exists !");
        appRole = AppRole.builder()
                .role(role)
                .build();
        return appRoleRepository.save(appRole);
    }
}
