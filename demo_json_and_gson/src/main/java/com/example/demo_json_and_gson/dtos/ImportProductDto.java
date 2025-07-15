package com.example.demo_json_and_gson.dtos;

public class ImportProductDto {
    private String name;
    private double price;

    public ImportProductDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
