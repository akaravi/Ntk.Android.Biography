package ntk.android.biography.activity;

import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
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
import ntk.android.biography.utill.EasyPreference;
import ntk.android.biography.utill.EndlessRecyclerViewScrollListener;
import ntk.android.biography.utill.FontManager;
import ntk.base.api.biography.interfase.IBiography;
import ntk.base.api.biography.model.BiographyContent;
import ntk.base.api.biography.model.BiographyContentResponse;
import ntk.base.api.biography.model.BiographyContentWithDatePeriodStartListRequest;
import ntk.base.api.biography.model.BiographyContentWithSimilarDatePeriodStartDayOfYearListRequest;
import ntk.base.api.utill.RetrofitManager;

public class ActSameYear extends AppCompatActivity {

    @BindView(R.id.lblTitleActSameYear)
    TextView LblTitle;

    @BindView(R.id.recyclerActSameYear)
    RecyclerView Rv;

    @BindView(R.id.mainLayoutActSameYear)
    CoordinatorLayout layout;

    @BindView(R.id.swipRefreshActSameYear)
    SwipeRefreshLayout Refresh;

    private int Total = 0;
    private List<BiographyContent> biography = new ArrayList<>();
    private AdBiography adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_same_year);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        findViewById(R.id.rowProgressActSameYear).setVisibility(View.VISIBLE);
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
        adapter = new AdBiography(this, biography);
        Rv.setAdapter(adapter);

        Refresh.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorAccent,
                R.color.colorAccent);

        Refresh.setOnRefreshListener(() -> {
            biography.clear();
            init();
            Refresh.setRefreshing(false);
        });

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
    }

    private void RestCall(int i) {
        if (AppUtill.isNetworkAvailable(this)) {
            String Gregorian = EasyPreference.with(ActSameYear.this).getString("BirthDay", "");
            BiographyContentWithDatePeriodStartListRequest request = new BiographyContentWithDatePeriodStartListRequest();
            request.SearchDateMin = AppUtill.GetMinOfYear(AppUtill.GregorianToPersian(Gregorian));
            request.SearchDateMax = AppUtill.GetMaxOfYear(AppUtill.GregorianToPersian(Gregorian));

            RetrofitManager manager = new RetrofitManager(ActSameYear.this);
            IBiography iBiography = manager.getRetrofitUnCached(new ConfigStaticValue(ActSameYear.this).GetApiBaseUrl()).create(IBiography.class);

            request.RowPerPage = 20;
            request.CurrentPageNumber = i;

            Observable<BiographyContentResponse> call = iBiography.GetContentWithDatePeriodStartList(new ConfigRestHeader().GetHeaders(this), request);
            call.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<BiographyContentResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BiographyContentResponse response) {
                            if (response.IsSuccess) {
                                findViewById(R.id.rowProgressActSameYear).setVisibility(View.GONE);
                                biography.addAll(response.ListItems);
                                Total = response.TotalRowCount;
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            findViewById(R.id.rowProgressActSameYear).setVisibility(View.GONE);
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
            findViewById(R.id.rowProgressActSameYear).setVisibility(View.GONE);
            Snackbar.make(layout, "عدم دسترسی به اینترنت", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            }).show();
        }
    }

    @OnClick(R.id.imgBackActSameYear)
    public void ClickBack() {
        finish();
    }
}
