package in.aikya.mt.prersenter.activity.presenter;

import in.aikya.mt.model.RequestModel.UserRegistrationModel;

/**
 * Created by saoud_traxsmart on 24/04/20
 */
public interface RegistrationActivityPresenter {
    void validate();
    void addUserSuccessfully(UserRegistrationModel registrationModel);
}
