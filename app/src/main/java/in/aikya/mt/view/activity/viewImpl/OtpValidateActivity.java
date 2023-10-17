package in.aikya.mt.view.activity.viewImpl;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import android.content.Context;
import android.os.Bundle;
import android.view.View;

import in.aikya.mt.R;
import in.aikya.mt.databinding.ActivityVerifiyOtypBinding;
import in.aikya.mt.prersenter.activity.presenter.OtpValidateActivityPresenter;
import in.aikya.mt.prersenter.activity.presenterImpl.OtpValidateActivityPresenterImpl;
import in.aikya.mt.view.activity.view.OtpValidateActivityView;
import in.aikya.mt.view.base.BaseActivity;

public class OtpValidateActivity extends BaseActivity implements OtpValidateActivityView {

    ActivityVerifiyOtypBinding verifyOtpBinding;
    OtpValidateActivityPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        verifyOtpBinding = DataBindingUtil.setContentView(this,R.layout.activity_verifiy_otyp);
        presenter = new OtpValidateActivityPresenterImpl(this);
        verifyOtpBinding.proceed.setOnClickListener(this);
        verifyOtpBinding.backButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        if (v.getId() == R.id.proceed){
            presenter.verifyVerificationCode(verifyOtpBinding.otpEditText.getText().toString());
        }else if (v.getId() == R.id.back_button){
            onBackPressed();
        }
    }

    @Override
    public Context getContext() {
        return this;
    }

    @Override
    public void setCode(String code) {
        verifyOtpBinding.otpEditText.setText(code);
    }
}
