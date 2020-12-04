package ntk.android.biography.activity;

import ntk.android.base.activity.poling.PolingActivity;

public class ActPooling extends PolingActivity {

//    @BindView(R.id.lblTitleActPooling)
//    TextView LblTitle;
//
//    @BindView(R.id.recyclerPooling)
//    RecyclerView Rv;
//
//    @BindView(R.id.mainLayoutActPooling)
//    CoordinatorLayout layout;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_pooling);
//        ButterKnife.bind(this);
//        init();
//    }
//
//    private void init() {
//        if (AppUtill.isNetworkAvailable(this)) {
//            LblTitle.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
//            Rv.setHasFixedSize(true);
//            Rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
//            RetrofitManager manager = new RetrofitManager(this);
//            IPooling iPooling = manager.getRetrofitUnCached(new ConfigStaticValue(this).GetApiBaseUrl()).create(IPooling.class);
//            Observable<PoolingCategoryResponse> call = iPooling.GetCategoryList(new ConfigRestHeader().GetHeaders(this));
//            call.observeOn(AndroidSchedulers.mainThread())
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(new Observer<PoolingCategoryResponse>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(PoolingCategoryResponse poolingCategoryResponse) {
//                            if (poolingCategoryResponse.IsSuccess) {
//                                AdPoolCategory adapter = new AdPoolCategory(ActPooling.this, poolingCategoryResponse.ListItems);
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
//
//    @OnClick(R.id.imgBackActPooling)
//    public void ClickBack() {
//        finish();
//    }
}
