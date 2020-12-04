package ntk.android.biography.activity;

import ntk.android.base.activity.ticketing.FaqActivity;

public class ActFaq extends FaqActivity {

//    @BindView(R.id.lblTitleActFaq)
//    TextView Lbl;
//
//    @BindView(R.id.recyclerFaq)
//    RecyclerView Rv;
//
//    @BindView(R.id.mainLayoutActFaq)
//    CoordinatorLayout layout;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_faq);
//        ButterKnife.bind(this);
//        init();
//    }
//
//    private void init() {
//        if (AppUtill.isNetworkAvailable(this)) {
//            Lbl.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
//            Lbl.setText("پرسش های متداول");
//
//            Rv.setHasFixedSize(true);
//            Rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//
//            RetrofitManager retro = new RetrofitManager(this);
//            ITicket iTicket = retro.getCachedRetrofit(new ConfigStaticValue(this).GetApiBaseUrl()).create(ITicket.class);
//            Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);
//
//            TicketingFaqListRequest request = new TicketingFaqListRequest();
//            request.RowPerPage = 100;
//
//            Observable<TicketingFaqListResponse> Call = iTicket.GetTicketFaqList(headers, request);
//            Call.subscribeOn(Schedulers.io())
//                    .observeOn(AndroidSchedulers.mainThread())
//                    .subscribe(new Observer<TicketingFaqListResponse>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(TicketingFaqListResponse model) {
//                            AdFaq adapter = new AdFaq(ActFaq.this, model.ListItems);
//                            Rv.setAdapter(adapter);
//                            adapter.notifyDataSetChanged();
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            Snackbar.make(layout, "خطای سامانه مجددا تلاش کنید", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
//                                @Override
//                                public void onClick(View v) {
//                                    init();
//                                }
//                            }).show();
//
//                        }
//
//                        @Override
//                        public void onComplete() {
//
//                        }
//                    });
//        } else {
//            Snackbar.make(layout, "عدم دسترسی به اینترنت", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    init();
//                }
//            }).show();
//        }
//    }
//
//    @OnClick(R.id.imgBackActFaq)
//    public void ClickBack() {
//        finish();
//    }
}
