package in.aikya.mt.prersenter.activity.presenter;

import in.aikya.mt.model.RequestModel.UserRegistrationModel;

/**
 * Created by saoud_traxsmart on 24/04/20
 */
public interface ProfileActivityPresenter {
    UserRegistrationModel userModel();
    void updateProfile();
    void updateSuccess();
}
