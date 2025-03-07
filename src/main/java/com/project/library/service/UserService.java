package com.project.library.service;

import com.project.library.model.User;
import com.project.library.repository.UserRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    
    @Autowired
    private UserRepository userRepository;
    
    public User login(String email, String password) {
        User loggedUser = null;
        List<User> allUsers = userRepository.findAll();
        
        for (User user : allUsers) {
            if (user.getEmail().equals(email) && user.getPassword().equals(password)) {
                loggedUser = user;
                break;
            }
        }
        
        return loggedUser;
    }
    
    public void registerUser(User user) {
        userRepository.save(user);
    }
    
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }
}
