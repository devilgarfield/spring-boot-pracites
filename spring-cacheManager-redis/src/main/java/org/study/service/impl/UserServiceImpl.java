package org.study.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.study.model.User;
import org.study.service.IUserService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Service
@CacheConfig(cacheNames = "user" )
@Slf4j
public class UserServiceImpl implements IUserService {
    private final List<User> users;

    {
        User hdx = User.builder().id(1).age(32).name("hdx").build();
        users = new ArrayList<>();
        users.add(hdx);
    }


    @Override
    @Cacheable(key = "#id")
    public User getUser(Integer id) {
        log.info("invoke method with id {}", id);
        User user = users.get(0);
        return Objects.equals(id, user.getId()) ? user : User.builder().id(2).age(33).name("lm").build();
    }

    @Override
    @CachePut(key = "#user?.id", condition = "result?.id>0")
    public User updateUser(User user) {
        return null;
    }

    @Override
    public void deleteUser(User user) {

    }
}
