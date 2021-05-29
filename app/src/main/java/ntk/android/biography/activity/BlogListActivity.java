package ntk.android.biography.activity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.Observable;
import java9.util.function.Function;
import ntk.android.base.activity.common.BaseFilterModelListActivity;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.base.FilterModel;
import ntk.android.base.entitymodel.blog.BlogContentModel;
import ntk.android.base.services.blog.BlogContentService;
import ntk.android.biography.adapter.BlogAdapter;

public class BlogListActivity extends BaseFilterModelListActivity<BlogContentModel> {
    @Override
    public RecyclerView.LayoutManager getRvLayoutManager() {
        return new GridLayoutManager(this, 2);
    }
    @Override
    public RecyclerView.Adapter createAdapter() {
        return new BlogAdapter(this,models);
    }
    @Override
    public Function<FilterModel, Observable<ErrorException<BlogContentModel>>> getService() {
        return new BlogContentService(this)::getAll;
    }

    @Override
    public void ClickSearch() {

    }
}
