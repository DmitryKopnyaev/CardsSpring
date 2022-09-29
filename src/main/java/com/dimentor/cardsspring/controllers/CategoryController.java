package com.dimentor.cardsspring.controllers;

import com.dimentor.cardsspring.model.Category;
import com.dimentor.cardsspring.model.User;
import com.dimentor.cardsspring.service.CategoryService;
import com.dimentor.cardsspring.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/category")
public class CategoryController {

    private CategoryService categoryService;
    private UserService userService;

    @Autowired
    public void setCategoryService(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @Autowired
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(produces = "application/json")
    public List<Category> getAllCategories() {
        return this.categoryService.getAll();
    }

    //по id категории
    @GetMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Category> getCategoryById(@PathVariable long id) {
        Category categoryById = this.categoryService.getCategoryById(id);
        if (categoryById == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(categoryById, HttpStatus.OK);
    }

    //по id юзера
    @GetMapping(value = "/user/{id}", produces = "application/json")
    public ResponseEntity<List<Category>> getCategoriesByUserId(@PathVariable long id) {
        //см. card
        User userById = this.userService.getUserById(id);
        if (userById == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        else {
            List<Category> categories = userById.getCategories();
            return new ResponseEntity<>(categories, HttpStatus.OK);
        }
    }

    @PostMapping(value = "/{id}", consumes = "application/json", produces = "application/json")
    public ResponseEntity<Category> addCategoryByUserId(@PathVariable long id, @RequestBody Category category) {
        User userById = this.userService.getUserById(id);
        if (userById == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        else {
            category.setUser(userById);
            try {
                this.categoryService.add(category);
                return new ResponseEntity<>(category, HttpStatus.OK);
            } catch (IllegalArgumentException e) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
        }
    }

    @PutMapping(consumes = "application/json", produces = "application/json")
    public ResponseEntity<Category> updateCategory(@RequestBody Category category) {
        try {
            this.categoryService.update(category);
            return new ResponseEntity<>(category, HttpStatus.OK);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @DeleteMapping(value = "/{id}", produces = "application/json")
    public ResponseEntity<Category> deleteCategoryById(@PathVariable long id) {
        Category delete = this.categoryService.delete(id);
        if (delete == null)
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        else return new ResponseEntity<>(delete, HttpStatus.OK);
    }
}
