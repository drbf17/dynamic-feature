package br.com.drbf17.pocmodule.card;

import android.content.Context;
import android.content.Intent;
import android.util.AttributeSet;
import android.util.Log;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;

import com.example.core.moduleManager.ModuleManager;
import br.com.drbf17.pocmodule.event.NewModuleEvent;
import com.example.ui.card.BaseCard;
import com.example.ui.card.CardState;
import com.google.android.play.core.splitinstall.SplitInstallSessionState;
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener;
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode;
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus;

import org.greenrobot.eventbus.EventBus;

import java.util.HashSet;
import java.util.Set;

import br.com.drbf17.pocmodule.R;

public class AddFeatureCard extends BaseCard implements SplitInstallStateUpdatedListener{

    private TextView labelState;
    private ProgressBar progressBar;

    private Integer mySessionId =0;
    private Button btnGoto;
    private Button btnOpen;

    public AddFeatureCard(Context context) {
        super(context);
    }

    public AddFeatureCard(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public AddFeatureCard(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
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
    public void onStateUpdate(SplitInstallSessionState state) {

        Log.d(this.getClass().getName(), "Status do update: " + state.status());
        if (state.status() == SplitInstallSessionStatus.FAILED
                && state.errorCode() == SplitInstallErrorCode.SERVICE_DIED) {
            Log.e(this.getClass().getName(), state.toString());
            return;
        }
        if (state.sessionId() == mySessionId) {
            switch (state.status()) {
                case SplitInstallSessionStatus.DOWNLOADING:
                    long totalBytes = state.totalBytesToDownload();
                    long progress = state.bytesDownloaded();
                    progressBar.setMax((int) totalBytes);
                    progressBar.setProgress( (int) progress);
                    break;

                case SplitInstallSessionStatus.INSTALLED:
                    clearDownloadMode();
                    EventBus.getDefault().post(new NewModuleEvent());

                    // After a module is installed, you can start accessing its content or
                    // fire an intent to start an activity in the installed module.
                    // For other use cases, see access code and resources from installed modules.

                    // If the request is an on demand module for an Android Instant App
                    // running on Android 8.0 (API level 26) or higher, you need to
                    // update the app context using the SplitInstallHelper API.
            }
        }
    }

    @Override
    public void setup() {
        inflate(this.getContext(), R.layout.add_card_feature, this);

        btnGoto = findViewById(R.id.btn_add_card);
        if (btnGoto != null) {
            btnGoto.setOnClickListener(view -> {

                btnGoto.setVisibility(INVISIBLE);
                progressBar.setVisibility(VISIBLE);

                Set<String> dynamicModuleNames = new HashSet<>();
                dynamicModuleNames.add("dynamic_feature_test");


                ModuleManager moduleManager = ModuleManager.create(getContext().getApplicationContext());
                moduleManager.downloadModules(dynamicModuleNames,

                        sessionId -> {
                            mySessionId = sessionId;
                            Log.e(this.getClass().getName(), "SessionID: " + sessionId);

                        },
                        error -> {


                            clearDownloadMode();
                            Log.e(this.getClass().getName(), error);

                        }, this


                );
            });

        }


        btnOpen = findViewById(R.id.btn_open_card);
        if (btnOpen != null) {
            btnOpen.setOnClickListener(view -> {


                Intent intent = new Intent();
                intent.setClassName("br.com.drbf17.pocmodule", "com.example.dynamic_feature_test.activity.FeatureDynamicActivity");
                getContext().getApplicationContext().startActivity(intent);
            });

        }

        progressBar = findViewById(R.id.progressBar);


    }

    private void clearDownloadMode() {
        btnGoto.setVisibility(VISIBLE);
        progressBar.setVisibility(INVISIBLE);
        progressBar.setMax(100);
        progressBar.setProgress(0);
    }

    @Override
        protected AddFeatureCard geInstance (Context context){
            AddFeatureCard instance = new AddFeatureCard(context);
            onFinishInflate();
            return instance;
        }

        @Override
        protected void onFinishInflate () {
            setup();
            super.onFinishInflate();
        }
    }
