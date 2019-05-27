package com.example.ui.card;

import android.content.Context;
import android.util.AttributeSet;
import android.view.animation.AnimationUtils;
import android.widget.LinearLayout;

import com.example.ui.R;

import androidx.annotation.Nullable;

public abstract class BaseCard extends LinearLayout implements CardHandler {

    protected CardListener listener;
    private int position;
    protected CardState state = CardState.CLOSED;

    public BaseCard(Context context) {
        super(context);
        setup();
    }

    public BaseCard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        setup();
    }

    public BaseCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setup();
    }

    public BaseCard(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setup();
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public void setListener(CardListener listener) {
        this.listener = listener;
    }

    public void animateCard() {
        int delay = 250;
        if (position == 0) {
            delay = 150;
        }
        delay = (position + 1) * delay;

        postDelayed(() -> startAnimation(AnimationUtils.loadAnimation(getContext(), R.anim.bounce_left)), delay);
    }

    @Override
    public abstract void closeCard();

    public abstract void setup();

    protected abstract BaseCard geInstance(Context context);
}

