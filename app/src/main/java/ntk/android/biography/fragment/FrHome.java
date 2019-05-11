package ntk.android.biography.fragment;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.biography.R;
import ntk.android.biography.activity.ActNews;
import ntk.android.biography.adapter.AdBiography;
import ntk.android.biography.adapter.AdNews;
import ntk.android.biography.adapter.AdTag;
import ntk.android.biography.config.ConfigRestHeader;
import ntk.android.biography.config.ConfigStaticValue;
import ntk.android.biography.utill.AppUtill;
import ntk.android.biography.utill.EndlessRecyclerViewScrollListener;
import ntk.android.biography.utill.FontManager;
import ntk.base.api.biography.interfase.IBiography;
import ntk.base.api.biography.model.BiographyContent;
import ntk.base.api.biography.model.BiographyContentListRequest;
import ntk.base.api.biography.model.BiographyContentResponse;
import ntk.base.api.biography.model.BiographyContentWithSimilarDatePeriodStartDayAndMonthOfYearListRequest;
import ntk.base.api.biography.model.BiographyTag;
import ntk.base.api.biography.model.BiographyTagRequest;
import ntk.base.api.biography.model.BiographyTagResponse;
import ntk.base.api.news.interfase.INews;
import ntk.base.api.news.model.NewsContent;
import ntk.base.api.news.model.NewsContentListRequest;
import ntk.base.api.news.model.NewsContentResponse;
import ntk.base.api.utill.NTKUtill;
import ntk.base.api.utill.RetrofitManager;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.views.BannerSlider;

public class FrHome extends Fragment {

    @BindViews({R.id.lblProgressFrHome,
            R.id.lblNewsFrHome,
            R.id.lblTodeyFrHome,
            R.id.lblTomorrowFrHome,
            R.id.lblAllNewsFrHome,
            R.id.lblLastFrHome,
            R.id.lblRandomFrHome})
    List<TextView> Lbls;

    @BindView(R.id.rowProgressFrHome)
    LinearLayout Loading;

    @BindView(R.id.progressFrHome)
    ProgressBar Progress;

    @BindViews({R.id.RecyclerTagRecyclerFrHome,
            R.id.RecyclerNewsFrHome,
            R.id.RecyclerTodayFrHome,
            R.id.RecyclerTomorrowFrHome,
            R.id.RecyclerLastFrHome,
            R.id.RecyclerRandomFrHome})
    List<RecyclerView> Rvs;

    @BindView(R.id.BannerSliderFrHome)
    BannerSlider Banner;

    @BindViews({R.id.RLTodayFrHome, R.id.RLTomorrowFrHome, R.id.RLLastFrHome, R.id.RLRandomFrHome})
    List<RelativeLayout> Rows;

    @BindView(R.id.swipRefreshFrHome)
    SwipeRefreshLayout Refresh;

    private List<BiographyTag> tags = new ArrayList<>();
    private AdTag adTag;
    private int TotalTag = 0;

