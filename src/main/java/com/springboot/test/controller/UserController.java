package com.springboot.test.controller;

import com.google.gson.Gson;
import com.springboot.test.exception.UserNotFoundException;
import com.springboot.test.model.User;
import com.springboot.test.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * REST controller for managing user accounts.
 */
@RestController
public class UserController {
    public static final Logger logger = LoggerFactory.getLogger(UserController.class);
    @Autowired
    UserService userService;
    Gson gson = new Gson();

    /**
     * {@code POST  /register} : register the user.
     *
     * @param user the user to register.
     * @return the registered user.
     * @throws MethodArgumentNotValidException {@code 400 (Bad Request)} if the username, the birthdate or the countryOfResidence is null.
     * @throws MethodArgumentNotValidException {@code 400 (Bad Request)} if the countryOfResidence is not France.
     * @throws MethodArgumentNotValidException {@code 400 (Bad Request)} if the birthdate does not correspond to an adult user.
     */
    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    private User registerUser(@Valid @RequestBody User user) {
        long startTime = System.currentTimeMillis();
        logger.info(gson.toJson(user));

        User registeredUser = userService.save(user);

        logger.info(gson.toJson(registeredUser));
        long endTime = System.currentTimeMillis();
        logger.info("Processing time : " + (endTime - startTime));
        return userService.save(registeredUser);
    }

    /**
     * {@code GET  /user} : get the registered user.
     *
     * @param username the username of the user.
     * @return the registered user.
     * @throws UserNotFoundException {@code 404 (Not Found)} if the user is not found.
     */
    @GetMapping("/user")
    private User getRegisteredUser(@RequestParam("username") String username) throws UserNotFoundException {
        long startTime = System.currentTimeMillis();
        logger.info(username);

        User user = userService.findByUserName(username);

        logger.info(gson.toJson(user));
        long endTime = System.currentTimeMillis();
        logger.info("Processing time : " + (endTime - startTime));
        return user;
    }
}
