package ntk.android.biography.activity;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import com.google.android.material.snackbar.Snackbar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;

import java.util.ArrayList;
import java.util.List;

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
import ntk.android.biography.adapter.AdBiography;
import ntk.android.biography.config.ConfigRestHeader;
import ntk.android.biography.config.ConfigStaticValue;
import ntk.android.biography.utill.AppUtill;
import ntk.android.biography.utill.FontManager;
import ntk.base.api.biography.interfase.IBiography;
import ntk.base.api.biography.model.BiographyContentListRequest;
import ntk.base.api.biography.model.BiographyContentResponse;
import ntk.base.api.baseModel.Filters;
import ntk.base.api.utill.NTKUtill;
import ntk.base.api.utill.RetrofitManager;

public class ActSearch extends AppCompatActivity {

    @BindView(R.id.txtSearchActSearch)
    EditText Txt;

    @BindView(R.id.recyclerSearch)
    RecyclerView Rv;

    @BindView(R.id.btnRefreshActSearch)
    Button btnRefresh;

    @BindView(R.id.mainLayoutActSearch)
    CoordinatorLayout layout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_search);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        Rv.setHasFixedSize(true);
        Rv.setLayoutManager(new GridLayoutManager(this, 2));

        Txt.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        Txt.setOnEditorActionListener((textView, i, keyEvent) -> {
            if (i == EditorInfo.IME_ACTION_SEARCH) {
                Search();
                return true;
            }
            return false;
        });

    }

    private void Search() {
        if (AppUtill.isNetworkAvailable(this)) {
            RetrofitManager manager = new RetrofitManager(this);
            IBiography iBiography = manager.getRetrofitUnCached(new ConfigStaticValue(this).GetApiBaseUrl()).create(IBiography.class);


            BiographyContentListRequest request = new BiographyContentListRequest();
            List<Filters> filters = new ArrayList<>();
            Filters ft = new Filters();
            ft.PropertyName = "Title";
            ft.StringValue = Txt.getText().toString();
            ft.ClauseType = NTKUtill.ClauseType_Or;
            ft.SearchType = NTKUtill.Search_Type_Contains;
            filters.add(ft);

            Filters fd = new Filters();
            fd.PropertyName = "Description";
            fd.StringValue = Txt.getText().toString();
            fd.ClauseType = NTKUtill.ClauseType_Or;
            fd.SearchType = NTKUtill.Search_Type_Contains;
            filters.add(fd);

            Filters fb = new Filters();
            fb.PropertyName = "Body";
            fb.StringValue = Txt.getText().toString();
            fb.ClauseType = NTKUtill.ClauseType_Or;
            fb.SearchType = NTKUtill.Search_Type_Contains;

            filters.add(fb);

            request.filters = filters;

            Observable<BiographyContentResponse> Call = iBiography.GetContentList(new ConfigRestHeader().GetHeaders(this), request);
            Call.observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new Observer<BiographyContentResponse>() {
                        @Override
                        public void onSubscribe(Disposable d) {

                        }

                        @Override
                        public void onNext(BiographyContentResponse response) {
                            if (response.IsSuccess) {
                                if (response.ListItems.size() != 0) {
                                    AdBiography adapter = new AdBiography(ActSearch.this, response.ListItems);
                                    Rv.setAdapter(adapter);
                                    adapter.notifyDataSetChanged();
                                } else {
                                    Toasty.warning(ActSearch.this, "نتیجه ای یافت نشد", Toasty.LENGTH_LONG, true).show();
                                }
                            }
                        }

                        @Override
                        public void onError(Throwable e) {
                            btnRefresh.setVisibility(View.VISIBLE);
                            Snackbar.make(layout, "عدم دسترسی به اینترنت", Snackbar.LENGTH_INDEFINITE).setAction("تلاش مجددا", new View.OnClickListener() {
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
            btnRefresh.setVisibility(View.VISIBLE);
            Toasty.warning(this, "عدم دسترسی به اینترنت", Toasty.LENGTH_LONG, true).show();
        }
    }

    @OnClick(R.id.imgBackActSearch)
    public void ClickBack() {
        finish();
    }

    @OnClick(R.id.btnRefreshActSearch)
    public void ClickRefresh() {
        btnRefresh.setVisibility(View.GONE);
        init();
    }
}
