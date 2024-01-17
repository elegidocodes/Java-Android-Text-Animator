package com.elegidocodes.java.android.text.animator;

import android.animation.ValueAnimator;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;

import androidx.annotation.Nullable;

/**
 * Custom TextView subclass that provides the ability to animate text content.
 * This class extends androidx.appcompat.widget.AppCompatTextView.
 */
public class AnimatedText extends androidx.appcompat.widget.AppCompatTextView {

    private ValueAnimator textAnimator; // Reference to the animator

    /**
     * Constructor for creating an AnimatedText instance.
     *
     * @param context The context in which this view will be used.
     */
    public AnimatedText(Context context) {
        super(context);
    }

    /**
     * Constructor for creating an AnimatedText instance with attributes.
     *
     * @param context The context in which this view will be used.
     * @param attrs   The attribute set containing the view's attributes.
     */
    public AnimatedText(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    /**
     * Constructor for creating an AnimatedText instance with attributes and a style.
     *
     * @param context      The context in which this view will be used.
     * @param attrs        The attribute set containing the view's attributes.
     * @param defStyleAttr An attribute from the current theme.
     */
    public AnimatedText(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     * Sets up scroll animation for a single line of text.
     *
     * @param repeatCount The number of times the animation should repeat.
     */
    public void setScrollAnimationInSingleLine(int repeatCount) {
        this.setSelected(true);
        this.setSingleLine(true);
        this.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        this.setMarqueeRepeatLimit(repeatCount);
    }

    /**
     * Starts the text animation with the provided text, duration, and animation listener.
     *
     * @param animText            The text to animate.
     * @param duration            The duration of the animation in milliseconds.
     * @param onAnimationListener A listener to handle animation start and end events.
     */
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

    /**
     * Called when this view is detached from the window.
     * Cancels the text animation if it is running.
     */
    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        if (textAnimator != null) {
            textAnimator.cancel(); // Cancel the animation
        }
    }

    /**
     * Interface for handling animation start and end events.
     */
    interface OnAnimationListener {
        /**
         * Callback when the animation starts.
         */
        void onStartAnimation();

        /**
         * Callback when the animation ends.
         */
        void onEndAnimation();
    }
}

