package com.cdio.petshop.services;

import com.cdio.petshop.entities.User;
import com.cdio.petshop.model.MyUserDetail;
import com.cdio.petshop.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.getUserByUsername(username);
        if (user == null){
            throw new UsernameNotFoundException("could not find user");
        }
        return new MyUserDetail(user);
    }
}
