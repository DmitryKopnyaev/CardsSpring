package com.dimentor.cardsspring.service;

import com.dimentor.cardsspring.model.Card;
import com.dimentor.cardsspring.model.Category;
import com.dimentor.cardsspring.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public void setCategoryRepository(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }


    @Override
    public Category getCategoryById(long id) {
        return this.categoryRepository.findById(id).orElse(null);
    }

    @Override
    public void add(Category category) {
        try {
            this.categoryRepository.save(category);
        } catch (Exception e) {
            throw new IllegalArgumentException("Duplicate");
        }
    }

    @Override
    public void update(Category category) {
        this.categoryRepository.save(category);
    }

    @Override
    public Category delete(long id) {
        Category categoryById = this.getCategoryById(id);
        if (categoryById == null)
            return null;
        else this.categoryRepository.deleteById(id);
        return categoryById;
    }

    @Override
    public List<Category> getAll() {
        return this.categoryRepository.findAll();
    }
}
