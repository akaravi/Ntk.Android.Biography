package ntk.android.biography.activity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import butterknife.OnClick;
import io.reactivex.Observable;
import java9.util.function.Function;
import ntk.android.base.activity.common.BaseFilterModelListActivity;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterModel;
import ntk.android.base.entitymodel.news.NewsContentModel;
import ntk.android.base.services.news.NewsContentService;
import ntk.android.biography.R;
import ntk.android.biography.adapter.NewsAdapter;

public class NewsListActivity extends BaseFilterModelListActivity<NewsContentModel> {

    @Override
    public RecyclerView.LayoutManager getRvLayoutManager() {
        return new GridLayoutManager(this, 2);
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return new NewsAdapter(this, models);
    }

    @OnClick(R.id.imgBackActNews)
    public void ClickBack() {
        finish();
    }

    @Override
    public void ClickSearch() {

    }

    @Override
    public Function<FilterModel, Observable<ErrorException<NewsContentModel>>> getService() {
        return new NewsContentService(this)::getAll;
    }

//    @BindView(R.id.lblTitleActNews)
//    TextView LblTitle;
//
//    @BindView(R.id.recyclerNews)
//    RecyclerView Rv;
//
//    @BindView(R.id.mainLayoutActNews)
//    CoordinatorLayout layout;
//
//    @BindView(R.id.swipRefreshActNews)
//    SwipeRefreshLayout Refresh;
//
//    private int Total = 0;
//    private List<NewsContent> news = new ArrayList<>();
//    private AdNews adapter;
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_news);
//        ButterKnife.bind(this);
//        init();
//    }
//
//    private void init() {
//        findViewById(R.id.rowProgressActNews).setVisibility(View.VISIBLE);
//        LblTitle.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
//        Rv.setHasFixedSize(true);
//        GridLayoutManager LMC = new GridLayoutManager(this, 2);
//        Rv.setLayoutManager(LMC);
//        adapter = new AdNews(this, news);
//        Rv.setAdapter(adapter);
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
//
//        Refresh.setOnRefreshListener(() -> {
//            news.clear();
//            init();
//            Refresh.setRefreshing(false);
//        });
//    }
//
//    private void RestCall(int i) {
//        if (AppUtill.isNetworkAvailable(this)) {
//            RetrofitManager manager = new RetrofitManager(this);
//            INews iNews = manager.getRetrofitUnCached(new ConfigStaticValue(this).GetApiBaseUrl()).create(INews.class);
//            NewsContentListRequest request = new NewsContentListRequest();
//            request.RowPerPage = 20;
//            request.CurrentPageNumber = i;
//            Observable<NewsContentResponse> call = iNews.GetContentList(new ConfigRestHeader().GetHeaders(this), request);
//            call.observeOn(AndroidSchedulers.mainThread())
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(new Observer<NewsContentResponse>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(NewsContentResponse newsContentResponse) {
//                            if (newsContentResponse.IsSuccess) {
//                                findViewById(R.id.rowProgressActNews).setVisibility(View.GONE);
//                                news.addAll(newsContentResponse.ListItems);
//                                Total = newsContentResponse.TotalRowCount;
//                                adapter.notifyDataSetChanged();
//                            }
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            findViewById(R.id.rowProgressActNews).setVisibility(View.GONE);
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
//            findViewById(R.id.rowProgressActNews).setVisibility(View.GONE);
//            Snackbar.make(layout, "عدم دسترسی به اینترنت", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    init();
//                }
//            }).show();
//        }
//    }

}
