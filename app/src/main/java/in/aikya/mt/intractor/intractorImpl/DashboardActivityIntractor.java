package in.aikya.mt.intractor.intractorImpl;

import in.aikya.mt.intractor.base.BaseActivityInteractor;
import in.aikya.mt.prersenter.activity.presenterImpl.DashboardActivityPresenterImpl;

/**
 * Created by saoud_traxsmart on 24/04/20
 */
public class DashboardActivityIntractor extends BaseActivityInteractor<DashboardActivityPresenterImpl> {

    public DashboardActivityIntractor(DashboardActivityPresenterImpl presenter) {
        super(presenter);
    }

    @Override
    protected void onInternetUnavailable() {

    }

    public void clearSharedPreference() {
        getServices().getPersistenceServices().clearAll();
    }
}
