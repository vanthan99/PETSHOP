package com.cdio.petshop.services;

import com.cdio.petshop.entities.Role;
import com.cdio.petshop.entities.User;
import com.cdio.petshop.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User findById(String username){
        return userRepository.getOne(username);
    }

    public List<User> findAll(){
        return userRepository.findAll(Sort.by("role"));
    }

    public void deleteById(String username){
        userRepository.deleteById(username);
    }

    public void save(User user){
        userRepository.save(user);
    }
}
