package ntk.android.biography.activity;

public class ActSameDay extends SameDayActivity {

//    @BindView(R.id.lblTitleActSameDay)
//    TextView LblTitle;
//
//    @BindView(R.id.recyclerActSameDay)
//    RecyclerView Rv;
//
//    @BindView(R.id.mainLayoutActSameDay)
//    CoordinatorLayout layout;
//
//    @BindView(R.id.swipRefreshActSameDay)
//    SwipeRefreshLayout Refresh;
//
//    private int Total = 0;
//    private List<BiographyContent> biography = new ArrayList<>();
//    private AdBiography adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_same_day);
//        ButterKnife.bind(this);
//        init();
//    }
//
//    private void init() {
//        findViewById(R.id.rowProgressActSameDay).setVisibility(View.VISIBLE);
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
//            String[] date = EasyPreference.with(ActSameDay.this).getString("BirthDay", "").split("/");
//            BiographyContentWithSimilarDatePeriodStartDayAndMonthOfYearListRequest model = new BiographyContentWithSimilarDatePeriodStartDayAndMonthOfYearListRequest();
//            model.MonthOfYear = Integer.parseInt(date[1]);
//            model.DayOfMonth = Integer.parseInt(date[2]);
//            model.RowPerPage = 20;
//            model.CurrentPageNumber = i;
//
//            RetrofitManager manager = new RetrofitManager(ActSameDay.this);
//            IBiography iBiography = manager.getRetrofitUnCached(new ConfigStaticValue(ActSameDay.this).GetApiBaseUrl()).create(IBiography.class);
//
//            Observable<BiographyContentResponse> call = iBiography.GetContentWithSimilarDatePeriodStartDayAndMonthOfYearList(new ConfigRestHeader().GetHeaders(this), model);
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
//                                findViewById(R.id.rowProgressActSameDay).setVisibility(View.GONE);
//                                biography.addAll(response.ListItems);
//                                Total = response.TotalRowCount;
//                                adapter.notifyDataSetChanged();
//                            }
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            findViewById(R.id.rowProgressActSameDay).setVisibility(View.GONE);
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
//            findViewById(R.id.rowProgressActSameDay).setVisibility(View.GONE);
//            Snackbar.make(layout, R.string.per_no_net, Snackbar.LENGTH_INDEFINITE).setAction(R.string.try_again, new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    init();
//                }
//            }).show();
//        }
//    }
//
//    @OnClick(R.id.imgBackActSameDay)
//    public void ClickBack() {
//        finish();
//    }
}
