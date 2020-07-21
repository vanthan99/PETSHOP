package com.cdio.petshop.services;

import com.cdio.petshop.entities.Product;
import com.cdio.petshop.repositories.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProductService{
    @Autowired
    private ProductRepository productRepository;
    public Product findById(Long id){
        return productRepository.getOne(id);
    }

    public Page<Product> listAll(int pageNum){
        int pageSize = 12;
        Pageable pageable = PageRequest.of(pageNum-1,pageSize);
        return productRepository.findAll(pageable);
    }

    public List<Product> finAllProductIsActive(){
            return productRepository.finAllProductActive();
    }

    public List<Product> finAllProductNotActive(){
        return productRepository.finAllProductNotActive();
    }

    public void save(Product product){
         productRepository.save(product);
    }

    // nếu có tham số truyền vào thì trả về danh sách các sản phẩm có liên quan tới từ khóa "keyquoc"
    // Ngược lại. nếu không có tham số truyền vào. thì trả về toàn bộ danh sách sản phẩm.
    public List<Product> findByProductName(String productName){
        if (productName == null){
            return productRepository.findAll();
        }
        else return productRepository.findByProductName(productName);
    }

    public List<Product> findAllByKeyword(String keyword){
        if (keyword == null){
            return productRepository.findAll();
        }
        else return productRepository.findAllByKeyword(keyword);
    }
}
