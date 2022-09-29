package com.dimentor.cardsspring.service;

import com.dimentor.cardsspring.model.User;
import com.dimentor.cardsspring.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;

    @Autowired
    public void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User getUserById(long id) {
        return this.userRepository.findById(id).orElse(null);
    }

    @Override
    public void add(User user) {
        try {
            this.userRepository.save(user);
        } catch (Exception e) {
            throw new IllegalArgumentException("Duplicate");
        }
    }

    @Override
    public void update(User user) {
        this.userRepository.save(user);
    }

    @Override
    public User delete(long id) {
        User userById = this.getUserById(id);
        if (userById == null)
            return null;
        else this.userRepository.deleteById(id);
        return userById;
    }

    @Override
    public List<User> getAll() {
        return this.userRepository.findAll();
    }
}