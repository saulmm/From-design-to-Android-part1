package com.saulmm.cui.model;


import java.util.Arrays;
import java.util.List;

import static com.saulmm.cui.model.Product.ID.*;

public class Product {
    public enum ID {
        ALL_STAR, SANDAL, WOMEN_SHOES, SPORT_SHOES;
    }

    public final String name;
    public final ID productId;
    public final String price;

    private Product(String name, String price, ID productId) {
        this.name = name;
        this.price = price;
        this.productId = productId;
    }

    public static List<Product> createFakeProducts() {
        return Arrays.asList(
            new Product("Shooting Stars", "$ 45", ALL_STAR),
            new Product("Pictures in Sky", "$ 575", SANDAL),
            new Product("The basics of buying a telescope", "$ 892", WOMEN_SHOES),
            new Product("The skyrider", "$ 23", SPORT_SHOES)
        );
    }
}
