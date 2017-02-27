package com.saulmm.cui;

import android.databinding.BindingAdapter;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.OvalShape;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.text.Spannable;
import android.text.SpannableString;
import android.text.style.RelativeSizeSpan;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.OvershootInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.saulmm.cui.databinding.FragmentOrderFormBinding;
import com.saulmm.cui.databinding.LayoutFormOrderStep1Binding;
import com.saulmm.cui.databinding.LayoutFormOrderStep2Binding;
import com.saulmm.cui.databinding.LayoutOrderConfirmationBinding;
import com.saulmm.cui.model.Product;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;


public class OrderDialogFragment extends BottomSheetDialogFragment {
    public static final String ID_SIZE_SUFFIX = "txt_size";
    public static final String ID_COLOR_SUFFIX = "img_color";
    public static final String ID_DATE_SUFFIX = "container_date";
    public static final String ID_TIME_SUFFIX = "container_time";
    private static final String ARG_PRODUCT = "arg_product";

    private List<View> clonedViews = new ArrayList<>();

    private FragmentOrderFormBinding binding;
    private Transition selectedViewTransition;

    private OrderSelection orderSelection;

    public static class OrderSelection {
        public int size;
        public int color;
        public String date;
        public String time;
    }

    public interface Step1Listener {
        void onSizeSelected(View v);

        void onColorSelected(View v);
    }

    public interface Step2Listener {
        void onDateSelected(View v);

        void onTimeSelected(View v);
    }

    public static OrderDialogFragment newInstance(Product product) {
        final Bundle args = new Bundle();
        args.putSerializable(ARG_PRODUCT, product);

        final OrderDialogFragment orderFragment = new OrderDialogFragment();
        orderFragment.setArguments(args);

        return orderFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        binding = FragmentOrderFormBinding.inflate(
            LayoutInflater.from(getContext()), container, false);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        orderSelection = new OrderSelection();

        final Product product = (Product) getArguments().getSerializable(ARG_PRODUCT);
        binding.setProduct(product);
        binding.imgProduct.setImageDrawable(createProductImageDrawable(product));

        selectedViewTransition = TransitionInflater.from(getContext())
            .inflateTransition(R.transition.move);

        initOrderStepOneView(binding.layoutStep1);
    }


    private void transitionToSecondStep(View v) {
        final LayoutFormOrderStep2Binding step2Binding = LayoutFormOrderStep2Binding.inflate(
            LayoutInflater.from(getContext()), binding.formContainer, false);//

        final Scene deliveryFormScene = new Scene(binding.formContainer,
            ((ViewGroup) step2Binding.getRoot()));

        ViewCompat.animate(binding.layoutStep1.getRoot()).alpha(0)
            .setInterpolator(new AccelerateInterpolator(1.5f))
            .setDuration(100)
            .withEndAction(() -> TransitionManager
                .go(deliveryFormScene, null));

        deliveryFormScene.setEnterAction(() -> {
            initOrderStepTwoView(step2Binding);

            deliveryFormScene.getSceneRoot().setTranslationX(
                binding.formContainer.getWidth());

            ViewCompat.animate(deliveryFormScene.getSceneRoot())
                .translationX(0)
                .start();
        });
    }

    private void transitionSelectedView(View v) {
        v.setSelected(true);

        // Create the cloned view from the selected view at the same position
        final View clonedView = createClonedView(v);

        // Add the cloned view to the constraint layout
        if (clonedViews.size() < 2) {
            binding.mainContainer.addView(clonedView);
            clonedViews.add(clonedView);
        }

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

        return (resourceName.startsWith(ID_COLOR_SUFFIX))
            ? createFakeSelectedColorView((ImageView) v)
            : createFakeSelectedTextView(v);
    }

    private View getTargetView(View v) {
        final String resourceName = getResources().getResourceEntryName(v.getId());

        if (resourceName.startsWith(ID_SIZE_SUFFIX) ||
            resourceName.startsWith(ID_DATE_SUFFIX)) {
            v.setId(R.id.first_position);
            return binding.txtLabelSize;

        } else if (resourceName.startsWith(ID_COLOR_SUFFIX) ||
            resourceName.startsWith(ID_TIME_SUFFIX)) {
            v.setId(R.id.second_position);
            return binding.txtLabelColour;
        }

        throw new IllegalStateException();
    }

