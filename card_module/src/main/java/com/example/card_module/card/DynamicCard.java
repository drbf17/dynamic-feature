package com.example.card_module.card;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;


import com.example.card_module.R;
import com.example.ui.card.BaseCard;
import com.example.ui.card.CardState;

public class DynamicCard extends BaseCard {

    private TextView labelState;
    private TextView erro;

    public DynamicCard(Context context) {
        super(context);
    }

    public DynamicCard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public DynamicCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
        inflate(this.getContext(), R.layout.card_feature_dynamic, this);


        Button btnGoto = findViewById(R.id.btn_goto_two);

        if (btnGoto != null) {
            btnGoto.setOnClickListener(view -> {

                try {
                  /*  boolean a = true;
                    if (a)
                        throw new Exception("Errado para cacete");*/
                    Class<?> cls = Class.forName("com.example.dynamic_feature_test.activity.FeatureDynamicActivity");


                    Intent intent = new Intent();
                    intent.setClass(this.getContext(), cls);
                    getContext().getApplicationContext().startActivity(intent);

                } catch (Exception e) {

                    erro = findViewById(R.id.tv_description);
                    erro.setText("Erro " + e.toString());


                }


            });
        }


        TextView labelTitle = findViewById(R.id.label_title_two);
        labelState = findViewById(R.id.label_state_dynamic);
        if (labelTitle != null && labelState != null) {
            labelTitle.setText(R.string.label_title_dynamic);
            labelTitle.setOnClickListener(v -> {
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
    protected DynamicCard geInstance(Context context) {
        DynamicCard instance = new DynamicCard(context);
        onFinishInflate();
        return instance;
    }

    @Override
    protected void onFinishInflate() {
        setup();
        super.onFinishInflate();
    }
}
