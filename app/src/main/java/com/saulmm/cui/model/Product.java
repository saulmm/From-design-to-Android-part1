package com.saulmm.cui.model;


import com.saulmm.cui.R;

import java.io.Serializable;
import java.util.Arrays;
import java.util.List;


public class Product implements Serializable{
    public final String name;
    public final String price;
    public final int image;
    public final int color;

    private Product(String name, String price, int image, int color) {
        this.name = name;
        this.price = price;
        this.image = image;
        this.color = color;
    }

    public static List<Product> createFakeProducts() {
        return Arrays.asList(
            new Product("Shooting Stars", "$ 45", R.drawable.img_sneaker, R.color.product_yellow),
            new Product("Pictures in Sky", "$ 575", R.drawable.img_sandal, R.color.product_green),
            new Product("The basics of buying a telescope", "$ 892", R.drawable.img_sandal, R.color.product_blue),
            new Product("The skyrider", "$ 23", R.drawable.img_ice_skate, R.color.product_purple)
        );
    }
}