    private void initOrderStepOneView(LayoutFormOrderStep1Binding layoutStep1) {
        binding.btnGo.setOnClickListener(v -> {
            binding.txtAction.setText(R.string.action_book);

            for (View clonedView : clonedViews)
                binding.mainContainer.removeView(clonedView);

            clonedViews.clear();

            transitionToSecondStep(v);
        });

        layoutStep1.setListener(new Step1Listener() {
            @Override
            public void onSizeSelected(View v) {
                orderSelection.size = Integer.parseInt(((TextView) v).getText().toString());
                transitionSelectedView(v);
            }

            @Override
            public void onColorSelected(View v) {
                orderSelection.color = ((ColorDrawable) ((ImageView) v).getDrawable()).getColor();
                transitionSelectedView(v);
            }
        });
    }

    @BindingAdapter("app:spanOffset")
    public static void setItemSpan(View v, int spanOffset) {
        final String itemText = ((TextView) v).getText().toString();
        final SpannableString sString = new SpannableString(itemText);

        sString.setSpan(new RelativeSizeSpan(1.5f), itemText.length() - spanOffset, itemText.length(),
            Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);

        ((TextView) v).setText(sString);
    }

    private void initOrderStepTwoView(LayoutFormOrderStep2Binding step2Binding) {
        binding.btnGo.setBackground(new ColorDrawable(orderSelection.color));
        binding.btnGo.setOnClickListener(v -> changeToConfirmScene());

        step2Binding.setListener(new Step2Listener() {
            @Override
            public void onDateSelected(View v) {
                transitionSelectedView(v);
            }

            @Override
            public void onTimeSelected(View v) {
                transitionSelectedView(v);
            }
        });
    }

    private View createFakeSelectedTextView(View v) {
        final TextView fakeSelectedTextView = new TextView(
            getContext(), null, R.attr.selectedTextStyle);

        fakeSelectedTextView.setText("This is a test"); // TODO, set the proper text
        fakeSelectedTextView.setLayoutParams(createNewViewLayoutParams(v));
        return fakeSelectedTextView;
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

    private void changeToConfirmScene() {
        final LayoutOrderConfirmationBinding confirmationBinding = LayoutOrderConfirmationBinding
            .inflate(LayoutInflater.from(getContext()), binding.mainContainer, false);

        confirmationBinding.getRoot().setBackground(
            new ColorDrawable(orderSelection.color));

        // TODO why content here?
        final Scene scene = new Scene(binding.content,
            ((ViewGroup) confirmationBinding.getRoot()));

        confirmationBinding.setProduct((Product) getArguments().getSerializable(ARG_PRODUCT));
        scene.setEnterAction(onEnterConfirmScene(confirmationBinding));

        final Transition transition = TransitionInflater.from(getContext())
            .inflateTransition(R.transition.move);

        TransitionManager.go(scene, transition);
    }

    private Drawable createProductImageDrawable(Product product) {
        final ShapeDrawable background = new ShapeDrawable();
        background.setShape(new OvalShape());
        background.getPaint().setColor(ContextCompat.getColor(getContext(), product.color));

        final BitmapDrawable bitmapDrawable = new BitmapDrawable(getResources(),
            BitmapFactory.decodeResource(getResources(), product.image));

        final LayerDrawable layerDrawable = new LayerDrawable
            (new Drawable[]{background, bitmapDrawable});

        final int padding = (int) getResources().getDimension(R.dimen.spacing_huge);
        layerDrawable.setLayerInset(1, padding, padding, padding, padding);

        return layerDrawable;
    }

    @NonNull
    private Runnable onEnterConfirmScene(LayoutOrderConfirmationBinding confirmationBinding) {
        return () -> {
            ViewCompat.animate(confirmationBinding.txtSubtitle)
                .scaleX(1).scaleY(1)
                .setInterpolator(new OvershootInterpolator())
                .setStartDelay(200)
                .start();
        };
    }
}