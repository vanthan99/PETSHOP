package com.cdio.petshop.services;

import com.cdio.petshop.entities.DiscountDetail;
import com.cdio.petshop.entities.DiscountDetailIdentity;
import com.cdio.petshop.repositories.DiscountDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DiscountDetailService {
    @Autowired
    private DiscountDetailRepository discountDetailRepository;

    public void deleteById(DiscountDetailIdentity discountDetailIdentity){
        discountDetailRepository.deleteById(discountDetailIdentity);
    }

    public void save(DiscountDetail discountDetail){
        discountDetailRepository.save(discountDetail);
    }

    public DiscountDetail findById(DiscountDetailIdentity discountDetailIdentity){
        return discountDetailRepository.getOne(discountDetailIdentity);
    }

    public List<DiscountDetail> findAll(){
        return discountDetailRepository.findAll();
    }
}
