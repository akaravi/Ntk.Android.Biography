package ntk.android.biography.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.biography.R;
import ntk.android.biography.adapter.AdBiographyGrid;
import ntk.android.biography.config.ConfigRestHeader;
import ntk.android.biography.config.ConfigStaticValue;
import ntk.android.biography.utill.AppUtill;
import ntk.android.biography.utill.EndlessRecyclerViewScrollListener;
import ntk.android.biography.utill.FontManager;
import ntk.base.api.biography.interfase.IBiography;
import ntk.base.api.biography.entity.BiographyContent;
import ntk.base.api.biography.model.BiographyContentListRequest;
import ntk.base.api.biography.model.BiographyContentResponse;
import ntk.base.api.utill.RetrofitManager;

public class ActBiographyContentList extends AppCompatActivity {

    @BindView(R.id.lblTitleActBiographyContentList)
    TextView Lbl;

    @BindView(R.id.recyclerActBiographyContentList)
    RecyclerView Rv;

    @BindView(R.id.mainLayoutActBiographyContentList)
    CoordinatorLayout layout;

    @BindView(R.id.RefreshActBiographyContentList)
    SwipeRefreshLayout Refresh;

    private String RequestStr;

    private EndlessRecyclerViewScrollListener scrollListener;
    private int TotalItem = 0;
    private AdBiographyGrid adapter;
    private List<BiographyContent> contents = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_biography_content_list);
        ButterKnife.bind(this);
        configStaticValue = new ConfigStaticValue(this);
        init();
    }

    private void init() {
        Lbl.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        Rv.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(this, 2);
        Rv.setLayoutManager(manager);
        adapter = new AdBiographyGrid(this, contents);
        Rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
        RequestStr = getIntent().getExtras().getString("Request");

        Refresh.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorAccent,
                R.color.colorAccent);

        Refresh.setOnRefreshListener(() -> {
            contents.clear();
            init();
            Refresh.setRefreshing(false);
        });

        scrollListener = new EndlessRecyclerViewScrollListener(manager) {

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (totalItemsCount <= TotalItem) {
                    HandelData((page + 1), new Gson().fromJson(RequestStr, BiographyContentListRequest.class));
                }
            }
        };
        Rv.addOnScrollListener(scrollListener);
        HandelData(1, new Gson().fromJson(RequestStr, BiographyContentListRequest.class));
    }

    private ConfigStaticValue configStaticValue;

    private void HandelData(int i, BiographyContentListRequest request) {
        if (AppUtill.isNetworkAvailable(this)) {
            RetrofitManager retro = new RetrofitManager(this);
            IBiography iBiography = retro.getRetrofitUnCached(configStaticValue.GetApiBaseUrl()).create(IBiography.class);
            Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);
            request.RowPerPage = 16;
            request.CurrentPageNumber = i;
            Observable<BiographyContentResponse> call = iBiography.GetContentList(headers, request);
            call.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<BiographyContentResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BiographyContentResponse response) {
                            contents.addAll(response.ListItems);
                            adapter.notifyDataSetChanged();
                            TotalItem=response.TotalRowCount;
                            Rv.setItemViewCacheSize(contents.size());
                        }

                        @Override
                        public void onError(Throwable e) {
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
            Snackbar.make(layout, "عدم دسترسی به اینترنت", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            }).show();
        }
    }

    @OnClick(R.id.imgBackBiographyContentList)
    public void Back() {
        finish();
    }
}
