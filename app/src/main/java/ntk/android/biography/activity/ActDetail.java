package ntk.android.biography.activity;

import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Html;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.BindViews;
import butterknife.ButterKnife;
import butterknife.OnClick;
import es.dmoral.toasty.Toasty;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.biography.R;
import ntk.android.biography.adapter.AdBiography;
import ntk.android.biography.adapter.AdComment;
import ntk.android.biography.adapter.AdTab;
import ntk.android.biography.config.ConfigRestHeader;
import ntk.android.biography.config.ConfigStaticValue;
import ntk.android.biography.event.EvHtmlBody;
import ntk.android.biography.utill.AppUtill;
import ntk.android.biography.utill.FontManager;
import ntk.base.api.biography.interfase.IBiography;
import ntk.base.api.biography.model.BiographyCommentAddRequest;
import ntk.base.api.biography.model.BiographyCommentListRequest;
import ntk.base.api.biography.model.BiographyCommentResponse;
import ntk.base.api.biography.model.BiographyContentCategoryListRequest;
import ntk.base.api.biography.model.BiographyContentFavoriteAddRequest;
import ntk.base.api.biography.model.BiographyContentFavoriteAddResponse;
import ntk.base.api.biography.model.BiographyContentFavoriteRemoveRequest;
import ntk.base.api.biography.model.BiographyContentFavoriteRemoveResponse;
import ntk.base.api.biography.model.BiographyContentOtherInfo;
import ntk.base.api.biography.model.BiographyContentOtherInfoRequest;
import ntk.base.api.biography.model.BiographyContentOtherInfoResponse;
import ntk.base.api.biography.model.BiographyContentResponse;
import ntk.base.api.biography.model.BiographyContentSimilarListRequest;
import ntk.base.api.biography.model.BiographyContentViewRequest;
import ntk.base.api.model.ErrorException;
import ntk.base.api.model.Filters;
import ntk.base.api.utill.RetrofitManager;

public class ActDetail extends AppCompatActivity {

    @BindView(R.id.progressActDetail)
    ProgressBar Progress;

    @BindView(R.id.rowProgressActDetail)
    LinearLayout Loading;

    @BindViews({R.id.lblTitleActDetail,
            R.id.lblNameCommandActDetail,
            R.id.lblKeySeenActDetail,
            R.id.lblValueSeenActDetail,
            R.id.lblMenuActDetail,
            R.id.lblMenuTwoActDetail,
            R.id.lblCommentActDetail,
            R.id.lblProgressActDetail
    })
    List<TextView> Lbls;

    @BindView(R.id.imgHeaderActDetail)
    ImageView ImgHeader;

    @BindView(R.id.recyclerMenuActDetail)
    RecyclerView RvSimilarBiography;

    @BindView(R.id.recyclerMenuTwoActDetail)
    RecyclerView RvSimilarCategory;

    @BindView(R.id.WebViewActDetail)
    WebView webView;

    @BindView(R.id.recyclerTabActDetail)
    RecyclerView RvTab;

    @BindView(R.id.recyclerCommentActDetail)
    RecyclerView RvComment;

    @BindView(R.id.ratingBarActDetail)
    RatingBar Rate;

    @BindView(R.id.PageActDetail)
    LinearLayout Page;
    @BindView(R.id.btnRefreshActDetail)
    Button btnRefresh;

