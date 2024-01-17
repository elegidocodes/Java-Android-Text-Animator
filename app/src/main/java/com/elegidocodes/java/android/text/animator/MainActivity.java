package com.elegidocodes.java.android.text.animator;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    boolean nextText = false;
    CharSequence charSequence;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnimatedText animatedText = findViewById(R.id.text);
        //animatedText.setScrollAnimationInSingleLine(5);

        Button button = findViewById(R.id.button);
        button.setOnClickListener(v -> {

            if (nextText) {
                charSequence = getText(R.string.lorem_2);
                nextText = false;
            } else {
                charSequence = getText(R.string.lorem);
                nextText = true;
            }

            animatedText.startAnimation(charSequence, 6000, new AnimatedText.OnAnimationListener() {
                @Override
                public void onStartAnimation() {
                    // Code to execute when the animation starts
                }

                @Override
                public void onEndAnimation() {
                    // Code to execute when the animation ends
                }
            });
        });

    }

}