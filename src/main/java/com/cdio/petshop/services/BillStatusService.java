package com.cdio.petshop.services;

import com.cdio.petshop.entities.BillStatus;
import com.cdio.petshop.entities.Category;
import com.cdio.petshop.repositories.BillStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BillStatusService {
    @Autowired
    private BillStatusRepository billStatusRepository;

    public BillStatus findById(Long id){
        return billStatusRepository.getOne(id);
    }

    public List<BillStatus> findAll(){
        return billStatusRepository.findAll();
    }
}