    private String RequestStr;
    private BiographyContentResponse model;
    private BiographyContentOtherInfoResponse Info;
    private BiographyContentViewRequest Request;
    private ConfigStaticValue configStaticValue;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_detail);
        ButterKnife.bind(this);
        configStaticValue = new ConfigStaticValue(this);
        init();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void init() {
        for (TextView tv : Lbls) {
            tv.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        }
        Progress.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.colorAccent), PorterDuff.Mode.SRC_IN);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setBuiltInZoomControls(true);
        RvTab.setHasFixedSize(true);
        RvTab.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        RequestStr = getIntent().getExtras().getString("Request");
        Request = new Gson().fromJson(RequestStr, BiographyContentViewRequest.class);
        HandelDataContent(Request);
        Loading.setVisibility(View.VISIBLE);

        RvComment.setHasFixedSize(true);
        RvComment.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        RvSimilarBiography.setHasFixedSize(true);
        RvSimilarBiography.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

        RvSimilarCategory.setHasFixedSize(true);
        RvSimilarCategory.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, true));

        if (AppUtill.isNetworkAvailable(this)) {
            Rate.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
                BiographyContentViewRequest request = new BiographyContentViewRequest();
                request.Id = Request.Id;
                request.ActionClientOrder = 55;
                if (rating == 0.5) {
                    request.ScorePercent = 10;
                }
                if (rating == 1) {
                    request.ScorePercent = 20;
                }
                if (rating == 1.5) {
                    request.ScorePercent = 30;
                }
                if (rating == 2) {
                    request.ScorePercent = 40;
                }
                if (rating == 2.5) {
                    request.ScorePercent = 50;
                }
                if (rating == 3) {
                    request.ScorePercent = 60;
                }
                if (rating == 3.5) {
                    request.ScorePercent = 70;
                }
                if (rating == 4) {
                    request.ScorePercent = 80;
                }
                if (rating == 4.5) {
                    request.ScorePercent = 90;
                }
                if (rating == 5) {
                    request.ScorePercent = 100;
                }
                RetrofitManager manager = new RetrofitManager(ActDetail.this);
                IBiography iBiography = manager.getRetrofitUnCached(new ConfigStaticValue(ActDetail.this).GetApiBaseUrl()).create(IBiography.class);
                Map<String, String> headers = new ConfigRestHeader().GetHeaders(ActDetail.this);

                Observable<BiographyContentResponse> Call = iBiography.GetContentView(headers, request);
                Call.observeOn(AndroidSchedulers.mainThread())
                        .subscribeOn(Schedulers.io())
                        .subscribe(new Observer<BiographyContentResponse>() {
                            @Override
                            public void onSubscribe(Disposable d) {

                            }

                            @Override
                            public void onNext(BiographyContentResponse biographyContentResponse) {
                                if (biographyContentResponse.IsSuccess) {
                                } else {
                                }
                            }

                            @Override
                            public void onError(Throwable e) {
                                Toasty.warning(ActDetail.this, "خطای سامانه مجددا تلاش کنید", Toasty.LENGTH_LONG, true).show();
                            }

                            @Override
                            public void onComplete() {

                            }
                        });
            });
        } else {
            Toasty.warning(this, "عدم دسترسی به اینترنت", Toasty.LENGTH_LONG, true).show();
        }
    }


    private void HandelDataContent(BiographyContentViewRequest request) {
        if (AppUtill.isNetworkAvailable(this)) {
            RetrofitManager retro = new RetrofitManager(this);
            IBiography iBiography = retro.getRetrofitUnCached(configStaticValue.GetApiBaseUrl()).create(IBiography.class);
            Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);

            Observable<BiographyContentResponse> call = iBiography.GetContentView(headers, request);
            call.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<BiographyContentResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BiographyContentResponse biographyContentResponse) {
                            model = biographyContentResponse;
                            SetData(model);
                            HandelSimilary(Request.Id);
                            HandelSimilaryCategory(Request.Id);
                            if (Request.Id > 0) {
                                HandelDataContentOtherInfo(Request.Id);
                                HandelDataComment(Request.Id);
                            }
                            Loading.setVisibility(View.GONE);
                            Page.setVisibility(View.VISIBLE);
                        }

                        @Override
                        public void onError(Throwable e) {
                            Loading.setVisibility(View.GONE);
                            btnRefresh.setVisibility(View.VISIBLE);
                            Toasty.warning(ActDetail.this, "خطای سامانه مجددا تلاش کنید", Toasty.LENGTH_LONG, true).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            btnRefresh.setVisibility(View.VISIBLE);
            Toasty.warning(this, "عدم دسترسی به اینترنت", Toasty.LENGTH_LONG, true).show();
        }
    }

    private void HandelSimilary(long id) {
        if (AppUtill.isNetworkAvailable(this)) {
            RetrofitManager manager = new RetrofitManager(this);
            IBiography iBiography = manager.getCachedRetrofit(new ConfigStaticValue(this).GetApiBaseUrl()).create(IBiography.class);
            Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);

            BiographyContentSimilarListRequest request = new BiographyContentSimilarListRequest();
            request.LinkContentId = id;

            Observable<BiographyContentResponse> call = iBiography.GetContentSimilarList(headers, request);
            call.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<BiographyContentResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BiographyContentResponse response) {
                            if (response.ListItems.size() == 0) {
                                findViewById(R.id.RowSimilaryActDetail).setVisibility(View.GONE);
                            } else {
                                AdBiography adapter = new AdBiography(ActDetail.this, response.ListItems);
                                RvSimilarBiography.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                                findViewById(R.id.RowSimilaryActDetail).setVisibility(View.VISIBLE);
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            btnRefresh.setVisibility(View.VISIBLE);
                            Toasty.warning(ActDetail.this, "خطای سامانه مجددا تلاش کنید", Toasty.LENGTH_LONG, true).show();

                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            btnRefresh.setVisibility(View.VISIBLE);
            Toasty.warning(this, "عدم دسترسی به اینترنت", Toasty.LENGTH_LONG, true).show();
        }
    }

    private void HandelSimilaryCategory(long id) {
        if (AppUtill.isNetworkAvailable(this)) {
            RetrofitManager manager = new RetrofitManager(this);
            IBiography iBiography = manager.getCachedRetrofit(new ConfigStaticValue(this).GetApiBaseUrl()).create(IBiography.class);
            Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);

            BiographyContentCategoryListRequest request = new BiographyContentCategoryListRequest();
            request.LinkContentId = id;

            Observable<BiographyContentResponse> call = iBiography.GetContentCategoryList(headers, request);
            call.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<BiographyContentResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BiographyContentResponse response) {
                            if (response.ListItems.size() == 0) {
                                findViewById(R.id.RowSimilaryCategoryActDetail).setVisibility(View.GONE);
                            } else {
                                findViewById(R.id.RowSimilaryCategoryActDetail).setVisibility(View.VISIBLE);
                                AdBiography adapter = new AdBiography(ActDetail.this, response.ListItems);
                                RvSimilarCategory.setAdapter(adapter);
                                adapter.notifyDataSetChanged();
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            btnRefresh.setVisibility(View.VISIBLE);
                            Toasty.warning(ActDetail.this, "خطای سامانه مجددا تلاش کنید", Toasty.LENGTH_LONG, true).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            btnRefresh.setVisibility(View.VISIBLE);
            Toasty.warning(this, "عدم دسترسی به اینترنت", Toasty.LENGTH_LONG, true).show();
        }
    }

    private void HandelDataComment(long ContentId) {
        if (AppUtill.isNetworkAvailable(this)) {
            List<Filters> filters = new ArrayList<>();
            BiographyCommentListRequest Request = new BiographyCommentListRequest();
            Filters f = new Filters();
            f.PropertyName = "LinkContentId";
            f.IntValue1 = ContentId;
            filters.add(f);
            Request.filters = filters;
            RetrofitManager retro = new RetrofitManager(this);
            IBiography iBiography = retro.getRetrofitUnCached(configStaticValue.GetApiBaseUrl()).create(IBiography.class);
            Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);
            Observable<BiographyCommentResponse> call = iBiography.GetCommentList(headers, Request);
            call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BiographyCommentResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BiographyCommentResponse model) {
                            if (model.IsSuccess) {
                                if (model.ListItems.size() == 0) {
                                    findViewById(R.id.RowCommentActDetail).setVisibility(View.GONE);
                                } else {
                                    AdComment adapter = new AdComment(ActDetail.this, model.ListItems);
                                    RvComment.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                    findViewById(R.id.RowCommentActDetail).setVisibility(View.VISIBLE);
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            btnRefresh.setVisibility(View.VISIBLE);
                            Toasty.warning(ActDetail.this, "خطای سامانه مجددا تلاش کنید", Toasty.LENGTH_LONG, true).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            btnRefresh.setVisibility(View.VISIBLE);
            Toasty.warning(this, "عدم دسترسی به اینترنت", Toasty.LENGTH_LONG, true).show();
        }
    }

    private void HandelDataContentOtherInfo(long ContentId) {
        if (AppUtill.isNetworkAvailable(this)) {
            List<Filters> filters = new ArrayList<>();
            BiographyContentOtherInfoRequest Request = new BiographyContentOtherInfoRequest();
            Filters f = new Filters();
            f.PropertyName = "LinkContentId";
            f.IntValue1 = ContentId;
            filters.add(f);
            Request.filters = filters;
            RetrofitManager retro = new RetrofitManager(this);
            IBiography iBiography = retro.getRetrofitUnCached(configStaticValue.GetApiBaseUrl()).create(IBiography.class);
            Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);


            Observable<BiographyContentOtherInfoResponse> call = iBiography.GetContentOtherInfoList(headers, Request);
            call.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<BiographyContentOtherInfoResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BiographyContentOtherInfoResponse BiographyContentOtherInfoResponse) {
                            SetDataOtherinfo(BiographyContentOtherInfoResponse);
                        }

                        @Override
                        public void onError(Throwable e) {
                            btnRefresh.setVisibility(View.VISIBLE);
                            Toasty.warning(ActDetail.this, "خطای سامانه مجددا تلاش کنید", Toasty.LENGTH_LONG, true).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            btnRefresh.setVisibility(View.VISIBLE);
            Toasty.warning(this, "عدم دسترسی به اینترنت", Toasty.LENGTH_LONG, true).show();
        }
    }

    private void SetDataOtherinfo(BiographyContentOtherInfoResponse model) {
        Info = model;
        if (model.ListItems == null || model.ListItems.size() == 0) {
            LinearLayout.LayoutParams p = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            p.weight = 3;
            return;
        }
        List<BiographyContentOtherInfo> Info = new ArrayList<>();
        BiographyContentOtherInfo i = new BiographyContentOtherInfo();
        i.Title = "بیوگرافی";
        i.TypeId = 0;
        i.HtmlBody = this.model.Item.Body;
        Info.add(i);

        for (BiographyContentOtherInfo ai : model.ListItems) {
            switch (ai.TypeId) {
                case 21:
                    Lbls.get(7).setText(ai.Title);
                    ai.HtmlBody = ai.HtmlBody.replace("<p>", "");
                    ai.HtmlBody = ai.HtmlBody.replace("</p>", "");
                    Lbls.get(6).setText(Html.fromHtml(ai.HtmlBody));
                    break;
                case 22:
                    Lbls.get(9).setText(ai.Title);
                    ai.HtmlBody = ai.HtmlBody.replace("<p>", "");
                    ai.HtmlBody = ai.HtmlBody.replace("</p>", "");
                    Lbls.get(8).setText(Html.fromHtml(ai.HtmlBody));
                    break;
                case 23:
                    Lbls.get(11).setText(ai.Title);
                    ai.HtmlBody = ai.HtmlBody.replace("<p>", "");
                    ai.HtmlBody = ai.HtmlBody.replace("</p>", "");
                    Lbls.get(10).setText(Html.fromHtml(ai.HtmlBody));
                    break;
                default:
                    Info.add(ai);
                    break;
            }
        }
        AdTab adapter = new AdTab(ActDetail.this, Info);
        RvTab.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void SetData(BiographyContentResponse model) {
        if (model.Item.ScoreSumPercent == 0) {
            Rate.setRating(0);
        } else {
            Rate.setRating((model.Item.ScoreSumPercent / model.Item.ScoreSumClick));
        }
        ImageLoader.getInstance().displayImage(model.Item.imageSrc, ImgHeader);
        Lbls.get(0).setText(model.Item.Title);
        Lbls.get(1).setText(model.Item.Title);
        Lbls.get(3).setText(String.valueOf(model.Item.viewCount));
        if (model.Item.Favorited) {
        }
    }

    @OnClick(R.id.imgBackActDetail)
    public void ClickBack() {
        finish();
    }

    @Subscribe
    public void EventHtmlBody(EvHtmlBody event) {
        webView.loadDataWithBaseURL("", event.GetMessage(), "text/html", "UTF-8", "");
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @OnClick(R.id.imgCommentActDetail)
    public void ClickCommentAdd() {
        if (AppUtill.isNetworkAvailable(this)) {
            final Dialog dialog = new Dialog(this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            dialog.setCanceledOnTouchOutside(true);
            Window window = dialog.getWindow();
            window.setLayout(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
            window.setGravity(Gravity.CENTER);
            dialog.setContentView(R.layout.dialog_comment_add);

            TextView Lbl = dialog.findViewById(R.id.lblTitleDialogAddComment);
            Lbl.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));

            EditText Txt[] = new EditText[2];

            Txt[0] = dialog.findViewById(R.id.txtNameDialogAddComment);
            Txt[0].setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));

            Txt[1] = dialog.findViewById(R.id.txtContentDialogAddComment);
            Txt[1].setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));

            Button Btn = dialog.findViewById(R.id.btnSubmitDialogCommentAdd);
            Btn.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));

            Btn.setOnClickListener(v -> {
                if (Txt[0].getText().toString().isEmpty()) {
                    Toast.makeText(ActDetail.this, "لطفا مقادیر را وارد نمایید", Toast.LENGTH_SHORT).show();
                } else {
                    if (Txt[1].getText().toString().isEmpty()) {
                        Toast.makeText(ActDetail.this, "لطفا مقادیر را وارد نمایید", Toast.LENGTH_SHORT).show();
                    } else {
                        BiographyCommentAddRequest add = new BiographyCommentAddRequest();
                        add.Writer = Txt[0].getText().toString();
                        add.Comment = Txt[1].getText().toString();
                        add.LinkContentId = Request.Id;
                        RetrofitManager retro = new RetrofitManager(this);
                        IBiography iBiography = retro.getRetrofitUnCached(configStaticValue.GetApiBaseUrl()).create(IBiography.class);
                        Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);


                        Observable<BiographyCommentResponse> call = iBiography.SetComment(headers, add);
                        call.subscribeOn(Schedulers.io())
                                .observeOn(AndroidSchedulers.mainThread())
                                .subscribe(new Observer<ErrorException>() {
                                    @Override
                                    public void onSubscribe(Disposable d) {

                                    }

                                    @Override
                                    public void onNext(ErrorException e) {
                                        if (e.IsSuccess) {
                                            HandelDataComment(Request.Id);
                                            dialog.dismiss();
                                        }
                                    }

                                    @Override
                                    public void onError(Throwable e) {
                                        btnRefresh.setVisibility(View.VISIBLE);
                                        Toasty.warning(ActDetail.this, "خطای سامانه مجددا تلاش کنید", Toasty.LENGTH_LONG, true).show();
                                    }

                                    @Override
                                    public void onComplete() {

                                    }
                                });
                    }
                }
            });
            dialog.show();
        } else {
            btnRefresh.setVisibility(View.VISIBLE);
            Toasty.warning(this, "عدم دسترسی به اینترنت", Toasty.LENGTH_LONG, true).show();
        }
    }

    @OnClick(R.id.imgFavActDetail)
    public void ClickFav() {
        if (!model.Item.Favorited) {
            Fav();
        } else {
            UnFav();
        }
    }

    private void UnFav() {
        if (AppUtill.isNetworkAvailable(this)) {
            RetrofitManager retro = new RetrofitManager(this);
            IBiography iBiography = retro.getRetrofitUnCached(configStaticValue.GetApiBaseUrl()).create(IBiography.class);
            Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);

            BiographyContentFavoriteRemoveRequest add = new BiographyContentFavoriteRemoveRequest();
            add.Id = model.Item.Id;

            Observable<BiographyContentFavoriteRemoveResponse> Call = iBiography.SetContentFavoriteRemove(headers, add);
            Call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BiographyContentFavoriteRemoveResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BiographyContentFavoriteRemoveResponse e) {
                            if (e.IsSuccess) {
                                model.Item.Favorited = !model.Item.Favorited;
                                if (model.Item.Favorited) {
                                    ((ImageView) findViewById(R.id.imgHeartActDetailNews)).setImageResource(R.drawable.ic_fav_full);
                                } else {
                                    ((ImageView) findViewById(R.id.imgHeartActDetailNews)).setImageResource(R.drawable.ic_fav);
                                }
                            } else {
                                Toasty.error(ActDetail.this, e.ErrorMessage, Toast.LENGTH_LONG, true).show();
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            btnRefresh.setVisibility(View.VISIBLE);
                            Toasty.warning(ActDetail.this, "خطای سامانه مجددا تلاش کنید", Toasty.LENGTH_LONG, true).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            btnRefresh.setVisibility(View.VISIBLE);
            Toasty.warning(this, "عدم دسترسی به اینترنت", Toasty.LENGTH_LONG, true).show();
        }
    }

    private void Fav() {
        if (AppUtill.isNetworkAvailable(this)) {
            RetrofitManager retro = new RetrofitManager(this);
            IBiography iBiography = retro.getRetrofitUnCached(configStaticValue.GetApiBaseUrl()).create(IBiography.class);
            Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);

            BiographyContentFavoriteAddRequest add = new BiographyContentFavoriteAddRequest();
            add.Id = model.Item.Id;

            Observable<BiographyContentFavoriteAddResponse> Call = iBiography.SetContentFavoriteAdd(headers, add);
            Call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<BiographyContentFavoriteAddResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BiographyContentFavoriteAddResponse biographyContentFavoriteAddResponse) {
                            Log.i("0000", "onNext: " + biographyContentFavoriteAddResponse.IsSuccess);
                            if (biographyContentFavoriteAddResponse.IsSuccess) {
                                model.Item.Favorited = !model.Item.Favorited;
                                if (model.Item.Favorited) {
                                    ((ImageView) findViewById(R.id.imgHeartActDetailNews)).setImageResource(R.drawable.ic_fav_full);
                                } else {
                                    ((ImageView) findViewById(R.id.imgHeartActDetailNews)).setImageResource(R.drawable.ic_fav);
                                }
                            } else {
                                Toasty.error(ActDetail.this, biographyContentFavoriteAddResponse.ErrorMessage, Toast.LENGTH_LONG, true).show();
                            }

                        }

                        @Override
                        public void onError(Throwable e) {
                            btnRefresh.setVisibility(View.VISIBLE);
                            Toasty.warning(ActDetail.this, "خطای سامانه مجددا تلاش کنید", Toasty.LENGTH_LONG, true).show();
                        }

                        @Override
                        public void onComplete() {

                        }
                    });
        } else {
            btnRefresh.setVisibility(View.VISIBLE);
            Toasty.warning(this, "عدم دسترسی به اینترنت", Toasty.LENGTH_LONG, true).show();
        }
    }

    @OnClick(R.id.imgShareActDetail)
    public void ClickShare() {
        if (model.Item.Source.contains("https") || model.Item.Source.contains("http") || model.Item.Source.contains("www")) {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(model.Item.Source));
            startActivity(i);
        } else {
            Toasty.warning(this, "این محتوا امکان به اشتراک گذاری ندارد", Toasty.LENGTH_LONG, true).show();
        }
    }

    @OnClick(R.id.btnRefreshActDetail)
    public void ClickRefresh() {
        btnRefresh.setVisibility(View.GONE);
        init();
    }
}
