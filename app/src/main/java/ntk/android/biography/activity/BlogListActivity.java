package ntk.android.biography.activity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.Observable;
import java9.util.function.Function;
import ntk.android.base.activity.common.BaseFilterModelListActivity;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.blog.BlogContentModel;
import ntk.android.base.services.blog.BlogContentService;
import ntk.android.biography.adapter.AdBlog;
import ntk.android.biography.adapter.BlogAdapter;

public class BlogListActivity extends BaseFilterModelListActivity<BlogContentModel> {
    @Override
    public RecyclerView.LayoutManager getRvLayoutManager() {
        return new GridLayoutManager(this, 2);
    }
    @Override
    public RecyclerView.Adapter createAdapter() {
        return new BlogAdapter(this,models);
    }
    @Override
    public Function<FilterDataModel, Observable<ErrorException<BlogContentModel>>> getService() {
        return new BlogContentService(this)::getAll;
    }

    @Override
    public void ClickSearch() {

    }

//    @BindView(R.id.lblTitleActBlog)
//    TextView LblTitle;
//
//    @BindView(R.id.recyclerBlog)
//    RecyclerView Rv;
//
//    @BindView(R.id.mainLayoutActBlog)
//    CoordinatorLayout layout;
//
//    @BindView(R.id.swipRefreshActBlog)
//    SwipeRefreshLayout Refresh;
//
//    private int Total = 0;
//    private List<BlogContent> blog = new ArrayList<>();
//    private AdBlog adapter;
//
//    @Override
//    protected void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_blog);
//        ButterKnife.bind(this);
//        init();
//    }
//
//    private void init() {
//        findViewById(R.id.rowProgressActBlog).setVisibility(View.VISIBLE);
//        LblTitle.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
//        Rv.setHasFixedSize(true);
//        GridLayoutManager LMC = new GridLayoutManager(this, 2);
//        Rv.setLayoutManager(LMC);
//        adapter = new AdBlog(this, blog);
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
//            blog.clear();
//            init();
//            Refresh.setRefreshing(false);
//        });
//    }
//
//    private void RestCall(int i) {
//        if (AppUtill.isNetworkAvailable(this)) {
//            RetrofitManager manager = new RetrofitManager(this);
//            IBlog iBlog = manager.getRetrofitUnCached(new ConfigStaticValue(this).GetApiBaseUrl()).create(IBlog.class);
//            BlogContentListRequest request = new BlogContentListRequest();
//            request.RowPerPage = 20;
//            request.CurrentPageNumber = i;
//            Observable<BlogContentListResponse> call = iBlog.GetContentList(new ConfigRestHeader().GetHeaders(this), request);
//            call.observeOn(AndroidSchedulers.mainThread())
//                    .subscribeOn(Schedulers.io())
//                    .subscribe(new Observer<BlogContentListResponse>() {
//                        @Override
//                        public void onSubscribe(Disposable d) {
//
//                        }
//
//                        @Override
//                        public void onNext(BlogContentListResponse blogContentResponse) {
//                            if (blogContentResponse.IsSuccess) {
//                                findViewById(R.id.rowProgressActBlog).setVisibility(View.GONE);
//                                blog.addAll(blogContentResponse.ListItems);
//                                Total = blogContentResponse.TotalRowCount;
//                                adapter.notifyDataSetChanged();
//                            }
//                        }
//
//                        @Override
//                        public void onError(Throwable e) {
//                            findViewById(R.id.rowProgressActBlog).setVisibility(View.GONE);
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
//            findViewById(R.id.rowProgressActBlog).setVisibility(View.GONE);
//            Snackbar.make(layout, "عدم دسترسی به اینترنت", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    init();
//                }
//            }).show();
//        }
//    }
//
//    @OnClick(R.id.imgBackActBlog)
//    public void ClickBack() {
//        finish();
//    }
}
