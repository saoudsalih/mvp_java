package in.aikya.mt.prersenter.activity.presenterImpl;

import android.os.Bundle;

import in.aikya.mt.intractor.intractorImpl.DashboardActivityIntractor;
import in.aikya.mt.prersenter.activity.presenter.DashboardActivityPresenter;
import in.aikya.mt.prersenter.base.BaseActivityPresenter;
import in.aikya.mt.view.activity.view.DashboardActivityView;
import in.aikya.mt.view.activity.viewImpl.LoginActivity;
import in.aikya.mt.view.activity.viewImpl.ProfileActivity;

/**
 * Created by saoud_traxsmart on 24/04/20
 */
public class DashboardActivityPresenterImpl extends BaseActivityPresenter<DashboardActivityView> implements DashboardActivityPresenter {

    DashboardActivityIntractor intractor;
    public DashboardActivityPresenterImpl(DashboardActivityView dashboardActivityView) {
        attachView(dashboardActivityView);
        intractor = new DashboardActivityIntractor(this);
    }

    @Override
    protected void onAttached() {

    }

    @Override
    protected void showPopUp() {

    }

    @Override
    public void onClickViewProfile() {
        showAnimatedNextScreen(ProfileActivity.class, Bundle.EMPTY,0,0);
    }

    @Override
    public void logout() {
        intractor.clearSharedPreference();
        showNextScreenByFinish(LoginActivity.class,Bundle.EMPTY);
    }
}
