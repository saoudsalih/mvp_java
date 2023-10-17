package in.aikya.mt.prersenter.activity.presenterImpl;

import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.util.Log;

import com.google.gson.Gson;

import java.util.HashMap;

import in.aikya.mt.R;
import in.aikya.mt.app.GlobalConstants;
import in.aikya.mt.intractor.intractorImpl.RegistrationActivityIntractor;
import in.aikya.mt.model.RequestModel.UserRegistrationModel;
import in.aikya.mt.prersenter.activity.presenter.RegistrationActivityPresenter;
import in.aikya.mt.prersenter.base.BaseActivityPresenter;
import in.aikya.mt.view.activity.view.RegistrationActivityView;
import in.aikya.mt.view.activity.viewImpl.OtpValidateActivity;

/**
 * Created by saoud_traxsmart on 24/04/20
 */
public class RegistrationActivityPresenterImpl extends BaseActivityPresenter<RegistrationActivityView> implements RegistrationActivityPresenter {

    private RegistrationActivityIntractor intractor;
    public RegistrationActivityPresenterImpl(RegistrationActivityView registrationActivityView) {
        attachView(registrationActivityView);
        intractor = new RegistrationActivityIntractor(this);
    }



    @Override
    protected void onAttached() {
    }

    @Override
    protected void showPopUp() {

    }

    @Override
    public void validate() {

        String name = getView().getUserName();
        String mail = getView().getUserEmail();
        String phone = getView().getUserMobileNumber();
        String password = getView().getUserPassword();

        if(name.equalsIgnoreCase("")){
            getView().onError(GlobalConstants.USER_NAME,getContext().getResources().getString(R.string.field_required));
        }else if(mail.equalsIgnoreCase("")){
            getView().onError(GlobalConstants.USER_EMAIL,getContext().getResources().getString(R.string.field_required));
        }else if(phone.equalsIgnoreCase("")){
            getView().onError(GlobalConstants.USER_MOBILE,getContext().getResources().getString(R.string.field_required));
        }else if (phone.length()<8) {
            showToast("Enter valid number");
        }else if (password.equalsIgnoreCase("")){
            getView().onError(GlobalConstants.USER_PASSWORD,getContext().getResources().getString(R.string.field_required));
        }else if (password.length()<6){
           showToast("Minimum 6 characters required");
        }else{

            UserRegistrationModel registrationModel = new UserRegistrationModel();
            registrationModel.setName(name);
            registrationModel.setEmail(mail);
            if (phone.contains("+91")){
                registrationModel.setPhoneNumber(phone);
            }else{
                registrationModel.setPhoneNumber("+91"+phone);
            }
            registrationModel.setPassword(password);
            intractor.addUser(registrationModel);

        }
    }

    @Override
    public void addUserSuccessfully(UserRegistrationModel registrationModel) {
        Bundle bundle = new Bundle();
        bundle.putSerializable(GlobalConstants.USER_MODEL,new Gson().toJson(registrationModel));
        showNextScreenByFinish(OtpValidateActivity.class,bundle);
    }
}
