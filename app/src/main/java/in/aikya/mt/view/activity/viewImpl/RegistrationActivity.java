package in.aikya.mt.view.activity.viewImpl;


import androidx.databinding.DataBindingUtil;

import android.app.ProgressDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;

import in.aikya.mt.R;
import in.aikya.mt.app.GlobalConstants;
import in.aikya.mt.databinding.ActivityRegisterBinding;
import in.aikya.mt.prersenter.activity.presenter.RegistrationActivityPresenter;
import in.aikya.mt.prersenter.activity.presenterImpl.RegistrationActivityPresenterImpl;
import in.aikya.mt.view.activity.view.RegistrationActivityView;
import in.aikya.mt.view.base.BaseActivity;

public class RegistrationActivity extends BaseActivity implements RegistrationActivityView {


    ActivityRegisterBinding registerBinding;
    RegistrationActivityPresenter presenter;
    ProgressDialog progressDialog;
    private static final String TAG = "RegistrationActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        registerBinding = DataBindingUtil.setContentView(this,R.layout.activity_register);
        presenter = new RegistrationActivityPresenterImpl(this);
        registerBinding.registerHere.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.register_here){
            presenter.validate();
        }
    }
    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public String getUserName() {
        return registerBinding.name.getText().toString();
    }

    @Override
    public String getUserEmail() {
        return registerBinding.mailtext.getText().toString();
    }

    @Override
    public String getUserMobileNumber() {
        return registerBinding.phonenumber.getText().toString();
    }

    @Override
    public String getUserPassword() {
        return registerBinding.password.getText().toString();
    }

    @Override
    public void onError(String filed, String message) {
        if(filed.equalsIgnoreCase(GlobalConstants.USER_NAME)){
            registerBinding.nameLayout.setErrorEnabled(true);
            registerBinding.nameLayout.setError(getResources().getString(R.string.field_required));
        }else if(filed.equalsIgnoreCase(GlobalConstants.USER_EMAIL)){
            registerBinding.mailLayout.setErrorEnabled(true);
            registerBinding.mailLayout.setError(getResources().getString(R.string.field_required));
        }else if(filed.equalsIgnoreCase(GlobalConstants.USER_MOBILE)){
            registerBinding.mobileLayout.setErrorEnabled(true);
            registerBinding.mobileLayout.setError(getResources().getString(R.string.field_required));
        }else{
            registerBinding.passwordLayout.setErrorEnabled(true);
            registerBinding.passwordLayout.setError(getResources().getString(R.string.field_required));
        }
    }
}
