package ntk.android.biography.activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;

import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.biography.R;
import ntk.android.biography.adapter.AdDetailPoolCategory;
import ntk.android.biography.config.ConfigRestHeader;
import ntk.android.biography.config.ConfigStaticValue;
import ntk.android.biography.utill.AppUtill;
import ntk.android.biography.utill.FontManager;
import ntk.base.api.pooling.interfase.IPooling;
import ntk.base.api.pooling.model.PoolingContentListRequest;
import ntk.base.api.pooling.model.PoolingContentListResponse;
import ntk.base.api.utill.RetrofitManager;

public class ActDetailPooling extends AppCompatActivity {

    @BindView(R.id.lblTitleActDetailPooling)
    TextView LblTitle;

    @BindView(R.id.recyclerDetailPooling)
    RecyclerView Rv;

    @BindView(R.id.mainLayoutActDetailPooling)
    CoordinatorLayout layout;

    private String RequestStr;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detail_pooling);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        LblTitle.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        LblTitle.setText(getIntent().getStringExtra("Title"));
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));


        RequestStr = getIntent().getExtras().getString("Request");

        HandelData(1, new Gson().fromJson(RequestStr, PoolingContentListRequest.class));
    }

    private void HandelData(int i, PoolingContentListRequest request) {
        if (AppUtill.isNetworkAvailable(this)) {
            RetrofitManager retro = new RetrofitManager(this);
            IPooling iPooling = retro.getRetrofitUnCached(new ConfigStaticValue(this).GetApiBaseUrl()).create(IPooling.class);
            Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);
            Observable<PoolingContentListResponse> call = iPooling.GetContentList(headers, request);
            call.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<PoolingContentListResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(PoolingContentListResponse poolingContentListResponse) {
                            if (poolingContentListResponse.IsSuccess) {
                                AdDetailPoolCategory adapter = new AdDetailPoolCategory(ActDetailPooling.this, poolingContentListResponse.ListItems);
                                Rv.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
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

    @OnClick(R.id.imgBackActDetailPooling)
    public void ClickBack() {
        finish();
    }
}
