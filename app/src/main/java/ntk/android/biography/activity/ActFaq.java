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
import ntk.android.biography.R;
import ntk.android.biography.adapter.AdFaq;
import ntk.android.biography.config.ConfigRestHeader;
import ntk.android.biography.config.ConfigStaticValue;
import ntk.android.biography.utill.AppUtill;
import ntk.android.biography.utill.FontManager;
import ntk.base.api.ticket.interfase.ITicket;
import ntk.base.api.ticket.model.TicketingFaqListRequest;
import ntk.base.api.ticket.model.TicketingFaqListResponse;
import ntk.base.api.utill.RetrofitManager;

public class ActFaq extends AppCompatActivity {

    @BindView(R.id.lblTitleActFaq)
    TextView Lbl;

    @BindView(R.id.recyclerFaq)
    RecyclerView Rv;

    @BindView(R.id.mainLayoutActFaq)
    CoordinatorLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_faq);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        if (AppUtill.isNetworkAvailable(this)) {
            Lbl.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
            Lbl.setText("پرسش های متداول");

            Rv.setHasFixedSize(true);
            Rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

            RetrofitManager retro = new RetrofitManager(this);
            ITicket iTicket = retro.getCachedRetrofit(new ConfigStaticValue(this).GetApiBaseUrl()).create(ITicket.class);
            Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);

            TicketingFaqListRequest request = new TicketingFaqListRequest();
            request.RowPerPage = 100;

            Observable<TicketingFaqListResponse> Call = iTicket.GetTicketFaqList(headers, request);
            Call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<TicketingFaqListResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(TicketingFaqListResponse model) {
                            AdFaq adapter = new AdFaq(ActFaq.this, model.ListItems);
                            Rv.setAdapter(adapter);
                            adapter.notifyDataSetChanged();
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

    @OnClick(R.id.imgBackActFaq)
    public void ClickBack() {
        finish();
    }
}
