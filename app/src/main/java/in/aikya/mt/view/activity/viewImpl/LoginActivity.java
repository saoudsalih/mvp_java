package in.aikya.mt.view.activity.viewImpl;


import androidx.databinding.DataBindingUtil;
import android.content.Context;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.View;

import in.aikya.mt.R;
import in.aikya.mt.databinding.ActivityLoginBinding;
import in.aikya.mt.prersenter.activity.presenter.LoginActivityPresenter;
import in.aikya.mt.prersenter.activity.presenterImpl.LoginActivityPresenterImpl;
import in.aikya.mt.view.activity.view.LoginActivityView;
import in.aikya.mt.view.base.BaseActivity;

public class LoginActivity extends BaseActivity implements LoginActivityView {


    LoginActivityPresenter presenter;
    ActivityLoginBinding loginBinding;
    boolean isPasswordView = true;

    private static final String TAG = "LoginActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loginBinding = DataBindingUtil.setContentView(this,R.layout.activity_login);
        presenter = new LoginActivityPresenterImpl(this);
        loginBinding.emailSignInButton.setOnClickListener(this);
        loginBinding.forgotPassword.setOnClickListener(this);
        loginBinding.signUpClick.setOnClickListener(this);
        loginBinding.passwordEye.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()){
            case R.id.email_sign_in_button :
                presenter.validateCredentials(getUserName(),getUserPassword());
                break;
            case R.id.sign_up_click:
                presenter.signUpUser();
                break;
            case R.id.passwordEye:
                passwordVisibilityOption();
                break;

        }
    }

    private void passwordVisibilityOption(){
        if (isPasswordView) {
            loginBinding.password.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
            loginBinding.passwordEye.setImageResource(R.drawable.ic_visibility_off);
        } else {
            loginBinding.password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
            loginBinding.passwordEye.setImageResource(R.drawable.ic_eye);
        }
        loginBinding.password.setSelection(loginBinding.password.length());
        isPasswordView = !isPasswordView;
    }

    @Override
    public String getUserName() {
        return loginBinding.email.getText().toString();
    }

    @Override
    public String getUserPassword() {
        return loginBinding.password.getText().toString();
    }

    @Override
    public void onError(String field, String message) {
        if (field.equalsIgnoreCase("userName")){
            loginBinding.email.setError(message);
        }else{
            loginBinding.password.setError(message);
        }
    }

   

    @Override
    public Context getContext() {
        return this;
    }
}
