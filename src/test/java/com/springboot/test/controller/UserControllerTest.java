package com.springboot.test.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.springboot.test.service.UserService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Integration tests for UserController.
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UserControllerTest {

    private static final ObjectMapper om = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Mock
    private UserService userService;

    @Test
    public void registerAUserWithANullUserName() throws Exception {

        String userInJson = "{\n" +
                "    \"birthDate\" : \"1995-12-01\",\n" +
                "    \"countryOfResidence\" : \"FR\",\n" +
                "    \"phoneNumber\": 33145453245,\n" +
                "    \"gender\": \"M\"\n" +
                "}";

        mockMvc.perform(post("/register")
                        .content(userInJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(1)))
                .andExpect(jsonPath("$.errors", hasItem("Please provide your user name")));
    }

    @Test
    public void registerAMinorNotFrenchUser() throws Exception {

        String userInJson = "{\n" +
                "    \"username\" : \"jeanmichel\",\n" +
                "    \"birthDate\" : \"2005-12-01\",\n" +
                "    \"countryOfResidence\" : \"SN\",\n" +
                "    \"phoneNumber\": 33145453245,\n" +
                "    \"gender\": \"M\"\n" +
                "}";

        mockMvc.perform(post("/register")
                        .content(userInJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.timestamp", is(notNullValue())))
                .andExpect(jsonPath("$.status", is(400)))
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(2)))
                .andExpect(jsonPath("$.errors", hasItem("The birth date must be greater or equal than 18")))
                .andExpect(jsonPath("$.errors", hasItem("The country of residence must be France")));
    }

    @Test
    public void registerAValidUser() throws Exception {
        String userInJson = "{\n" +
                "    \"username\" : \"jeanmichel\",\n" +
                "    \"birthDate\" : \"1995-12-01\",\n" +
                "    \"countryOfResidence\" : \"FR\",\n" +
                "    \"phoneNumber\": 33145453245,\n" +
                "    \"gender\": \"M\"\n" +
                "}";

        mockMvc.perform(post("/register")
                        .content(userInJson)
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id", is(notNullValue())))
                .andExpect(jsonPath("$.username", is(notNullValue())))
                .andExpect(jsonPath("$.birthDate", is(notNullValue())))
                .andExpect(jsonPath("$.countryOfResidence", is(notNullValue())));
    }

    @Test
    public void getANonExtentUser() throws Exception {
        mockMvc.perform(get("/user" + "?username=xxxxxxxx")
                        .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }
}