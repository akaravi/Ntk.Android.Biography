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
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.base.Filters;
import ntk.android.base.entitymodel.biography.BiographyContentTagModel;
import ntk.android.biography.R;
import ntk.android.biography.activity.BiographyListActivity;
import ntk.android.biography.utill.FontManager;

public class BiographyTagAdapter extends RecyclerView.Adapter<BiographyTagAdapter.ViewHolder> {

    private List<BiographyContentTagModel> list;
    private Context context;

    public BiographyTagAdapter(Context context, List<BiographyContentTagModel> arrayList) {
        this.list = arrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_recycler_circle, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
//todo add tags
//        holder.Lbl.setText(list.get(position).Title);
        holder.Lbl.setOnClickListener(view -> {
            Intent intent = new Intent(context, BiographyListActivity.class);
            FilterDataModel request = new FilterDataModel();
            request.addFilter(new Filters().setPropertyName("ContentTagId").setIntValue1(list.get(position).Id));
            intent.putExtra(Extras.EXTRA_FIRST_ARG, new Gson().toJson(request));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lblTitleCategory)
        TextView Lbl;

        @BindView(R.id.rooCategory)
        LinearLayout Root;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            Lbl.setTypeface(FontManager.GetTypeface(context, FontManager.IranSans));
        }
    }
}
