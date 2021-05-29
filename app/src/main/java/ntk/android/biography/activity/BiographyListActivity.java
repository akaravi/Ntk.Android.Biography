package ntk.android.biography.activity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.Observable;
import java9.util.function.Function;
import ntk.android.base.activity.common.BaseFilterModelListActivity;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.base.FilterModel;
import ntk.android.base.entitymodel.biography.BiographyContentModel;
import ntk.android.base.services.biography.BiographyContentService;
import ntk.android.biography.adapter.BiographyGridAdapter;

public class BiographyListActivity extends BaseFilterModelListActivity<BiographyContentModel> {
    public RecyclerView.LayoutManager getRvLayoutManager() {
        return new GridLayoutManager(this, 2);
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return new BiographyGridAdapter(this, models);
    }

    @Override
    public Function<FilterModel, Observable<ErrorException<BiographyContentModel>>> getService() {
        return new BiographyContentService(this)::getAll;
    }

    @Override
    public void ClickSearch() {

    }
}
