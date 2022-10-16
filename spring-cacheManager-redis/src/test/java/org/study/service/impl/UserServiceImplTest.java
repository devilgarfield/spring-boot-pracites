package org.study.service.impl;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;
import org.study.model.User;
import org.study.service.IUserService;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
class UserServiceImplTest {
    private static final Logger log =
            LoggerFactory.getLogger(UserServiceImplTest.class);
    @Autowired
    private IUserService userService;

    @Autowired
    ApplicationContext ctx;

    @RepeatedTest(2)
    void getUser() {
        IUserService bean = ctx.getBean(IUserService.class);
        log.info("bean is {}", bean);
        User user = userService.getUser(1);
        assertEquals("hdx", user.getName());
    }

    @Test
    void updateUser() {
    }

    @Test
    void deleteUser() {
    }

    @AfterAll
    static void validateCallTime() {

    }
}