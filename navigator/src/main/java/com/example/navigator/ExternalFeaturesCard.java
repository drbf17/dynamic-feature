package com.example.navigator;

public enum ExternalFeaturesCard {

    FEATURE_ONE(0,"com.example.feature_one.card.FeatureOneCard", null),
    FEATURE_TWO(1,"com.example.feature_two.card.FeatureTwoCard", null),
    FEATURE_THREE(2,"br.com.drbf17.pocmodule.card.AddFeatureCard", null),
    FEATURE_FOUR(3,"com.XXX.pocmodule.card.AddFeatureCardl", "naotem"),
    FEATURE_FIVE(4,"com.example.card_module.card.DynamicCard", "dynamic_feature_test"),
    ;

    private final String card;
    private final int id;
    private final String dynamicModuleName;

    ExternalFeaturesCard(int id, String card, String dynamicModuleName ){
        this.id = id;
        this.card = card;
        this.dynamicModuleName = dynamicModuleName;
    }


    public int getId() {
        return id;
    }

    public String getCard() {
        return card;
    }

    public String getDynamicModuleName() {return dynamicModuleName;}
}
