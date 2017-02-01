package com.saulmm.cui;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.saulmm.cui.databinding.FragmentOrderFormBinding;


public class OrderDialogFragment extends BottomSheetDialogFragment {
    private FragmentOrderFormBinding binding;

    public static OrderDialogFragment newInstance() {
        return new OrderDialogFragment();
    }

    @NonNull
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        return new BottomSheetDialog(getContext(), R.style.BottomSheetDialog);
    }

    @Override
    public void setupDialog(Dialog dialog, int style) {
        this.binding = FragmentOrderFormBinding.inflate(LayoutInflater.from(getContext()));
        final View contentView = binding.getRoot();
        dialog.setContentView(contentView);
    }

    public void onColorClick(View view) {
    }
}
