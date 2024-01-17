package com.elegidocodes.java.android.text.animator;

import android.animation.ValueAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;

public class AnimatedText extends androidx.appcompat.widget.AppCompatTextView {

    private ValueAnimator textAnimator; // Reference to the animator

    public AnimatedText(Context context) {
        super(context);
    }

    public AnimatedText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AnimatedText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public void setScrollAnimationInSingleLine(int repeatCount) {
        this.setSelected(true);
        this.setSingleLine(true);
        this.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        this.setMarqueeRepeatLimit(repeatCount);
    }

    public void startAnimation(CharSequence animText, int duration, OnAnimationListener onAnimationListener) {

        // Use the provided text for animation
        int numberOfLetters = animText.length();

        // Log the total number of letters
        Log.d(getClass().getName(), String.valueOf(numberOfLetters));

        // Notify animation start
        if (onAnimationListener != null) {
            onAnimationListener.onStartAnimation();
        }

        // Reset the text to an empty string
        this.setText("");

        // Use the provided duration for the animation
        textAnimator = ValueAnimator.ofInt(0, numberOfLetters);
        textAnimator.setDuration(duration);
        textAnimator.addUpdateListener(animation -> {
            int index = (int) animation.getAnimatedValue();
            this.setText(animText.subSequence(0, index));

            // Check if this is the last letter, and notify the listener
            if (index == numberOfLetters && onAnimationListener != null) {
                onAnimationListener.onEndAnimation();
            }
        });
        textAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (textAnimator != null) {
            textAnimator.cancel(); // Cancel the animation
        }
    }

    interface OnAnimationListener {
        void onStartAnimation();

        void onEndAnimation();
    }

}
