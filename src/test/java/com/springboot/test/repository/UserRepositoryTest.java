package com.springboot.test.repository;

import com.springboot.test.model.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit4.SpringRunner;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
@DataJpaTest
public class UserRepositoryTest {

    @Autowired
    UserRepository userRepository;
    @Autowired
    private TestEntityManager entityManager;

    @Test
    public void registerUserTest() throws ParseException {
        Date birthDate = null;
        birthDate = new SimpleDateFormat("yyyy-MM-dd").parse("1995-12-01");
        User user = new User("jeanmichel", birthDate, "FR", 331454532, "M");
        User user1 = userRepository.save(user);
        assertThat(user1.getUsername() == user.getUsername()).isTrue();
    }

    @Test
    public void getARegisteredUser() throws ParseException {
        Date birthDate = null;
        birthDate = new SimpleDateFormat("yyyy-MM-dd").parse("1993-12-01");
        User user = new User("jeancharles", birthDate, "FR", 331110532, "M");
        entityManager.persist(user);
        entityManager.flush();
        assertThat(userRepository.findByUsername("jeancharles").isPresent()).isTrue();
    }
}
