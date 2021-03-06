package com.cdio.petshop.controllers;

import com.cdio.petshop.entities.Category;
import com.cdio.petshop.entities.Product;
import com.cdio.petshop.entities.User;
import com.cdio.petshop.model.Item;
import com.cdio.petshop.model.UserTemp;
import com.cdio.petshop.repositories.ProductRepository;
import com.cdio.petshop.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

@Controller
public class AppController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;
    @GetMapping("/")
    public String demo(Model model, HttpSession session){
        //  ngay lúc người dùng vào trang web. khởi tạo giỏ hàng có tên cart ngay lập tức;
        if (session.getAttribute("cart") == null){
            List<Item> cart = new ArrayList<>();
            session.setAttribute("cart",cart);
        }
        //danh sách sản phẩm đang kinh doanh tại shop
        model.addAttribute("products",productService.finAllProductIsActive());
        model.addAttribute("categories",categoryService.findAll());
        return "App_index";
    }

    // hiển thị thông tin chi tiết sản phẩm
    @GetMapping("/product/id={id}")
    public String viewProductDetailByIdPage(Model model, @PathVariable(name = "id") Long id){
        model.addAttribute("product", productService.findById(id));

        Product product = productService.findById(id);
        Item item = new Item();
        item.setProduct(product);
        item.setQuantity(1);
        model.addAttribute("item",item);

        Category category = product.getCategory();
        model.addAttribute("products", category.getProducts());
        return "App_ProductDetailById";
    }

    // hiển thị danh sách sản phẩm theo category ID
    @GetMapping("/products/catId={id}")
    public String viewListProductByCategoryIdPage(Model model,@PathVariable(name = "id") Long id){
        model.addAttribute("title", categoryService.findById(id).getName());

        // lấy ra danh sách sản phẩm đang hoạt động thuộc mỗi loại hàng cụ thể tùy thuộc vào category id.
        List<Product> products = new ArrayList<>();
        for (Product product: categoryService.findById(id).getProducts()){
            if (product.getEnable() == 2){
                products.add(product);
            }
        }
        model.addAttribute("products",products);

        return "App_ListProductByCategoryOrSupplier";
    }

    // hiển thi thông tin chi tiết của nhà cung cấp
    @GetMapping("/supplier/id={id}")
    public String viewSupplierDetailByPage(Model model, @PathVariable(name = "id") Long id){
        model.addAttribute("supplier",supplierService.findById(id));
        return "App_SupplierDetail";
    }

    // hiển thị danh sách sản phẩm theo nhà cung cấp
    @GetMapping("/products/supId={id}")
    public String viewListProductBySupplierPage(Model model,@PathVariable(name = "id") Long id){
        model.addAttribute("title","Danh Sách Sản Phẩm Thuộc Danh Mục " + supplierService.findById(id).getName());
        // lấy ra danh sách sản phẩm đang hoạt động thuộc mỗi loại hàng cụ thể tùy thuộc vào category id.
        List<Product> products = new ArrayList<>();
        for (Product product: supplierService.findById(id).getProducts()){
            if (product.getEnable() == 2){
                products.add(product);
            }
        }
        model.addAttribute("products",products);
        return "App_ListProductByCategoryOrSupplier";
    }

    // hiển thị danh sách khuyến mãi
    @GetMapping("/discounts")
    public String viewListDiscountProduct(Model model){
//        tìm kiếm những sản phẩm có khuyến mãi rồi đưa vào danh sách products
        ArrayList<Product> products = new ArrayList<>();
        for (Product product : productService.finAllProductIsActive()){
            if (product.getPromotionPrice() != null && product.getEnable()==2){
                products.add(product);
            }
        }
        model.addAttribute("products",products);
        model.addAttribute("title","Danh Sách sản phẩm hiện đang được khuyến mãi");
        return "App_ListProductByCategoryOrSupplier";
    }

    // Kiểm tra đơn hàng
    @GetMapping("/bill")
    public String showbill(Model model){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User user = userService.findById(authentication.getName());
        model.addAttribute("bills",user.getBills());
        return "App_Bill";
    }

    // thông tin user
    @GetMapping("/user/info")
    public String viewUserInfoPage(Model model,@Param("action") String action){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        model.addAttribute("user",userService.findById(authentication.getName()));
        model.addAttribute("action",action);
        UserTemp temp = new UserTemp();
        model.addAttribute("userTemp",temp);
        return "App_UserInfo";
    }

    // Thay đổi thông tin user
    @PostMapping("/user/info")
    public String editInfoUser(@ModelAttribute("user") User user,@ModelAttribute("userTemp") UserTemp userTemp,RedirectAttributes redirectAttributes,@Param("action") String action){
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        User userAuthen = userService.findById(authentication.getName());
        // Trường hợp thay đổi tên, số điện thoại và địa chỉ người dùng
        if (action.equalsIgnoreCase("edit")){
            if (BCrypt.checkpw(user.getPassword(), userAuthen.getPassword())){
                // đồng ý thay đổi
                /*
                 * chỉ thay đổi tên, số điện thoại và địa chỉ của người dùng
                 */
                User userFinal = userService.findById(user.getUsername());
                userFinal.setAddress(user.getAddress());
                userFinal.setPhoneNumber(user.getPhoneNumber());
                userFinal.setFullName(user.getFullName());
                userService.save(userFinal);
                redirectAttributes.addFlashAttribute("message","Cập nhật thông tin thành công");
                redirectAttributes.addFlashAttribute("class","alert alert-success");
                return "redirect:/user/info?action=view";
            }
            else {
                //thông báo sai mật khẩu xác nhận
                redirectAttributes.addFlashAttribute("message","Mật khẩu không chính xác, vui lòng thực hiện lại");
                redirectAttributes.addFlashAttribute("class","alert alert-danger");
                return "redirect:/user/info?action=edit";
            }
        }
        else {
            // trường hợp thay đổi mật khẩu.
            if (BCrypt.checkpw(userTemp.getPresentPassword(),userAuthen.getPassword())){
                userService.changePassword(userAuthen.getUsername(),passwordGenerator(userTemp.getNewPasswordConfirm()));
                redirectAttributes.addFlashAttribute("message","Cập nhật mật khẩu thành công");
                redirectAttributes.addFlashAttribute("class","alert alert-success");
                return "redirect:/user/info?action=view";
            }
            else {
                //thông báo sai mật khẩu hiện tại.
                redirectAttributes.addFlashAttribute("message","Mật khẩu hiện tại không chính xác, vui lòng thực hiện lại");
                redirectAttributes.addFlashAttribute("class","alert alert-danger");
                return "redirect:/user/info?action=changePassword";
            }
        }

    }

    // xử lý tìm kiếm
    @GetMapping("/search")
    public String toSearch(@Param("keyword") String keyword,Model model){
        keyword = keyword.replace("+"," ");
        List<Product> products = productService.findAllByKeyword(keyword);
        model.addAttribute("products",products);
        model.addAttribute("title","Tìm kiếm theo từ khóa : '"+keyword+"'");
        return "App_ListProductByCategoryOrSupplier";
    }

    private String passwordGenerator(String password){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        return bCryptPasswordEncoder.encode(password);
    }
}
