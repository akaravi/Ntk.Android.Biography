package ntk.android.biography.adapter.theme.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.android.biography.R;

public class HoTag extends RecyclerView.ViewHolder {

    @BindView(R.id.RecyclerTagRecyclerHome)
    public RecyclerView RvTag;

    public HoTag(Context context , View view) {
        super(view);
        ButterKnife.bind(this, view);
        RvTag.setHasFixedSize(true);
    }
}
