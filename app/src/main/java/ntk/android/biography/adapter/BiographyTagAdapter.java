package ntk.android.biography.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.google.gson.Gson;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.android.base.Extras;
import ntk.android.base.adapter.BaseRecyclerAdapter;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.base.FilterModel;
import ntk.android.base.entitymodel.coremodulemain.CoreModuleTagModel;
import ntk.android.base.utill.FontManager;
import ntk.android.biography.R;
import ntk.android.biography.activity.BiographyListActivity;

public class BiographyTagAdapter extends BaseRecyclerAdapter<CoreModuleTagModel,BiographyTagAdapter.ViewHolder> {

    private Context context;

    public BiographyTagAdapter(Context context, List<CoreModuleTagModel> arrayList) {
        super(arrayList);
        this.context = context;
        drawable=R.drawable.tag_placeholder;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflate(viewGroup,R.layout.row_recycler_circle);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.Lbl.setText(list.get(position).Title);
        holder.Lbl.setOnClickListener(view -> {
           //todo change filtermodel
            Intent intent = new Intent(context, BiographyListActivity.class);
            FilterModel request = new FilterModel();
            request.addFilter(new FilterDataModel().setPropertyName("ContentTagId").setIntValue(list.get(position).Id));
            intent.putExtra(Extras.EXTRA_FIRST_ARG, new Gson().toJson(request));
            context.startActivity(intent);
        });
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lblTitleCategory)
        TextView Lbl;

        @BindView(R.id.rooCategory)
        LinearLayout Root;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            Lbl.setTypeface(FontManager.T1_Typeface(context));
        }
    }
}
