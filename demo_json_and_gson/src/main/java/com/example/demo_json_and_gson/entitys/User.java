package com.example.demo_json_and_gson.entitys;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name", nullable = false)
    private String lastName;

    @Basic
    private Integer age;

    @OneToMany(mappedBy = "seller", targetEntity = Product.class)
    private List<Product> soldProducts;

    @OneToMany(mappedBy = "buyer")
    private List<Product> boughtProducts;

    @ManyToMany
    @JoinTable(inverseJoinColumns = @JoinColumn(name = "frien_id"))
    private List<User> friends;

    public User() {
        this.soldProducts = new ArrayList<>();
        this.boughtProducts = new ArrayList<>();
        this.friends = new ArrayList<>();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public List<Product> getSoldProducts() {
        return soldProducts;
    }

    public void setSoldProducts(List<Product> selling) {
        this.soldProducts = selling;
    }

    public List<Product> getBoughtProducts() {
        return boughtProducts;
    }

    public void setBoughtProducts(List<Product> bought) {
        this.boughtProducts = bought;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }
}
