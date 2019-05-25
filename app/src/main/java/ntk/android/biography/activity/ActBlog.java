package ntk.android.biography.activity;

import android.os.Bundle;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.widget.TextView;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.biography.R;
import ntk.android.biography.adapter.AdBlog;
import ntk.android.biography.config.ConfigRestHeader;
import ntk.android.biography.config.ConfigStaticValue;
import ntk.android.biography.utill.AppUtill;
import ntk.android.biography.utill.EndlessRecyclerViewScrollListener;
import ntk.android.biography.utill.FontManager;
import ntk.base.api.blog.interfase.IBlog;
import ntk.base.api.blog.model.BlogContent;
import ntk.base.api.blog.model.BlogContentListRequest;
import ntk.base.api.blog.model.BlogContentListResponse;
import ntk.base.api.utill.RetrofitManager;

public class ActBlog extends AppCompatActivity {

    @BindView(R.id.lblTitleActBlog)
    TextView LblTitle;

    @BindView(R.id.recyclerBlog)
    RecyclerView Rv;

    @BindView(R.id.mainLayoutActBlog)
    CoordinatorLayout layout;

    @BindView(R.id.swipRefreshActBlog)
    SwipeRefreshLayout Refresh;

    private int Total = 0;
    private List<BlogContent> blog = new ArrayList<>();
    private AdBlog adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_blog);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        findViewById(R.id.rowProgressActBlog).setVisibility(View.VISIBLE);
        LblTitle.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        Rv.setHasFixedSize(true);
        DisplayMetrics dm = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(dm);
        int width = dm.widthPixels;
        Log.i("00000000", "width: " + width + "");

        Rv.setHasFixedSize(true);
        GridLayoutManager LMC;
        if (width < 1000 && width > 600) {
            LMC = new GridLayoutManager(this, 4);
        } else {
            LMC = new GridLayoutManager(this, 2);
        }
        Rv.setLayoutManager(LMC);
        adapter = new AdBlog(this, blog);
        Rv.setAdapter(adapter);

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(LMC) {

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (totalItemsCount <= Total) {
                    RestCall((page + 1));
                }
            }
        };
        Rv.addOnScrollListener(scrollListener);

        RestCall(1);

        Refresh.setOnRefreshListener(() -> {
            blog.clear();
            init();
            Refresh.setRefreshing(false);
        });
    }

    private void RestCall(int i) {
        if (AppUtill.isNetworkAvailable(this)) {
            RetrofitManager manager = new RetrofitManager(this);
            IBlog iBlog = manager.getRetrofitUnCached(new ConfigStaticValue(this).GetApiBaseUrl()).create(IBlog.class);
            BlogContentListRequest request = new BlogContentListRequest();
            request.RowPerPage = 20;
            request.CurrentPageNumber = i;
            Observable<BlogContentListResponse> call = iBlog.GetContentList(new ConfigRestHeader().GetHeaders(this), request);
            call.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<BlogContentListResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BlogContentListResponse blogContentResponse) {
                            if (blogContentResponse.IsSuccess) {
                                findViewById(R.id.rowProgressActBlog).setVisibility(View.GONE);
                                blog.addAll(blogContentResponse.ListItems);
                                Total = blogContentResponse.TotalRowCount;
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            findViewById(R.id.rowProgressActBlog).setVisibility(View.GONE);
                            Snackbar.make(layout, "خطای سامانه مجددا تلاش کنید", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    init();
                                }
                            }).show();

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            findViewById(R.id.rowProgressActBlog).setVisibility(View.GONE);
            Snackbar.make(layout, "عدم دسترسی به اینترنت", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            }).show();
        }
    }

    @OnClick(R.id.imgBackActBlog)
    public void ClickBack() {
        finish();
    }
}
