package com.springboot.test.service;

import com.springboot.test.model.User;
import com.springboot.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;


/**
 * Service class for managing users.
 */
@Service
public class UserServiceImpl implements UserService {
    @Autowired
    UserRepository userRepository;

    public UserServiceImpl() {
    }

    /**
     * Get a registered user by the username
     *
     * @param username the username of the user.
     * @return the registered user.
     */
    @Override
    public Optional<User> findByUserName(String username) {
        Optional<User> user = userRepository.findByUsername(username);
        return user;
    }

    /**
     * Register the user
     *
     * @param user the user to register.
     * @return the registered user.
     */
    @Override
    public User save(User user) {
        return userRepository.save(user);
    }
}