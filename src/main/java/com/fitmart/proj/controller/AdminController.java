package com.fitmart.proj.controller;

import com.fitmart.proj.dto.ProductDTO;
import com.fitmart.proj.model.Product;
import com.fitmart.proj.service.CategoryService;
import com.fitmart.proj.service.ProductService;
import com.fitmart.proj.model.Category;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Controller
public class AdminController {
    public static String uploadDir = System.getProperty("user.dir") + "/src/main/resources/static/productImages";
    @Autowired
    CategoryService categoryService;
    @Autowired
    ProductService productService;
    @GetMapping("/admin")
    public String adminHome() {
          return "adminHome";
    }

    @GetMapping("/admin/categories")
    public String categories(Model model) {
        model.addAttribute("categories", categoryService.getAllCategory());
        return "categories";
    }

    @GetMapping("/admin/categories/add")
    public String getAddCategories(Model model) {
        model.addAttribute("category", new Category());
        return "categoriesAdd";
    }

    @PostMapping("/admin/categories/add")
    public String postAddCategories(@ModelAttribute("category") Category category ) {
        categoryService.addCategory(category);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/delete/{id}")
    public String deleteCategories(@PathVariable int id) {
        categoryService.deleteCategoryById(id);
        return "redirect:/admin/categories";
    }

    @GetMapping("/admin/categories/update/{id}")
    public String updateCategories(@PathVariable int id, Model model) {
        Optional<Category> category = categoryService.getCategoryById(id);
        if (category.isPresent()) {
            model.addAttribute("category", category.get());
            return "categoriesAdd";
        } else {
            return "404";
        }

    }

    @GetMapping("/admin/products")
    public String getProducts(Model model) {
        model.addAttribute("products",productService.getAllProducts());
        return "products";
    }

    @GetMapping("/admin/products/add")
    public String getProductsAdd(Model model) {
        model.addAttribute("productDTO",new ProductDTO());
        model.addAttribute("categories", categoryService.getAllCategory());
        return "productsAdd";
    }

    @PostMapping("/admin/products/add")
    public String postProductsAdd(@ModelAttribute("productDTO")ProductDTO productDTO,
                                  @RequestParam("productImage")MultipartFile file,
                                  @RequestParam("imgName")String imgName)throws IOException {
        Product product = new Product();
        product.setId(productDTO.getId());
        product.setName(productDTO.getName());
        product.setCategory(categoryService.getCategoryById(productDTO.getCategoryId()).get());
        product.setPrice(productDTO.getPrice());
        product.setDescription(productDTO.getDescription());
        product.setWeight(productDTO.getWeight());
        String imageUuid;
        if(!file.isEmpty()) {
            imageUuid = file.getOriginalFilename();
            Path fileNameAndPath = Paths.get(uploadDir, imageUuid);
            Files.write(fileNameAndPath,file.getBytes());
        }
        else {
            imageUuid = imgName;
        }
        product.setImageName(imageUuid);
        productService.addProduct(product);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/delete/{id}")
    public String deleteProducts(@PathVariable Long id) {
        productService.removeProductById(id);
        return "redirect:/admin/products";
    }

    @GetMapping("/admin/product/update/{id}")
    public String updateProducts(@PathVariable Long id, Model model) {
        Product product = productService.getProductById(id).get();

        ProductDTO productDTO = new ProductDTO();
        productDTO.setCategoryId(product.getCategory().getId());
        productDTO.setName(product.getName());
        productDTO.setPrice(product.getPrice());
        productDTO.setWeight(product.getWeight());
        productDTO.setId(product.getId());
        productDTO.setDescription(productDTO.getDescription());
        productDTO.setImageName(product.getImageName());
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("categories", categoryService.getAllCategory());
        return "productsAdd";
    }

}
