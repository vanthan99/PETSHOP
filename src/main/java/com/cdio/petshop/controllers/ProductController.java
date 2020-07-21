package com.cdio.petshop.controllers;

import com.cdio.petshop.entities.Product;
import com.cdio.petshop.services.CategoryService;
import com.cdio.petshop.services.ProductService;
import com.cdio.petshop.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/admin/product")
public class ProductController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private SupplierService supplierService;

    // Quản Lý Sản Phẩm
    // Hiển thị giao diện danh sách sản phẩm
    @GetMapping("/index")
    public String viewListProduct(Model model, @Param("keyword") String keyword){
//
//        Page<Product> page = productService.findByProductName(pageNum,keyword);
//        List<Product> products = page.getContent();
//        model.addAttribute("products",products);
//        model.addAttribute("currentPage", pageNum);
//        model.addAttribute("totalPages", page.getTotalPages());
//        model.addAttribute("totalItems", page.getTotalElements());
        model.addAttribute("products",productService.findByProductName(keyword));
        model.addAttribute("producrsIsActive",productService.finAllProductIsActive());
        model.addAttribute("producrsNotActive",productService.finAllProductNotActive());
        return "Admin_ProductManager";
    }

    // Hiển thị giao diện thêm sản phẩm
    @GetMapping("/new")
    public String viewInsertProductPage(Model model){
        model.addAttribute("product",new Product());
        model.addAttribute("suppliers", supplierService.findAll());
        model.addAttribute("categories", categoryService.findAll());
        return "Admin_InsertProduct";
    }

    //Lưu Sản Phẩm
    @PostMapping
    public String saveProduct(@ModelAttribute(name = "product") Product product, RedirectAttributes redirectAttributes){
        product.setEnable(2);
        productService.save(product);
        redirectAttributes.addFlashAttribute("message","Thông Báo: Lưu sản phẩm thành công");
        return "redirect:/admin/product/index";
    }

    //  hiển thị giao diện chỉnh sửa sản phẩn
    @GetMapping("/edit/productId={id}")
    public String viewEditProductPage(Model model,@PathVariable(name = "id") Long id){
        model.addAttribute("product",productService.findById(id));
        model.addAttribute("suppliers",supplierService.findAll());
        model.addAttribute("categories",categoryService.findAll());
        return "Admin_EditProduct";
    }

    // Chức năng xóa sản phẩm
    @GetMapping("/hidden/productId={id}")
    public String deleteProduct(@PathVariable(name = "id") Long id,RedirectAttributes redirectAttributes){
        // Kiểm tra. Thay đôi trạng thái hoạt động.
        Product product = productService.findById(id);
        if (productService.findById(id).getEnable()==2){
            product.setEnable(1);
            productService.save(product);
            redirectAttributes.addFlashAttribute("message","Thông Báo: Ẩn sản phẩm thành công!");
        }
        else {
            product.setEnable(2);
            productService.save(product);
            redirectAttributes.addFlashAttribute("message","Thông Báo: Mở khóa sản phẩm thành công!");
        }
        return "redirect:/admin/product/index";
    }
}
