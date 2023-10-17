package in.aikya.mt.view.activity.view;

import com.google.android.material.textfield.TextInputLayout;

import in.aikya.mt.view.base.BaseActivityView;

/**
 * Created by saoud_traxsmart on 24/04/20
 */
public interface RegistrationActivityView extends BaseActivityView {
    String getUserName();
    String getUserEmail();
    String getUserMobileNumber();
    String getUserPassword();
    void onError(String filed , String message);
}
