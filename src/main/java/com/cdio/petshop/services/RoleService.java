package com.cdio.petshop.services;

import com.cdio.petshop.entities.Role;
import com.cdio.petshop.repositories.RoleRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Role findById(Long id){
        return roleRepository.getOne(id);
    }

    public List<Role> findAll(){
        return roleRepository.findAll();
    }

    public void deleteById(Long id){
        roleRepository.deleteById(id);
    }

    public void save(Role role){
        roleRepository.save(role);
    }
}
