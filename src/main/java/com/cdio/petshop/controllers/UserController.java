package com.cdio.petshop.controllers;

import com.cdio.petshop.entities.User;
import com.cdio.petshop.repositories.UserRepository;
import com.cdio.petshop.services.RoleService;
import com.cdio.petshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/user")
public class UserController {
    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    // dùng để kiểm tra trong hệ thống có tồn tại username bất kỳ hay chưa?
    @Autowired
    private UserRepository userRepository;

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

    @PostMapping
    public String save(@ModelAttribute(name = "user") User user, Model model, RedirectAttributes redirectAttributes){
        // kiểm tra username đó có tồn tại trong hệ thống hay chưa
            user.setPassword(passwordGenerator(user.getPassword()));
            user.setEnable(2);
            userService.save(user);
            redirectAttributes.addFlashAttribute("message","Thêm người dùng thành công!");
            redirectAttributes.addFlashAttribute("class","alert alert-success");
            return "redirect:/admin/user/index";
    }

    // unlock account
    @GetMapping("/unlock/username={username}")
    public String unlockAccount(@PathVariable(name = "username") String username, RedirectAttributes redirectAttributes){
        User user = userService.findById(username);
        user.setEnable(2);
        userService.save(user);
        redirectAttributes.addFlashAttribute("message","Mở khóa tài khoản: " + username +"thành công");
        redirectAttributes.addFlashAttribute("class","alert alert-success");
        return "redirect:/admin/user/index";
    }

    // lock account
    @GetMapping("/lock/username={username}")
    public String lockAccount(@PathVariable(name = "username") String username, RedirectAttributes redirectAttributes){
        User user = userService.findById(username);
        user.setEnable(1);
        userService.save(user);
        redirectAttributes.addFlashAttribute("message","Khóa tài khoản: " + username +"thành công");
        redirectAttributes.addFlashAttribute("class","alert alert-success");
        return "redirect:/admin/user/index";
    }

    @GetMapping("/view/username={username}")
    public String viewInfoUserDetail(@PathVariable(name = "username") String username, Model model){
        model.addAttribute("user",userService.findById(username));
        return "Admin_UserInfo";
    }

    // mã hóa mật khẩu.
    private String passwordGenerator(String password){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }

    /*kiểm tra xem username có tồn tại trong hệ thống hay chưa?
    * nếu đã tồn tại username trong hệ thống rồi thì trả về true
    * ngược lại nếu không tồn tại username trong hệ thống thì trả về false.
    * */
    private boolean isContainsUsername(String username){
        // chưa tồn tại
        if (userRepository.getUserByUsername(username) == null){
            return false;
        }
        return true;
    }


}
