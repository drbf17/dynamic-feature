package br.com.drbf17.pocmodule;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import com.example.core.card.CardList;
import br.com.drbf17.pocmodule.event.NewModuleEvent;
import com.example.ui.card.BaseCard;
import com.example.ui.card.CardListener;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

public class MainActivity extends AppCompatActivity implements CardListener {

    private ListCardAdapter adapter;
    private RecyclerView listMain;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listMain = findViewById(R.id.list_main);
        this.adapter = new ListCardAdapter(this);
        listMain.setLayoutManager(new LinearLayoutManager(this, RecyclerView.VERTICAL, false));
        listMain.setAdapter(adapter);
        adapter.setCards(CardList.getInstance().getCards(this));
        EventBus.getDefault().register(this);

    }

    @Override
    public void openedCard(BaseCard card) {
        adapter.closeCards(card);
    }

    @Subscribe
    public void newModule(NewModuleEvent newModuleEvent) {
        Log.d(this.getClass().getName(), "Evento");

        adapter.setCards(CardList.getInstance().getCards(this.getApplicationContext()));
        adapter.notifyDataSetChanged();


    }

    @Override
    protected void onDestroy() {
        EventBus.getDefault().unregister(this);
    }
}
