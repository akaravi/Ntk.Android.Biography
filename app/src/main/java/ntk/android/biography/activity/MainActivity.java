package ntk.android.biography.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.google.android.material.snackbar.Snackbar;
import com.google.gson.Gson;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import es.dmoral.toasty.Toasty;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.annotations.NonNull;
import io.reactivex.schedulers.Schedulers;
import ntk.android.base.Extras;
import ntk.android.base.activity.abstraction.AbstractMainActivity;
import ntk.android.base.config.NtkObserver;
import ntk.android.base.dtomodel.application.MainResponseDtoModel;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.services.application.ApplicationAppService;
import ntk.android.biography.MyApplication;
import ntk.android.biography.R;
import ntk.android.biography.adapter.AdFragment;
import ntk.android.biography.adapter.AdPager;
import ntk.android.biography.adapter.drawer.DrawerAdapter;
import ntk.android.biography.adapter.toolbar.ToolbarAdapter;
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


public class MainActivity extends AbstractMainActivity implements AHBottomNavigation.OnTabSelectedListener {

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
        applicationStart = getIntent().getBooleanExtra(Extras.EXTRA_FIRST_ARG, false);
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
        Theme theme = new Gson().fromJson(MyApplication.JsonThemeExmaple, Theme.class);

        RvToolbar.setHasFixedSize(true);
        RvToolbar.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        List<Toolbar> toolbars = new ArrayList<>();
        toolbars.add(theme.Toolbar);
        ToolbarAdapter AdToobar = new ToolbarAdapter(this, toolbars);
        RvToolbar.setAdapter(AdToobar);
        AdToobar.notifyDataSetChanged();


        RvDrawer.setHasFixedSize(true);
        RvDrawer.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        DrawerAdapter AdDrawer = new DrawerAdapter(this, theme.Toolbar.Drawer.Child, drawer);
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



    private void HandelData() {
        if (ntk.android.base.utill.AppUtill.isNetworkAvailable(this)) {
            new ApplicationAppService(this).getResponseMain().observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(new NtkObserver<ErrorException<MainResponseDtoModel>>() {


                        @Override
                        public void onNext(@NonNull ErrorException<MainResponseDtoModel> mainCoreResponse) {
                            if(!mainCoreResponse.IsSuccess)
                            {
                                //BtnRefresh.setVisibility(View.VISIBLE);
                                Toasty.warning(MainActivity.this, "خطای سامانه مجددا تلاش کنید"+mainCoreResponse.ErrorMessage, Toasty.LENGTH_LONG, true).show();
                                return;
                            }
                            EasyPreference.with(MainActivity.this).addString("configapp", new Gson().toJson(mainCoreResponse.Item));
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


}
