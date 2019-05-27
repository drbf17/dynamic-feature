package com.example.navigator;

public enum ExternalFeaturesURL {

    FEATURE_ONE(0,"com.example.feature_one.activity.FeatureOneActivity"),
    FEATURE_TWO(1,"com.example.feature_two.activity.FeatureTwoActivity"),
        ;

    private final String activity;
    private final int id;

    ExternalFeaturesURL(int id, String activity){
        this.id = id;
        this.activity = activity;
    }


    public int getId() {
        return id;
    }

    public String getActivity() {
        return activity;
    }
}
