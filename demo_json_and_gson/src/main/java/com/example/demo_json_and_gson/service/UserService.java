package com.example.demo_json_and_gson.service;

import com.example.demo_json_and_gson.dtos.ImportUserDto;
import com.example.demo_json_and_gson.dtos.UserWithSoldProductsDto;
import com.example.demo_json_and_gson.entitys.Product;
import com.example.demo_json_and_gson.entitys.User;
import com.example.demo_json_and_gson.repository.ProductRepository;
import com.example.demo_json_and_gson.repository.UserRepository;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
public class UserService {

    private Gson gson;
    private ModelMapper modelMapper;
    private final UserRepository userRepository;
    private final ProductRepository productRepository;

    public UserService(UserRepository userRepository, Gson gson, ProductRepository productRepository) {
        this.userRepository = userRepository;
        this.productRepository = productRepository;

        this.gson = new GsonBuilder().setPrettyPrinting().create();
        this.modelMapper = new ModelMapper();
    }

    public void importData() throws IOException {

        //1.read json -> dto
        Path fileLocation = Path.of("src/main/resources/Jsons/users.json");
        List<String> lines = Files.readAllLines(fileLocation);

        ImportUserDto[] fromJson =
                this.gson.fromJson(String.join("", lines), ImportUserDto[].class);

        for (ImportUserDto importUserDto : fromJson) {

            //2.validate??
            if (importUserDto.getLastName() == null || importUserDto.getLastName().length() < 3) {
                System.out.println("User last name is invalid");
                continue;
            }

                //3.dto -> entity
                User user = this.modelMapper.map(importUserDto, User.class);

                //4.Persist
                this.userRepository.save(user);



        }
    }

    public User getRandomUsers() {
        Random rand = new Random();

        long total = this.userRepository.count();

        if (total == 0) {
            return null;
        }

        while (true) {
            long id = rand.nextLong(total) + 1;

            Optional<User> user = this.userRepository.findById(id);

            if (user.isPresent()) {
                return user.get();
            }
        }
    }
    @Transactional
    public void getAllWithSoldProducts() {
        List<User> users = this.userRepository.findWithSoldProductsOrderByLastName();

        for (User user : users) {
            List<Product> soldProducts = this.productRepository.findBySellerAndBuyerIsNotNull(user);
            user.setSoldProducts(soldProducts);
        }





        UserWithSoldProductsDto[] result = this.modelMapper.map(users, UserWithSoldProductsDto[].class);

        String json = gson.toJson(result);
        System.out.println(json);
    }
}
