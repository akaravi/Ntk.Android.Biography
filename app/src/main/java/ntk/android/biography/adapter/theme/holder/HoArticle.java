package ntk.android.biography.adapter.theme.holder;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import java.util.List;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import ntk.android.biography.R;
import ntk.android.biography.utill.FontManager;

public class HoArticle extends RecyclerView.ViewHolder {

    @BindView(R.id.RecyclerMenuRecyclerHome)
    public RecyclerView RvMenu;

    @BindViews({R.id.lblMenuRecyclerHome, R.id.lblAllMenuRecyclerHome})
    public List<TextView> Lbls;

    public HoArticle(Context context, View view) {
        super(view);
        ButterKnife.bind(this, view);
        RvMenu.setHasFixedSize(true);
        Lbls.get(0).setTypeface(FontManager.GetTypeface(context, FontManager.IranSans));
        Lbls.get(1).setTypeface(FontManager.GetTypeface(context, FontManager.IranSans));
    }
}
