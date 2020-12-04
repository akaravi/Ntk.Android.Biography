package ntk.android.biography.activity;

import ntk.android.base.activity.common.NotificationsActivity;

public class ActInbox extends NotificationsActivity {
}
//    @BindView(R.id.recyclerInbox)
//    RecyclerView Rv;
//
//    private ArrayList<Notify> notifies = new ArrayList<>();
//    private AdInbox adapter;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_inbox);
//        ButterKnife.bind(this);
//        init();
//    }
//
//    private void init() {
//        Rv.setHasFixedSize(true);
//        RecyclerView.LayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
//        Rv.setLayoutManager(manager);
//        notifies.addAll(RoomDb.getRoomDb(this).NotificationDoa().All());
//        if (notifies.size() == 0) {
//            Toast.makeText(this, "پیامی برای نمایش وجود ندارد", Toast.LENGTH_SHORT).show();
//            finish();
//        } else {
//            adapter = new AdInbox(this, notifies);
//            Rv.post(() -> {
//                Rv.setAdapter(adapter);
//                adapter.notifyDataSetChanged();
//            });
//        }
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        MyApplication.Inbox = true;
//        EventBus.getDefault().register(this);
//    }
//
//    @Override
//    protected void onStop() {
//        super.onStop();
//        MyApplication.Inbox = false;
//        EventBus.getDefault().unregister(this);
//    }
//
//    @Subscribe
//    public void SetDataChange(EvNotify event) {
//        if (event.DataChange()) {
//            notifies.clear();
//            notifies.addAll(RoomDb.getRoomDb(ActInbox.this).NotificationDoa().All());
//            Rv.post(() -> adapter.notifyDataSetChanged());
//        }
//    }
//
//    @OnClick(R.id.imgBackActInbox)
//    public void ClickBack() {
//        finish();
//    }
//}
