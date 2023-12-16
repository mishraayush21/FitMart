package com.fitmart.proj.service;

import com.fitmart.proj.repository.CategoryRepository;
import com.fitmart.proj.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;
    public List<Category> getAllCategory() {
        return categoryRepository.findAll();
    }
    public void addCategory(Category category) {
        categoryRepository.save(category);
    }

    public void deleteCategoryById(int categoryId) {
        categoryRepository.deleteById(categoryId);
    }

    public Optional<Category> getCategoryById(int categoryId) {
        return categoryRepository.findById(categoryId);
    }

}
