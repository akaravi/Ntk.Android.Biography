package ntk.android.biography.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.biography.R;
import ntk.android.biography.config.ConfigRestHeader;
import ntk.android.biography.config.ConfigStaticValue;
import ntk.android.biography.utill.AppUtill;
import ntk.android.biography.utill.EasyPreference;
import ntk.android.biography.utill.FontManager;
import ntk.base.api.application.interfase.IApplication;
import ntk.base.api.application.model.ApplicationIntroRequest;
import ntk.base.api.application.model.ApplicationIntroResponse;
import ntk.base.api.utill.RetrofitManager;

public class ActIntro extends AppCompatActivity {

    @BindViews({R.id.lblTitleActIntro, R.id.lblDescriptionActIntro, R.id.lblBtnAfterActIntro})
    List<TextView> Lbls;

    @BindView(R.id.imgPhotoActIntro)
    ImageView Img;

    @BindView(R.id.mainLayoutActIntro)
    CoordinatorLayout layout;

    private ApplicationIntroResponse Intro = new ApplicationIntroResponse();
    private int CountIntro = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_intro);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        if (AppUtill.isNetworkAvailable(this)) {
            Lbls.get(0).setTypeface(FontManager.GetTypeface(this, FontManager.IranSansBold));
            Lbls.get(1).setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
            Lbls.get(2).setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
            RetrofitManager manager = new RetrofitManager(this);
            IApplication iApplication = manager.getCachedRetrofit(new ConfigStaticValue(this).GetApiBaseUrl()).create(IApplication.class);
            Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);
            ApplicationIntroRequest request = new ApplicationIntroRequest();
            Observable<ApplicationIntroResponse> Call = iApplication.GetApplicationIntro(headers, request);
            Call.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<ApplicationIntroResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(ApplicationIntroResponse response) {
                            if (response.ListItems.size() != 0) {
                                Intro.ListItems = response.ListItems;
                                HandelIntro();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            Snackbar.make(layout, "خطای سامانه مجددا تلاش کنید", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    init();
                                }
                            }).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            Snackbar.make(layout, "عدم دسترسی به اینترنت", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    init();
                }
            }).show();
        }
    }

    private void HandelIntro() {
        ImageLoader.getInstance().displayImage(Intro.ListItems.get(CountIntro).MainImageSrc, Img);
        Lbls.get(0).setText(Intro.ListItems.get(CountIntro).Title);
        Lbls.get(1).setText(Intro.ListItems.get(CountIntro).Description);
    }

    @OnClick(R.id.btnBeforeActIntro)
    public void ClickBefore() {
        if (CountIntro > 0) {
            CountIntro = CountIntro - 1;
            if (CountIntro == 0) {
                findViewById(R.id.btnBeforeActIntro).setVisibility(View.INVISIBLE);
                HandelIntro();
            }
            Lbls.get(2).setText("بعدی");
        }
    }

    @OnClick(R.id.btnAfterActIntro)
    public void ClickAfter() {
        if (CountIntro < Intro.ListItems.size()) {
            CountIntro = CountIntro + 1;
            findViewById(R.id.btnBeforeActIntro).setVisibility(View.VISIBLE);
            HandelIntro();
            if (CountIntro == Intro.ListItems.size()) {
                Lbls.get(2).setText("شروع");
            }
        } else {
            EasyPreference.with(this).addBoolean("Intro", true);
            new Handler().postDelayed(() -> {
                startActivity(new Intent(ActIntro.this, ActMain.class));
                finish();
            }, 3000);
        }
    }
}
