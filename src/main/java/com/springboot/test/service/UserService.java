package com.springboot.test.service;

import com.springboot.test.exception.UserNotFoundException;
import com.springboot.test.model.User;

import java.util.Optional;

public interface UserService {
    Optional<User> findByUserName(String username) throws UserNotFoundException;

    User save(User user);
}
