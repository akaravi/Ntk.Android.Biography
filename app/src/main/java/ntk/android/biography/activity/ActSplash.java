package ntk.android.biography.activity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.biography.BuildConfig;
import ntk.android.biography.R;
import ntk.android.biography.config.ConfigRestHeader;
import ntk.android.biography.config.ConfigStaticValue;
import ntk.android.biography.utill.AppUtill;
import ntk.android.biography.utill.FontManager;
import ntk.base.api.core.interfase.ICore;
import ntk.base.api.core.model.MainCoreResponse;
import ntk.base.api.utill.RetrofitManager;

public class ActSplash extends AppCompatActivity {


    @BindView(R.id.lblVersionActSplash)
    TextView Lbl;

    @BindView(R.id.btnRefreshActSplash)
    Button BtnRefresh;

    @BindView(R.id.lblActSplash)
    TextView Title;

    public static String APPLICATION_START = "start";
    private Handler handler = new Handler();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_splash);
        ButterKnife.bind(this);
        init();
    }

    @SuppressLint("SetTextI18n")
    private void init() {
        Lbl.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        Lbl.setText("نسخه  " + (int) Float.parseFloat(BuildConfig.VERSION_NAME) + "." + BuildConfig.VERSION_CODE);
        BtnRefresh.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        Title.setTypeface(FontManager.GetTypeface(this, FontManager.Harlow));
    }

    @Override
    protected void onStart() {
        super.onStart();
        HandelData(14000);
    }

    private void HandelData(int time) {
        if (AppUtill.isNetworkAvailable(this)) {
            RetrofitManager manager = new RetrofitManager(this);
            ICore iCore = manager.getCachedRetrofit(new ConfigStaticValue(this).GetApiBaseUrl()).create(ICore.class);
            Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);
            Observable<MainCoreResponse> observable = iCore.GetResponseMain(headers);
            observable.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<MainCoreResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(MainCoreResponse mainCoreResponse) {
                            if(!mainCoreResponse.IsSuccess)
                            {
                                BtnRefresh.setVisibility(View.VISIBLE);
                                Toasty.warning(ActSplash.this, "خطای سامانه مجددا تلاش کنید"+mainCoreResponse.ErrorMessage, Toasty.LENGTH_LONG, true).show();
                                return;
                            }
                            handler.postDelayed(() -> {
                                startActivity(new Intent(ActSplash.this, ActMain.class).putExtra(APPLICATION_START, true));
                                finish();
                            }, time);
                        }

                        @Override
                        public void onError(Throwable e) {
                            BtnRefresh.setVisibility(View.VISIBLE);
                            Toasty.warning(ActSplash.this, "خطای سامانه مجددا تلاش کنید", Toasty.LENGTH_LONG, true).show();

                        }

                        @Override
                        public void onComplete() {

                        }

                    });
        } else {
            BtnRefresh.setVisibility(View.VISIBLE);
            Toasty.warning(this, "عدم دسترسی به اینترنت", Toasty.LENGTH_LONG, true).show();
        }
    }

    @OnClick(R.id.btnRefreshActSplash)
    public void ClickRefresh() {
        BtnRefresh.setVisibility(View.GONE);
        HandelData(14000);
    }

    @OnClick(R.id.mainLayoutActSplash)
    public void onPageClick() {
        handler.removeCallbacksAndMessages(null);
        HandelData(0);
    }
}
