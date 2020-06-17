package com.cdio.petshop.services;

import com.cdio.petshop.entities.Product;
import com.cdio.petshop.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductService {
    private final ProductRepository productRepository;

    public Product findById(Long id){
        return productRepository.getOne(id);
    }

    public List<Product> findAll(){
        return productRepository.findAll(Sort.by("name"));
    }

    public void deleteById(Long id){
        productRepository.deleteById(id);
    }

    public void save(Product product){
         productRepository.save(product);
    }
}
