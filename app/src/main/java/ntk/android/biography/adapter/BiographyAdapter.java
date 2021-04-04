package ntk.android.biography.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.nostra13.universalimageloader.core.DisplayImageOptions;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.android.base.Extras;
import ntk.android.base.adapter.BaseRecyclerAdapter;
import ntk.android.base.entitymodel.biography.BiographyContentModel;
import ntk.android.base.services.base.CmsApiScoreApi;
import ntk.android.base.utill.FontManager;
import ntk.android.biography.R;
import ntk.android.biography.activity.BiographyDetailActivity;

public class BiographyAdapter extends BaseRecyclerAdapter<BiographyContentModel, BiographyAdapter.ViewHolder> {

    private Context context;

    public BiographyAdapter(Context context, List<BiographyContentModel> arrayList) {
        super(arrayList);
        this.context = context;
        drawable = R.drawable.news_placeholder;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = inflate(viewGroup, R.layout.row_recycler_biography);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        BiographyContentModel item = getItem(position);
        holder.LblName.setText(item.Title);
        holder.LblLike.setText(String.valueOf(item.ViewCount));
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).build();
        loadImage(item.LinkMainImageIdSrc, holder.Img, holder.Progress);
        double rating = CmsApiScoreApi.CONVERT_TO_RATE(item.ViewCount, item.ScoreSumPercent);
        holder.Rate.setRating((float) rating);

        holder.Root.setOnClickListener(view -> {
            Intent intent = new Intent(context, BiographyDetailActivity.class);
            intent.putExtra(Extras.EXTRA_FIRST_ARG, item.Id);
            context.startActivity(intent);
        });
    }


    public class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.lblNameRowRecyclerBiography)
        TextView LblName;

        @BindView(R.id.lblLikeRowRecyclerBiography)
        TextView LblLike;

        @BindView(R.id.imgRowRecyclerBiography)
        ImageView Img;

        @BindView(R.id.ratingBarRowRecyclerBiography)
        RatingBar Rate;

        @BindView(R.id.rootBiography)
        CardView Root;

        @BindView(R.id.ProgressRecyclerBiography)
        ProgressBar Progress;


        public ViewHolder(View view) {
            super(view);
            ButterKnife.bind(this, view);
            LblName.setTypeface(FontManager.T1_Typeface(context));
            LblLike.setTypeface(FontManager.T1_Typeface(context));
            Progress.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        }
    }
}
