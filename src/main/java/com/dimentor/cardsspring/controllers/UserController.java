package com.dimentor.cardsspring.controllers;

import com.dimentor.cardsspring.model.User;
import com.dimentor.cardsspring.service.UserService;
import com.dimentor.cardsspring.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {

    private UserService userService;

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = "application/json")
    public List<User> getAllUsers() {
        return this.userService.getAll();
    }

    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<User> getUserById(@PathVariable long id) {
        User userById = this.userService.getUserById(id);
        if (userById == null) return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @PostMapping(value = "/reg", consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> addUser(@RequestBody User user) {
        try {
            user.setRegDate(new Date());
            this.userService.add(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @PostMapping(value = "/login", produces = "application/json")
    public ResponseEntity<User> signIn(@RequestParam String login, @RequestParam String password) {
        User user = this.userService.getAll().stream()
                .filter(o -> o.getLogin().equals(login) && o.getPassword().equals(password))
                .findFirst().orElse(null);
        if (user == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        else {
            String hash = StringUtil.generateHash();
            user.setHash(hash);
            this.userService.update(user);
            return new ResponseEntity<>(user, HttpStatus.OK);
        }
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        long id = user.getId();
        User userById = this.userService.getUserById(id);
        if (userById == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        else
            this.userService.update(user);
        return new ResponseEntity<>(userById, HttpStatus.OK);
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<User> deleteUserById(@PathVariable long id) {
        User delete = this.userService.delete(id);
        if (delete == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(delete, HttpStatus.OK);
    }
}

