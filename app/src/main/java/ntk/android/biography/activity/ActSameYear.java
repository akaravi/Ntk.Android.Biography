package ntk.android.biography.activity;

public class ActSameYear extends SameYearActivity {
//
//    @BindView(R.id.lblTitleActSameYear)
//    TextView LblTitle;
//
//    @BindView(R.id.recyclerActSameYear)
//    RecyclerView Rv;
//
//    @BindView(R.id.mainLayoutActSameYear)
//    CoordinatorLayout layout;
//
//    @BindView(R.id.swipRefreshActSameYear)
//    SwipeRefreshLayout Refresh;
//
//    private int Total = 0;
//    private List<BiographyContent> biography = new ArrayList<>();
//    private AdBiography adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_same_year);
//        ButterKnife.bind(this);
//        init();
//    }
//
//    private void init() {
//        findViewById(R.id.rowProgressActSameYear).setVisibility(View.VISIBLE);
//        LblTitle.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
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
//            String Gregorian = EasyPreference.with(ActSameYear.this).getString("BirthDay", "");
//            BiographyContentWithDatePeriodStartListRequest request = new BiographyContentWithDatePeriodStartListRequest();
//            request.SearchDateMin = AppUtill.GetMinOfYear(AppUtill.GregorianToPersian(Gregorian));
//            request.SearchDateMax = AppUtill.GetMaxOfYear(AppUtill.GregorianToPersian(Gregorian));
//
//            RetrofitManager manager = new RetrofitManager(ActSameYear.this);
//            IBiography iBiography = manager.getRetrofitUnCached(new ConfigStaticValue(ActSameYear.this).GetApiBaseUrl()).create(IBiography.class);
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
//                                findViewById(R.id.rowProgressActSameYear).setVisibility(View.GONE);
//                                biography.addAll(response.ListItems);
//                                Total = response.TotalRowCount;
//                                adapter.notifyDataSetChanged();
//                            }
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            findViewById(R.id.rowProgressActSameYear).setVisibility(View.GONE);
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
//            findViewById(R.id.rowProgressActSameYear).setVisibility(View.GONE);
//            Snackbar.make(layout, "عدم دسترسی به اینترنت", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    init();
//                }
//            }).show();
//        }
//    }
//
//    @OnClick(R.id.imgBackActSameYear)
//    public void ClickBack() {
//        finish();
//    }
}
