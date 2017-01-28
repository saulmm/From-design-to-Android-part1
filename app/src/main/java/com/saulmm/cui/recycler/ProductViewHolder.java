package com.saulmm.cui.recycler;

import android.support.v7.widget.RecyclerView;

import com.saulmm.cui.databinding.ItemProductBinding;
import com.saulmm.cui.model.Product;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    private final ItemProductBinding binding;

    ProductViewHolder(ItemProductBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    void bind(Product product) {
        binding.setProduct(product);
    }
}
