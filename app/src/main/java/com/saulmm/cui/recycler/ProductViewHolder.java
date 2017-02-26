package com.saulmm.cui.recycler;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.saulmm.cui.OrderDialogFragment;
import com.saulmm.cui.R;
import com.saulmm.cui.databinding.ItemProductBinding;
import com.saulmm.cui.model.Product;

public class ProductViewHolder extends RecyclerView.ViewHolder {
    private final ItemProductBinding binding;
    private final Context context;

    ProductViewHolder(ItemProductBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
        this.context = itemView.getContext();
    }

    void bind(Product product) {
        binding.setProduct(product);

        setProductImage(product.productId);
    }

    private void setProductImage(Product.ID productId) {
        Drawable productDrawable = null;
        int image = 0;

        switch (productId) {
            case ALL_STAR:
                productDrawable = ContextCompat.getDrawable(
                    context, R.drawable.product_allstar);

                image = R.drawable.img_sneaker;

                break;

            case SANDAL:
                productDrawable = ContextCompat.getDrawable(
                    context, R.drawable.product_sandal);

                image = R.drawable.img_sandal;
                break;

            case WOMEN_SHOES:
                productDrawable = ContextCompat.getDrawable(
                    context, R.drawable.product_women_shoes);
                break;

            case SPORT_SHOES:
                productDrawable = ContextCompat.getDrawable(
                    context, R.drawable.product_sport_shoes);

                image = R.drawable.img_sneaker;
                break;
        }

        binding.image.setImageResource(image);
        binding.image.setBackground(productDrawable);

    }
}
