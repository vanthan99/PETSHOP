package com.cdio.petshop.controllers;

import com.cdio.petshop.entities.Bill;
import com.cdio.petshop.model.Item;
import com.cdio.petshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ProductService productService;

    @GetMapping("/index")
    public String showCartPage(Model model, HttpSession session){
        if (session.getAttribute("cart") == null){
            List<Item> cart = new ArrayList<>();
            session.setAttribute("cart",cart);
        }
        else{
            model.addAttribute("total",getTotalMoney(session));
        }
        return "App_Cart";
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

    @GetMapping("/buy/productId={id}")
    public String buy(@PathVariable(name = "id") Long id, HttpSession session){
        if (session.getAttribute("cart") == null){
            List<Item> cart = new ArrayList<Item>();
            cart.add(new Item(productService.findById(id),1));
            session.setAttribute("cart",cart);
        }
        else {
            List<Item> cart = (List<Item>) session.getAttribute("cart");
            int index = this.exists(id, cart);
            if (index == -1){
                cart.add(new Item(productService.findById(id),1));
            }
            else {
                int quantity =cart.get(index).getQuantity() + 1;
                cart.get(index).setQuantity(Math.min(quantity, 20));
            }
            session.setAttribute("cart",cart);

        }
        return "redirect:/cart/index";
    }

    @GetMapping("/edit/productId={id}")
    public String editCart(Model model, @PathVariable(name = "id") Long id,HttpSession session){
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        for (Item item : cart){
            if (item.getProduct().getId().equals(id)){
                model.addAttribute("item",item);
            }
        }
        return "App_EditCart";
    }

    @PostMapping("/edit")
    public String updateQuantityCart(@ModelAttribute(name = "item") Item item, HttpSession session,RedirectAttributes redirectAttributes){
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        int index = exists(item.getProduct().getId(),cart);
        cart.get(index).setQuantity(item.getQuantity());
        session.setAttribute("cart",cart);
        redirectAttributes.addFlashAttribute("message","Cập nhập giỏ hàng thành công");
        return "redirect:/cart/index";
    }


    @PostMapping
    public String post(@ModelAttribute("item") Item item, HttpSession session){
        if (session.getAttribute("cart") == null){
            List<Item> cart = new ArrayList<>();
            cart.add(item);
            session.setAttribute("cart",cart);
        }
        else {
            List<Item> cart = (List<Item>) session.getAttribute("cart");
            int index = this.exists(item.getProduct().getId(),cart);
            if (index == -1){
                cart.add(item);
            }
            else {
                int quantity = cart.get(index).getQuantity() + item.getQuantity();
                if (quantity <= 20){
                    cart.get(index).setQuantity(quantity);
                }
                else {
                    cart.get(index).setQuantity(20);
                }
            }

            session.setAttribute("cart",cart);
        }
        return "redirect:/cart/index";
    }

    @GetMapping(value = "remove/{id}")
    public String remove(@PathVariable("id") Long id, HttpSession session) {
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        int index = this.exists(id, cart);
        cart.remove(index);
        session.setAttribute("cart", cart);
        return "redirect:/cart/index";
    }

    @GetMapping(value = "removeAll")
    public String removeAll(HttpSession session, RedirectAttributes redirectAttributes){
        List<Item> cart = new ArrayList<>();
        session.setAttribute("cart",cart);
        redirectAttributes.addFlashAttribute("message","Xóa giỏ hàng thành công!");
        return "redirect:/cart/index";
    }

//    kiểm tra xem trong giỏ hàng có sản phẩm có mã id hay chưa
    private int exists(Long id, List<Item> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getProduct().getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}
