package com.saulmm.cui;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.graphics.Rect;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.saulmm.cui.databinding.ActivityHomeBinding;
import com.saulmm.cui.databinding.ItemProductBinding;
import com.saulmm.cui.model.Product;

import java.util.List;

public class HomeActivity extends AppCompatActivity {

    private ActivityHomeBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        binding.productsRecycler.setHasFixedSize(true);
        binding.productsRecycler.setAdapter(new ProductAdapter(Product.createFakeProducts()));
        binding.productsRecycler.addItemDecoration(new ProductItemPaddingDecoration(this));
    }

    private static class ProductItemPaddingDecoration extends RecyclerView.ItemDecoration {
        private final Rect paddingRect;

        ProductItemPaddingDecoration(Context context) {
            final int padding = (int) context.getResources().getDimension(R.dimen.product_margin);
            paddingRect = new Rect(padding, 0, padding, 0);
        }

        @Override
        public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
            outRect.set(paddingRect);
        }
    }

    private static class ProductAdapter extends RecyclerView.Adapter<ProductViewHolder> {
        private final List<Product> products;

        ProductAdapter(List<Product> products) {
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
    }

    private static class ProductViewHolder extends RecyclerView.ViewHolder {
        private final ItemProductBinding binding;

        ProductViewHolder(ItemProductBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Product product) {
            binding.setProduct(product);
        }
    }
}
