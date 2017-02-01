package com.saulmm.cui;

import android.content.Intent;
import android.support.transition.ChangeBounds;
import android.support.transition.Fade;
import android.support.transition.Scene;
import android.support.transition.Transition;
import android.support.transition.TransitionManager;
import android.support.transition.TransitionSet;
import android.support.v4.view.ViewCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.transition.TransitionInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.DecelerateInterpolator;
import android.widget.Button;

public class Playground extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_playground);

        final ViewGroup sceneRoot = (ViewGroup) findViewById(R.id.container_scene);
        final ViewGroup firstSceneViewGroup = (ViewGroup) findViewById(R.id.form_order_step1);

        final Transition fade = new Fade();
        fade.setDuration(100);

        final Scene secondScene = Scene.getSceneForLayout(sceneRoot, R.layout.layout_form_order_step2, this);
        secondScene.setEnterAction(new Runnable() {
            @Override
            public void run() {
                secondScene.getSceneRoot()
                    .setTranslationX(secondScene.getSceneRoot().getWidth());

                ViewCompat.animate(secondScene.getSceneRoot())
                    .setDuration(200)
                    .setInterpolator(new DecelerateInterpolator(1.5f))
                    .translationX(0);
            }
        });

        findViewById(R.id.startButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ViewCompat.animate(firstSceneViewGroup)
                    .alpha(0)
                    .setInterpolator(new AccelerateInterpolator(1.5f))
                    .setDuration(100)
                    .withEndAction(new Runnable() {
                        @Override
                        public void run() {
                            TransitionManager.go(secondScene, null);
                        }
                    });


            }
        });

        findViewById(R.id.restart).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Playground.this, Playground.class));
            }
        });
    }
}
