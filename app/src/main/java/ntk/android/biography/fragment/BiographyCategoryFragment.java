package ntk.android.biography.fragment;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.google.android.material.snackbar.Snackbar;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterModel;
import ntk.android.base.entitymodel.biography.BiographyCategoryModel;
import ntk.android.base.services.biography.BiographyCategoryService;
import ntk.android.base.utill.FontManager;
import ntk.android.biography.R;
import ntk.android.biography.adapter.BiographyCategoryAdapter;
import ntk.android.biography.utill.AppUtill;

public class BiographyCategoryFragment extends Fragment {

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
        init();
        return view;
    }


    private void init() {
        LblProgress.setTypeface(FontManager.T1_Typeface(getContext()));
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


            FilterModel request = new FilterModel();
            request.RowPerPage = 20;
            new BiographyCategoryService(getContext()).getAll(request).observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new NtkObserver<ErrorException<BiographyCategoryModel>>() {


                        @Override
                        public void onNext(ErrorException<BiographyCategoryModel> response) {
                            BiographyCategoryAdapter adapter = new BiographyCategoryAdapter(getContext(), response.ListItems);
                            Rv.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
                            Loading.setVisibility(View.GONE);
                            Rv.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Loading.setVisibility(View.GONE);
                             Snackbar.make(layout, R.string.per_no_net, Snackbar.LENGTH_INDEFINITE).setAction(R.string.try_again, v -> init()).show();
                        }


                    });
        } else {
            Loading.setVisibility(View.GONE);
            Snackbar.make(layout, R.string.per_no_net, Snackbar.LENGTH_INDEFINITE).setAction(R.string.try_again, new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            }).show();
        }
    }

}
