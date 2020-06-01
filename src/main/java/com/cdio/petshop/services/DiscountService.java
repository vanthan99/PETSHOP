package com.cdio.petshop.services;

import com.cdio.petshop.entities.Discount;
import com.cdio.petshop.repositories.DiscountRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DiscountService {
    private final DiscountRepository discountRepository;

    public Discount findById(Long id){
        return discountRepository.getOne(id);
    }

    public List<Discount> findAll(){
        return discountRepository.findAll();
    }

    public void deleteById(Long id){
        discountRepository.deleteById(id);
    }

    public void save(Discount discount){
        discountRepository.save(discount);
    }
}
