package ntk.android.biography.activity;

import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.Observable;
import java9.util.function.Function;
import ntk.android.base.activity.abstraction.AbstractionListActivity;
import ntk.android.base.dtomodel.biography.BiographyContentWithSimilarDatePeriodStartDayAndMonthOfYearDtoModel;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.biography.BiographyContentModel;
import ntk.android.base.services.biography.BiographyContentService;
import ntk.android.biography.adapter.AdBiography;
import ntk.android.biography.utill.EasyPreference;

public class SameDayActivity extends
        AbstractionListActivity<BiographyContentWithSimilarDatePeriodStartDayAndMonthOfYearDtoModel, BiographyContentModel> {

    @Override
    public RecyclerView.LayoutManager getRvLayoutManager() {
        return new GridLayoutManager(this, 2);
    }

    @Override
    protected void requestOnIntent() {
        String[] date = EasyPreference.with(this).getString("BirthDay", "").split("/");
        request = new BiographyContentWithSimilarDatePeriodStartDayAndMonthOfYearDtoModel();
        request.MonthOfYear = Integer.parseInt(date[1]);
        request.DayOfMonth = Integer.parseInt(date[2]);
    }

    @Override
    protected void onSuccessNext(ErrorException<BiographyContentModel> response) {
        models.addAll(response.ListItems);
        Total = response.TotalRowCount;
        adapter.notifyDataSetChanged();
    }

    @Override
    protected Function<Integer, Observable<ErrorException<BiographyContentModel>>> apiService() {
        return integer -> {
            //karavi say need to paging
            return new BiographyContentService(this).getAllWithSimilarDatePeriodStartDayAndMonthOfYear(request);
        };
    }

    @Override
    public RecyclerView.Adapter createAdapter() {
        return new AdBiography(this, models);
    }

    @Override
    public void ClickSearch() {

    }
}
