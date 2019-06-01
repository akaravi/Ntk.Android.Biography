package ntk.android.biography.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.gson.Gson;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import ntk.android.biography.Biography;
import ntk.android.biography.BuildConfig;
import ntk.android.biography.R;
import ntk.android.biography.adapter.AdFragment;
import ntk.android.biography.adapter.AdPager;
import ntk.android.biography.adapter.drawer.AdDrawer;
import ntk.android.biography.adapter.toolbar.AdToobar;
import ntk.android.biography.config.ConfigRestHeader;
import ntk.android.biography.config.ConfigStaticValue;
import ntk.android.biography.event.toolbar.EVHamberMenuClick;
import ntk.android.biography.event.toolbar.EVSearchClick;
import ntk.android.biography.fragment.FrCommand;
import ntk.android.biography.fragment.FrFav;
import ntk.android.biography.fragment.FrHome;
import ntk.android.biography.fragment.FrSame;
import ntk.android.biography.library.ahbottomnavigation.AHBottomNavigation;
import ntk.android.biography.library.ahbottomnavigation.AHBottomNavigationItem;
import ntk.android.biography.model.theme.Theme;
import ntk.android.biography.model.theme.Toolbar;
import ntk.android.biography.utill.AppUtill;
import ntk.android.biography.utill.EasyPreference;
import ntk.android.biography.utill.FontManager;
import ntk.base.api.core.interfase.ICore;
import ntk.base.api.core.model.CoreMain;
import ntk.base.api.core.model.MainCoreResponse;
import ntk.base.api.utill.RetrofitManager;

import static ntk.android.biography.activity.ActSplash.APPLICATION_START;

public class ActMain extends AppCompatActivity implements AHBottomNavigation.OnTabSelectedListener {

    @BindView(R.id.bottomNavMenu)
    AHBottomNavigation navigation;

    @BindView(R.id.ViewPagerContainer)
    AdPager pager;

    @BindView(R.id.drawerlayout)
    FlowingDrawer drawer;

    @BindView(R.id.HeaderImageActMain)
    KenBurnsView Header;

    @BindView(R.id.RecyclerToolbarActMain)
    RecyclerView RvToolbar;

    @BindView(R.id.RecyclerDrawer)
    RecyclerView RvDrawer;

    @BindView(R.id.mainLayoutActMain)
    CoordinatorLayout layout;

