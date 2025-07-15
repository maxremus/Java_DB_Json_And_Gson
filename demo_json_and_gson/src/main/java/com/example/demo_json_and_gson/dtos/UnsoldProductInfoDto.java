package com.example.demo_json_and_gson.dtos;

import com.example.demo_json_and_gson.entitys.Product;

public class UnsoldProductInfoDto {

    private String name;
    private double price;
    private String seller;

    public UnsoldProductInfoDto() {
    }

    public UnsoldProductInfoDto(Product product) {
        this.name = product.getName();
        this.price = product.getPrice().doubleValue();

        if (product.getSeller() != null) {
            if (product.getSeller().getFirstName() != null) {
                this.seller = product.getSeller().getFirstName() + " " + product.getSeller().getLastName();
            } else {
                this.seller = product.getSeller().getLastName();
            }
        } else {
            this.seller = "Няма продавач";
        }

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

    public String getSeller() {
        return seller;
    }

    public void setSeller(String seller) {
        this.seller = seller;
    }
}
