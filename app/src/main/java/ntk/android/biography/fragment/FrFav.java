package ntk.android.biography.fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.biography.R;
import ntk.android.biography.adapter.AdBiography;
import ntk.android.biography.config.ConfigRestHeader;
import ntk.android.biography.config.ConfigStaticValue;
import ntk.android.biography.utill.AppUtill;
import ntk.android.biography.utill.EndlessRecyclerViewScrollListener;
import ntk.android.biography.utill.FontManager;
import ntk.base.api.biography.interfase.IBiography;
import ntk.base.api.biography.model.BiographyContent;
import ntk.base.api.biography.model.BiographyContentFavoriteListRequest;
import ntk.base.api.biography.model.BiographyContentFavoriteListResponse;
import ntk.base.api.utill.RetrofitManager;

public class FrFav extends Fragment {

    @BindView(R.id.RecyclerFav)
    RecyclerView Rv;

    @BindView(R.id.swipRefreshFrFav)
    SwipeRefreshLayout Refresh;

    @BindView(R.id.lblProgressFrFav)
    TextView LblProgress;

    @BindView(R.id.progressFrFav)
    ProgressBar Progress;

    @BindView(R.id.rowProgressFrFav)
    LinearLayout Loading;

    List<BiographyContent> contents = new ArrayList<>();
    AdBiography adapter;

    @BindView(R.id.mainLayoutFrFav)
    CoordinatorLayout layout;

    private int TotalArticle = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_fav, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        LblProgress.setTypeface(FontManager.GetTypeface(getContext(), FontManager.IranSans));
        Progress.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);

        Rv.setHasFixedSize(true);
        GridLayoutManager manager = new GridLayoutManager(getContext(), 2);
        Rv.setLayoutManager(manager);
        Loading.setVisibility(View.VISIBLE);

        adapter = new AdBiography(getContext(), contents);
        Rv.setAdapter(adapter);

        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(manager) {

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (totalItemsCount <= TotalArticle) {
                    HandleCategory((page + 1));
                }
            }
        };

        Rv.addOnScrollListener(scrollListener);
        HandleCategory(1);

        Refresh.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorAccent,
                R.color.colorAccent);

        Refresh.setOnRefreshListener(() -> {
            contents.clear();
            HandleCategory(1);
            Refresh.setRefreshing(false);
        });
    }

    private void HandleCategory(int i) {
        if (AppUtill.isNetworkAvailable(getContext())) {
            RetrofitManager manager = new RetrofitManager(getContext());
            IBiography iBiography = manager.getRetrofitUnCached(new ConfigStaticValue(getContext()).GetApiBaseUrl()).create(IBiography.class);
            BiographyContentFavoriteListRequest request = new BiographyContentFavoriteListRequest();
            request.RowPerPage = 20;
            request.CurrentPageNumber = i;
            Observable<BiographyContentFavoriteListResponse> call = iBiography.GetContentFavoriteList(new ConfigRestHeader().GetHeaders(getContext()), request);
            call.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<BiographyContentFavoriteListResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BiographyContentFavoriteListResponse response) {
                            Loading.setVisibility(View.GONE);
                            contents.addAll(response.ListItems);
                            adapter.notifyDataSetChanged();
                            Rv.setItemViewCacheSize(contents.size());
                        }

                        @Override
                        public void onError(Throwable e) {
                            Loading.setVisibility(View.GONE);
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
            Loading.setVisibility(View.GONE);
            Snackbar.make(layout, "عدم دسترسی به اینترنت", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            }).show();
        }
    }

}
