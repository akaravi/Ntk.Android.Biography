package ntk.android.biography.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.android.biography.R;
import ntk.android.biography.library.scrollgallery.MediaInfo;
import ntk.android.biography.library.scrollgallery.ScrollGalleryView;
import ntk.android.biography.library.scrollgallery.loader.DefaultImageLoader;

public class ActPhotoGallery extends AppCompatActivity {

    @BindView(R.id.scroll_gallery_view)
    ScrollGalleryView Gallery;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_photo_gallery);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        String Request = getIntent().getExtras().getString("Request");
        String[] Links = Request.split("@");
        List<String> links = Arrays.asList(Links);
        List<MediaInfo> infos = new ArrayList<>(links.size());
        for (String url : links) infos.add(MediaInfo.mediaLoader(new DefaultImageLoader(url)));
        Gallery
                .setThumbnailSize(250)
                .setZoom(true)
                .setFragmentManager(getSupportFragmentManager())
                .addMedia(infos);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Gallery.clearGallery();
    }
}
