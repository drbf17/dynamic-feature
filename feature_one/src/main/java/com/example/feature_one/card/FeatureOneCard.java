package com.example.feature_one.card;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import com.example.feature_one.R;
import com.example.feature_one.activity.FeatureOneActivity;
import com.example.ui.card.BaseCard;
import com.example.ui.card.CardState;

import androidx.annotation.Nullable;

public class FeatureOneCard extends BaseCard {

    private TextView labelState;

    public FeatureOneCard(Context context) {
        super(context);
    }

    public FeatureOneCard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FeatureOneCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public void closeCard() {
        if (labelState != null) {
            state = CardState.CLOSED;
            labelState.setText(R.string.state_closed);
        }
    }

    @Override
    public void setup() {
        inflate(this.getContext(), R.layout.card_feature_one, this);
        Button btnGoto = findViewById(R.id.btn_goto_one);
        if (btnGoto != null) {
            btnGoto.setOnClickListener(view -> getContext().startActivity(new Intent(getContext(), FeatureOneActivity.class)));
        }
        TextView labelTitle = findViewById(R.id.label_title_one);
        labelState = findViewById(R.id.label_state_one);
        if (labelTitle != null && labelState != null) {
            labelTitle.setText(R.string.label_title_one);
            labelTitle.setOnClickListener( v -> {
                if (listener != null) {
                    listener.openedCard(this);
                    if (state == CardState.CLOSED) {
                        state = CardState.OPENED;
                        labelState.setText(R.string.state_opened);
                    } else {
                        state = CardState.CLOSED;
                        labelState.setText(R.string.state_closed);
                    }
                }
            });
        }
    }

    @Override
    protected FeatureOneCard geInstance(Context context) {
        FeatureOneCard instance = new FeatureOneCard(context);
        onFinishInflate();
        return instance;
    }

    @Override
    protected void onFinishInflate() {
        setup();
        super.onFinishInflate();
    }
}
