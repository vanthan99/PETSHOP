package com.cdio.petshop.controllers;

import com.cdio.petshop.entities.*;
import com.cdio.petshop.model.Item;
import com.cdio.petshop.services.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

@Controller
@RequestMapping("/checkout")
public class CheckoutController {
    @Autowired
    private BillService billService;
    @Autowired
    private BillDetailService billDetailService;
    @Autowired
    private BillStatusService billStatusService;
    @Autowired
    private UserService userService;

    @PostMapping
    public String checkout(HttpSession session, @ModelAttribute(name = "bill") Bill bill, RedirectAttributes redirectAttributes){
        List<Item> cart = (List<Item>) session.getAttribute("cart");

        // lấu user đang đăng nhập vào hệ thống
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findById(auth.getName());
        bill.setUser(user);
        bill.setBillStatus(billStatusService.findById((long) 1));
        bill.setTotal(getTotalMoney(session));
        billService.save(bill);


        BillDetailIdentity identity = new BillDetailIdentity();
        for (Item item : cart){
            // Thiết lập khóa chính cho bill detail
            identity.setBillId(bill.getId());
            identity.setProductId(item.getProduct().getId());

            BillDetail billDetail = new BillDetail();
            billDetail.setBillDetailIdentity(identity);
            billDetail.setQuantity(item.getQuantity());
            billDetail.setProduct(item.getProduct());
            billDetail.setBill(bill);

            // Trường hợp sản phẩm không có khuyến mãi
            if (item.getProduct().getPromotionPrice() == null){
                billDetail.setPrice(item.getProduct().getUnitPrice());
            }
            // Trường hơp sản phẩm có khuyến mãi
            else {
                billDetail.setPrice(item.getProduct().getPromotionPrice());
            }
            billDetailService.save(billDetail);
        }

        // clean session khi đã checkout thành công.
        ((List<Item>) session.getAttribute("cart")).clear();
        redirectAttributes.addFlashAttribute("message","Đặt hàng thành công");
        return "redirect:/bill";
    }

    @GetMapping
    public String viewCheckout(HttpSession session,Model model){
        // lấu user đang đăng nhập vào hệ thống
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = userService.findById(auth.getName());
        model.addAttribute("user",user);
        model.addAttribute("bill", new Bill());
        model.addAttribute("total",getTotalMoney(session));
        return "App_Checkout";
    }

    private int getTotalMoney(HttpSession session) {
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        int total = 0;
        for (Item item : cart){
            // trường hợp nếu sản phẩm không có khuyến mãi
            if (item.getProduct().getPromotionPrice() == null){
                total += item.getProduct().getUnitPrice() * item.getQuantity();
            }
            // Trường hợp sản phẩm có khuyến mãi
            else {
                total += item.getProduct().getPromotionPrice() * item.getQuantity();
            }
        }
        return total;
    }
}
