package com.cdio.petshop.controllers;

import com.cdio.petshop.entities.Supplier;
import com.cdio.petshop.services.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping(value = "/admin/supplier")
public class SupplierController {
    @Autowired
    private SupplierService supplierService;
    // Quản Lý Nhà Cung Cấp
    @GetMapping("/index")
    public String viewListSupplier(Model model){
        model.addAttribute("suppliers",supplierService.findAll());
        return "Admin_SupplierManager";
    }

    @GetMapping("/edit/supId={id}")
    public String getSupplierDetail(Model model, @PathVariable(name = "id") Long id){
        model.addAttribute("supplier",supplierService.findById(id));
        return "Admin_EditSupplier";
    }

//    @GetMapping("/delete/supId={id}")
//    public String deleteSupplier(@PathVariable(name = "id") Long id){
//        supplierService.deleteById(id);
//        return "redirect:/admin/supplier/index";
//    }

    @PostMapping
    public String saveSupplier(@ModelAttribute(name = "supplier") Supplier supplier, RedirectAttributes redirectAttributes){
        supplierService.save(supplier);
        redirectAttributes.addFlashAttribute("message","Thông Báo: Lưu thành công!");
        return "redirect:/admin/supplier/index";
    }

    @GetMapping("/new")
    public String viewInsertSupplierPage(Model model){
        model.addAttribute("supplier",new Supplier());
        return "Admin_InsertSupplier";
    }

    // show danh sách sản phẩm theo từng loại category
    @GetMapping("/product/supId={id}")
    public String listProductByCatId(Model model, @PathVariable(name = "id") Long id){
        model.addAttribute("products",supplierService.findById(id).getProducts());
        return "Admin_ProductManager";
    }
}
