package com.fitmart.proj.controller;

import com.fitmart.proj.global.GlobalData;
import com.fitmart.proj.service.CategoryService;
import com.fitmart.proj.service.KafkaService;
import com.fitmart.proj.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashMap;

@Controller
public class HomeController {
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    KafkaService kafkaService;
    @GetMapping({"/", "/home"})
    public String home(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());

        return "index";
    }

    @GetMapping("/shop")
    public String shop(Model model) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("products", productService.getAllProducts());
        model.addAttribute("categories", categoryService.getAllCategory());

        return "shop";
    }

    @GetMapping("/shop/category/{id}")
    public String shopByCategory(Model model, @PathVariable int id) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("products", productService.getAllProductsByCategoryId(id));
        model.addAttribute("categories", categoryService.getAllCategory());
        return "shop";
    }

    @GetMapping("/shop/viewproduct/{id}")
    public String viewProduct(Model model, @PathVariable long id) {
        model.addAttribute("cartCount", GlobalData.cart.size());
        model.addAttribute("product", productService.getProductById(id).get());
        HashMap<String, String> item = new HashMap<>();
        item.put("itemId", String.valueOf(id));
//        item.put("itemName", productService.getProductById(id).get().getName());
        this.kafkaService.itemViewed(item);
        return "viewProduct";
    }

}

