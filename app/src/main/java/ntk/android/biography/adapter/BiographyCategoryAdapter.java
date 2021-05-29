package ntk.android.biography.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.balysv.materialripple.MaterialRippleLayout;
import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.android.base.Extras;
import ntk.android.base.adapter.BaseRecyclerAdapter;
import ntk.android.base.entitymodel.base.FilterDataModel;
import ntk.android.base.entitymodel.base.FilterModel;
import ntk.android.base.entitymodel.biography.BiographyCategoryModel;
import ntk.android.base.utill.FontManager;
import ntk.android.biography.R;
import ntk.android.biography.activity.BiographyListActivity;

public class BiographyCategoryAdapter extends BaseRecyclerAdapter<BiographyCategoryModel, BiographyCategoryAdapter.ViewHolder> {

    private Context context;

    public BiographyCategoryAdapter(Context context, List<BiographyCategoryModel> arrayList) {
        super(arrayList);
        this.context = context;
        drawable=R.drawable.news_placeholder;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view =  inflate(viewGroup, R.layout.row_recycler_category);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.LblName.setText(list.get(position).Title);
        loadImage(list.get(position).LinkMainImageIdSrc, holder.Img, holder.Progress);

        if (list.get(position).Children.size() == 0) {
            holder.ImgDrop.setVisibility(View.GONE);
        } else {
            holder.ImgDrop.setVisibility(View.VISIBLE);
        }
        holder.Img.setOnClickListener(view -> {
            FilterModel request = new FilterModel();

            FilterDataModel f = new FilterDataModel();
            f.PropertyName = "LinkCategoryId";
            f.setIntValue(list.get(position).Id);
            request.addFilter(f);
            Intent intent = new Intent(context, BiographyListActivity.class);
            intent.putExtra(Extras.EXTRA_FIRST_ARG, new Gson().toJson(request));
            context.startActivity(intent);
        });
        holder.ImgDrop.setOnClickListener(view -> {
            if (holder.Rv.getVisibility() == View.GONE) {
                holder.ImgArrow.setRotation(180);
                BiographyCategoryAdapter adapter = new BiographyCategoryAdapter(context, list.get(position).Children);
                holder.Rv.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                holder.Rv.setVisibility(View.VISIBLE);
            } else {
                holder.Rv.setAdapter(null);
                holder.Rv.setVisibility(View.GONE);
                holder.ImgArrow.setRotation(0);
            }
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lblRowRecyclerCategory)
        TextView LblName;

        @BindView(R.id.imgRowRecyclerCategory)
        ImageView Img;

        @BindView(R.id.imgArrow)
        ImageView ImgArrow;

        @BindView(R.id.recyclerSubCategory)
        RecyclerView Rv;

        @BindView(R.id.imgArrowRecyclerCategory)
        MaterialRippleLayout ImgDrop;

        @BindView(R.id.ProgressRecyclerCategory)
        ProgressBar Progress;

        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            LblName.setTypeface(FontManager.T1_Typeface(context ));
            Rv.setHasFixedSize(true);
            RecyclerView.LayoutManager manager = new LinearLayoutManager(context, LinearLayoutManager.VERTICAL, true);
            Rv.setLayoutManager(manager);
            Progress.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(R.color.colorWhite), PorterDuff.Mode.SRC_IN);
        }
    }
}
