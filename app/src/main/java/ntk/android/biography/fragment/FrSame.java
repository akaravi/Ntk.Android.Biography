package ntk.android.biography.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.BindViews;
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
import ntk.android.biography.utill.EasyPreference;
import ntk.android.biography.utill.FontManager;
import ntk.base.api.biography.interfase.IBiography;
import ntk.base.api.biography.model.BiographyContentResponse;
import ntk.base.api.biography.model.BiographyContentWithDatePeriodStartListRequest;
import ntk.base.api.biography.model.BiographyContentWithSimilarDatePeriodStartDayAndMonthOfYearListRequest;
import ntk.base.api.biography.model.BiographyContentWithSimilarDatePeriodStartDayOfYearListRequest;
import ntk.base.api.utill.RetrofitManager;

public class FrSame extends Fragment {

    @BindViews({R.id.lblSameDay,
            R.id.lblAlsoMonth,
            R.id.lblSameYear,
            R.id.lblFellowCitizen,
            R.id.lblAllSameYear,
            R.id.lblAllAlsoMmonth,
            R.id.lblAllSameDay,
            R.id.lblAllFellowCitizen,
            R.id.lblAllDay,
            R.id.lblDay})
    List<TextView> Lbls;

    @BindViews({R.id.RecyclerSameDay,
            R.id.RecyclerAlsoMonth,
            R.id.RecyclerSameYear,
            R.id.RecyclerFellowCitizen,
            R.id.RecyclerDay})
    List<RecyclerView> Rvs;

    @BindViews({R.id.row_one_fr_me_like,
            R.id.row_two_fr_me_like,
            R.id.row_three_fr_me_like,
            R.id.row_four_fr_me_like,
            R.id.row_zero_fr_me_like})
    List<RelativeLayout> Rows;

    @BindView(R.id.swipRefreshFrMeLike)
    SwipeRefreshLayout Refresh;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_me_like, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        for (TextView tv : Lbls) {
            tv.setTypeface(FontManager.GetTypeface(getContext(), FontManager.IranSans));
        }

        Rvs.get(0).setHasFixedSize(true);
        Rvs.get(0).setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        RestCallOne();

        Rvs.get(1).setHasFixedSize(true);
        Rvs.get(1).setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        RestCallTwo();

        Rvs.get(2).setHasFixedSize(true);
        Rvs.get(2).setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        RestCallThree();

        Rvs.get(3).setHasFixedSize(true);
        Rvs.get(3).setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));

        Rvs.get(4).setHasFixedSize(true);
        Rvs.get(4).setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, true));
        RestCallZero();

        Refresh.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorAccent,
                R.color.colorAccent);

        Refresh.setOnRefreshListener(() -> {
            init();
            Refresh.setRefreshing(false);
        });
    }

    private void RestCallZero() {
        String Gregorian = EasyPreference.with(getContext()).getString("BirthDay", "");
        BiographyContentWithDatePeriodStartListRequest request = new BiographyContentWithDatePeriodStartListRequest();
        request.SearchDateMin = Gregorian;
        request.SearchDateMax = Gregorian;

        RetrofitManager manager = new RetrofitManager(getContext());
        IBiography iBiography = manager.getRetrofitUnCached(new ConfigStaticValue(getContext()).GetApiBaseUrl()).create(IBiography.class);

        Observable<BiographyContentResponse> Call = iBiography.GetContentWithDatePeriodStartList(new ConfigRestHeader().GetHeaders(getContext()), request);
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
                                Rows.get(4).setVisibility(View.VISIBLE);
                                adapter.notifyDataSetChanged();
                            } else {
                                Rvs.get(4).setVisibility(View.GONE);
                                Rows.get(4).setVisibility(View.GONE);
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

    private void RestCallOne() {
        String date[] = EasyPreference.with(getContext()).getString("BirthDay", "").split("/");
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
                                Rvs.get(0).setAdapter(adapter);
                                Rows.get(0).setVisibility(View.VISIBLE);
                                adapter.notifyDataSetChanged();
                            } else {
                                Rvs.get(0).setVisibility(View.GONE);
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

    private void RestCallTwo() {
        String Gregorian = EasyPreference.with(getContext()).getString("BirthDay", "");

        BiographyContentWithSimilarDatePeriodStartDayOfYearListRequest request = new BiographyContentWithSimilarDatePeriodStartDayOfYearListRequest();
        request.DayOfYearMin = AppUtill.GetMinDayOfYear(AppUtill.GregorianToPersian(Gregorian), Gregorian);
        request.DayOfYearMax = AppUtill.GetMaxDayOfYear(AppUtill.GregorianToPersian(Gregorian), Gregorian);

        RetrofitManager manager = new RetrofitManager(getContext());
        IBiography iBiography = manager.getRetrofitUnCached(new ConfigStaticValue(getContext()).GetApiBaseUrl()).create(IBiography.class);

        Observable<BiographyContentResponse> Call = iBiography.GetContentWithSimilarDatePeriodStartDayOfYearList(new ConfigRestHeader().GetHeaders(getContext()), request);
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
                                Rvs.get(1).setAdapter(adapter);
                                Rows.get(1).setVisibility(View.VISIBLE);
                                adapter.notifyDataSetChanged();
                            } else {
                                Rvs.get(1).setVisibility(View.GONE);
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

    private void RestCallThree() {
        String Gregorian = EasyPreference.with(getContext()).getString("BirthDay", "");
        BiographyContentWithDatePeriodStartListRequest request = new BiographyContentWithDatePeriodStartListRequest();
        request.SearchDateMin = AppUtill.GetMinOfYear(AppUtill.GregorianToPersian(Gregorian));
        request.SearchDateMax = AppUtill.GetMaxOfYear(AppUtill.GregorianToPersian(Gregorian));

        RetrofitManager manager = new RetrofitManager(getContext());
        IBiography iBiography = manager.getRetrofitUnCached(new ConfigStaticValue(getContext()).GetApiBaseUrl()).create(IBiography.class);

        Observable<BiographyContentResponse> Call = iBiography.GetContentWithDatePeriodStartList(new ConfigRestHeader().GetHeaders(getContext()), request);
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
                                Rows.get(2).setVisibility(View.VISIBLE);
                                adapter.notifyDataSetChanged();
                            } else {
                                Rvs.get(2).setVisibility(View.GONE);
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
}
