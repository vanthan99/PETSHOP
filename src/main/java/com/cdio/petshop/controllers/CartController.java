package com.cdio.petshop.controllers;

import com.cdio.petshop.model.Item;
import com.cdio.petshop.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private ProductService productService;

    @GetMapping("/index")
    public String showCartPage(){
        return "Cart";
    }

    @GetMapping("/buy/productId={id}")
    public String buy(@PathVariable(name = "id") Long id, HttpSession session){
        if (session.getAttribute("cart") == null){
            List<Item> cart = new ArrayList<>();
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

    @GetMapping(value = "remove/{id}")
    public String remove(@PathVariable("id") Long id, HttpSession session) {
        List<Item> cart = (List<Item>) session.getAttribute("cart");
        int index = this.exists(id, cart);
        cart.remove(index);
        session.setAttribute("cart", cart);
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
