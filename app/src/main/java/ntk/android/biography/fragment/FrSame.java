package ntk.android.biography.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

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
import ntk.base.api.utill.RetrofitManager;

public class FrSame extends Fragment {

    @BindViews({R.id.lblSameDay,
            R.id.lblAlsoMonth,
            R.id.lblSameYear,
            R.id.lblFellowCitizen,
            R.id.lblAllSameYear,
            R.id.lblAllAlsoMmonth,
            R.id.lblAllSameDay,
            R.id.lblAllFellowCitizen})
    List<TextView> Lbls;

    @BindViews({R.id.RecyclerSameDay,
            R.id.RecyclerAlsoMonth,
            R.id.RecyclerSameYear,
            R.id.RecyclerFellowCitizen})
    List<RecyclerView> Rvs;


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
    }

    private void RestCallOne() {
//        String date[] = EasyPreference.with(getContext()).getString("BirthDay" , "").split("-");
//        BiographyContentWithSimilarDatePeriodStartDayAndMonthOfYearListRequest model = new BiographyContentWithSimilarDatePeriodStartDayAndMonthOfYearListRequest();
//        model.MonthOfYear = Integer.parseInt(date[1]);
//        model.DayOfMonth = Integer.parseInt(date[2]);
//
//        RetrofitManager manager = new RetrofitManager(getContext());
//        IBiography iBiography = manager.getRetrofitUnCached(new ConfigStaticValue(getContext()).GetApiBaseUrl()).create(IBiography.class);
//
//        Observable<BiographyContentResponse> Call = iBiography.GetContentWithSimilarDatePeriodStartDayAndMonthOfYearList(new ConfigRestHeader().GetHeaders(getContext()), model);
//        Call.observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Observer<BiographyContentResponse>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(BiographyContentResponse response) {
//                        if (response.IsSuccess) {
//                            if (response.ListItems.size() != 0) {
//                                AdBiography adapter = new AdBiography(getContext(), response.ListItems);
//                                Rvs.get(0).setAdapter(adapter);
//                                adapter.notifyDataSetChanged();
//                            } else {
//                                Rvs.get(0).setVisibility(View.GONE);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }

    private void RestCallTwo() {
//        BiographyContentWithDatePeriodStartListRequest request = new BiographyContentWithDatePeriodStartListRequest();
//        request.SearchDateMin = EasyPreference.with(getContext()).getString("BirthDay", "");
//        request.SearchDateMax = EasyPreference.with(getContext()).getString("BirthDay", "");
//
//        RetrofitManager manager = new RetrofitManager(getContext());
//        IBiography iBiography = manager.getRetrofitUnCached(new ConfigStaticValue(getContext()).GetApiBaseUrl()).create(IBiography.class);
//
//        Observable<BiographyContentResponse> Call = iBiography.GetContentWithDatePeriodStartList(new ConfigRestHeader().GetHeaders(getContext()), request);
//        Call.observeOn(AndroidSchedulers.mainThread())
//                .subscribeOn(Schedulers.io())
//                .subscribe(new Observer<BiographyContentResponse>() {
//                    @Override
//                    public void onSubscribe(Disposable d) {
//
//                    }
//
//                    @Override
//                    public void onNext(BiographyContentResponse response) {
//                        if (response.IsSuccess) {
//                            if (response.ListItems.size() != 0) {
//                                AdBiography adapter = new AdBiography(getContext(), response.ListItems);
//                                Rvs.get(0).setAdapter(adapter);
//                                adapter.notifyDataSetChanged();
//                            } else {
//                                Rvs.get(0).setVisibility(View.GONE);
//                            }
//                        }
//                    }
//
//                    @Override
//                    public void onError(Throwable e) {
//
//                    }
//
//                    @Override
//                    public void onComplete() {
//
//                    }
//                });
    }

    private void RestCallThree() {

    }
}
