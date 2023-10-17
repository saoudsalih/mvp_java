package in.aikya.mt.view.activity.viewImpl;


import androidx.databinding.DataBindingUtil;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import in.aikya.mt.R;
import in.aikya.mt.databinding.ActivityProfileBinding;
import in.aikya.mt.model.RequestModel.UserRegistrationModel;
import in.aikya.mt.prersenter.activity.presenter.ProfileActivityPresenter;
import in.aikya.mt.prersenter.activity.presenterImpl.ProfileActivityPresenterImpl;
import in.aikya.mt.view.activity.view.ProfileActivityView;
import in.aikya.mt.view.base.BaseActivity;

public class ProfileActivity extends BaseActivity implements ProfileActivityView {

    ProfileActivityPresenter presenter;
    ActivityProfileBinding profileBinding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        profileBinding = DataBindingUtil.setContentView(this,R.layout.activity_profile);
        presenter = new ProfileActivityPresenterImpl(this);

        init();
    }

    private void init() {
        profileBinding.backButton.setOnClickListener(this);
        profileBinding.submit.setOnClickListener(this);
        profileBinding.editDetails.setOnClickListener(this);

        UserRegistrationModel userRegistrationModel = presenter.userModel();
        profileBinding.name.setText(userRegistrationModel.getName());
        profileBinding.email.setText(userRegistrationModel.getEmail());

        if (userRegistrationModel.getAttributes() != null){
           profileBinding.address.setText(userRegistrationModel.getAttributes().getAddress());
           profileBinding.desc.setText(userRegistrationModel.getAttributes().getComments());
        }


    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.back_button){
            onBackPressed();
        }else if (v.getId() == R.id.submit){
            presenter.updateProfile();
        }
    }


    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public String getUserName() {
        return profileBinding.name.getText().toString();
    }

    @Override
    public String getUserEmail() {
        return profileBinding.email.getText().toString();
    }

    @Override
    public String getUserAddress() {
        return profileBinding.address.getText().toString();
    }

    @Override
    public String getUserComments() {
        return profileBinding.desc.getText().toString();
    }
}
