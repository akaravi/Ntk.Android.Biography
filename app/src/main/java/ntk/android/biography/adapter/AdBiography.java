package ntk.android.biography.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.PorterDuff;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.android.biography.R;
import ntk.android.biography.activity.ActDetail;
import ntk.android.biography.utill.FontManager;
import ntk.base.api.article.model.ArticleContent;
import ntk.base.api.article.model.ArticleContentViewRequest;
import ntk.base.api.biography.model.BiographyContent;

public class AdBiography extends RecyclerView.Adapter<AdBiography.ViewHolder> {

    private List<BiographyContent> arrayList;
    private Context context;

    public AdBiography(Context context, List<BiographyContent> arrayList) {
        this.arrayList = arrayList;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.row_recycler_biography, viewGroup, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        holder.LblName.setText(arrayList.get(position).Title);
        holder.LblLike.setText(String.valueOf(arrayList.get(position).viewCount));
        DisplayImageOptions options = new DisplayImageOptions.Builder()
                .cacheOnDisk(true).build();
        ImageLoader.getInstance().displayImage(arrayList.get(position).imageSrc, holder.Img, options, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {
                holder.Progress.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                holder.Progress.setVisibility(View.GONE);
            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });

        if (arrayList.get(position).ScoreSumPercent == 0) {
            holder.Rate.setRating(0);
        } else {
            holder.Rate.setRating((arrayList.get(position).ScoreSumPercent / arrayList.get(position).ScoreSumClick));
        }

        holder.Img.setOnClickListener(view -> {
//            Intent intent = new Intent(context, ActDetail.class);
//            ArticleContentViewRequest request = new ArticleContentViewRequest();
//            request.Id = arrayList.get(position).Id;
//            intent.putExtra("Request", new Gson().toJson(request));
//            context.startActivity(intent);
        });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
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
            LblName.setTypeface(FontManager.GetTypeface(context, FontManager.IranSans));
            LblLike.setTypeface(FontManager.GetTypeface(context, FontManager.IranSans));
            Progress.getIndeterminateDrawable().setColorFilter(context.getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        }
    }
}
