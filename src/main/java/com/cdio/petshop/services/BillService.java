package com.cdio.petshop.services;

import com.cdio.petshop.entities.Bill;
import com.cdio.petshop.repositories.BillRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BillService {
    private final BillRepository billRepository;

    public Bill findById(Long id){
        return billRepository.getOne(id);
    }

    public List<Bill> findAll(){
        return billRepository.findAll();
    }

    public void deleteById(Long id){
        billRepository.deleteById(id);
    }

    public void save(Bill bill){
        billRepository.save(bill);
    }
}
