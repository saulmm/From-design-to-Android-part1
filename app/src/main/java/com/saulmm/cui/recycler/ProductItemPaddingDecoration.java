package com.saulmm.cui.recycler;

import android.content.Context;
import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.saulmm.cui.R;

public class ProductItemPaddingDecoration extends RecyclerView.ItemDecoration {
    private final Rect paddingRect;

    public ProductItemPaddingDecoration(Context context) {
        final int padding = (int) context.getResources().getDimension(R.dimen.product_margin);
        paddingRect = new Rect(padding, 0, padding, 0);
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        outRect.set(paddingRect);
    }
}
