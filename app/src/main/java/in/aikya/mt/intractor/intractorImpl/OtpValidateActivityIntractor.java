package in.aikya.mt.intractor.intractorImpl;

import com.google.gson.Gson;

import in.aikya.mt.app.GlobalConstants;
import in.aikya.mt.intractor.base.BaseActivityInteractor;
import in.aikya.mt.model.RequestModel.UserRegistrationModel;
import in.aikya.mt.prersenter.activity.presenterImpl.OtpValidateActivityPresenterImpl;

/**
 * Created by saoud_traxsmart on 24/04/20
 */
public class OtpValidateActivityIntractor extends BaseActivityInteractor<OtpValidateActivityPresenterImpl> {
    public OtpValidateActivityIntractor(OtpValidateActivityPresenterImpl presenter) {
        super(presenter);
    }

    public void setSharedPreference(UserRegistrationModel userRegistrationModel){
        getServices().getPersistenceServices().saveString(GlobalConstants.USER_MODEL,new Gson().toJson(userRegistrationModel));
        setUserLoggedIn();
    }
    public void setUserLoggedIn(){
        getServices().getPersistenceServices().isLoggedIn(GlobalConstants.LOGGED_IN,true);
    }
    @Override
    protected void onInternetUnavailable() {

    }
}