    private List<NewsContent> news = new ArrayList<>();
    private AdNews adNews;
    private int TotalNews = 0;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_home, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        Loading.setVisibility(View.GONE);
        Lbls.get(0).setTypeface(FontManager.GetTypeface(getContext(), FontManager.IranSans));
        Lbls.get(1).setTypeface(FontManager.GetTypeface(getContext(), FontManager.IranSans));
        Lbls.get(2).setTypeface(FontManager.GetTypeface(getContext(), FontManager.IranSans));
        Lbls.get(3).setTypeface(FontManager.GetTypeface(getContext(), FontManager.IranSans));
        Lbls.get(4).setTypeface(FontManager.GetTypeface(getContext(), FontManager.IranSans));
        Lbls.get(5).setTypeface(FontManager.GetTypeface(getContext(), FontManager.IranSans));
        Lbls.get(6).setTypeface(FontManager.GetTypeface(getContext(), FontManager.IranSans));
        Progress.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);

        Rvs.get(0).setHasFixedSize(true);
        adTag = new AdTag(getContext(), tags);
        LinearLayoutManager LMC1 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        Rvs.get(0).setLayoutManager(LMC1);
        EndlessRecyclerViewScrollListener scrollListener = new EndlessRecyclerViewScrollListener(LMC1) {

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (totalItemsCount <= TotalTag) {
                    RestCategory((page + 1));
                }
            }
        };
        Rvs.get(0).addOnScrollListener(scrollListener);
        Rvs.get(0).setAdapter(adTag);
        RestCategory(1);

        Rvs.get(1).setHasFixedSize(true);
        LinearLayoutManager LMC2 = new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true);
        Rvs.get(1).setLayoutManager(LMC2);
        adNews = new AdNews(getContext(), news);
        Rvs.get(1).setAdapter(adNews);
        EndlessRecyclerViewScrollListener SLN = new EndlessRecyclerViewScrollListener(LMC2) {

            @Override
            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) {
                if (totalItemsCount <= TotalNews) {
                    RestCallNews((page + 1));
                }
            }
        };
        Rvs.get(1).addOnScrollListener(SLN);
        RestCallNews(1);

        Rvs.get(2).setHasFixedSize(true);
        Rvs.get(2).setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        RestCallToday();

        Rvs.get(3).setHasFixedSize(true);
        Rvs.get(3).setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        RestCallTommorow();

        Rvs.get(4).setHasFixedSize(true);
        Rvs.get(4).setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        RestCallLast();

        Rvs.get(5).setHasFixedSize(true);
        Rvs.get(5).setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        RestCallRandom();


        List<ss.com.bannerslider.banners.Banner> banners = new ArrayList<>();
        banners.add(new RemoteBanner("https://wikifamous.com/wp-content/uploads/2018/06/Messi-HD-Wallpaper-1200x630.jpg?x50236"));
        banners.add(new RemoteBanner("https://wikifamous.com/wp-content/uploads/2018/06/Messi-HD-Wallpaper-1200x630.jpg?x50236"));
        Banner.setBanners(banners);

        Refresh.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorAccent,
                R.color.colorAccent);

        Refresh.setOnRefreshListener(() -> {
            news.clear();
            tags.clear();
            init();
            Refresh.setRefreshing(false);
        });
    }

    private void RestCategory(int i) {
        RetrofitManager manager = new RetrofitManager(getContext());
        IBiography iBiography = manager.getCachedRetrofit(new ConfigStaticValue(getContext()).GetApiBaseUrl()).create(IBiography.class);
        Map<String, String> headers = new ConfigRestHeader().GetHeaders(getContext());

        BiographyTagRequest request = new BiographyTagRequest();
        request.RowPerPage = 8;
        request.CurrentPageNumber = i;
        request.SortType = NTKUtill.Random_Sort;
        request.SortColumn = "Id";

        Observable<BiographyTagResponse> call = iBiography.GetTagList(headers, request);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BiographyTagResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BiographyTagResponse articleTagResponse) {
                        tags.addAll(articleTagResponse.ListItems);
                        adTag.notifyDataSetChanged();
                        TotalTag = articleTagResponse.TotalRowCount;
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void RestCallNews(int i) {
        RetrofitManager manager = new RetrofitManager(getContext());
        INews iNews = manager.getRetrofitUnCached(new ConfigStaticValue(getContext()).GetApiBaseUrl()).create(INews.class);

        NewsContentListRequest request = new NewsContentListRequest();
        request.RowPerPage = 20;
        request.CurrentPageNumber = i;
        Observable<NewsContentResponse> call = iNews.GetContentList(new ConfigRestHeader().GetHeaders(getContext()), request);
        call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<NewsContentResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(NewsContentResponse newsContentResponse) {
                        if (newsContentResponse.IsSuccess) {
                            news.addAll(newsContentResponse.ListItems);
                            TotalNews = newsContentResponse.TotalRowCount;
                            adNews.notifyDataSetChanged();
                        }
                    }

                    @Override
                    public void onError(Throwable e) {
                        Toasty.warning(getContext(), "خطای سامانه", Toasty.LENGTH_LONG, true).show();

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void RestCallToday() {
        String date[] = AppUtill.GetDateTime().split("-");
        BiographyContentWithSimilarDatePeriodStartDayAndMonthOfYearListRequest model = new BiographyContentWithSimilarDatePeriodStartDayAndMonthOfYearListRequest();
        model.MonthOfYear = Integer.parseInt(date[1]);
        model.DayOfMonth = Integer.parseInt(date[2]);

        RetrofitManager manager = new RetrofitManager(getContext());
        IBiography iBiography = manager.getRetrofitUnCached(new ConfigStaticValue(getContext()).GetApiBaseUrl()).create(IBiography.class);

        Observable<BiographyContentResponse> Call = iBiography.GetContentWithSimilarDatePeriodStartDayAndMonthOfYearList(new ConfigRestHeader().GetHeaders(getContext()), model);
        Call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BiographyContentResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BiographyContentResponse response) {
                        if (response.IsSuccess) {
                            if (response.ListItems.size() != 0) {
                                AdBiography adapter = new AdBiography(getContext(), response.ListItems);
                                Rvs.get(2).setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                Rvs.get(2).setVisibility(View.GONE);
                                Rows.get(0).setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    private void RestCallTommorow() {
        String date[] = AppUtill.GetDateTime().split("-");
        BiographyContentWithSimilarDatePeriodStartDayAndMonthOfYearListRequest model = new BiographyContentWithSimilarDatePeriodStartDayAndMonthOfYearListRequest();
        model.MonthOfYear = Integer.parseInt(date[1]);
        model.DayOfMonth = (Integer.parseInt(date[2]) + 1);

        RetrofitManager manager = new RetrofitManager(getContext());
        IBiography iBiography = manager.getRetrofitUnCached(new ConfigStaticValue(getContext()).GetApiBaseUrl()).create(IBiography.class);

        Observable<BiographyContentResponse> Call = iBiography.GetContentWithSimilarDatePeriodStartDayAndMonthOfYearList(new ConfigRestHeader().GetHeaders(getContext()), model);
        Call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BiographyContentResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BiographyContentResponse response) {
                        if (response.IsSuccess) {
                            if (response.ListItems.size() != 0) {
                                AdBiography adapter = new AdBiography(getContext(), response.ListItems);
                                Rvs.get(3).setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                Rvs.get(3).setVisibility(View.GONE);
                                Rows.get(1).setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void RestCallLast() {
        BiographyContentListRequest request = new BiographyContentListRequest();
        request.SortType = NTKUtill.Descnding_Sort;
        request.SortColumn = "Id";

        RetrofitManager manager = new RetrofitManager(getContext());
        IBiography iBiography = manager.getRetrofitUnCached(new ConfigStaticValue(getContext()).GetApiBaseUrl()).create(IBiography.class);

        Observable<BiographyContentResponse> Call = iBiography.GetContentList(new ConfigRestHeader().GetHeaders(getContext()), request);
        Call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BiographyContentResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BiographyContentResponse response) {
                        if (response.IsSuccess) {
                            if (response.ListItems.size() != 0) {
                                AdBiography adapter = new AdBiography(getContext(), response.ListItems);
                                Rvs.get(4).setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                Rvs.get(4).setVisibility(View.GONE);
                                Rows.get(2).setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }

    private void RestCallRandom(){
        BiographyContentListRequest request = new BiographyContentListRequest();
        request.SortType = NTKUtill.Random_Sort;
        request.SortColumn = "Id";

        RetrofitManager manager = new RetrofitManager(getContext());
        IBiography iBiography = manager.getRetrofitUnCached(new ConfigStaticValue(getContext()).GetApiBaseUrl()).create(IBiography.class);

        Observable<BiographyContentResponse> Call = iBiography.GetContentList(new ConfigRestHeader().GetHeaders(getContext()), request);
        Call.observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(new Observer<BiographyContentResponse>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(BiographyContentResponse response) {
                        if (response.IsSuccess) {
                            if (response.ListItems.size() != 0) {
                                AdBiography adapter = new AdBiography(getContext(), response.ListItems);
                                Rvs.get(5).setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            } else {
                                Rvs.get(5).setVisibility(View.GONE);
                                Rows.get(3).setVisibility(View.GONE);
                            }
                        }
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });
    }
    @OnClick(R.id.lblAllNewsFrHome)
    public void onMoreNewsClick(){
        Objects.requireNonNull(getContext()).startActivity(new Intent(getContext(), ActNews.class));
    }
}
