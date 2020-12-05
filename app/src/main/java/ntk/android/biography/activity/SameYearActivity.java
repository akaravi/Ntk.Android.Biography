package ntk.android.biography.activity;

import android.view.View;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.Observable;
import java9.util.function.Function;
import ntk.android.base.activity.abstraction.AbstractionListActivity;
import ntk.android.base.dtomodel.biography.BiographyContentWithDatePeriodEndDtoModel;
import ntk.android.base.dtomodel.biography.BiographyContentWithSimilarDatePeriodStartDayAndMonthOfYearDtoModel;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.biography.BiographyContentModel;
import ntk.android.base.services.biography.BiographyContentService;
import ntk.android.biography.R;
import ntk.android.biography.adapter.BiographyAdapter;
import ntk.android.biography.utill.EasyPreference;

public class SameYearActivity extends
        AbstractionListActivity<BiographyContentWithSimilarDatePeriodStartDayAndMonthOfYearDtoModel, BiographyContentModel> {

    @Override
    public RecyclerView.LayoutManager getRvLayoutManager() {
        return new GridLayoutManager(this, 2);
    }

    @Override
    protected void requestOnIntent() {
        String Gregorian = EasyPreference.with(this).getString("BirthDay", "");
//        request.DayOfMonth = AppUtill.GetMinOfYear(AppUtill.GregorianToPersian(Gregorian));
//        request.MonthOfYear = AppUtill.GetMaxOfYear(AppUtill.GregorianToPersian(Gregorian));

    }

    @Override
    protected void onSuccessNext(ErrorException<BiographyContentModel> response) {
        findViewById(R.id.rowProgressActSameLocation).setVisibility(View.GONE);
        models.addAll(response.ListItems);
        Total = response.TotalRowCount;
        adapter.notifyDataSetChanged();
    }

    @Override
    protected Function<Integer, Observable<ErrorException<BiographyContentModel>>> apiService() {
        return integer -> {
            //karavi say need to paging
            return new BiographyContentService(this).getAllGetAllWithDatePeriodEnd(
                    new BiographyContentWithDatePeriodEndDtoModel() );
        };
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return new BiographyAdapter(this, models);
    }

    @Override
    public void ClickSearch() {

    }
}
