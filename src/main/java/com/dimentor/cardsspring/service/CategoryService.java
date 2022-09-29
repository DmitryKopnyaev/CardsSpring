package com.dimentor.cardsspring.service;

import com.dimentor.cardsspring.model.Category;
import com.dimentor.cardsspring.model.User;

import java.util.List;

public interface CategoryService {
    Category getCategoryById(long id);
    void add(Category category);
    void update(Category category);
    Category delete(long id);
    List<Category> getAll();

}
