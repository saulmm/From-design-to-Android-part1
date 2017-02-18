package com.saulmm.cui;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

public class Playground extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground2);

        findViewById(R.id.btn_show_sheet).setOnClickListener((v) -> {
            PlaygroundBottomSheet.newInstance().show(getSupportFragmentManager(), "");
        });
    }
}
