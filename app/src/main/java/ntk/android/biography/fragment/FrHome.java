package ntk.android.biography.fragment;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.Extras;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.config.ServiceExecute;
import ntk.android.base.dtomodel.biography.BiographyContentWithSimilarDatePeriodStartDayAndMonthOfYearDtoModel;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterModel;
import ntk.android.base.entitymodel.biography.BiographyContentModel;
import ntk.android.base.entitymodel.coremodulemain.CoreModuleTagModel;
import ntk.android.base.entitymodel.enums.EnumSortType;
import ntk.android.base.entitymodel.news.NewsContentModel;
import ntk.android.base.services.biography.BiographyContentService;
import ntk.android.base.services.coremodulemain.CoreModuleTagService;
import ntk.android.base.services.news.NewsContentService;
import ntk.android.base.utill.FontManager;
import ntk.android.biography.R;
import ntk.android.biography.activity.NewsDetailActivity;
import ntk.android.biography.activity.NewsListActivity;
import ntk.android.biography.adapter.BiographyAdapter;
import ntk.android.biography.adapter.BiographyTagAdapter;
import ntk.android.biography.adapter.NewsAdapter;
import ntk.android.biography.utill.AppUtill;
import ntk.android.biography.utill.EndlessRecyclerViewScrollListener;
import ss.com.bannerslider.banners.RemoteBanner;
import ss.com.bannerslider.events.OnBannerClickListener;
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

    @BindViews({R.id.RLTodayFrHome,
            R.id.RLTomorrowFrHome,
            R.id.RLLastFrHome,
            R.id.RLRandomFrHome,
            R.id.RLNewsFrHome})
    List<RelativeLayout> Rows;

    @BindView(R.id.swipRefreshFrHome)
    SwipeRefreshLayout Refresh;

    @BindView(R.id.mainLayoutFrHome)
    CoordinatorLayout layout;

    private List<CoreModuleTagModel> tags = new ArrayList<>();
    private BiographyTagAdapter adTag;
    private int TotalTag = 0;

    private List<NewsContentModel> news = new ArrayList<>();
    private NewsAdapter adNews;
    private int TotalNews = 0;

    private List<ss.com.bannerslider.banners.Banner> banners = new ArrayList<>();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_home, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void setBanners(List<NewsContentModel> list) {
        if (AppUtill.isNetworkAvailable(getContext())) {
            int size = list.size();
            if (list.size() > 5) size = 5;
            for (int i = 0; i < size; i++) {
                banners.add(new RemoteBanner(list.get(i).LinkMainImageIdSrc));
                banners.get(i).setScaleType(ImageView.ScaleType.FIT_XY);
            }
            Banner.setVisibility(View.VISIBLE);
            Banner.setBanners(banners);
            Banner.setIndicatorSize(banners.size());
            Banner.setOnBannerClickListener(new OnBannerClickListener() {
                @Override
                public void onClick(int position) {
                    startActivity(new Intent(getContext(), NewsDetailActivity.class).putExtra(Extras.EXTRA_FIRST_ARG, new Gson().toJson(list.get(position).Id)));
                }
            });
        } else {
            Banner.setVisibility(View.GONE);
            Snackbar.make(layout, R.string.per_no_net, Snackbar.LENGTH_INDEFINITE).setAction(R.string.try_again, v -> init()).show();
        }
    }

    private void init() {
        Lbls.get(0).setTypeface(FontManager.T1_Typeface(getContext()));
        Lbls.get(1).setTypeface(FontManager.T1_Typeface(getContext()));
        Lbls.get(2).setTypeface(FontManager.T1_Typeface(getContext()));
        Lbls.get(3).setTypeface(FontManager.T1_Typeface(getContext()));
        Lbls.get(4).setTypeface(FontManager.T1_Typeface(getContext()));
        Lbls.get(5).setTypeface(FontManager.T1_Typeface(getContext()));
        Lbls.get(6).setTypeface(FontManager.T1_Typeface(getContext()));
        Progress.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);

        Rvs.get(0).setHasFixedSize(true);
        adTag = new BiographyTagAdapter(getContext(), tags);
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
        adNews = new NewsAdapter(getContext(), news);
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

        Refresh.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorAccent,
                R.color.colorAccent);

        Refresh.setOnRefreshListener(() -> {
            news.clear();
            tags.clear();
            banners.clear();
            init();
            Refresh.setRefreshing(false);
        });
    }

    private void RestCategory(int i) {
        if (AppUtill.isNetworkAvailable(getContext())) {
            FilterModel request = new FilterModel();
            request.RowPerPage = 8;
            request.CurrentPageNumber = i;
            request.SortType = EnumSortType.Random.index();
            request.SortColumn = "Id";
            ServiceExecute.execute(new CoreModuleTagService(getContext()).getAll(request))
                    .subscribe(new NtkObserver<ErrorException<CoreModuleTagModel>>() {
                        @Override
                        public void onNext(@io.reactivex.annotations.NonNull ErrorException<CoreModuleTagModel> response) {
                            tags.addAll(response.ListItems);
                            adTag.notifyDataSetChanged();
                            TotalTag = response.TotalRowCount;
                        }

                        @Override
                        public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                            Snackbar.make(layout, R.string.error_raised, Snackbar.LENGTH_INDEFINITE).setAction(R.string.try_again, new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    init();
                                }
                            }).show();
                        }
                    });


        } else {
            Snackbar.make(layout, R.string.per_no_net, Snackbar.LENGTH_INDEFINITE).setAction(R.string.try_again, v -> init()).show();
        }
    }

    private void RestCallNews(int i) {
        if (AppUtill.isNetworkAvailable(getContext())) {


            FilterModel request = new FilterModel();
            request.RowPerPage = 20;
            request.CurrentPageNumber = i;
            new NewsContentService(getContext()).getAll(request)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new NtkObserver<ErrorException<NewsContentModel>>() {


                        @Override
                        public void onNext(ErrorException<NewsContentModel> newsContentResponse) {
                            if (newsContentResponse.IsSuccess) {
                                Rows.get(4).setVisibility(View.VISIBLE);
                                Rvs.get(1).setVisibility(View.VISIBLE);
                                news.addAll(newsContentResponse.ListItems);
                                setBanners(newsContentResponse.ListItems);
                                TotalNews = newsContentResponse.TotalRowCount;
                                adNews.notifyDataSetChanged();
                            } else {
                                Rows.get(4).setVisibility(View.GONE);
                                Rvs.get(1).setVisibility(View.GONE);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Snackbar.make(layout, R.string.per_no_net, Snackbar.LENGTH_INDEFINITE).setAction(R.string.try_again, v -> init()).show();
                        }


                    });
        } else {
            Rvs.get(1).setVisibility(View.GONE);
            Rows.get(4).setVisibility(View.GONE);
            Snackbar.make(layout, R.string.per_no_net, Snackbar.LENGTH_INDEFINITE).setAction(R.string.try_again, v -> init()).show();
        }
    }

    private void RestCallToday() {
        if (AppUtill.isNetworkAvailable(getContext())) {
            String[] date = AppUtill.GetDateTime().split("-");
            BiographyContentWithSimilarDatePeriodStartDayAndMonthOfYearDtoModel model = new BiographyContentWithSimilarDatePeriodStartDayAndMonthOfYearDtoModel();
            model.MonthOfYear = Integer.parseInt(date[1]);
            model.DayOfMonth = Integer.parseInt(date[2]);


            ServiceExecute.execute(new BiographyContentService(getContext())
                    .getAllWithSimilarDatePeriodStartDayAndMonthOfYear(model))
                    .subscribe(new NtkObserver<ErrorException<BiographyContentModel>>() {
                        @Override
                        public void onNext(@io.reactivex.annotations.NonNull ErrorException<BiographyContentModel> response) {
                            if (response.IsSuccess) {
                                if (response.ListItems.size() != 0) {
                                    Rows.get(0).setVisibility(View.VISIBLE);
                                    Rvs.get(2).setVisibility(View.VISIBLE);
                                    BiographyAdapter adapter = new BiographyAdapter(getContext(), response.ListItems);
                                    Rvs.get(2).setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Rvs.get(2).setVisibility(View.GONE);
                                    Rows.get(0).setVisibility(View.GONE);
                                }
                            }
                        }

                        @Override
                        public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                            Snackbar.make(layout, R.string.per_no_net, Snackbar.LENGTH_INDEFINITE).setAction(R.string.try_again, v -> init()).show();

                        }
                    });
        } else {
            Rvs.get(2).setVisibility(View.GONE);
            Rows.get(0).setVisibility(View.GONE);
            Snackbar.make(layout, R.string.per_no_net, Snackbar.LENGTH_INDEFINITE).setAction(R.string.try_again, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            }).show();
        }
    }

    private void RestCallTommorow() {
        if (AppUtill.isNetworkAvailable(getContext())) {
            String[] date = AppUtill.GetDateTime().split("-");
            BiographyContentWithSimilarDatePeriodStartDayAndMonthOfYearDtoModel model = new BiographyContentWithSimilarDatePeriodStartDayAndMonthOfYearDtoModel();
            model.MonthOfYear = Integer.parseInt(date[1]);
            model.DayOfMonth = (Integer.parseInt(date[2]) + 1);

            ServiceExecute.execute(new BiographyContentService(getContext())
                    .getAllWithSimilarDatePeriodStartDayAndMonthOfYear(model))
                    .subscribe(new NtkObserver<ErrorException<BiographyContentModel>>() {
                        @Override
                        public void onNext(@io.reactivex.annotations.NonNull ErrorException<BiographyContentModel> response) {
                            if (response.IsSuccess) {
                                if (response.ListItems.size() != 0) {
                                    Rows.get(1).setVisibility(View.VISIBLE);
                                    Rvs.get(3).setVisibility(View.VISIBLE);
                                    BiographyAdapter adapter = new BiographyAdapter(getContext(), response.ListItems);
                                    Rvs.get(3).setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Rvs.get(3).setVisibility(View.GONE);
                                    Rows.get(1).setVisibility(View.GONE);
                                }
                            }
                        }

                        @Override
                        public void onError(@io.reactivex.annotations.NonNull Throwable e) {
                            Snackbar.make(layout, R.string.per_no_net, Snackbar.LENGTH_INDEFINITE).setAction(R.string.try_again, v -> init()).show();

                        }
                    });
        } else {
            Rvs.get(3).setVisibility(View.GONE);
            Rows.get(1).setVisibility(View.GONE);
            Snackbar.make(layout, R.string.per_no_net, Snackbar.LENGTH_INDEFINITE).setAction(R.string.try_again, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            }).show();
        }
    }

    private void RestCallLast() {
        if (AppUtill.isNetworkAvailable(getContext())) {
            FilterModel request = new FilterModel();
            request.SortType = EnumSortType.Descending.index();
            request.SortColumn = "Id";
            ServiceExecute.execute(new BiographyContentService(getContext()).getAll(request))
                    .subscribe(new NtkObserver<ErrorException<BiographyContentModel>>() {

                        @Override
                        public void onNext(ErrorException<BiographyContentModel> response) {
                            if (response.IsSuccess) {
                                if (response.ListItems.size() != 0) {
                                    Rows.get(2).setVisibility(View.VISIBLE);
                                    Rvs.get(4).setVisibility(View.VISIBLE);
                                    BiographyAdapter adapter = new BiographyAdapter(getContext(), response.ListItems);
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
                            Snackbar.make(layout, R.string.per_no_net, Snackbar.LENGTH_INDEFINITE).setAction(R.string.try_again, v -> init()).show();
                        }


                    });
        } else {
            Rvs.get(4).setVisibility(View.GONE);
            Rows.get(2).setVisibility(View.GONE);
            Snackbar.make(layout, R.string.per_no_net, Snackbar.LENGTH_INDEFINITE).setAction(R.string.try_again, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            }).show();
        }
    }

    private void RestCallRandom() {
        if (AppUtill.isNetworkAvailable(getContext())) {
            FilterModel request = new FilterModel();
            request.SortType = EnumSortType.Random.index();
            request.SortColumn = "Id";


            ServiceExecute.execute(new BiographyContentService(getContext()).getAll(request))
                    .subscribe(new NtkObserver<ErrorException<BiographyContentModel>>() {


                        @Override
                        public void onNext(ErrorException<BiographyContentModel> response) {
                            if (response.IsSuccess) {
                                if (response.ListItems.size() != 0) {
                                    Rows.get(3).setVisibility(View.VISIBLE);
                                    Rvs.get(5).setVisibility(View.VISIBLE);
                                    BiographyAdapter adapter = new BiographyAdapter(getContext(), response.ListItems);
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
                            Snackbar.make(layout, R.string.per_no_net, Snackbar.LENGTH_INDEFINITE).setAction(R.string.try_again, v -> init()).show();
                        }


                    });
        } else {
            Rows.get(3).setVisibility(View.GONE);
            Rvs.get(5).setVisibility(View.GONE);
            Snackbar.make(layout, R.string.per_no_net, Snackbar.LENGTH_INDEFINITE).setAction(R.string.try_again, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            }).show();
        }
    }

    @OnClick(R.id.lblAllNewsFrHome)
    public void onMoreNewsClick() {
        Objects.requireNonNull(getContext()).startActivity(new Intent(getContext(), NewsListActivity.class));
    }
}
