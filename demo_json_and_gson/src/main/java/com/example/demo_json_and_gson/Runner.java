package com.example.demo_json_and_gson;

import com.example.demo_json_and_gson.service.CategoryService;
import com.example.demo_json_and_gson.service.ProductService;
import com.example.demo_json_and_gson.service.UserService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class Runner implements CommandLineRunner {

    private final UserService userService;
    private final CategoryService categoryService;
    private final ProductService productService;

    public Runner(UserService userService, CategoryService categoryService, ProductService productService) {
        this.userService = userService;
        this.categoryService = categoryService;
        this.productService = productService;
    }

    @Override
    public void run(String... args) throws Exception {

        System.out.printf("Working.....");

       // productService.getUnsoldProductsInRange(500, 1000);
       // userService.getAllWithSoldProducts();
        categoryService.getCategoriesStatus();
    }
}
