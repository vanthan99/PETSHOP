package com.cdio.petshop.services;

import com.cdio.petshop.entities.Category;
import com.cdio.petshop.repositories.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryService {
    private final CategoryRepository categoryRepository;

    public Category findById(Long id){
        return categoryRepository.getOne(id);
    }

    public List<Category> findAll(){
        return categoryRepository.findAll();
    }

    public void deleteById(Long id){
        categoryRepository.deleteById(id);
    }

    public void save(Category category){
        categoryRepository.save(category);
    }
}
