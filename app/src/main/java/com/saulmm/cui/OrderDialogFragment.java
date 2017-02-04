package com.saulmm.cui;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetBehavior;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.design.widget.CoordinatorLayout;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.content.ContextCompat;
import android.transition.ChangeTransform;
import android.transition.TransitionInflater;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;

import com.saulmm.cui.databinding.FragmentOrderFormBinding;

import static com.saulmm.cui.R.id.view;


public class OrderDialogFragment extends BottomSheetDialogFragment implements View.OnClickListener {
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

        binding.layoutStep1.txt1.setOnClickListener(this);
        binding.layoutStep1.txt2.setOnClickListener(this);
        binding.layoutStep1.txt3.setOnClickListener(this);
        binding.layoutStep1.txt4.setOnClickListener(this);
        binding.layoutStep1.txt5.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        View testView = binding.testButton;
        ConstraintLayout.LayoutParams layoutParams = (ConstraintLayout.LayoutParams) testView.getLayoutParams();
        layoutParams.bottomToBottom = binding.txtLabelSize.getId();
        layoutParams.rightToRight = binding.txtLabelSize.getId();
        layoutParams.topToTop = binding.txtLabelSize.getId();
        layoutParams.verticalBias = 0.88f;
        layoutParams.setMargins(getResources().getDimensionPixelOffset(R.dimen.spacing_large), 0, 0, 0);

        android.transition.Transition transition = TransitionInflater.from(getContext()).inflateTransition(R.transition.move);

        ViewGroup sceneRoot = (ViewGroup) testView.getParent();
        android.transition.TransitionManager.beginDelayedTransition(sceneRoot, transition);

        testView.setLayoutParams(layoutParams);


        v.setSelected(true);
    }
}
