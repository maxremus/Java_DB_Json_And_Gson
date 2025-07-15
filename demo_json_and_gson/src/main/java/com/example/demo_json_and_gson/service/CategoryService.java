package com.example.demo_json_and_gson.service;

import com.example.demo_json_and_gson.dtos.CategoryStatsDto;
import com.example.demo_json_and_gson.dtos.ImportCategoryDto;
import com.example.demo_json_and_gson.entitys.Category;
import com.example.demo_json_and_gson.repository.CategoryRepository;
import com.example.demo_json_and_gson.repository.CategoryStatsProj;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@Service
public class CategoryService {
    private final Gson gson;
    private final CategoryRepository categoryRepository;
    private final ModelMapper modelMapper;


    public CategoryService(CategoryRepository categoryRepository) {
        this.modelMapper = new ModelMapper();

        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.categoryRepository = categoryRepository;
    }

    public void importData() throws IOException {
        //read json
        Path path = Path.of("src/main/resources/Jsons/categories.json");
        List<String> lines = Files.readAllLines(path);

        ImportCategoryDto[] input = this.gson.fromJson(String.join("", lines), ImportCategoryDto[].class);


        for (ImportCategoryDto importCategoryDto : input) {
            //validate
            String name = importCategoryDto.getName();
            if (name == null || name.length() < 3 || name.length() > 15) {
                System.out.println("Invalid category name" + name);

                continue;
            }
            Category category = new Category(name);

            this.categoryRepository.save(category);

        }
    }

    public Set<Category> getRandomCategories() {
        Random rand = new Random();

        long total = this.categoryRepository.count();

        long count = rand.nextLong(total);

        Set<Category> result = new HashSet<>();
        for (int i = 0; i < count; i++) {
            long  id = rand.nextLong(total) + 1;
            Optional<Category> category = this.categoryRepository.findById(id);

            category.ifPresent(result::add);

        }

        return result;
    }

    public void getCategoriesStatus() {
        List<CategoryStatsProj> categoryStatus = this.categoryRepository.findCategoryStatus();

        CategoryStatsDto[] result = this.modelMapper.map(categoryStatus, CategoryStatsDto[].class);

        String json = this.gson.toJson(result);
        System.out.println(json);
    }
}
