package com.saulmm.cui;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialog;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.view.ViewCompat;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.saulmm.cui.databinding.FragmentOrderFormBinding;
import com.saulmm.cui.databinding.LayoutFormOrderStep1Binding;

import de.hdodenhof.circleimageview.CircleImageView;


public class OrderDialogFragment extends BottomSheetDialogFragment {
    public static final String ID_SIZE_SUFFIX = "txt_size";
    public static final String ID_COLOR_SUFFIX = "img_color";
    public static final String ID_DATE_SUFFIX = "container_date";
    public static final String ID_TIME_SUFFIX = "container_time";

    private FragmentOrderFormBinding binding;
    private Transition selectedViewTransition;

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
        binding = FragmentOrderFormBinding.inflate(LayoutInflater.from(getContext()));

        selectedViewTransition = TransitionInflater.from(getContext())
            .inflateTransition(R.transition.move);

        final View contentView = binding.getRoot();
        dialog.setContentView(contentView);

        initOrderStepOneView(binding.layoutStep1);
    }

    public void onGoButtonClick(View v) {
        final Scene deliveryFormScene = Scene.getSceneForLayout(binding.formContainer,
            R.layout.layout_form_order_step2, getContext());

        ViewCompat.animate(binding.layoutStep1.getRoot()).alpha(0)
            .setInterpolator(new AccelerateInterpolator(1.5f))
            .setDuration(100)
            .withEndAction(() -> TransitionManager
                .go(deliveryFormScene, null));

        deliveryFormScene.setEnterAction(() -> {
            initOrderStepTwoView(deliveryFormScene.getSceneRoot());

            deliveryFormScene.getSceneRoot().setTranslationX(
                binding.formContainer.getWidth());

            ViewCompat.animate(deliveryFormScene.getSceneRoot())
                .translationX(0)
                .start();
        });
    }

    private void transitionSelectedView(View v) {
        v.setVisibility(View.INVISIBLE);

        // Create the cloned view from the selected view at the same position
        final View clonedView = createClonedView(v);

        // Add the cloned view to the constraint layout
        ((ViewGroup) binding.getRoot()).addView(clonedView);

        // Fire the transition by changing its constraint's layout params
        startCloneAnimation(clonedView, getTargetView(v));
    }

    private void startCloneAnimation(View clonedView, View targetView) {
        clonedView.post(() -> {
            if (getActivity() != null) {
                TransitionManager.beginDelayedTransition(
                    (ViewGroup) binding.getRoot(), selectedViewTransition);

                ConstraintLayout.LayoutParams fakeEndParams = createFakeEndParams(clonedView, targetView);
                clonedView.setLayoutParams(fakeEndParams); // Fire the transition
            }
        });
    }

    private View createClonedView(View v) {
        final String resourceName = getResources().getResourceEntryName(v.getId());
        
        if (resourceName.startsWith(ID_SIZE_SUFFIX))
            return createFakeSelectedSizeView((TextView) v);

        else if (resourceName.startsWith(ID_COLOR_SUFFIX))
            return createFakeSelectedColorView((ImageView) v);

        else if (resourceName.startsWith(ID_DATE_SUFFIX) ||
            resourceName.startsWith(ID_TIME_SUFFIX))
            return createFakeSelectedTextView(v);

        throw new IllegalStateException();
    }

    private View getTargetView(View v) {
        final String resourceName = getResources().getResourceEntryName(v.getId());

        if (resourceName.startsWith(ID_SIZE_SUFFIX) ||
            resourceName.startsWith(ID_DATE_SUFFIX))
            return binding.txtLabelSize;

        else if (resourceName.startsWith(ID_COLOR_SUFFIX) ||
            resourceName.startsWith(ID_TIME_SUFFIX))
            return binding.txtLabelColour;

        throw new IllegalStateException();
    }

    private void initOrderStepOneView(LayoutFormOrderStep1Binding layoutStep1) {
        View.OnClickListener formListener = this::transitionSelectedView;
        binding.btnGo.setOnClickListener(this::onGoButtonClick);

        layoutStep1.txtSize1.setOnClickListener(formListener);
        layoutStep1.txtSize2.setOnClickListener(formListener);
        layoutStep1.txtSize3.setOnClickListener(formListener);
        layoutStep1.txtSize4.setOnClickListener(formListener);
        layoutStep1.txtSize5.setOnClickListener(formListener);

        layoutStep1.imgColorBlue.setOnClickListener(formListener);
        layoutStep1.imgColorGreen.setOnClickListener(formListener);
        layoutStep1.imgColorPurple.setOnClickListener(formListener);
        layoutStep1.imgColorRed.setOnClickListener(formListener);
        layoutStep1.imgColorYellow.setOnClickListener(formListener);
    }

    private void initOrderStepTwoView(ViewGroup sceneRoot) {
        final View.OnClickListener stepTwoListener = this::transitionSelectedView;

        sceneRoot.findViewById(R.id.container_date1).setOnClickListener(stepTwoListener);
        sceneRoot.findViewById(R.id.container_date2).setOnClickListener(stepTwoListener);
        sceneRoot.findViewById(R.id.container_date3).setOnClickListener(stepTwoListener);

        sceneRoot.findViewById(R.id.container_time1).setOnClickListener(stepTwoListener);
        sceneRoot.findViewById(R.id.container_time2).setOnClickListener(stepTwoListener);
        sceneRoot.findViewById(R.id.container_time3).setOnClickListener(stepTwoListener);
    }

    private View createFakeSelectedTextView(View v) {
        final TextView fakeSelectedTextView = new TextView(
            getContext(), null, R.attr.selectedTextStyle);

        fakeSelectedTextView.setText("This is a test"); // TODO, set the proper text
        fakeSelectedTextView.setLayoutParams(createNewViewLayoutParams(v));
        return fakeSelectedTextView;
    }

    private View createFakeSelectedSizeView(TextView textView) {
        final TextView fakeSelectedView = new TextView(
            getContext(), null, R.attr.sizeStyle);

        fakeSelectedView.setText(textView.getText());
        fakeSelectedView.setLayoutParams(createCloneLayoutParams(textView));
        fakeSelectedView.setSelected(true);
        return fakeSelectedView;
    }

    private View createFakeSelectedColorView(ImageView imageView) {
        final ImageView fakeImageView = new CircleImageView(
            getContext(), null, R.attr.colorStyle);

        fakeImageView.setImageDrawable(imageView.getDrawable());
        fakeImageView.setLayoutParams(createCloneLayoutParams(imageView));
        return fakeImageView;
    }

    private ConstraintLayout.LayoutParams createFakeEndParams(View v, View targetView) {
        final ConstraintLayout.LayoutParams layoutParams =
            (ConstraintLayout.LayoutParams) v.getLayoutParams();

        final int marginLeft = getContext().getResources()
            .getDimensionPixelOffset(R.dimen.spacing_medium);

        layoutParams.setMargins(marginLeft, 0, 0, 0);
        layoutParams.topToTop = targetView.getId();
        layoutParams.startToEnd = targetView.getId();
        layoutParams.bottomToBottom = targetView.getId();

        return layoutParams;
    }

    private ConstraintLayout.LayoutParams createCloneLayoutParams(View v) {
        final ConstraintLayout.LayoutParams layoutParams =
            new ConstraintLayout.LayoutParams(v.getWidth(), v.getHeight());

        layoutParams.topToTop = binding.formContainer.getId();
        layoutParams.leftToLeft = binding.formContainer.getId();
        layoutParams.setMargins((int) v.getX(), (int) v.getY(), 0, 0);
        return layoutParams;
    }

    private ConstraintLayout.LayoutParams createNewViewLayoutParams(View v) {
        final ConstraintLayout.LayoutParams layoutParams =
            new ConstraintLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        layoutParams.topToTop = binding.formContainer.getId();
        layoutParams.leftToLeft = binding.formContainer.getId();
        layoutParams.setMargins((int) v.getX(), (int) v.getY(), 0, 0);
        return layoutParams;
    }
}
