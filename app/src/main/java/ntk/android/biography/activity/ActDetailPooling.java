package ntk.android.biography.activity;

import ntk.android.base.activity.poling.PolingDetailActivity;

public class ActDetailPooling extends PolingDetailActivity {
//
//    @BindView(R.id.lblTitleActDetailPooling)
//    TextView LblTitle;
//
//    @BindView(R.id.recyclerDetailPooling)
//    RecyclerView Rv;
//
//    @BindView(R.id.mainLayoutActDetailPooling)
//    CoordinatorLayout layout;
//
//    private String RequestStr;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_detail_pooling);
//        ButterKnife.bind(this);
//        init();
//    }
//
//    private void init() {
//        LblTitle.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
//        LblTitle.setText(getIntent().getStringExtra("Title"));
//        Rv.setHasFixedSize(true);
//        Rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//
//
//        RequestStr = getIntent().getExtras().getString("Request");
//
//        HandelData(1, new Gson().fromJson(RequestStr, PoolingContentListRequest.class));
//    }
//
//    private void HandelData(int i, PoolingContentListRequest request) {
//        if (AppUtill.isNetworkAvailable(this)) {
//            RetrofitManager retro = new RetrofitManager(this);
//            IPooling iPooling = retro.getRetrofitUnCached(new ConfigStaticValue(this).GetApiBaseUrl()).create(IPooling.class);
//            Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);
//            Observable<PoolingContentListResponse> call = iPooling.GetContentList(headers, request);
//            call.observeOn(AndroidSchedulers.mainThread())
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(new Observer<PoolingContentListResponse>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(PoolingContentListResponse poolingContentListResponse) {
//                            if (poolingContentListResponse.IsSuccess) {
//                                AdDetailPoolCategory adapter = new AdDetailPoolCategory(ActDetailPooling.this, poolingContentListResponse.ListItems);
//                                Rv.setAdapter(adapter);
//                                adapter.notifyDataSetChanged();
//                            }
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
//    @OnClick(R.id.imgBackActDetailPooling)
//    public void ClickBack() {
//        finish();
//    }
}
