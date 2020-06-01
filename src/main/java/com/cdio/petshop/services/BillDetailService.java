package com.cdio.petshop.services;

import com.cdio.petshop.entities.BillDetail;
import com.cdio.petshop.entities.BillDetailIdentity;
import com.cdio.petshop.repositories.BillDetailRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BillDetailService {
    @Autowired
    private BillDetailRepository billDetailRepository;

    public List<BillDetail> findAll(){
        return billDetailRepository.findAll();
    }

    public BillDetail findById(BillDetailIdentity id){
        return billDetailRepository.getOne(id);
    }

    public void deleteById(BillDetailIdentity id){
        billDetailRepository.deleteById(id);
    }

    public void save(BillDetail billDetail){
        billDetailRepository.save(billDetail);
    }
}
