package ntk.android.biography.activity;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ntk.android.base.activity.biography.BaseBiographyDetail_2_Activity;
import ntk.android.base.entitymodel.biography.BiographyCommentModel;
import ntk.android.base.entitymodel.biography.BiographyContentModel;
import ntk.android.base.entitymodel.biography.BiographyContentOtherInfoModel;
import ntk.android.biography.adapter.BiographyAdapter;
import ntk.android.biography.adapter.BiographyCommentAdapter;
import ntk.android.biography.adapter.TabBiographyAdapter;

public class BiographyDetailActivity  extends BaseBiographyDetail_2_Activity {

    @Override
    public RecyclerView.Adapter createCommentAdapter(List<BiographyCommentModel> listItems) {
        return new BiographyCommentAdapter(this,listItems);
    }

    @Override
    protected RecyclerView.Adapter createOtherInfoAdapter(List<BiographyContentOtherInfoModel> info) {
        return new TabBiographyAdapter(this,info);
    }

    @Override
    protected RecyclerView.Adapter createSimilarContentAdapter(List<BiographyContentModel> listItems) {
        return new BiographyAdapter(this,listItems);
    }

    @Override
    protected RecyclerView.Adapter createSimilarCategoryAdapter(List<BiographyContentModel> listItems) {
        return new BiographyAdapter(this,listItems);
    }


    @Override
    protected void showErrorDialog(String toString, Runnable onTryingAgain) {

    }
}
