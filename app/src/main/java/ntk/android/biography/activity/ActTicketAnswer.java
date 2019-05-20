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

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;
import java.util.List;
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
import ntk.android.biography.adapter.AdAttach;
import ntk.android.biography.adapter.AdTicketAnswer;
import ntk.android.biography.config.ConfigRestHeader;
import ntk.android.biography.config.ConfigStaticValue;
import ntk.android.biography.utill.AppUtill;
import ntk.android.biography.utill.FontManager;
import ntk.base.api.ticket.interfase.ITicket;
import ntk.base.api.ticket.model.TicketingAnswer;
import ntk.base.api.ticket.model.TicketingAnswerListRequest;
import ntk.base.api.ticket.model.TicketingAnswerListResponse;
import ntk.base.api.utill.RetrofitManager;


public class ActTicketAnswer extends AppCompatActivity {

    @BindView(R.id.recyclerAnswer)
    RecyclerView Rv;

    @BindView(R.id.lblTitleActTicketAnswer)
    TextView Lbl;

    @BindView(R.id.mainLayoutActTicketAnswer)
    CoordinatorLayout layout;

    private ArrayList<TicketingAnswer> tickets = new ArrayList<>();
    private AdTicketAnswer adapter;
    private List<String> attaches = new ArrayList<>();
    private AdAttach AdAtach;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_ticket_answer);
        ButterKnife.bind(this);
        init();
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

    private void init() {
        Lbl.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        Lbl.setText("پاسخ تیکت شماره");
        Rv.setHasFixedSize(true);
        LinearLayoutManager manager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        Rv.setLayoutManager(manager);

        adapter = new AdTicketAnswer(this, tickets);
        Rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();

        HandelData(1);

        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        AdAtach = new AdAttach(this, attaches);
        Rv.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private void HandelData(int i) {
        if (AppUtill.isNetworkAvailable(this)) {
            RetrofitManager retro = new RetrofitManager(this);
            ITicket iTicket = retro.getCachedRetrofit(new ConfigStaticValue(this).GetApiBaseUrl()).create(ITicket.class);
            Map<String, String> headers = new ConfigRestHeader().GetHeaders(this);
            Observable<TicketingAnswerListResponse> Call = iTicket.GetTicketAnswerList(headers, new Gson().fromJson(getIntent().getExtras().getString("Request"), TicketingAnswerListRequest.class));
            Call.subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(new Observer<TicketingAnswerListResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(TicketingAnswerListResponse model) {
                            tickets.addAll(model.ListItems);
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

    @OnClick(R.id.imgBackActTicketAnswer)
    public void ClickBack() {
        finish();
    }
}