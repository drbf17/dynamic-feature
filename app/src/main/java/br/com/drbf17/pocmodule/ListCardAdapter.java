package br.com.drbf17.pocmodule;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import com.example.ui.card.BaseCard;
import com.example.ui.card.CardListener;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ListCardAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Class> cards = new ArrayList<>();
    private CardListener listener;
    private List<BaseCard> baseCards = new ArrayList<>();

    public ListCardAdapter(CardListener listener) {
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        try {
            BaseCard card = (BaseCard) cards.get(viewType).getConstructor(Context.class).newInstance(parent.getContext());
            card.setPosition(viewType);
            RecyclerView.LayoutParams lp = new RecyclerView.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            card.setLayoutParams(lp);
            card.setListener(listener);
            if (!baseCards.contains(card)) {
                baseCards.add(card);
            }
            return new ListCardViewHolder(card);
        } catch (IllegalAccessException | InstantiationException | InvocationTargetException | NoSuchMethodException e) {
            e.printStackTrace();
        }
        return new EmptyViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_empty, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
    }

    @Override
    public int getItemCount() {
        return cards.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    public void setCards(List<Class> cards) {
        this.cards = cards;
        notifyDataSetChanged();
    }

    public void closeCards(BaseCard clickedCard) {
        for (BaseCard card : baseCards) {
            if (!card.equals(clickedCard)) {
                card.closeCard();
            }
        }
    }
}
