package com.cdio.petshop.services;

import com.cdio.petshop.entities.Supplier;
import com.cdio.petshop.repositories.SupplierRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class SupplierService {
    private final SupplierRepository supplierRepository;

    public Supplier findById(Long id){
        return supplierRepository.getOne(id);
    }

    public List<Supplier> findAll(){
        return supplierRepository.findAll();
    }

    public void deleteById(Long id){
        supplierRepository.deleteById(id);
    }

    public void save(Supplier supplier){
        supplierRepository.save(supplier);
    }
}
