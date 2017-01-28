package com.saulmm.cui;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class HomeActivity extends AppCompatActivity {

    private final String [] PRODUCTS = new String[] {
        "Shooting Stars", "Pictures in Sky", "The basics of buying a telescope",
        "The skyrider"
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
    }

    private static class ProductViewHolder extends RecyclerView.ViewHolder {

        public ProductViewHolder(View itemView) {
            super(itemView);
        }
    }
}
