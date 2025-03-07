package com.project.library.service;

import com.project.library.model.Admin;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.project.library.repository.AdminRepository;

@Service
public class AdminService {
    
    @Autowired
    private AdminRepository repository;
    
    public Admin login(String email, String password) {
        Admin user = null;
        Admin admin = repository.findById(1).get();
        
        if (admin.getEmail().equals(email) && admin.getPassword().equals(password)) {
            user = admin;
        }
        
        return user;
    }
    
    public Admin getAdminDetails() {
        return repository.findById(1).get();
    }
    
}
