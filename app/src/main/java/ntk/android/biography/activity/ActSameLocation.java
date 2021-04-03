package ntk.android.biography.activity;

public class ActSameLocation extends SameLocationActivity {
//
//    @BindView(R.id.lblTitleActSameLocation)
//    TextView LblTitle;
//
//    @BindView(R.id.recyclerActSameLocation)
//    RecyclerView Rv;
//
//    @BindView(R.id.mainLayoutActSameLocation)
//    CoordinatorLayout layout;
//
//    @BindView(R.id.swipRefreshActSameLocation)
//    SwipeRefreshLayout Refresh;
//
//    private int Total = 0;
//    private List<BiographyContent> biography = new ArrayList<>();
//    private AdBiography adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_same_location);
//        ButterKnife.bind(this);
//        init();
//    }
//
//    private void init() {
//        findViewById(R.id.rowProgressActSameLocation).setVisibility(View.VISIBLE);
//        LblTitle.setTypeface(FontManager.GetTypeface(this, ));
//        Rv.setHasFixedSize(true);
//        GridLayoutManager LMC = new GridLayoutManager(this, 2);
//        Rv.setLayoutManager(LMC);
//        adapter = new AdBiography(this, biography);
//        Rv.setAdapter(adapter);
//
//        Refresh.setColorSchemeResources(
//                R.color.colorAccent,
//                R.color.colorAccent,
//                R.color.colorAccent);
//
//        Refresh.setOnRefreshListener(() -> {
//            biography.clear();
//            init();
//            Refresh.setRefreshing(false);
//        });
//
//        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(LMC) {
//
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                if (totalItemsCount <= Total) {
//                    RestCall((page + 1));
//                }
//            }
//        };
//        Rv.addOnScrollListener(scrollListener);
//
//        RestCall(1);
//    }
//
//    private void RestCall(int i) {
//        if (AppUtill.isNetworkAvailable(this)) {
//            String Gregorian = EasyPreference.with(ActSameLocation.this).getString("BirthDay", "");
//            BiographyContentWithDatePeriodStartListRequest request = new BiographyContentWithDatePeriodStartListRequest();
//            request.SearchDateMin = AppUtill.GetMinOfYear(AppUtill.GregorianToPersian(Gregorian));
//            request.SearchDateMax = AppUtill.GetMaxOfYear(AppUtill.GregorianToPersian(Gregorian));
//
//            RetrofitManager manager = new RetrofitManager(ActSameLocation.this);
//            IBiography iBiography = manager.getRetrofitUnCached(new ConfigStaticValue(ActSameLocation.this).GetApiBaseUrl()).create(IBiography.class);
//
//            request.RowPerPage = 20;
//            request.CurrentPageNumber = i;
//
//            Observable<BiographyContentResponse> call = iBiography.GetContentWithDatePeriodStartList(new ConfigRestHeader().GetHeaders(this), request);
//            call.observeOn(AndroidSchedulers.mainThread())
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(new Observer<BiographyContentResponse>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(BiographyContentResponse response) {
//                            if (response.IsSuccess) {
//                                findViewById(R.id.rowProgressActSameLocation).setVisibility(View.GONE);
//                                biography.addAll(response.ListItems);
//                                Total = response.TotalRowCount;
//                                adapter.notifyDataSetChanged();
//                            }
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            findViewById(R.id.rowProgressActSameLocation).setVisibility(View.GONE);
//                            Snackbar.make(layout, "خطای سامانه مجددا تلاش کنید", Snackbar.LENGTH_INDEFINITE).setAction(R.string.try_again, new View.OnClickListener() {
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
//            findViewById(R.id.rowProgressActSameLocation).setVisibility(View.GONE);
//            Snackbar.make(layout, R.string.per_no_net, Snackbar.LENGTH_INDEFINITE).setAction(R.string.try_again, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    init();
//                }
//            }).show();
//        }
//    }
//
//    @OnClick(R.id.imgBackActSameLocation)
//    public void ClickBack() {
//        finish();
//    }
}
