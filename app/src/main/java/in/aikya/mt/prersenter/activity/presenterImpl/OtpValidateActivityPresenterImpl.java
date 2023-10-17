package in.aikya.mt.prersenter.activity.presenterImpl;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.gms.tasks.TaskExecutors;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseException;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.PhoneAuthCredential;
import com.google.firebase.auth.PhoneAuthProvider;
import com.google.gson.Gson;

import java.util.concurrent.TimeUnit;

import in.aikya.mt.app.GlobalConstants;
import in.aikya.mt.intractor.intractorImpl.OtpValidateActivityIntractor;
import in.aikya.mt.model.RequestModel.UserRegistrationModel;
import in.aikya.mt.prersenter.activity.presenter.OtpValidateActivityPresenter;
import in.aikya.mt.prersenter.base.BaseActivityPresenter;
import in.aikya.mt.view.activity.view.OtpValidateActivityView;
import in.aikya.mt.view.activity.viewImpl.DashboardActivity;
import in.aikya.mt.view.activity.viewImpl.OtpValidateActivity;

/**
 * Created by saoud_traxsmart on 24/04/20
 */
public class OtpValidateActivityPresenterImpl extends BaseActivityPresenter<OtpValidateActivityView> implements OtpValidateActivityPresenter {

    OtpValidateActivityIntractor intractor;
    //It is the verification id that will be sent to the user
    private String mVerificationId;
    //firebase auth object
    private FirebaseAuth mAuth;
    UserRegistrationModel userRegistrationModel;
    public OtpValidateActivityPresenterImpl(OtpValidateActivityView otpValidateActivity) {
       attachView(otpValidateActivity);
       intractor = new OtpValidateActivityIntractor(this);
       init();
    }

    private void init() {
        mAuth = FirebaseAuth.getInstance();
        userRegistrationModel = new Gson().fromJson(getBundle().getSerializable(GlobalConstants.USER_MODEL).toString(),UserRegistrationModel.class);
        sendVerificationCode(userRegistrationModel.getPhoneNumber());


    }

    private void sendVerificationCode(String mobile){
        PhoneAuthProvider.getInstance().verifyPhoneNumber(
                mobile,
                60,
                TimeUnit.SECONDS,
                TaskExecutors.MAIN_THREAD,
                mCallbacks);
    }
    //the callback to detect the verification status
    private PhoneAuthProvider.OnVerificationStateChangedCallbacks mCallbacks = new PhoneAuthProvider.OnVerificationStateChangedCallbacks() {
        @Override
        public void onVerificationCompleted(PhoneAuthCredential phoneAuthCredential) {

            //Getting the code sent by SMS
            String code = phoneAuthCredential.getSmsCode();

            //sometime the code is not detected automatically
            //in this case the code will be null
            //so user has to manually enter the code
            if (code != null) {
                getView().setCode(code);
                //verifying the code
                verifyVerificationCode(code);
            }
        }

        @Override
        public void onVerificationFailed(FirebaseException e) {
             showToast(e.getMessage());
        }

        @Override
        public void onCodeSent(String s, PhoneAuthProvider.ForceResendingToken forceResendingToken) {
            super.onCodeSent(s, forceResendingToken);
            //storing the verification id that is sent to the user
            mVerificationId = s;
        }
    };

    public void verifyVerificationCode(String code) {
        //creating the credential
        PhoneAuthCredential credential = PhoneAuthProvider.getCredential(mVerificationId, code);

        //signing the user
        signInWithPhoneAuthCredential(credential);
    }

    private void signInWithPhoneAuthCredential(PhoneAuthCredential credential) {
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(getActivity(), new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            //verification successful we will start the profile activity
                            intractor.setSharedPreference(userRegistrationModel);
                           showNextScreenByFinish(DashboardActivity.class, Bundle.EMPTY);

                        } else {
                            //verification unsuccessful.. display an error message

                            String message = "Somthing is wrong, we will fix it soon...";

                            if (task.getException() instanceof FirebaseAuthInvalidCredentialsException) {
                                message = "Invalid code entered...";
                            }
                            showToast(message);
                        }
                    }
                });
    }
    @Override
    protected void onAttached() {

    }

    @Override
    protected void showPopUp() {

    }
}
