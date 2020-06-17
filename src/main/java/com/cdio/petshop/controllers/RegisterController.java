package com.cdio.petshop.controllers;

import com.cdio.petshop.entities.Role;
import com.cdio.petshop.entities.User;
import com.cdio.petshop.repositories.UserRepository;
import com.cdio.petshop.services.RoleService;
import com.cdio.petshop.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;

@Controller
@RequestMapping("/register")
public class RegisterController {
    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleService roleService;
    @GetMapping
    public String viewRegisterPage(Model model){
        model.addAttribute("user",new User());
        return "register";
    }

    @PostMapping
    public String saveNewUser(@ModelAttribute(name = "user") User user, RedirectAttributes redirectAttributes){
//        /*
//        * mỗi lần nhận thông tin từ client thì đều làm mới session
//        * */
//        if (session.getAttribute("message") != null){
//            session.removeAttribute("message");
//        }
        if (isContainsUsername(user.getUsername())){
//            session.setAttribute("message","error"); // đăng ký thất bại
            redirectAttributes.addFlashAttribute("message","Tên đăng nhập đã tồn tại. Vui lòng sử dụng tên khác!");
            redirectAttributes.addFlashAttribute("class","alert alert-danger");
        }
        else {
            Role role = roleService.findById((long) 1); // role user-customer.
            user.setRole(role);
            user.setAddressOfDelivery(user.getPersonalAddress()); // Lấy địa chỉ nơi ở làm địa chỉ giao hàng
            user.setPassword(passwordGenerator(user.getPassword())); // mã hóa mật khẩu
            userService.save(user);
            redirectAttributes.addFlashAttribute("message","Đăng ký thành công. Nhấn vào đăng nhập để tiếp tục mua sắm!");
            redirectAttributes.addFlashAttribute("class","alert alert-success");

        }
        return "redirect:/register";
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

    // mã hóa mật khẩu.
    private String passwordGenerator(String password){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }
}
