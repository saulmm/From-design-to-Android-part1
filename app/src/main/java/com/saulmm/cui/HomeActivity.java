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
package com.saulmm.cui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.saulmm.cui.databinding.ActivityHomeBinding;
import com.saulmm.cui.model.Product;
import com.saulmm.cui.recycler.OnItemSelectedListener;
import com.saulmm.cui.recycler.ProductAdapter;
import com.saulmm.cui.recycler.ProductItemPaddingDecoration;

import java.util.List;

import uk.co.chrisjenx.calligraphy.CalligraphyContextWrapper;

public class HomeActivity extends AppCompatActivity {
    private final List<Product> fakeProducts = Product.createFakeProducts();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        ActivityHomeBinding binding = DataBindingUtil.setContentView(
            this, R.layout.activity_home);

        initRecycler(binding.productsRecycler);
    }

    private void initRecycler(RecyclerView productsRecycler) {
        productsRecycler.setAdapter(new ProductAdapter(fakeProducts));
        productsRecycler.addItemDecoration(new ProductItemPaddingDecoration(this));
        productsRecycler.addOnItemTouchListener(new OnItemSelectedListener(this) {
            @Override
            public void onItemSelected(RecyclerView.ViewHolder holder, int position) {
                OrderDialogFragment.newInstance(fakeProducts.get(position)).show(getSupportFragmentManager(), null);
            }
        });
    }

    @Override
    protected void attachBaseContext(Context newBase) {
        super.attachBaseContext(CalligraphyContextWrapper.wrap(newBase));
    }
}
