package com.cdio.petshop.controllers;

import com.cdio.petshop.entities.Discount;
import com.cdio.petshop.entities.DiscountDetail;
import com.cdio.petshop.entities.Product;
import com.cdio.petshop.model.DiscountDetailDTO;
import com.cdio.petshop.services.DiscountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/admin/discount")
public class DiscountController {
    @Autowired
    private DiscountService discountService;

    // Quản Lý Khuyến Mãi
    @GetMapping("/index")
    public String viewListDiscountPage(Model model){
        model.addAttribute("discounts",discountService.findAll());
        return "Admin_DiscountManager";
    }

    @PostMapping
    public String insertDiscount(@ModelAttribute(name = "discount") Discount discount){
        discountService.save(discount);
        return "redirect:/admin/discount/index";
    }

    // hiển thị trang thêm mới khuyến mãi
    @GetMapping("/new")
    public String viewInsertDiscountPage(Model model){
        model.addAttribute("discount",new Discount());
        return "Admin_InsertDiscount";
    }

    // hiển thị trang khuyến mãi
    @GetMapping("/edit/disId={id}")
    public String viewEditDiscountPage(Model model, @PathVariable(name = "id") Long id){
        model.addAttribute("discount",discountService.findById(id));
        return "Admin_EditDiscount";
    }

    // Xóa Khuyến Mãi
    @GetMapping("/delete/disId={id}")
    public String deleteDiscount(@PathVariable(name = "id") Long id){
        discountService.deleteById(id);
        return "redirect:/admin/discount/index";
    }

    // Danh sách sản phẩn theo từng khuyến mãi
    @GetMapping("/product/disId={id}")
    public String listProduct(Model model,@PathVariable(name = "id") Long id){
        model.addAttribute("discountDetails",discountService.findById(id).getDiscountDetails());
        return "Admin_ListProductByDiscountId";
    }
}
