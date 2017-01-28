package com.saulmm.cui.model;


import java.util.Arrays;
import java.util.List;

public class Product {
    public final String name;
    public final String price;

    public Product(String name, String price) {
        this.name = name;
        this.price = price;
    }

    public static List<Product> createFakeProducts() {
        return Arrays.asList(
            new Product("Shooting Stars", "$45"),
            new Product("Pictures in Sky", "$575"),
            new Product("The basics of buying a telescope", "$892"),
            new Product("The skyrider", "$23")
        );
    }
}
