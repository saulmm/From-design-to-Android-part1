/*
 * Copyright (C) 2017
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
            new Product("The basics of buying a telescope", "$ 892", R.drawable.img_shoe, R.color.product_blue),
            new Product("The skyrider", "$ 23", R.drawable.img_ice_skate, R.color.product_purple)
        );
    }
}
