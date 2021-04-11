package ntk.android.biography.fragment;

import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.Observable;
import java9.util.function.Function;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterModel;
import ntk.android.base.entitymodel.biography.BiographyContentModel;
import ntk.android.base.fragment.abstraction.AbstractionListFragment;
import ntk.android.base.fragment.common.BaseFilterModelFragment;
import ntk.android.base.services.biography.BiographyContentService;
import ntk.android.biography.adapter.BiographyAdapter;

public class BiographyFavoriteList extends BaseFilterModelFragment<BiographyContentModel> {
    @Override
    public Function<FilterModel, Observable<ErrorException<BiographyContentModel>>> getService() {
        return new BiographyContentService(getContext())::getFavoriteList;
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return new BiographyAdapter(getContext(), models);
    }

    @Override
    public void ClickSearch() {

    }
}
