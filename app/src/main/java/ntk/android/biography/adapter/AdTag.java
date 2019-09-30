package ntk.android.biography.adapter;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.android.biography.R;
import ntk.android.biography.activity.ActBiographyContentList;
import ntk.android.biography.utill.FontManager;
import ntk.base.api.biography.model.BiographyContentListRequest;
import ntk.base.api.biography.entity.BiographyTag;

public class AdTag extends RecyclerView.Adapter<AdTag.ViewHolder> {

    private List<BiographyTag> arrayList;
    private Context context;

    public AdTag(Context context, List<BiographyTag> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_recycler_circle, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {

        holder.Lbl.setText(arrayList.get(position).Title);
        holder.Lbl.setOnClickListener(view -> {
            Intent intent = new Intent(context, ActBiographyContentList.class);
            BiographyContentListRequest request = new BiographyContentListRequest();
            List<Long> Tags = new ArrayList<>();
            Tags.add(arrayList.get(position).Id);
            request.TagIds = Tags;
            intent.putExtra("Request", new Gson().toJson(request));
            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
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
