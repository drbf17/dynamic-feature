package com.example.feature_two.card;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import com.example.feature_two.R;
import com.example.feature_two.activity.FeatureTwoActivity;
import com.example.ui.card.BaseCard;
import com.example.ui.card.CardState;

import androidx.annotation.Nullable;

public class FeatureTwoCard extends BaseCard {

    private TextView labelState;

    public FeatureTwoCard(Context context) {
        super(context);
    }

    public FeatureTwoCard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public FeatureTwoCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        inflate(this.getContext(), R.layout.card_feature_two, this);
        Button btnGoto = findViewById(R.id.btn_goto_two);
        if (btnGoto != null) {
            btnGoto.setOnClickListener(view -> getContext().startActivity(new Intent(getContext(), FeatureTwoActivity.class)));
        }
        TextView labelTitle = findViewById(R.id.label_title_two);
        labelState = findViewById(R.id.label_state_two);
        if (labelTitle != null && labelState != null) {
            labelTitle.setText(R.string.label_title_two);
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
    protected FeatureTwoCard geInstance(Context context) {
        FeatureTwoCard instance = new FeatureTwoCard(context);
        onFinishInflate();
        return instance;
    }

    @Override
    protected void onFinishInflate() {
        setup();
        super.onFinishInflate();
    }
}
