package ntk.android.biography.activity;

import android.view.View;

import androidx.recyclerview.widget.RecyclerView;

import io.reactivex.Observable;
import java9.util.function.Function;
import ntk.android.base.activity.abstraction.AbstractionListActivity;
import ntk.android.base.dtomodel.biography.BiographyContentWithDatePeriodEndDtoModel;
import ntk.android.base.entitymodel.base.ErrorException;
import ntk.android.base.entitymodel.biography.BiographyContentModel;
import ntk.android.base.services.biography.BiographyContentService;
import ntk.android.biography.R;
import ntk.android.biography.adapter.BiographyAdapter;
import ntk.android.biography.utill.EasyPreference;

public class SameBirthDayActivity extends
        AbstractionListActivity<BiographyContentWithDatePeriodEndDtoModel, BiographyContentModel> {
    @Override
    protected void requestOnIntent() {
        request = new BiographyContentWithDatePeriodEndDtoModel();
        String Gregorian = EasyPreference.with(this).getString("BirthDay", "");
        request.SearchDateMin = Gregorian;
        request.SearchDateMax = Gregorian;
    }


    @Override
    protected void onSuccessNext(ErrorException<BiographyContentModel> response) {
        findViewById(R.id.rowProgressActSameBirthDay).setVisibility(View.GONE);
        models.addAll(response.ListItems);
        Total = response.TotalRowCount;
        adapter.notifyDataSetChanged();
    }

    @Override
    protected Function<Integer, Observable<ErrorException<BiographyContentModel>>> apiService() {
        return integer -> {
            //karavi say need to paging
            return new BiographyContentService(this).getAllGetAllWithDatePeriodEnd(request);
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
