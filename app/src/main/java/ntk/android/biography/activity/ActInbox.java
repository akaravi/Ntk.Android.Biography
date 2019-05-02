package ntk.android.biography.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import ntk.android.biography.Biography;
import ntk.android.biography.R;
import ntk.android.biography.adapter.AdInbox;
import ntk.android.biography.event.EvNotify;
import ntk.android.biography.model.Notify;
import ntk.android.biography.room.RoomDb;

public class ActInbox extends AppCompatActivity {

    @BindView(R.id.recyclerInbox)
    RecyclerView Rv;

    private ArrayList<Notify> notifies = new ArrayList<>();
    private AdInbox adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_inbox);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Rv.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        Rv.setLayoutManager(manager);
        notifies.addAll(RoomDb.getRoomDb(this).NotificationDoa().All());
        if (notifies.size() == 0) {
            Toast.makeText(this, "پیامی برای نمایش وجود ندارد", Toast.LENGTH_SHORT).show();
            finish();
        } else {
            adapter = new AdInbox(this, notifies);
            Rv.post(() -> {
                Rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            });
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        Biography.Inbox = true;
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        Biography.Inbox = false;
        EventBus.getDefault().unregister(this);
    }

    @Subscribe
    public void SetDataChange(EvNotify event) {
        if (event.DataChange()) {
            notifies.clear();
            notifies.addAll(RoomDb.getRoomDb(ActInbox.this).NotificationDoa().All());
            Rv.post(() -> adapter.notifyDataSetChanged());
        }
    }

    @OnClick(R.id.imgBackActInbox)
    public void ClickBack() {
        finish();
    }
}
