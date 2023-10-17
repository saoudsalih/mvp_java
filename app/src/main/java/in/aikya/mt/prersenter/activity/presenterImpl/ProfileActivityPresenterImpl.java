package in.aikya.mt.prersenter.activity.presenterImpl;


import in.aikya.mt.intractor.intractorImpl.ProfileActivityIntractor;
import in.aikya.mt.model.RequestModel.Attributes;
import in.aikya.mt.model.RequestModel.UserRegistrationModel;
import in.aikya.mt.prersenter.activity.presenter.ProfileActivityPresenter;
import in.aikya.mt.prersenter.base.BaseActivityPresenter;
import in.aikya.mt.view.activity.view.ProfileActivityView;
import in.aikya.mt.view.activity.viewImpl.ProfileActivity;

/**
 * Created by saoud_traxsmart on 24/04/20
 */
public class ProfileActivityPresenterImpl extends BaseActivityPresenter<ProfileActivityView> implements ProfileActivityPresenter {

    ProfileActivityIntractor intractor;
    UserRegistrationModel userRegistrationModel;
    public ProfileActivityPresenterImpl(ProfileActivityView profileActivity) {
        attachView(profileActivity);
        intractor = new ProfileActivityIntractor(this);
        init();
    }

    private void init() {
        userRegistrationModel = intractor.getUserProfile();
    }

    @Override
    protected void onAttached() {
    }

    @Override
    protected void showPopUp() {

    }

    @Override
    public UserRegistrationModel userModel() {
        return userRegistrationModel;
    }

    @Override
    public void updateProfile() {
        userRegistrationModel.setName(getView().getUserName());
        userRegistrationModel.setEmail(getView().getUserEmail());
        Attributes attributes = new Attributes();
        attributes.setAddress(getView().getUserAddress());
        attributes.setComments(getView().getUserComments());
        userRegistrationModel.setAttributes(attributes);
        intractor.updateUserDetails(userRegistrationModel);
    }

    @Override
    public void updateSuccess() {
        showToast("user details updated");
        getActivity().onBackPressed();
    }
}
