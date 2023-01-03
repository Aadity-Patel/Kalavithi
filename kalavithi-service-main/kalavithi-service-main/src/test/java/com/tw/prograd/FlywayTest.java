package com.tw.prograd;

import com.tw.prograd.image.ImageRepository;
import com.tw.prograd.user.UserAuthRepository;
import com.tw.prograd.user.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;


@ExtendWith(SpringExtension.class)
@DataJpaTest
@TestPropertySource(properties = {
        "spring.jpa.hibernate.ddl-auto=validate"
})
public class FlywayTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private EntityManager entityManager;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserAuthRepository userAuthRepository;

    @Autowired
    private ImageRepository imageRepository;

    @Test
    void injectedComponentsAreNotNull() {
        assertThat(dataSource).isNotNull();
        assertThat(entityManager).isNotNull();
        assertThat(userRepository).isNotNull();
        assertThat(userAuthRepository).isNotNull();
        assertThat(imageRepository).isNotNull();
    }
}
