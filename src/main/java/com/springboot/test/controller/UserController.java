package com.springboot.test.controller;

import com.google.gson.Gson;
import com.springboot.test.exception.InvalidDataException;
import com.springboot.test.exception.UserAlreadyExistsException;
import com.springboot.test.exception.UserNotFoundException;
import com.springboot.test.model.User;
import com.springboot.test.service.UserServiceImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

/**
 * REST controller for managing user accounts.
 */
@RestController
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserServiceImpl userServiceImpl;
    Gson gson;

    public UserController(UserServiceImpl userServiceImpl) {
        this.userServiceImpl = userServiceImpl;
        this.gson = new Gson();
    }

    /**
     * {@code POST  /register} : register the user.
     *
     * @param user the user to register.
     * @return the registered user.
     * @throws MethodArgumentNotValidException {@code 400 (Bad Request)} if the username, the birthdate or the countryOfResidence is null.
     * @throws MethodArgumentNotValidException {@code 400 (Bad Request)} if the countryOfResidence is not France.
     * @throws MethodArgumentNotValidException {@code 400 (Bad Request)} if the birthdate does not correspond to an adult user.
     * @throws UserAlreadyExistsException      {@code 400 (Bad Request)} if the user the username corresponds to an existing user.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    private User registerUser(@Valid @RequestBody User user) throws UserAlreadyExistsException {
        long startTime = System.currentTimeMillis();
        logger.info(gson.toJson(user));

        User registeredUser;
        Optional<User> optional = userServiceImpl.findByUserName(user.getUsername());

        if (optional.isPresent()) {
            throw new UserAlreadyExistsException("A user with the username " + user.getUsername() + " is already registered");
        } else {
            registeredUser = userServiceImpl.save(user);
            logger.info(gson.toJson(registeredUser));
        }

        long endTime = System.currentTimeMillis();
        logger.info("Processing time : " + (endTime - startTime));
        return registeredUser;
    }

    /**
     * {@code GET  /user} : get the registered user.
     *
     * @param username the username of the user.
     * @return the registered user.
     * @throws UserNotFoundException {@code 404 (Not Found)} if the user is not found.
     * @throws InvalidDataException  {@code 404 (Bad Request)} if the username is null or empty.
     */
    @GetMapping("/user")
    private User getRegisteredUser(@RequestParam("username") String username) throws UserNotFoundException, InvalidDataException {
        long startTime = System.currentTimeMillis();

        if (username == null || username.isEmpty()) {
            throw new InvalidDataException("Please provide a valid user name");
        }

        logger.info(username);

        User user = userServiceImpl.findByUserName(username).orElseThrow(() -> new UserNotFoundException("No user found for the username " + username));

        logger.info(gson.toJson(user));
        long endTime = System.currentTimeMillis();
        logger.info("Processing time : " + (endTime - startTime));
        return user;
    }
}
