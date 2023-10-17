package in.aikya.mt.prersenter.activity.presenterImpl;

import android.os.Bundle;

import com.google.gson.Gson;

import java.util.HashMap;

import in.aikya.mt.R;
import in.aikya.mt.app.GlobalConstants;
import in.aikya.mt.intractor.intractorImpl.LoginActivityIntractor;
import in.aikya.mt.model.RequestModel.UserRegistrationModel;
import in.aikya.mt.prersenter.activity.presenter.LoginActivityPresenter;
import in.aikya.mt.prersenter.base.BaseActivityPresenter;
import in.aikya.mt.view.activity.view.LoginActivityView;
import in.aikya.mt.view.activity.viewImpl.DashboardActivity;
import in.aikya.mt.view.activity.viewImpl.OtpValidateActivity;
import in.aikya.mt.view.activity.viewImpl.RegistrationActivity;

/**
 * Created by saoud_traxsmart on 24/04/20
 */
public class LoginActivityPresenterImpl extends BaseActivityPresenter<LoginActivityView> implements LoginActivityPresenter {

    LoginActivityIntractor intractor;
    public LoginActivityPresenterImpl(LoginActivityView loginActivityView) {
        attachView(loginActivityView);
        intractor = new LoginActivityIntractor(this);
        init();
    }

    private void init() {
        if (intractor.isUserLoggedIn()){
            showNextScreenByFinish(DashboardActivity.class,Bundle.EMPTY);
        }
    }

    @Override
    public void validateCredentials(String userName, String password) {

        if (userName.equalsIgnoreCase("")){
            getView().onError("userName",getContext().getResources().getString(R.string.field_required));
        } else if(password.equalsIgnoreCase("")){
            getView().onError("password",getContext().getResources().getString(R.string.field_required));
        }else{
            UserRegistrationModel userRegistrationModel = intractor.getUserDetails(userName,password);
            if (userRegistrationModel != null){
                Bundle bundle = new Bundle();
                bundle.putSerializable(GlobalConstants.USER_MODEL,new Gson().toJson(userRegistrationModel));
                showNextScreenByFinish(OtpValidateActivity.class,bundle);
            }else{
                showToast("The email or password is incorrect");
            }
        }
    }

    @Override
    public void signUpUser() {
        showNextScreen(RegistrationActivity.class, Bundle.EMPTY);
    }

    @Override
    public void redirectPage() {
        showNextScreenByFinish(DashboardActivity.class,Bundle.EMPTY);
    }

    @Override
    public void message(String message) {
        showToast(message);
    }


    @Override
    protected void onAttached() { }

    @Override
    protected void showPopUp() { }
}
