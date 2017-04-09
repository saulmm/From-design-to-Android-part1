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
package com.saulmm.cui.recycler;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.saulmm.cui.R;
import com.saulmm.cui.databinding.ItemProductBinding;
import com.saulmm.cui.model.Product;

import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ProductAdapter.ProductViewHolder> {
    private final List<Product> products;

    public ProductAdapter(List<Product> products) {
        this.products = products;
    }

    @Override
    public ProductViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final ItemProductBinding productBinding = ItemProductBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false);

        return new ProductViewHolder(productBinding);
    }

    @Override
    public void onBindViewHolder(ProductViewHolder holder, int position) {
        holder.bind(products.get(position));
    }

    @Override
    public int getItemCount() {
        return products.size();
    }


    static class ProductViewHolder extends RecyclerView.ViewHolder {
        private final ItemProductBinding binding;

        ProductViewHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Product product) {
            binding.setProduct(product);
            binding.imgProduct.setBackground(createProductBackground(product));
            binding.imgProduct.setImageResource(product.image);
        }

        @NonNull
        private GradientDrawable createProductBackground(Product product) {
            final GradientDrawable gradientDrawable = (GradientDrawable) ContextCompat.getDrawable(
                itemView.getContext(), R.drawable.bg_product);

            gradientDrawable.setColor(ContextCompat.getColor(
                itemView.getContext(), product.color));

            gradientDrawable.setSize(itemView.getWidth(), getDrawableHeight());
            gradientDrawable.mutate();
            return gradientDrawable;
        }

        private int getDrawableHeight() {
            final Context context = itemView.getContext();

            return getAdapterPosition() % 2 == 0
                ? context.getResources().getDimensionPixelOffset(R.dimen.product_regular_height)
                : context.getResources().getDimensionPixelOffset(R.dimen.product_large_height);
        }
    }
}
