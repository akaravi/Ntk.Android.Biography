package ntk.android.biography.activity;

public class ActSameMonth extends SameMonthActivity {

//    @BindView(R.id.lblTitleActSameMonth)
//    TextView LblTitle;
//
//    @BindView(R.id.recyclerActSameMonth)
//    RecyclerView Rv;
//
//    @BindView(R.id.mainLayoutActSameMonth)
//    CoordinatorLayout layout;
//
//    @BindView(R.id.swipRefreshActSameMonth)
//    SwipeRefreshLayout Refresh;
//
//    private int Total = 0;
//    private List<BiographyContent> biography = new ArrayList<>();
//    private AdBiography adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_same_month);
//        ButterKnife.bind(this);
//        init();
//    }
//
//    private void init() {
//        findViewById(R.id.rowProgressActSameMonth).setVisibility(View.VISIBLE);
//        LblTitle.setTypeface(FontManager.GetTypeface(this, ));
//
//        Rv.setHasFixedSize(true);
//        GridLayoutManager LMC = new GridLayoutManager(this, 2);
//
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
//            String Gregorian = EasyPreference.with(ActSameMonth.this).getString("BirthDay", "");
//            BiographyContentWithSimilarDatePeriodStartDayOfYearListRequest request = new BiographyContentWithSimilarDatePeriodStartDayOfYearListRequest();
//            request.DayOfYearMin = AppUtill.GetMinDayOfYear(AppUtill.GregorianToPersian(Gregorian), Gregorian);
//            request.DayOfYearMax = AppUtill.GetMaxDayOfYear(AppUtill.GregorianToPersian(Gregorian), Gregorian);
//
//            RetrofitManager manager = new RetrofitManager(ActSameMonth.this);
//            IBiography iBiography = manager.getRetrofitUnCached(new ConfigStaticValue(ActSameMonth.this).GetApiBaseUrl()).create(IBiography.class);
//
//            request.RowPerPage = 20;
//            request.CurrentPageNumber = i;
//
//            Observable<BiographyContentResponse> call = iBiography.GetContentWithSimilarDatePeriodStartDayOfYearList(new ConfigRestHeader().GetHeaders(this), request);
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
//                                findViewById(R.id.rowProgressActSameMonth).setVisibility(View.GONE);
//                                biography.addAll(response.ListItems);
//                                Total = response.TotalRowCount;
//                                adapter.notifyDataSetChanged();
//                            }
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            findViewById(R.id.rowProgressActSameMonth).setVisibility(View.GONE);
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
//            findViewById(R.id.rowProgressActSameMonth).setVisibility(View.GONE);
//            Snackbar.make(layout, R.string.per_no_net, Snackbar.LENGTH_INDEFINITE).setAction(R.string.try_again, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    init();
//                }
//            }).show();
//        }
//    }
//
//    @OnClick(R.id.imgBackActSameMonth)
//    public void ClickBack() {
//        finish();
//    }
}
