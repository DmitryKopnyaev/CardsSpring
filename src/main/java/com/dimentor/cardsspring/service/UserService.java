package com.dimentor.cardsspring.service;

import com.dimentor.cardsspring.model.User;

import java.util.List;

public interface UserService {
    User getUserById(long id);
    void add(User user);
    void update(User user);
    User delete(long id);
    List<User> getAll();
}
