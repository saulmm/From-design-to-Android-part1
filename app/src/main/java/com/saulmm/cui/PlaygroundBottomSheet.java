package com.saulmm.cui;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.transition.Scene;
import android.transition.Transition;
import android.transition.TransitionInflater;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class PlaygroundBottomSheet extends BottomSheetDialogFragment {

    private View dialogView;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);

        dialogView = LayoutInflater.from(getContext())
            .inflate(R.layout.playground_sheet, container, false);

        return dialogView;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        getView().findViewById(R.id.container_button).setOnClickListener(v -> {
            ViewGroup container = (ViewGroup) getView().findViewById(R.id.content);
            Scene scene = Scene.getSceneForLayout(container, R.layout.playground_sheet2, getContext());

            Transition transition = TransitionInflater.from(getContext()).inflateTransition(R.transition.move);

            TransitionManager.go(scene, transition);
        });
    }

    public static PlaygroundBottomSheet newInstance() {
        return new PlaygroundBottomSheet();
    }
}
