package com.saulmm.cui.recycler;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;

import com.saulmm.cui.R;
import com.saulmm.cui.databinding.ItemProductBinding;
import com.saulmm.cui.model.Product;

class ProductViewHolder extends RecyclerView.ViewHolder {
    private final ItemProductBinding binding;
    private final Context context;

    ProductViewHolder(ItemProductBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.context = itemView.getContext();
    }

    void bind(Product product) {
        binding.setProduct(product);

        final GradientDrawable gradientDrawable = (GradientDrawable)
            binding.image.getBackground();

        gradientDrawable.setColor(ContextCompat.getColor(
            context, product.color));

        binding.image.setImageResource(product.image);
    }
}
