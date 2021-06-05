package com.sda.store.sdastore.service.implementation;

import com.sda.store.sdastore.exception.ResourceNotFoundInDatabase;
import com.sda.store.sdastore.model.Category;
import com.sda.store.sdastore.repository.CategoryRepository;
import com.sda.store.sdastore.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CategoryServiceImplementation implements CategoryService {

    private CategoryRepository categoryRepository;

    @Autowired
    public CategoryServiceImplementation(CategoryRepository categoryRepository){
        this.categoryRepository = categoryRepository;
    }

    @Override
    public Category create(Category category) {
       return  categoryRepository.save(category);
    }

    @Override
    public List<Category> findAll() {
       return categoryRepository.findAll();
    }

    @Override
    public Category update(Category category) {
        //TODO decide how update should work
       return categoryRepository.save(category);
    }

    @Override
    public void delete(Long id) {
        categoryRepository.deleteById(id);
    }

    @Override
    public Category findById(Long id) {
        Optional<Category> category = categoryRepository.findById(id);
        if(category.isPresent()){
            return category.get();
        } else{
            throw new ResourceNotFoundInDatabase(String.format("Category with id %d not found.", id));
        }
    }

    @Override
    public Category findByName(String name) {
        Optional<Category> category = categoryRepository.findByName(name);
        if(category.isPresent()){
            return category.get();
        } else {
            throw new ResourceNotFoundInDatabase(String.format("Category with name %s was not found", name));
        }

    }

    @Override
    public List<Category> findAllRootCategories() {
        return categoryRepository.findAllByParentNull();
    }
}
