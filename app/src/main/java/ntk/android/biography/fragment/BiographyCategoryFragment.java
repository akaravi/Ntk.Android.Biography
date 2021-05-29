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
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import java9.util.function.Function;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterModel;
import ntk.android.base.entitymodel.biography.BiographyCategoryModel;
import ntk.android.base.fragment.common.BaseFilterModelFragment;
import ntk.android.base.services.biography.BiographyCategoryService;
import ntk.android.base.utill.FontManager;
import ntk.android.biography.R;
import ntk.android.biography.adapter.BiographyCategoryAdapter;
import ntk.android.biography.utill.AppUtill;

public class BiographyCategoryFragment extends BaseFilterModelFragment<BiographyCategoryModel> {

    @Override
    public Function<FilterModel, Observable<ErrorException<BiographyCategoryModel>>> getService() {
        return new BiographyCategoryService(getContext())::getAll;
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return new BiographyCategoryAdapter(getContext(), models);
    }

}
