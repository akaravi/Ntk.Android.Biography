package ntk.android.biography.activity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.flaviofaria.kenburnsview.KenBurnsView;
import com.mxn.soul.flowingdrawer_core.ElasticDrawer;
import com.mxn.soul.flowingdrawer_core.FlowingDrawer;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import ntk.android.base.Extras;
import ntk.android.base.activity.abstraction.AbstractMainActivity;
import ntk.android.base.utill.FontManager;
import ntk.android.biography.JsonCreator;
import ntk.android.biography.R;
import ntk.android.biography.adapter.FragmentAdapter;
import ntk.android.biography.adapter.PagerAdapter;
import ntk.android.biography.adapter.drawer.DrawerAdapter;
import ntk.android.biography.adapter.toolbar.ToolbarAdapter;
import ntk.android.biography.event.toolbar.EVHamberMenuClick;
import ntk.android.biography.event.toolbar.EVSearchClick;
import ntk.android.biography.fragment.BiographyCategoryFragment;
import ntk.android.biography.fragment.BiographyFavoriteList;
import ntk.android.biography.fragment.MainFragment;
import ntk.android.biography.fragment.SimilarFragment;
import ntk.android.biography.library.ahbottomnavigation.AHBottomNavigation;
import ntk.android.biography.library.ahbottomnavigation.AHBottomNavigationItem;
import ntk.android.biography.model.theme.Theme;
import ntk.android.biography.model.theme.Toolbar;


public class MainActivity extends AbstractMainActivity implements AHBottomNavigation.OnTabSelectedListener {

    @BindView(R.id.bottomNavMenu)
    AHBottomNavigation navigation;

    @BindView(R.id.ViewPagerContainer)
    PagerAdapter pager;

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
        setContentView(R.layout.activity_main);
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
        navigation.setTitleTypeface(FontManager.T1_Typeface(this));

        AHBottomNavigationItem BMI = new AHBottomNavigationItem("مثل خودم", R.drawable.ic_one, R.color.colorMenu);
        AHBottomNavigationItem Favorite = new AHBottomNavigationItem("علاقه مندی", R.drawable.ic_two, R.color.colorMenu);
        AHBottomNavigationItem Home = new AHBottomNavigationItem("خانه", R.drawable.ic_three, R.color.colorMenu);
        AHBottomNavigationItem Category = new AHBottomNavigationItem("دسته بندی", R.drawable.ic_five, R.color.colorMenu);

        navigation.addItem(Category);
        navigation.addItem(Home);
        navigation.addItem(Favorite);
        navigation.addItem(BMI);

        navigation.setTitleState(AHBottomNavigation.TitleState.ALWAYS_SHOW);
        navigation.setCurrentItem(1);
        navigation.setTitleTextSize(20, 18);
        navigation.setTitleTypeface(FontManager.T1_Typeface(this));
        navigation.setAccentColor(Color.parseColor("#00796B"));
        navigation.setInactiveColor(Color.parseColor("#030303"));
        navigation.setOnTabSelectedListener(this);
        navigation.setColored(false);

        FragmentAdapter adapter = new FragmentAdapter(getSupportFragmentManager());
        adapter.addFragment(new BiographyCategoryFragment());
        adapter.addFragment(new MainFragment());
        adapter.addFragment(new BiographyFavoriteList());
        adapter.addFragment(new SimilarFragment());
        pager.setAdapter(adapter);
        pager.setOffscreenPageLimit(2);
        pager.setCurrentItem(1, false);

        HandelToolbarDrawer();
    }

    private void HandelToolbarDrawer() {
        Theme theme = JsonCreator.DRAWER();

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
        startActivity(new Intent(this, BiographySearchActivity.class));
    }

    @Subscribe
    public void EvClickMenu(EVHamberMenuClick click) {
        drawer.openMenu(false);
    }


}
