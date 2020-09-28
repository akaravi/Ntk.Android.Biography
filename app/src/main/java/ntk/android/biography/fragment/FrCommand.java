package ntk.android.biography.fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.fragment.app.Fragment;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.biography.R;
import ntk.android.biography.adapter.AdCategory;
import ntk.android.biography.config.ConfigRestHeader;
import ntk.android.biography.config.ConfigStaticValue;
import ntk.android.biography.utill.AppUtill;
import ntk.android.biography.utill.FontManager;
import ntk.base.api.biography.interfase.IBiography;
import ntk.base.api.biography.model.BiographyCategoryRequest;
import ntk.base.api.biography.model.BiographyCategoryResponse;
import ntk.base.api.utill.RetrofitManager;

public class FrCommand extends Fragment {

    @BindView(R.id.recyclerCategory)
    RecyclerView Rv;

    @BindView(R.id.RefreshFrCategory)
    SwipeRefreshLayout Refresh;

    @BindView(R.id.lblProgressFrCategory)
    TextView LblProgress;

    @BindView(R.id.progressFrCategory)
    ProgressBar Progress;

    @BindView(R.id.rowProgressFrCategory)
    LinearLayout Loading;

    @BindView(R.id.mainLayoutFrCommend)
    CoordinatorLayout layout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fr_category, container, false);
        ButterKnife.bind(this, view);
        configStaticValue = new ConfigStaticValue(this.getContext());
        init();
        return view;
    }

    private ConfigStaticValue configStaticValue;

    private void init() {
        LblProgress.setTypeface(FontManager.GetTypeface(getContext(), FontManager.IranSans));
        Progress.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);

        Rv.setHasFixedSize(true);
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        Rv.setLayoutManager(manager);
        Loading.setVisibility(View.VISIBLE);

        HandelRest();

        Refresh.setColorSchemeResources(
                R.color.colorAccent,
                R.color.colorAccent,
                R.color.colorAccent);

        Refresh.setOnRefreshListener(() -> {
            HandelRest();
            Refresh.setRefreshing(false);
        });
    }

    private void HandelRest() {
        if (AppUtill.isNetworkAvailable(getContext())) {
            RetrofitManager manager = new RetrofitManager(getContext());
            IBiography iBiography = manager.getCachedRetrofit(configStaticValue.GetApiBaseUrl()).create(IBiography.class);
            Map<String, String> headers = new ConfigRestHeader().GetHeaders(getContext());

            BiographyCategoryRequest request = new BiographyCategoryRequest();
            request.RowPerPage = 20;
            Observable<BiographyCategoryResponse> call = iBiography.GetCategoryList(headers, request);
            call.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<BiographyCategoryResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BiographyCategoryResponse response) {
                            AdCategory adapter = new AdCategory(getContext(), response.ListItems);
                            Rv.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            Loading.setVisibility(View.GONE);
                            Rv.setVisibility(View.VISIBLE);
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
