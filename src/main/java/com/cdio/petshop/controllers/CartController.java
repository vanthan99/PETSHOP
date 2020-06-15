package com.cdio.petshop.controllers;

import com.cdio.petshop.model.Item;
import com.cdio.petshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

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
            // tính tổng tiền trong giỏ hàng
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
            model.addAttribute("total",total);
        }
        return "App_Cart";
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
                cart.get(index).setQuantity(quantity);
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

    @PostMapping
    public String post(@ModelAttribute("item") Item item, HttpSession session){
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        cart.add(item);
        session.setAttribute("cart", cart);
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
    public String removeAll(HttpSession session){
        List<Item> cart = new ArrayList<>();
        session.setAttribute("cart",cart);
        return "redirect:/cart/index";
    }

    private int exists(Long id, List<Item> cart) {
        for (int i = 0; i < cart.size(); i++) {
            if (cart.get(i).getProduct().getId().equals(id)) {
                return i;
            }
        }
        return -1;
    }
}