    private long lastPressedTime;
    private static final int PERIOD = 2000;
    private boolean applicationStart;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);
        applicationStart = getIntent().getBooleanExtra(APPLICATION_START, false);
        ButterKnife.bind(this);
        init();
    }

    private void init() {
        drawer.setOnDrawerStateChangeListener(new ElasticDrawer.OnDrawerStateChangeListener() {
            @Override
            public void onDrawerStateChange(int oldState, int newState) {
                if (newState == ElasticDrawer.STATE_CLOSED) {
                }
            }

            @Override
            public void onDrawerSlide(float openRatio, int offsetPixels) {
            }
        });

        navigation.setDefaultBackgroundColor(Color.parseColor("#ffffff"));
        navigation.setBehaviorTranslationEnabled(false);
        navigation.setTitleTypeface(FontManager.GetTypeface(this, FontManager.IranSans));

        AHBottomNavigationItem BMI = new AHBottomNavigationItem("مثل خودم", R.drawable.ic_one, R.color.colorMenu);
        AHBottomNavigationItem Favorite = new AHBottomNavigationItem("علاقه مندی", R.drawable.ic_two, R.color.colorMenu);
        AHBottomNavigationItem Home = new AHBottomNavigationItem("خانه", R.drawable.ic_three, R.color.colorMenu);
        AHBottomNavigationItem Command = new AHBottomNavigationItem("مورد علاقه", R.drawable.ic_five, R.color.colorMenu);

        navigation.addItem(Command);
        navigation.addItem(Home);
        navigation.addItem(Favorite);
        navigation.addItem(BMI);

        navigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        navigation.setCurrentItem(1);
        navigation.setTitleTextSize(20, 18);
        navigation.setTitleTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        navigation.setAccentColor(Color.parseColor("#00796B"));
        navigation.setInactiveColor(Color.parseColor("#030303"));
        navigation.setOnTabSelectedListener(this);
        navigation.setColored(false);

        AdFragment adapter = new AdFragment(getSupportFragmentManager());
        adapter.addFragment(new FrCommand());
        adapter.addFragment(new FrHome());
        adapter.addFragment(new FrFav());
        adapter.addFragment(new FrSame());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);
        pager.setCurrentItem(1, false);

        HandelToolbarDrawer();
    }

    private void HandelToolbarDrawer() {
        Theme theme = new Gson().fromJson(Biography.JsonThemeExmaple, Theme.class);

        RvToolbar.setHasFixedSize(true);
        RvToolbar.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<Toolbar> toolbars = new ArrayList<>();
        toolbars.add(theme.Toolbar);
        AdToobar AdToobar = new AdToobar(this, toolbars);
        RvToolbar.setAdapter(AdToobar);
        AdToobar.notifyDataSetChanged();


        RvDrawer.setHasFixedSize(true);
        RvDrawer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        AdDrawer AdDrawer = new AdDrawer(this, theme.Toolbar.Drawer.Child, drawer);
        RvDrawer.setAdapter(AdDrawer);
        AdDrawer.notifyDataSetChanged();
    }

    @Override
    protected void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
        if (AppUtill.isNetworkAvailable(this)) {
            HandelData();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }

    @Override
    public boolean onTabSelected(int position, boolean wasSelected) {
        pager.setCurrentItem(position, false);
        return true;
    }

    @Subscribe
    public void EvClickSearch(EVSearchClick click) {
        startActivity(new Intent(this, ActSearch.class));
    }

    @Subscribe
    public void EvClickMenu(EVHamberMenuClick click) {
        drawer.openMenu(false);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
            switch (event.getAction()) {
                case KeyEvent.ACTION_DOWN:
                    if (event.getDownTime() - lastPressedTime < PERIOD) {
                        finish();
                    } else {
                        Toast.makeText(getApplicationContext(), "برای خروج مجددا کلید بازگشت را فشار دهید",
                                Toast.LENGTH_SHORT).show();
                        lastPressedTime = event.getEventTime();
                    }
                    return true;
            }
        }
        return false;
    }

    private void HandelData() {
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
                            EasyPreference.with(ActMain.this).addString("configapp", new Gson().toJson(mainCoreResponse.Item));
                            if (applicationStart) {
                                CheckUpdate();
                                applicationStart = false;
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

    private void CheckUpdate() {
        String st = EasyPreference.with(this).getString("configapp", "");
        CoreMain mcr = new Gson().fromJson(st, CoreMain.class);
        if (mcr.AppVersion > BuildConfig.VERSION_CODE && !BuildConfig.APPLICATION_ID.contains(".APPNTK")) {
            if (mcr.AppForceUpdate) {
                UpdateFore();
            } else {
                Update();
            }
        }
    }

    private void Update() {
        String st = EasyPreference.with(this).getString("configapp", "");
        CoreMain mcr = new Gson().fromJson(st, CoreMain.class);
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(true);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.dialog_permission);
        ((TextView) dialog.findViewById(R.id.lbl1PernissionDialog)).setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        ((TextView) dialog.findViewById(R.id.lbl1PernissionDialog)).setText("توجه");
        ((TextView) dialog.findViewById(R.id.lbl2PernissionDialog)).setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        ((TextView) dialog.findViewById(R.id.lbl2PernissionDialog)).setText("نسخه جدید اپلیکیشن اومده دوست داری آبدیت بشه؟؟");
        Button Ok = (Button) dialog.findViewById(R.id.btnOkPermissionDialog);
        Ok.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        Ok.setOnClickListener(view1 -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(mcr.AppUrl));
            startActivity(i);
            dialog.dismiss();
        });
        Button Cancel = (Button) dialog.findViewById(R.id.btnCancelPermissionDialog);
        Cancel.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        Cancel.setOnClickListener(view12 -> dialog.dismiss());
        dialog.show();
    }

    private void UpdateFore() {
        String st = EasyPreference.with(this).getString("configapp", "");
        CoreMain mcr = new Gson().fromJson(st, CoreMain.class);
        final Dialog dialog = new Dialog(this);
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setCanceledOnTouchOutside(false);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayoutCompat.LayoutParams.WRAP_CONTENT, LinearLayoutCompat.LayoutParams.WRAP_CONTENT);
        window.setGravity(Gravity.CENTER);
        dialog.setContentView(R.layout.dialog_update);
        ((TextView) dialog.findViewById(R.id.lbl1PernissionDialogUpdate)).setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        ((TextView) dialog.findViewById(R.id.lbl1PernissionDialogUpdate)).setText("توجه");
        ((TextView) dialog.findViewById(R.id.lbl2PernissionDialogUpdate)).setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        ((TextView) dialog.findViewById(R.id.lbl2PernissionDialogUpdate)).setText("نسخه جدید اپلیکیشن اومده حتما باید آبدیت بشه");
        Button Ok = (Button) dialog.findViewById(R.id.btnOkPermissionDialogUpdate);
        Ok.setTypeface(FontManager.GetTypeface(this, FontManager.IranSans));
        Ok.setOnClickListener(view1 -> {
            Intent i = new Intent(Intent.ACTION_VIEW);
            i.setData(Uri.parse(mcr.AppUrl));
            startActivity(i);
            dialog.dismiss();
        });
        dialog.setOnKeyListener((dialog1, keyCode, event) -> {
            switch (event.getAction()) {
                case KeyEvent.ACTION_DOWN:
                    finish();
            }
            return true;
        });
        dialog.show();
    }
}
