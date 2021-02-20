package ntk.android.biography.activity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.Observable;
import java9.util.function.Function;
import ntk.android.base.activity.common.BaseFilterModelListActivity;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.base.FilterModel;
import ntk.android.base.entitymodel.biography.BiographyContentModel;
import ntk.android.base.services.biography.BiographyContentService;
import ntk.android.biography.adapter.BiographyGridAdapter;

public class BiographyListActivity extends BaseFilterModelListActivity<BiographyContentModel> {
    public RecyclerView.LayoutManager getRvLayoutManager() {
        return new GridLayoutManager(this, 2);
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return new BiographyGridAdapter(this, models);
    }

    @Override
    public Function<FilterModel, Observable<ErrorException<BiographyContentModel>>> getService() {
        return new BiographyContentService(this)::getAll;
    }

    @Override
    public void ClickSearch() {

    }

//    @BindView(R.id.lblTitleActBiographyContentList)
//    TextView Lbl;
//
//    @BindView(R.id.recyclerActBiographyContentList)
//    RecyclerView Rv;
//
//    @BindView(R.id.mainLayoutActBiographyContentList)
//    CoordinatorLayout layout;
//
//    @BindView(R.id.RefreshActBiographyContentList)
//    SwipeRefreshLayout Refresh;
//
//    private String RequestStr;
//
//    private EndlessRecyclerViewScrollListener scrollListener;
//    private int TotalItem = 0;
//    private AdBiographyGrid adapter;
//    private List<BiographyContent> contents = new ArrayList<>();
//
//    @Override
//    protected void onCreate(@Nullable Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//        setContentView(R.layout.act_biography_content_list);
//        ButterKnife.bind(this);
//        configStaticValue = new ConfigStaticValue(this);
//        init();
//    }
//
//    private void init() {
//        Lbl.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
//        Rv.setHasFixedSize(true);
//        GridLayoutManager manager = new GridLayoutManager(this, 2);
//        Rv.setLayoutManager(manager);
//        adapter = new AdBiographyGrid(this, contents);
//        Rv.setAdapter(adapter);
//        adapter.notifyDataSetChanged();
//        RequestStr = getIntent().getExtras().getString("Request");
//
//        Refresh.setColorSchemeResources(
//                R.color.colorAccent,
//                R.color.colorAccent,
//                R.color.colorAccent);
//
//        Refresh.setOnRefreshListener(() -> {
//            contents.clear();
//            init();
//            Refresh.setRefreshing(false);
//        });
//
//        scrollListener = new EndlessRecyclerViewScrollListener(manager) {
//
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
//                if (totalItemsCount <= TotalItem) {
//                    HandelData((page + 1), new Gson().fromJson(RequestStr, BiographyContentListRequest.class));
//                }
//            }
//        };
//        Rv.addOnScrollListener(scrollListener);
//        HandelData(1, new Gson().fromJson(RequestStr, BiographyContentListRequest.class));
//    }
//
//    private ConfigStaticValue configStaticValue;
//
//    private void HandelData(int i, BiographyContentListRequest request) {
//        if (AppUtill.isNetworkAvailable(this)) {
//            RetrofitManager retro = new RetrofitManager(this);
//            IBiography iBiography = retro.getRetrofitUnCached(configStaticValue.GetApiBaseUrl()).create(IBiography.class);
//            Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);
//            request.RowPerPage = 16;
//            request.CurrentPageNumber = i;
//            Observable<BiographyContentResponse> call = iBiography.GetContentList(headers, request);
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
//                            contents.addAll(response.ListItems);
//                            adapter.notifyDataSetChanged();
//                            TotalItem=response.TotalRowCount;
//                            Rv.setItemViewCacheSize(contents.size());
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
//    @OnClick(R.id.imgBackBiographyContentList)
//    public void Back() {
//        finish();
//    }
}
