package com.springboot.test.service;

import com.springboot.test.exception.UserNotFoundException;
import com.springboot.test.model.User;
import com.springboot.test.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Service class for managing users.
 */
@Service
public class UserService {
    @Autowired
    UserRepository userRepository;

    /**
     * Get a registered user by the username
     *
     * @param username the username of the user.
     * @return the registered user.
     * @throws UserNotFoundException {@code 404 (Not Found)} if the user is not found.
     */
    public User findByUserName(String username) throws UserNotFoundException {
        List<User> users = userRepository.findByUsername(username);
        if (users == null || users.size() == 0) {
            throw new UserNotFoundException("No user found for this username : " + username);
        }
        User user = users.get(0);
        return user;
    }

    /**
     * Register the user
     *
     * @param user the user to register.
     * @return the registered user.
     */
    public User save(User user) {
        return userRepository.save(user);
    }
}