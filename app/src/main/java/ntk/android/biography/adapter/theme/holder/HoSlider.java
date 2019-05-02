package ntk.android.biography.adapter.theme.holder;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.android.biography.R;
import ss.com.bannerslider.views.BannerSlider;

public class HoSlider extends RecyclerView.ViewHolder {

    @BindView(R.id.RecyclerSliderRecyclerHome)
    public BannerSlider Slider;

    public HoSlider(@NonNull View view) {
        super(view);
        ButterKnife.bind(this, view);
    }
}
