package com.springboot.test.service;

import com.springboot.test.exception.UserNotFoundException;
import com.springboot.test.model.User;
import com.springboot.test.repository.UserRepository;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;

@RunWith(SpringRunner.class)
public class UserServiceImplTest {

    @Autowired
    private UserService userService;
    @MockBean
    private UserRepository userRepository;

    @Before
    public void setUp() throws ParseException, UserNotFoundException {
        Date birthDate = null;
        birthDate = new SimpleDateFormat("yyyy-MM-dd").parse("1995-12-01");
        User user = new User("jeanmichel", birthDate, "FR", 331454532, "M");

        Date birthDate1 = null;
        birthDate1 = new SimpleDateFormat("yyyy-MM-dd").parse("1993-10-25");
        User user1 = new User("jeancharles", birthDate1, "FR", 331000532, "M");

        Mockito.when(userRepository.findByUsername(user.getUsername())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(any(User.class))).thenReturn(user1);
    }

    @Test
    public void registerAUser() throws ParseException {
        Date birthDate = null;
        birthDate = new SimpleDateFormat("yyyy-MM-dd").parse("1993-10-25");
        User user = new User("jeancharles", birthDate, "FR", 331000532, "M");

        User user1 = userService.save(user);

        assertThat(user1).isNotNull();
    }

    @Test
    public void getARegisteredUser() throws UserNotFoundException {
        String username = "jeanmichel";
        User user = userService.findByUserName(username).get();

        assertThat(user.getUsername()).isEqualTo(username);
    }

    @TestConfiguration
    static class UserServiceTestContextConfiguration {
        @Bean
        public UserService userService() {
            return new UserServiceImpl();
        }
    }

}
