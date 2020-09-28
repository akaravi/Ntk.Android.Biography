package ntk.android.biography.activity;

import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

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
import ntk.base.api.biography.entity.BiographyContent;
import ntk.base.api.biography.model.BiographyContentResponse;
import ntk.base.api.biography.model.BiographyContentWithDatePeriodStartListRequest;
import ntk.base.api.utill.RetrofitManager;

public class ActSameBirthDay extends AppCompatActivity {

    @BindView(R.id.lblTitleActSameBirthDay)
    TextView LblTitle;

    @BindView(R.id.recyclerActSameBirthDay)
    RecyclerView Rv;

    @BindView(R.id.mainLayoutActSameBirthDay)
    CoordinatorLayout layout;

    @BindView(R.id.swipRefreshActSameBirthDay)
    SwipeRefreshLayout Refresh;

    private int Total = 0;
    private List<BiographyContent> biography = new ArrayList<>();
    private AdBiography adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_same_birth_day);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        findViewById(R.id.rowProgressActSameBirthDay).setVisibility(View.VISIBLE);
        LblTitle.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        Rv.setHasFixedSize(true);
        GridLayoutManager LMC = new GridLayoutManager(this, 2);
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
            String Gregorian = EasyPreference.with(ActSameBirthDay.this).getString("BirthDay", "");
            BiographyContentWithDatePeriodStartListRequest request = new BiographyContentWithDatePeriodStartListRequest();
            request.SearchDateMin = Gregorian;
            request.SearchDateMax = Gregorian;

            RetrofitManager manager = new RetrofitManager(ActSameBirthDay.this);
            IBiography iBiography = manager.getRetrofitUnCached(new ConfigStaticValue(ActSameBirthDay.this).GetApiBaseUrl()).create(IBiography.class);

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
                                findViewById(R.id.rowProgressActSameBirthDay).setVisibility(View.GONE);
                                biography.addAll(response.ListItems);
                                Total = response.TotalRowCount;
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            findViewById(R.id.rowProgressActSameBirthDay).setVisibility(View.GONE);
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
            findViewById(R.id.rowProgressActSameBirthDay).setVisibility(View.GONE);
            Snackbar.make(layout, "عدم دسترسی به اینترنت", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            }).show();
        }
    }

    @OnClick(R.id.imgBackActSameBirthDay)
    public void ClickBack() {
        finish();
    }
}
