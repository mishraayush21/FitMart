package com.fitmart.proj.controller;

import com.fitmart.proj.global.GlobalData;
import com.fitmart.proj.model.Product;
import com.fitmart.proj.service.KafkaService;
import com.fitmart.proj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

@Controller
public class CartController {
    @Autowired
    ProductService productService;
    @Autowired
    KafkaService kafkaService;
    @GetMapping("/addToCart/{id}")
    public String addToCart(@PathVariable Long id) {
        GlobalData.cart.add(productService.getProductById(id).get());
        HashMap<String, String> cartItem = new HashMap<>();
        cartItem.put("itemId", String.valueOf(id));
//        cartItem.put("itemName", productService.getProductById(id).get().getName());
        this.kafkaService.itemAddedToCart(cartItem);
        return "redirect:/shop";
    }

    @GetMapping("/cart")
    public String getCart(Model model) {
        model.addAttribute("cartCount",GlobalData.cart.size());
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart",GlobalData.cart);
        return "cart";
    }

    @GetMapping("/cart/removeItem/{index}")
    public String cartItemRemove(@PathVariable int index) {
        GlobalData.cart.remove(index);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkout(Model model) {
        model.addAttribute("total", GlobalData.cart.stream().mapToDouble(Product::getPrice).sum());
        model.addAttribute("cart",GlobalData.cart);
        return "checkout";
    }

    @PostMapping("/payNow")
    public String payNow(Model model) {
        model.addAttribute("cart",GlobalData.cart);
        boolean isPaymentSuccessful = (Math.random() <= 0.8) ;
        HashMap<String, String> paidStatus = new HashMap<>();
        if(isPaymentSuccessful) {
            List<HashMap<String, String>> cartProductsList = new ArrayList<>();
            for(Product productList : GlobalData.cart ) {
                HashMap<String, String> cartProducts = new HashMap<>();
                cartProducts.put("itemId", String.valueOf(productList.getId()));
                cartProductsList.add(cartProducts);
            }
            this.kafkaService.itemsSold(cartProductsList);
            paidStatus.put("status","success");
            this.kafkaService.paymentStatus(paidStatus);
            return "orderSuccess";
        }
        else {
            paidStatus.put("status","failed");
            this.kafkaService.paymentStatus(paidStatus);
            return "orderFailed";
        }

    }

}
