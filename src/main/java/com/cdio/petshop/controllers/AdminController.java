package com.cdio.petshop.controllers;

import com.cdio.petshop.entities.*;
import com.cdio.petshop.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class AdminController {
    @Autowired
    private RoleService roleService;

    @Autowired
    private UserService userService;

    @Autowired
    private BillDetailService billDetailService;
    
    @Autowired
    private SupplierService supplierService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private ProductService productService;

    @Autowired
    private BillService billService;

    @GetMapping("/accounts")
    public String viewListAccount(Model model){
        model.addAttribute("listUsers",roleService.findById((long) 1).getUsers());
        model.addAttribute("listAdmins",roleService.findById((long) 2).getUsers());
        return "Admin_AccountManager";
    }

//    // Quản Lý Loại Hàng
//    @GetMapping("/categories")
//    public String viewListCategory(Model model){
//        model.addAttribute("categories",categoryService.findAll());
//        return "Admin_CategoryManager";
//    }
//    // return page add new a category
//    @GetMapping("/category/new")
//    public String viewInsertCategoryPage(Model model){
//        model.addAttribute("category",new Category());
//        return "Admin_InsertCategory";
//    }
//
//    @GetMapping("/category/edit/{id}")
//    public String viewInsertCategoryPage(Model model,@PathVariable(name = "id") Long id){
//        model.addAttribute("category",categoryService.findById(id));
//        return "Admin_EditCategory";
//    }
//
//    @PostMapping("/category")
//    public String insertCategory(@ModelAttribute(name = "category") Category category){
//        categoryService.save(category);
//        return "redirect:/admin/categories";
//    }
//
//    @GetMapping("/category/delete/{id}")
//    public String deleteCategory(@PathVariable(name = "id") Long id){
//        categoryService.deleteById(id);
//        return "redirect:/admin/categories";
//    }



//    // Quản Lý Nhà Cung Cấp
//
//    @GetMapping("/suppliers")
//    public String viewListSupplier(Model model){
//        model.addAttribute("suppliers",supplierService.findAll());
//        return "Admin_SupplierManager";
//    }
//
//    @GetMapping("/supplier/edit/{id}")
//    public String getSupplierDetail(Model model, @PathVariable(name = "id") Long id){
//        model.addAttribute("supplier",supplierService.findById(id));
//        return "Admin_EditSupplier";
//    }
//
//    @GetMapping("/supplier/delete/{id}")
//    public String deleteSupplier(@PathVariable(name = "id") Long id){
//        supplierService.deleteById(id);
//        return "redirect:/admin/suppliers";
//    }
//
//    @PostMapping("/supplier/save")
//    public String saveSupplier(@ModelAttribute(name = "supplier") Supplier supplier){
//        supplierService.save(supplier);
//        return "redirect:/admin/suppliers";
//    }
//
//    @GetMapping("/addSupplier")
//    public String viewInsertSupplierPage(Model model){
//        model.addAttribute("supplier",new Supplier());
//        return "Admin_InsertSupplier";
//    }

    // Quản Lý Sản Phẩm


    // Hiển thị giao diện thêm sản phẩm
//    @GetMapping("/addProduct")
//    public String viewInsertProductPage(Model model){
//        model.addAttribute("product",new Product());
//        model.addAttribute("suppliers", supplierService.findAll());
//        model.addAttribute("categories", categoryService.findAll());
//        return "Admin_InsertProduct";
//    }
    //Lưu Sản Phẩm
//    @PostMapping("/saveProduct")
//    public String saveProduct(@ModelAttribute(name = "product") Product product){
//        productService.save(product);
//        return "redirect:/admin/listProduct";
//    }

    //  hiển thị giao diện chỉnh sửa sản phẩn
//    @GetMapping("/product/{id}")
//    public String viewEditProductPage(Model model,@PathVariable(name = "id") Long id){
//        model.addAttribute("product",productService.findById(id));
//        return "Admin_EditProduct";
//    }

    // Chức năng xóa sản phẩm
//    @GetMapping("/deleteProduct={id}")
//    public String deleteProduct(@PathVariable(name = "id") Long id){
//        productService.deleteById(id);
//        return "redirect:/admin/listProduct";
//    }




//    // Quản Lý Khuyến Mãi
//    @GetMapping("/discounts")
//    public String viewListDiscountPage(Model model){
//        model.addAttribute("discounts",discountService.findAll());
//        return "Admin_DiscountManager";
//    }
//
//    @PostMapping("/discount")
//    public String insertDiscount(@ModelAttribute(name = "discount") Discount discount){
//        discountService.save(discount);
//        return "redirect:/admin/discounts";
//    }
//
//    // hiển thị trang thêm mới khuyến mãi
//    @GetMapping("/discount/new")
//    public String viewInsertDiscountPage(Model model){
//
//        model.addAttribute("discount",new Discount());
//        return "Admin_InsertDiscount";
//    }
//
//    // hiển thị trang khuyến mãi
//    @GetMapping("/discount/edit/{id}")
//    public String viewEditDiscountPage(Model model, @PathVariable(name = "id") Long id){
//        model.addAttribute("discount",discountService.findById(id));
//        return "Admin_EditDiscount";
//    }
//
//    // Xóa Khuyến Mãi
//    @GetMapping("/discount/delete/{id}")
//    public String deleteDiscount(@PathVariable(name = "id") Long id){
//        discountService.deleteById(id);
//        return "redirect:/admin/discounts";
//    }
//
//    @GetMapping("/listProductDiscount/discountId={id}")
//    public String viewListDiscountDetailByDiscountIdPage(Model model,@PathVariable(name = "id") Long id){
//
//        // danh sách sản phẩm khuyến mãi theo discount ID
//        model.addAttribute("discountDetails",discountService.findById(id).getDiscountDetails());
//
//        model.addAttribute("discount",discountService.findById(id));
//        return "Admin_ListDiscountDetailByDiscountId";
//    }

    /**
     * QUẢN LÝ CHI TIẾT KHUYẾN MÃI
     *
     */

    // Hiển Thị trang thêm mới sản phẩm khuyến mãi dựa trên DiscountId
//    @GetMapping("/addDiscountDetail/discountId={id}")
//    public String viewInsertDiscountDetailByDiscountIdPage(Model model, @PathVariable(name = "id") Long id){
//        DiscountDetailDTO discountDetailDTO = new DiscountDetailDTO();
//        discountDetailDTO.setDiscount(discountService.findById(id));
//
//        model.addAttribute("discountDetailDTO",discountDetailDTO);
//        model.addAttribute("products",productService.findAll());

//        model.addAttribute("product",new Product()); // để lấy ra được product ID sang @PostMapping xử lý
//        model.addAttribute("products",productService.findAll());
//        model.addAttribute("discountDetail", new DiscountDetail()); // để lấy ra được rateDiscount sang cho @PostMapping save để xử lý
//        return "Admin_InsertDiscountDetailByDiscountId";
//    }

    // hiển thị trang nhập thông tin khuyến mãi chi tiết
//    @GetMapping("/addDiscountDetail")
//    public String viewInsertDiscountDetailPage(Model model){
//        model.addAttribute("discountDetailDTO",new DiscountDetailDTO());
//        model.addAttribute("products",productService.findAll());
//        model.addAttribute("discounts",discountService.findAll());
//        return "InsertDiscountDetailByDiscountId";
//    }


    // hiển thị danh sách khuyến mãi chi tiết
//    @GetMapping("/listDiscountDetail/discountId={id}")
//    public String viewListDiscountDetailPage(Model model){
//        model.addAttribute("discountDetails",discountDetailService.findAll());
//        return "Admin_ListDiscountDetailByDiscountId";
//    }


    //lưu chi tiết khuyến mãi
//    @PostMapping("/saveDiscountDetail/discountId={id}")
//    public String saveDiscountDetail(@PathVariable(name = "id") Long id,@ModelAttribute(name = "discountDetailDTO") DiscountDetailDTO discountDetailDTO) {
//        DiscountDetail discountDetail = new DiscountDetail();
//
//        //Lấy giá trị cho khóa composite.
//        DiscountDetailIdentity discountDetailIdentity = new DiscountDetailIdentity();
//        discountDetailIdentity.setProductId(discountDetailDTO.getProduct().getId());
//        discountDetailIdentity.setDiscountId(id);
//
//        // set gía trị cho khóa chính
//        discountDetail.setDiscountDetailIdentity(discountDetailIdentity);
//
//        // set rateDiscount cho  discountDetail.
//        discountDetail.setRateDiscount(discountDetailDTO.getRateDiscount());
//
//        discountDetail.setDiscount(discountDetailDTO.getDiscount());
//        discountDetail.setProduct(discountDetailDTO.getProduct());
//        discountDetailService.save(discountDetail);
//        return "Admin_DiscountManager";
//    }

        // Tạo đối tượng DiscountDetail và set data
//        DiscountDetail discountDetail = new DiscountDetail();
//        discountDetail.setDiscountDetailIdentity(new DiscountDetailIdentity(discountDetailDTO.getDiscount().getId(),discountDetailDTO.getProduct().getId()));
//        discountDetail.setRateDiscount(discountDetailDTO.getRateDiscount());
//        discountDetail.setDiscount(discountDetailDTO.getDiscount());
//        discountDetail.setProduct(discountDetailDTO.getProduct());

//        System.out.println(discountDetail.getDiscountDetailIdentity().getDiscountId());
//        System.out.println(discountDetail.getDiscountDetailIdentity().getProductId());
//        System.out.println(discountDetail.getRateDiscount());

        // error
//        discountDetailService.save(discountDetail);
//        return "redirect:/admin/listDiscountDetail";









    // Quản lý đơn hàng

    // hiển thị danh sách đơn hàng
    @GetMapping("/listBill")
    public String viewBillManagerPage(Model model){
        model.addAttribute("bills",billService.findAll());
        return "Admin_BillManager";
    }

    // Xóa đơn hàng
    @GetMapping("/bill?id={id}")
    public String deleteBillById(@PathVariable(name = "id") Long id){
        billService.deleteById(id);
        return "redirect:/admin/listBill";
    }

    /*
    * CHI TIẾT ĐƠN HÀNG
    *
    */
    @GetMapping("/listBillDetail/billId={id}")
    public String viewListBillDetailByBillId(@PathVariable(name = "id") Long id, Model model){
        Bill bill = billService.findById(id);
        model.addAttribute("bill",bill);
        model.addAttribute("listBillDetail",bill.getBillDetails());
        return "Admin_ListBillDetailByBillId";
    }

}
