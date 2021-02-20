package ntk.android.biography.activity;

import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.Observable;
import java9.util.function.Function;
import ntk.android.base.activity.abstraction.AbstractSearchActivity;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterModel;
import ntk.android.base.entitymodel.biography.BiographyContentModel;
import ntk.android.base.services.biography.BiographyContentService;
import ntk.android.biography.adapter.BiographyAdapter;

public class ActSearch extends AbstractSearchActivity<BiographyContentModel> {


    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new BiographyAdapter(this, models);
    }

    @Override
    public Function<FilterModel, Observable<ErrorException<BiographyContentModel>>> getService() {
        return filter -> new BiographyContentService(this).getAll(filter);
    }


}
