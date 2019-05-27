package com.example.core.card;

import android.content.Context;

import com.example.core.moduleManager.ModuleManager;
import com.example.navigator.ExternalFeaturesCard;
import com.example.ui.card.BaseCard;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

public class CardList {
    private static final CardList instance = new CardList();

    public CardList() {

    }

    public static CardList getInstance() {
        return instance;
    }

    public List<Class> getCards(Context context) {
        List<Class> cards = new ArrayList<>();
        ModuleManager moduleManager = ModuleManager.create(context);
        for (ExternalFeaturesCard card : ExternalFeaturesCard.values()) {
            Class<?> cls;
            try {
                if (card.getDynamicModuleName() == null || moduleManager.isDynamicModuleLoaded(card.getDynamicModuleName())) {
                    cls = Class.forName(card.getCard()).asSubclass(BaseCard.class);
                    cards.add(cls);
                }
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return cards;
    }
}
