package com.example.demo_json_and_gson.service;

import com.example.demo_json_and_gson.dtos.ImportProductDto;
import com.example.demo_json_and_gson.dtos.UnsoldProductInfoDto;
import com.example.demo_json_and_gson.entitys.Product;
import com.example.demo_json_and_gson.entitys.User;
import com.example.demo_json_and_gson.repository.ProductRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeMap;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class ProductService {
    private final Gson gson;
    private ModelMapper ModelMapper;
    private  final ProductRepository productRepository;
    private final UserService userService;
    private final CategoryService categoryService;

    public ProductService(ProductRepository productRepository, UserService userService, CategoryService categoryService) {
        this.productRepository = productRepository;
        this.userService = userService;
        this.categoryService = categoryService;

        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.ModelMapper = new ModelMapper();
    }

    public void getUnsoldProductsInRange (double lower, double upper) {
        // 1. query

        BigDecimal low = BigDecimal.valueOf(lower);
        BigDecimal high = BigDecimal.valueOf(upper);
        List<Product> products = this.productRepository.findByPriceBetweenAndBuyerIsNullOrderByPriceAsc(low, high);

        //map to dto
       // UnsoldProductInfoDto[] result = this.ModelMapper.map(products,UnsoldProductInfoDto[].class);
        List<UnsoldProductInfoDto> resultNew = new ArrayList<>();

        for (Product product : products) {
            resultNew.add(new UnsoldProductInfoDto(product));
        }

        // print json

        String json = this.gson.toJson(resultNew);
        System.out.println(json);
    }

    public void importData() throws IOException {
        // parse
        Path path = Path.of("src/main/resources/Jsons/products.json");
        List<String> lines = Files.readAllLines(path);

        ImportProductDto[] data = this.gson.fromJson(String.join("", lines), ImportProductDto[].class);
        //validate

        for (ImportProductDto importProductDto : data) {
            String name = importProductDto.getName();
            if (name == null || name.length() < 3) {
                System.out.println("Invalid product name" + name);
                continue;
            }

            Product product = this.ModelMapper.map(importProductDto, Product.class);

            //assign random data
            product.setSeller(this.getRandomUser(true));
            product.setBuyer(this.getRandomUser(true));
            product.setCategories(this.categoryService.getRandomCategories());


            this.productRepository.save(product);

        } //persist
    }

    private User getRandomUser(boolean canReturnNull) {
        Random  random = new Random();

        if (canReturnNull){
            boolean nullResult = random.nextBoolean();

            if (nullResult){
                return null;
            }
        }
        return this.userService.getRandomUsers();
    }
}
