package ntk.android.biography.activity;

import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import ntk.android.base.activity.blog.BaseBlogDetail_1_Activity;
import ntk.android.base.entitymodel.blog.BlogCommentModel;
import ntk.android.base.entitymodel.blog.BlogContentOtherInfoModel;
import ntk.android.biography.adapter.BlogCommentAdapter;
import ntk.android.biography.adapter.TabBlogAdapter;

public class BlogDetailActivity extends BaseBlogDetail_1_Activity {

    @Override
    public RecyclerView.Adapter createCommentAdapter(List<BlogCommentModel> listItems) {
        return new BlogCommentAdapter(this, listItems);
    }

    @Override
    protected RecyclerView.Adapter createOtherInfoAdapter(List<BlogContentOtherInfoModel> info) {
        return new TabBlogAdapter(this, info);
    }
}