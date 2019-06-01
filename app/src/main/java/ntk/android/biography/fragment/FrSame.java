package ntk.android.biography.fragment;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.mohamadamin.persianmaterialdatetimepicker.date.DatePickerDialog;

import java.util.Calendar;
import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.biography.R;
import ntk.android.biography.activity.ActSameBirthDay;
import ntk.android.biography.activity.ActSameDay;
import ntk.android.biography.activity.ActSameLocation;
import ntk.android.biography.activity.ActSameMonth;
import ntk.android.biography.activity.ActSameYear;
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
            R.id.lblAllSameMonth,
            R.id.lblAllSameDay,
            R.id.lblAllFellowCitizen,
            R.id.lblAllDate,
            R.id.lblDay,
            R.id.lblBirthDayFrSame,
            R.id.lblPersianBirthDayFrSame,
            R.id.lblBirthDateFrSame,
            R.id.lblBirthDateInLayoutFrSame})
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
            R.id.row_zero_fr_me_like,
            R.id.layoutBirthDayFrSame})
    List<RelativeLayout> Rows;

    @BindView(R.id.swipRefreshFrMeLike)
    SwipeRefreshLayout Refresh;

    @BindView(R.id.mainLayoutFrSame)
    CoordinatorLayout layout;

    @BindViews({R.id.btnBirthDayFrSame,
            R.id.btnBirthDateSubmitFrSame})
    List<Button> btns;

    @BindView(R.id.progressFrSame)
    ProgressBar Loading;

    private String Gregorian, Date = "";


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_me_like, container, false);
        ButterKnife.bind(this, view);
        init();
        return view;
    }

    private void init() {
        Gregorian = EasyPreference.with(getContext()).getString("BirthDay", "");
        Loading.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        if (Gregorian.equals("")) {
            Rows.get(5).setVisibility(View.VISIBLE);
            return;
        }
        Loading.setVisibility(View.GONE);
        Rows.get(5).setVisibility(View.GONE);
        for (TextView tv : Lbls) {
            tv.setTypeface(FontManager.GetTypeface(getContext(), FontManager.IranSans));
        }
        for (Button btn : btns) {
            btn.setTypeface(FontManager.GetTypeface(getContext(), FontManager.IranSans));
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

        Lbls.get(10).setText(" شمسی " + AppUtill.GregorianToPersian(EasyPreference.with(getContext()).getString("BirthDay", "")));
        Lbls.get(11).setText(" میلادی " + EasyPreference.with(getContext()).getString("BirthDay", ""));
        Lbls.get(12).setText(setDate());

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
        if (AppUtill.isNetworkAvailable(getContext())) {
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
                                    Rvs.get(4).setVisibility(View.VISIBLE);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Rvs.get(4).setVisibility(View.GONE);
                                    Rows.get(4).setVisibility(View.GONE);
                                }
                            }
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
            Rvs.get(4).setVisibility(View.GONE);
            Rows.get(4).setVisibility(View.GONE);
            Snackbar.make(layout, "عدم دسترسی به اینترنت", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            }).show();
        }
    }

    private void RestCallOne() {
        if (AppUtill.isNetworkAvailable(getContext())) {
            String date[] = Gregorian.split("/");
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
                                    Rvs.get(0).setVisibility(View.VISIBLE);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Rvs.get(0).setVisibility(View.GONE);
                                    Rows.get(0).setVisibility(View.GONE);
                                }
                            }
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
            Rvs.get(0).setVisibility(View.GONE);
            Rows.get(0).setVisibility(View.GONE);
            Snackbar.make(layout, "عدم دسترسی به اینترنت", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            }).show();
        }
    }

    private void RestCallTwo() {
        if (AppUtill.isNetworkAvailable(getContext())) {
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
                                    Rvs.get(1).setVisibility(View.VISIBLE);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Rvs.get(1).setVisibility(View.GONE);
                                    Rows.get(1).setVisibility(View.GONE);
                                }
                            }
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
            Rvs.get(1).setVisibility(View.GONE);
            Rows.get(1).setVisibility(View.GONE);
            Snackbar.make(layout, "عدم دسترسی به اینترنت", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            }).show();
        }
    }

    private void RestCallThree() {
        if (AppUtill.isNetworkAvailable(getContext())) {
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
                                    Rvs.get(2).setVisibility(View.VISIBLE);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Rvs.get(2).setVisibility(View.GONE);
                                    Rows.get(2).setVisibility(View.GONE);
                                }
                            }
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
            Rvs.get(2).setVisibility(View.GONE);
            Rows.get(2).setVisibility(View.GONE);
            Snackbar.make(layout, "عدم دسترسی به اینترنت", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            }).show();
        }
    }

    @OnClick(R.id.lblAllDate)
    public void onAllDayClick() {
        startActivity(new Intent(getContext(), ActSameBirthDay.class));
    }

    @OnClick(R.id.lblAllSameDay)
    public void onAllSameDayClick() {
        startActivity(new Intent(getContext(), ActSameDay.class));
    }

    @OnClick(R.id.lblAllSameMonth)
    public void onAllSameMonthClick() {
        startActivity(new Intent(getContext(), ActSameMonth.class));
    }

    @OnClick(R.id.lblAllSameYear)
    public void onAllSameYearClick() {
        startActivity(new Intent(getContext(), ActSameYear.class));
    }

    @OnClick(R.id.lblAllFellowCitizen)
    public void onAllFellowCitizenClick() {
        startActivity(new Intent(getContext(), ActSameLocation.class));
    }

    private String setDate() {
        String[] date = Gregorian.split("/");
        String currentDateTime = String.valueOf(Calendar.getInstance().getTime());
        String year = String.valueOf(Integer.valueOf(date[0]) - Integer.valueOf(currentDateTime.substring(30, 34)));
        if (year.startsWith("-")) {
            year = year.substring(1);
        }
        int birthDayMonth = 0;
        switch (currentDateTime.substring(4, 7).toLowerCase()) {
            case "jan":
                birthDayMonth = 1;
                break;
            case "feb":
                birthDayMonth = 2;
                break;
            case "mar":
                birthDayMonth = 3;
                break;
            case "apr":
                birthDayMonth = 4;
                break;
            case "may":
                birthDayMonth = 5;
                break;
            case "jun":
                birthDayMonth = 6;
                break;
            case "jul":
                birthDayMonth = 7;
                break;
            case "aug":
                birthDayMonth = 8;
                break;
            case "sep":
                birthDayMonth = 9;
                break;
            case "oct":
                birthDayMonth = 10;
                break;
            case "nov":
                birthDayMonth = 11;
                break;
            case "dec":
                birthDayMonth = 12;
                break;
        }
        String month = String.valueOf(Integer.valueOf(date[1]) - birthDayMonth);
        if (month.startsWith("-")) {
            month = month.substring(1);
        }
        String day = String.valueOf(Integer.valueOf(date[2]) - Integer.valueOf(currentDateTime.substring(8, 10)));
        if (day.startsWith("-")) {
            day = day.substring(1);
        }
        return "اختلاف زمان : " + year + " سال " + month + " ماه " + day + " روز ";
    }

    @OnClick(R.id.btnBirthDayFrSame)
    public void ClickSelectDate() {
        DatePickerDialog dialog = new DatePickerDialog();
        dialog.setThemeDark(false);
        dialog.show(getActivity().getFragmentManager(), "انخاب تاریخ تولد");
        dialog.setOnDateSetListener((view, year, monthOfYear, dayOfMonth) -> {
            Date = year + "-" + (monthOfYear + 1) + "-" + dayOfMonth;
            Lbls.get(13).setText(Date);
            btns.get(1).setVisibility(View.VISIBLE);
        });
    }

    @OnClick(R.id.btnBirthDateSubmitFrSame)
    public void ClickSubmitDate() {
        EasyPreference.with(getContext()).addString("register", "1");
        EasyPreference.with(getContext()).addString("BirthDay", AppUtill.PersianToGregorian(Date));
        init();
        Loading.setVisibility(View.VISIBLE);
    }
}
