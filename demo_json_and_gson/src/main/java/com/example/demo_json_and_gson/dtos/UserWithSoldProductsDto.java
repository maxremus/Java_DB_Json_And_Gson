package com.example.demo_json_and_gson.dtos;

import java.util.List;

public class UserWithSoldProductsDto {
    private String firstName;
    private String lastName;
    private List<SoldProductsDto> soldProducts;

    public UserWithSoldProductsDto() {
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public List<SoldProductsDto> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<SoldProductsDto> soldProducts) {
        this.soldProducts = soldProducts;
    }
}
