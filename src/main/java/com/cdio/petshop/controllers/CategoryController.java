package com.cdio.petshop.controllers;

import com.cdio.petshop.entities.Category;
import com.cdio.petshop.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/admin/category")
public class CategoryController {
    @Autowired
    private CategoryService categoryService;

    @GetMapping("/index")
    public String viewListCategory(Model model){
        model.addAttribute("categories",categoryService.findAll());
        return "Admin_CategoryManager";
    }

    @GetMapping("/new")
    public String viewInsertCategoryPage(Model model){
        model.addAttribute("category",new Category());
        return "Admin_InsertCategory";
    }

    @GetMapping("/edit/catId={id}")
    public String viewInsertCategoryPage(Model model,@PathVariable(name = "id") Long id){
        model.addAttribute("category",categoryService.findById(id));
        return "Admin_EditCategory";
    }

    @PostMapping
    public String insertCategory(@ModelAttribute(name = "category") Category category, RedirectAttributes redirectAttributes){
        categoryService.save(category);
        redirectAttributes.addFlashAttribute("message","Lưu loại hàng thành công");
        return "redirect:/admin/category/index";
    }

//    @GetMapping("/delete/catId={id}")
//    public String deleteCategory(@PathVariable(name = "id") Long id,RedirectAttributes redirectAttributes){
//        categoryService.deleteById(id);
//        redirectAttributes.addFlashAttribute("message","Xóa loại hàng thành công");
//        return "redirect:/admin/category/index";
//    }

    // show danh sách sản phẩm theo từng loại category
    @GetMapping("/product/catId={id}")
    public String listProductByCatId(Model model, @PathVariable(name = "id") Long id){
        model.addAttribute("products",categoryService.findById(id).getProducts());
        return "Admin_ProductManager";
    }
}



