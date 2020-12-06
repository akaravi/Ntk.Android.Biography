package ntk.android.biography.fragment;

import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.Observable;
import java9.util.function.Function;
import ntk.android.base.activity.common.BaseFilterModelListActivity;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.biography.BiographyContentModel;
import ntk.android.base.services.biography.BiographyContentService;
import ntk.android.biography.adapter.BiographyAdapter;

public class BiographyFavoriteList extends BaseFilterModelListActivity<BiographyContentModel> {
    @Override
    public Function<FilterDataModel, Observable<ErrorException<BiographyContentModel>>> getService() {
        return new BiographyContentService(this)::getFavoriteList;
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return new BiographyAdapter(this, models);
    }

    @Override
    public void ClickSearch() {

    }
}
