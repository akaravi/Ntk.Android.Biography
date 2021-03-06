package ntk.android.biography.library.scrollgallery;

import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {

    private List<MediaInfo> mListOfMedia;

    private boolean isZoom = false;

    private ScrollGalleryView.OnImageClickListener onImageClickListener;

    public ScreenSlidePagerAdapter(FragmentManager fm, List<MediaInfo> listOfMedia,
                                   boolean isZoom, ScrollGalleryView.OnImageClickListener onImageClickListener) {
        super(fm);
        this.mListOfMedia = listOfMedia;
        this.isZoom = isZoom;
        this.onImageClickListener = onImageClickListener;
    }

    @Override
    public Fragment getItem(int position) {
        Fragment fragment = null;
        if (position < mListOfMedia.size()) {
            MediaInfo mediaInfo = mListOfMedia.get(position);
            fragment = loadImageFragment(mediaInfo);
        }
        return fragment;
    }

    private Fragment loadImageFragment(MediaInfo mediaInfo) {
        ImageFragment fragment = new ImageFragment();
        fragment.setMediaInfo(mediaInfo);
        if (onImageClickListener != null) {
            fragment.setOnImageClickListener(onImageClickListener);
        }
        Bundle bundle = new Bundle();
        bundle.putBoolean(Constants.ZOOM, isZoom);
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return mListOfMedia.size();
    }
}
