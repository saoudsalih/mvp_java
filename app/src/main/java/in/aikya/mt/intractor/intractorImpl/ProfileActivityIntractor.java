package in.aikya.mt.intractor.intractorImpl;

import android.database.sqlite.SQLiteDatabase;

import com.google.gson.Gson;

import in.aikya.mt.app.BaseApplication;
import in.aikya.mt.app.GlobalConstants;
import in.aikya.mt.intractor.base.BaseActivityInteractor;
import in.aikya.mt.model.RequestModel.UserRegistrationModel;
import in.aikya.mt.prersenter.activity.presenterImpl.ProfileActivityPresenterImpl;

import static android.content.Context.MODE_PRIVATE;

/**
 * Created by saoud_traxsmart on 24/04/20
 */
public class ProfileActivityIntractor extends BaseActivityInteractor<ProfileActivityPresenterImpl> {

    SQLiteDatabase mDatabase;
    public ProfileActivityIntractor(ProfileActivityPresenterImpl presenter) {
        super(presenter);
        mDatabase = BaseApplication.getInstance().openOrCreateDatabase(GlobalConstants.DATABASE_NAME, MODE_PRIVATE, null);
    }

    public void updateUserDetails(UserRegistrationModel userRegistrationModel){
        String sql = "UPDATE userInfo \n" +
                "SET name = ?, \n" +
                "mobile = ?, \n" +
                "email = ?, \n" +
                "password = ?, \n" +
                "attributes = ? \n" +
                "WHERE id = ?;\n";

        mDatabase.execSQL(sql, new String[]{userRegistrationModel.getName(), userRegistrationModel.getPhoneNumber(),userRegistrationModel.getEmail(),userRegistrationModel.getPassword(),new Gson().toJson(userRegistrationModel.getAttributes()), String.valueOf(userRegistrationModel.getId())});
        getServices().getPersistenceServices().saveString(GlobalConstants.USER_MODEL,new Gson().toJson(userRegistrationModel));
        getPresenter().updateSuccess();
    }
    public UserRegistrationModel getUserProfile(){
        return new Gson().fromJson(getServices().getPersistenceServices().getString(GlobalConstants.USER_MODEL),UserRegistrationModel.class);
    }

    @Override
    protected void onInternetUnavailable() {

    }
}
