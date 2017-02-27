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
    private ActivityHomeBinding binding;
    private List<Product> fakeProducts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_home);
        fakeProducts = Product.createFakeProducts();

        initRecycler(binding.productsRecycler);
    }

    private void initRecycler(RecyclerView productsRecycler) {
        productsRecycler.setHasFixedSize(true);

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
