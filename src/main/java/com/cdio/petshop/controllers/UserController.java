package com.cdio.petshop.controllers;

import com.cdio.petshop.entities.User;
import com.cdio.petshop.services.RoleService;
import com.cdio.petshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    @GetMapping("/index")
    public String index(Model model){
        model.addAttribute("users",userService.findAll());
        return "Admin_AccountManager";
    }

    @GetMapping("/new")
    public String insertUser(Model model){
        model.addAttribute("user",new User());
        model.addAttribute("roles",roleService.findAll());
        return "Admin_InsertUser";
    }

    // xem chi tiết thông tin người dùng
    @GetMapping("/view/username={username}")
    public String view(Model model, @PathVariable(name = "username") String username){
        model.addAttribute("user",userService.findById(username));
        return "Admin_ViewUserDetail";
    }


    @PostMapping
    public String save(@ModelAttribute(name = "user") User user){
        user.setPassword(passwordGenerator(user.getPassword()));
        userService.save(user);
        return "redirect:/admin/user/index";
    }

    private String passwordGenerator(String password){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }
}
