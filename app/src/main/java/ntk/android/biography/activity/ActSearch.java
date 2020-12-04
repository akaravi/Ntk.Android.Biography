package ntk.android.biography.activity;

import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.Observable;
import java9.util.function.Function;
import ntk.android.base.activity.abstraction.AbstractionSearchActivity;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.biography.BiographyContentModel;
import ntk.android.base.services.biography.BiographyContentService;
import ntk.android.biography.adapter.AdBiography;

public class ActSearch extends AbstractionSearchActivity<BiographyContentModel> {


    @Override
    protected RecyclerView.Adapter getAdapter() {
        return new AdBiography(this, models);
    }

    @Override
    public Function<FilterDataModel, Observable<ErrorException<BiographyContentModel>>> getService() {
        return filter -> new BiographyContentService(this).getAll(filter);
    }


}
