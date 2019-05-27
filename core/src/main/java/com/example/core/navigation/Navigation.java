package com.example.core.navigation;

import android.app.Activity;
import android.content.Intent;

import com.example.navigator.ExternalFeaturesURL;

public class Navigation {

    private static final Navigation instance = new Navigation();

    private Navigation(){

    }

    public static Navigation getInstance(){
        return instance;
    }

    public Intent getIntentDetail(Activity activity, ExternalFeaturesURL externalFeatures){
        try {
            return new Intent(activity, Class.forName(externalFeatures.getActivity()).asSubclass(Activity.class));
        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
