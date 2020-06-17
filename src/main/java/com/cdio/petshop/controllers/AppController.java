package com.cdio.petshop.controllers;

import com.cdio.petshop.entities.Category;
import com.cdio.petshop.entities.Product;
import com.cdio.petshop.model.Item;
import com.cdio.petshop.services.CategoryService;
import com.cdio.petshop.services.ProductService;
import com.cdio.petshop.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
public class AppController {
    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private SupplierService supplierService;

    @GetMapping("/")
    public String demo(Model model, HttpSession session){
        //  ngay lúc người dùng vào trang web. khởi tạo giỏ hàng có tên cart ngay lập tức;
        if (session.getAttribute("cart") == null){
            List<Item> cart = new ArrayList<>();
            session.setAttribute("cart",cart);
        }

        model.addAttribute("products", productService.findAll());
        model.addAttribute("categories",categoryService.findAll());
        return "App_index";
    }

    // hiển thị thông tin chi tiết sản phẩm
    @GetMapping("/product/id={id}")
    public String viewProductDetailByIdPage(Model model, @PathVariable(name = "id") Long id){
        model.addAttribute("product", productService.findById(id));

        // danh sách sản phẩm tương tự
        Product product = productService.findById(id);
        Category category = product.getCategory();
        model.addAttribute("products", category.getProducts());
        return "App_ProductDetailById";
    }

    // hiển thị danh sách sản phẩm theo category ID
    @GetMapping("/products/catId={id}")
    public String viewListProductByCategoryIdPage(Model model,@PathVariable(name = "id") Long id){
        model.addAttribute("title", categoryService.findById(id).getName());
        model.addAttribute("products", categoryService.findById(id).getProducts());
//        model.addAttribute("category",categoryService.findById(id));
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
        model.addAttribute("products",supplierService.findById(id).getProducts());
        return "App_ListProductByCategoryOrSupplier";
    }

    // hiển thị danh sách khuyến mãi
    @GetMapping("/discounts")
    public String viewListDiscountProduct(Model model){
//        tìm kiếm những sản phẩm có khuyến mãi rồi đưa vào danh sách products
        ArrayList<Product> products = new ArrayList<>();
        for (Product product : productService.findAll()){
            if (product.getPromotionPrice() != null){
                products.add(product);
            }
        }
        model.addAttribute("products",products);
        model.addAttribute("title","Danh Sách sản phẩm hiện đang được khuyến mãi");
        return "App_ListProductByCategoryOrSupplier";
    }
}
