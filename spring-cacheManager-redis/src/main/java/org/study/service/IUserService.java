package org.study.service;

import org.study.model.User;

public interface IUserService {

    User getUser(Integer id);

    User updateUser(User user);

    void deleteUser(User user);
}
