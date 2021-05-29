package ntk.android.biography.activity;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.Observable;
import java9.util.function.Function;
import ntk.android.base.activity.common.BaseFilterModelListActivity;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.base.FilterModel;
import ntk.android.base.entitymodel.news.NewsContentModel;
import ntk.android.base.services.news.NewsContentService;
import ntk.android.biography.adapter.NewsAdapter;

public class NewsListActivity extends BaseFilterModelListActivity<NewsContentModel> {
        @Override
        public void afterInit() {
                findViewById(ntk.android.base.R.id.imgSearch).setVisibility(View.GONE);
        }

        @Override
        public Function<FilterModel, Observable<ErrorException<NewsContentModel>>> getService() {
                return new NewsContentService(this)::getAll;
        }

        @Override
        public RecyclerView.Adapter createAdapter() {
                return new NewsAdapter(this, models);
        }

        @Override
        public void ClickSearch() {
                //not have search
        }
}